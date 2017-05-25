package root.assign2.myCountries;


import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import root.assign2.MainList;
import root.assign2.R;
import root.assign2.myCountries.utils.SharedPreference;
import root.assign2.myCountries.preferences.SimplePreferenceActivity;

/**
 * Created by grace on 2016-09-19.
 * Continued in 2016-12-17
 */

// it needs to implement the interface class, that is why we write every method
// in the CalendarProviderClient interface
public class MyCountries extends ListActivity
        implements CalendarProviderClient {

    private ListView listView;
    private Activity main_activity;
    private int selectedId;

    private CountryCursorAdapter myCursor;
    CalendarUtils clrUtil = new CalendarUtils();

    private static final int UPDATE_MENU_ITEM = Menu.FIRST;
    private static final int DELETE_MENU_ITEM = UPDATE_MENU_ITEM + 1;
    private final int INSERT_REQUEST = 0;
    private final int UPDATE_REQUEST = 1;

    private String preferenceSortOrder = "";
    private SharedPreference sharedPreference;
    Activity context = this; // for the shared preferences

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.action_bar);

        main_activity = this; // for the menubar intents
        sharedPreference = new SharedPreference();

        // Since a Cursor is a "list" of rows, a good way to display the contents of a Cursor is
        // to link it to a ListView via a SimpleCursorAdapter.
        listView = (ListView)findViewById(R.id.listView);
        // it didn't work with listView as it was null, why???
        registerForContextMenu(getListView());

        // An application accesses the data from a content provider with a ContentResolver client object.
        // it provides CRUD functions
        ContentResolver cr = getContentResolver();

        // check if MyCountries Calendar exists, if not (-1) then create one
        // otherwise, display the existing calendars on the device
        if (getMyCountriesCalendarId() == -1) {
            System.out.println("I the calendar needs to be created!!");
            createCal();
        } else {
            System.out.println("I the calendar is already created!!");
            preferenceSortOrder = sharedPreference.getValue(context);
            System.out.println("pref order is: " + preferenceSortOrder);
        }

        // ---- CONSTRUCTING THE QUERY ----

        // Does a query against the table and returns a Cursor object
            /*
            * Cursor is the class which represents a 2 dimensional table of any database. When you try to retrieve
            * some data using SELECT statement, then the database will first create
            * a CURSOR object and return its reference to you.
            * The pointer of this returned reference is pointing to the 0th location which is otherwise
            * called as before first location of the Cursor,  so when you want to retrive data from the cursor,
            * you have to first move to the first record so we have to use moveToFirst
            * */

        //Submit the query and get a Cursor object back.
        Cursor mEventCursor = cr.query(
                EVENTS_LIST_URI, // Content URI used to access the event(s)
                EVENTS_LIST_PROJECTION, // An array of columns to return for each row
                // selection + args retrieves the rows
                null, // Selection. in SQL: WHERE col = value. Can be null
                // An integer array of view IDs in the row layout
                null,  // Args. Replaces the "?" sign in the selection clause, "?" is the value entered. Can be empty
                null); // CalendarContract.Calendars._ID + " ASC"          // The sort order for the returned rows

        // -------- DISPLAY QUERY RESULTS ---------
        myCursor = new CountryCursorAdapter(this, mEventCursor);
        setListAdapter(myCursor);

        // Prepare the loader.  Either re-connect with an existing one, or start a new one.
        getLoaderManager().initLoader(LOADER_MANAGER_ID, null, this);
    }

    // ------------ CREATE MYCOUNTRIES CALENDAR ---------------
    public Uri createCal(){
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Calendars.ACCOUNT_NAME, ACCOUNT_TITLE);
        values.put(CalendarContract.Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL); // cal will not be synced
        values.put(CalendarContract.Calendars.NAME, CALENDAR_TITLE); //The name of the calendar.
        values.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, CALENDAR_TITLE); //The name of this calendar that is displayed to the user.
        values.put(CalendarContract.Calendars.CALENDAR_COLOR, Color.RED);
        values.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CAL_ACCESS_OWNER);
        values.put(CalendarContract.Calendars.OWNER_ACCOUNT, "szdave@msn.com");

        values.put(CalendarContract.Calendars.CALENDAR_TIME_ZONE, "Europe/Stockholm");
        values.put(CalendarContract.Calendars.SYNC_EVENTS, 1);

        Uri.Builder builder = CalendarContract.Calendars.CONTENT_URI.buildUpon();
        builder.appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, ACCOUNT_TITLE);
        builder.appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL);
        builder.appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true");

        Uri uri = getContentResolver().insert(builder.build(), values);

        return uri;
    }

    // ------------ ACTIONBAR ---------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // An XML based menu specification.
        // See res/menu/action_menu.xml for details
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.shared_prefs:
                startActivity(new Intent(this, SimplePreferenceActivity.class));
                return true;
            case R.id.sort_menu_item:

                return true;
            case R.id.add_menu_item:

                /* Start new Activity that returns a result */
                Intent intent = new Intent(this, Read_Country.class);
                main_activity.startActivityForResult(intent, INSERT_REQUEST);
                return true;

            case android.R.id.home:  // Predefined icon ID

                // app icon in action bar clicked ==>  go home
                Intent intent2 = new Intent(this, MainList.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);
                return true;

            //case android.R.id.sort_menu_item:

              //  return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // ------------ CONTEXT MENUS ---------------
    // when you long-click on an item in ListView
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select The Action");
        menu.add(0, UPDATE_MENU_ITEM, 0, "Update");//groupId, itemId, order, title
        menu.add(0, DELETE_MENU_ITEM, 1, "Delete");

        // get the id of the clicked item, which is the same as the eventId!!!
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        selectedId = (int)info.id;
        System.out.println("selected id is: "+ selectedId);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case UPDATE_MENU_ITEM:
                Toast.makeText(getApplicationContext(),"item updated", Toast.LENGTH_LONG).show();

                    /* Start new Activity that returns a result for updating */
                    Intent intent = new Intent(this, Read_Country.class);
                    main_activity.startActivityForResult(intent, UPDATE_REQUEST);

                break;
            case DELETE_MENU_ITEM:
                Toast.makeText(getApplicationContext(),"item deleted",Toast.LENGTH_LONG).show();
                deleteEvent(selectedId);
                break;
        }
        return super.onContextItemSelected(item);
    }

    // ------------ GET CALENDAR ID ---------------
    @Override
    public long getMyCountriesCalendarId() {

        String[] projection = new String[]{
                CalendarContract.Calendars._ID
        };

        String selection =
                CalendarContract.Calendars.ACCOUNT_NAME +
                        " = ? AND " +
                        CalendarContract.Calendars.ACCOUNT_TYPE +
                        " = ? ";
        // use the same values as above:
        String[] selArgs =
                new String[]{
                        ACCOUNT_TITLE,
                        CalendarContract.ACCOUNT_TYPE_LOCAL
                };

                Cursor cursor =
                getContentResolver().query(
                                CalendarContract.Calendars.CONTENT_URI,
                                projection,
                                selection,
                                selArgs,
                                null);
        if (cursor.moveToFirst()) {
            return cursor.getLong(0);
        }

        /*
        * To iterate through a new Cursor you only have to check the return value from moveToNext().
        * When the Cursor's index is set to -1 (like when it's new), moveToNext() will move to index 0
        * and return true only if there is valid data, otherwise if the are no rows moveToNext() returns false.
        * */
        return -1;
    }

    // ------------ CRUD ---------------
    @Override
    public void addNewEvent(int year, String country) {

        ContentResolver cr = getContentResolver();
        // Defines an object to contain the new values to insert
        ContentValues values = new ContentValues();
        /*
         * Sets the values of each column and inserts the value. The arguments to the "put"
         * method are "column name" and "value"
         */
        // CalendarUtils is used to convert the year (int) to milliseconds
        values.put(CalendarContract.Events.DTSTART, clrUtil.getEventStart(year));
        values.put(CalendarContract.Events.DTEND, clrUtil.getEventEnd(year));
        values.put(CalendarContract.Events.TITLE, country);
        values.put(CalendarContract.Events.DESCRIPTION, "Description");
        values.put(CalendarContract.Events.CALENDAR_ID, getMyCountriesCalendarId());
        values.put(CalendarContract.Events.EVENT_TIMEZONE, "Sweden");
        values.put(CalendarContract.Events.EVENT_LOCATION, country);

        Uri eventUri = cr.insert(EVENTS_LIST_URI, values);

        // get the event ID that is the last element in the Uri
        long eventID = Long.parseLong(eventUri.getLastPathSegment());
    }

    @Override
    public void updateEvent(int eventId, int year, String country) {
        ContentValues values = new ContentValues();
        Uri updateUri = null;
        values.put(CalendarContract.Events.DTSTART, clrUtil.getEventStart(year));
        values.put(CalendarContract.Events.DTEND, clrUtil.getEventEnd(year));
        values.put(CalendarContract.Events.TITLE, country);

        // one way to do it
        updateUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventId);
        int rows = getContentResolver().update(updateUri, values, null, null);
        Log.i("DEBUG TAG", "Rows updated: " + rows);
    }

    @Override
    public void deleteEvent(int eventId) {

        ContentResolver cr = getContentResolver();
        ContentValues values = new ContentValues();
        Uri deleteUri = null;
        deleteUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventId);
        int deletedRows = getContentResolver().delete(deleteUri, null, null);
        Log.i("DEBUG_TAG", "Rows deleted: " + deletedRows);
    }

    // ------------ LOADERS ---------------
    /**
     * You must actually create an instance of CursorLoader that would query the Calendar Provider
     * here.
     * */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_MANAGER_ID:

                // when no sorting was done the preferenceSortOrder is null, then we just want to load the default
                // loader
                if (preferenceSortOrder == null)
                    preferenceSortOrder = "";

                // when the application opens up, the loader should sort the listItems as they were sorted
                // when the user exited the app
                CursorLoader crLoader;
                switch (preferenceSortOrder) {
                    case "yearAscending":
                        crLoader =  new CursorLoader(this, EVENTS_LIST_URI, EVENTS_LIST_PROJECTION,
                                null, null, CalendarContract.Events.DTSTART+" ASC");
                        break;
                    case "yearDescending" :
                        crLoader =  new CursorLoader(this, EVENTS_LIST_URI, EVENTS_LIST_PROJECTION,
                                null, null, CalendarContract.Events.DTSTART+" DESC");
                        break;
                    case "countryAscending" :
                        crLoader =  new CursorLoader(this, EVENTS_LIST_URI, EVENTS_LIST_PROJECTION,
                                null, null, CalendarContract.Events.TITLE+" ASC");
                        break;
                    case "countryDescending" :
                        crLoader =  new CursorLoader(this, EVENTS_LIST_URI, EVENTS_LIST_PROJECTION,
                                null, null, CalendarContract.Events.TITLE+" DESC");
                        break;
                    default:
                       crLoader = new CursorLoader(this, EVENTS_LIST_URI, EVENTS_LIST_PROJECTION,
                                null, null, null);
                }
                // either (this, CALENDARS_LIST_URI, CALENDARS_LIST_PROJECTION) to display calendars or
                // display events as shown below
                return crLoader;
                //return new CursorLoader(this, EVENTS_LIST_URI, EVENTS_LIST_PROJECTION,
                //        null, null, null);
            default:
                // An invalid id was passed
                return null;
        }
    }
    /**
     * The Loader Manager callback that signals the end of the load.
     * You must use the cursor available as the second argument here to actually access the data
     * (the simplest way is to call yourSimpleCursorAdapter.swapCursor(data) )
     * swapCusor opens and closes the cursor
     * */
    // handle incoming data
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case LOADER_MANAGER_ID:
                myCursor.swapCursor(data);
                break;
        }
    }
    /**
     * The Loader Manager callback that signals the reset of the loader.
     * You must remove the reference to the old data here
     * (the simplest way is to call yourSimpleCursorAdapter.swapCursor(null) ).
     * */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case LOADER_MANAGER_ID:
                myCursor.swapCursor(null);
                break;
        }
    }

    // ------------ CUSTOM CURSOR ADAPTER ---------------
    // https://guides.codepath.com/android/Populating-a-ListView-with-a-CursorAdapter
    public class CountryCursorAdapter extends CursorAdapter {
        public CountryCursorAdapter(Context context, Cursor cursor) {
            super(context, cursor, 0);
        }

        // The newView method is used to inflate a new view and return it,
        // you don't bind any data to the view at this point.
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.list_item_my_countries, parent, false);
        }

        // The bindView method is used to bind all data to a given view
        // such as setting the text on a TextView.
        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            // Find fields to populate in inflated template
            TextView tvYear = (TextView) view.findViewById(R.id.tvYear);
            TextView tvCountry = (TextView) view.findViewById(R.id.tvCountry);
            // Extract properties from cursor
            String year1 = cursor.getString(cursor.getColumnIndexOrThrow(CalendarContract.Events.DTSTART));
            String title1 = cursor.getString(cursor.getColumnIndexOrThrow(CalendarContract.Events.TITLE));

            // Populate fields with extracted properties
            // convert String year1 to Long and then back to String for SetText
            tvYear.setText(clrUtil.getEventYear(Long.valueOf(year1).longValue())+"");
            tvCountry.setText(String.valueOf(title1));
        }
    }

    // ------------ GETTING INTENT RESULTS FROM READCOUTNRY CLASS ---------------
    /** Called when the activity receives a results. */
    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent result) {

        // requestCode is set when sending the intent in startActivityForResult
        // either in update or in insert
        if (requestCode == INSERT_REQUEST) {
            // resultCode is gotten back from Read_country.java class reply intent
            if (resultCode == RESULT_OK) {
                String country = result.getStringExtra("countryResult1");
                int year = result.getIntExtra("yearResult1", 0);

                addNewEvent(year, country);
                getLoaderManager().restartLoader(LOADER_MANAGER_ID, null, this);
            }
        } else if (requestCode == UPDATE_REQUEST) {
            if (resultCode == RESULT_OK) {
                String country = result.getStringExtra("countryResult1");
                int year = result.getIntExtra("yearResult1", 0);

                updateEvent(selectedId, year, country);
                getLoaderManager().restartLoader(LOADER_MANAGER_ID, null, this);
            }
        }
    }

    // ------------ SORTING ---------------
    // these are selected in the xml layout: action_menu.xml

    // Sorting the year in ascending order
    public void yearAscSort(MenuItem item) {
        Toast.makeText(this, "year in ascending order", Toast.LENGTH_LONG).show();
        ContentResolver cr = getContentResolver();

        Cursor sortAscCursor = cr.query(
                EVENTS_LIST_URI,
                EVENTS_LIST_PROJECTION,
                null,
                null,
                CalendarContract.Events.DTSTART+" ASC");
        myCursor = new CountryCursorAdapter(this, sortAscCursor);
        setListAdapter(myCursor);

        // I need to save the sorting order for the shared Preferences, when the user exits the application and
        // comes back
        preferenceSortOrder = "yearAscending";
        sharedPreference.save(context, preferenceSortOrder);
    }

    // Sorting the year in descending order
    public void yearDescSort(MenuItem item) {
        Toast.makeText(this, "year in descending order", Toast.LENGTH_LONG).show();
        ContentResolver cr = getContentResolver();

        Cursor sortDescCursor = cr.query(
                EVENTS_LIST_URI,
                EVENTS_LIST_PROJECTION,
                null,
                null,
                CalendarContract.Events.DTSTART+" DESC");
        myCursor = new CountryCursorAdapter(this, sortDescCursor);
        setListAdapter(myCursor);

        preferenceSortOrder = "yearDescending";
        sharedPreference.save(context, preferenceSortOrder);
    }

    // Sorting the countries in ascending order
    public void countryAscSort(MenuItem item) {
        Toast.makeText(this, "country in ascending order", Toast.LENGTH_LONG).show();
        ContentResolver cr = getContentResolver();

        Cursor sortAscCursor = cr.query(
                EVENTS_LIST_URI,
                EVENTS_LIST_PROJECTION,
                null,
                null,
                CalendarContract.Events.TITLE+" ASC");
        myCursor = new CountryCursorAdapter(this, sortAscCursor);
        setListAdapter(myCursor);

        preferenceSortOrder = "countryAscending";
        sharedPreference.save(context, preferenceSortOrder);
    }

    // Sorting the countries in descending order
    public void countryDescSort(MenuItem item) {
        Toast.makeText(this, "country in descending order", Toast.LENGTH_LONG).show();
        ContentResolver cr = getContentResolver();

        Cursor sortDescCursor = cr.query(
                EVENTS_LIST_URI,
                EVENTS_LIST_PROJECTION,
                null,
                null,
                CalendarContract.Events.TITLE+" DESC");
        myCursor = new CountryCursorAdapter(this, sortDescCursor);
        setListAdapter(myCursor);

        preferenceSortOrder = "countryDescending";
        sharedPreference.save(context, preferenceSortOrder);
    }
}
