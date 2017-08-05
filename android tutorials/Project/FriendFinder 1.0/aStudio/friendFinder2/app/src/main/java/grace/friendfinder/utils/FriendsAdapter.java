package grace.friendfinder.utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import grace.friendfinder.R;
import grace.friendfinder.domain.User;

/**
 * Created by grace on 01/08/17.
 *
 * Reference: https://coderwall.com/p/zpwrsg/add-search-function-to-list-view-in-android, 2017.08.01.
 */

public class FriendsAdapter extends BaseAdapter implements Filterable {
    private String TAG = "FriendsAdapter";
    private Context context;
    private ArrayList<User> userList;
    private ArrayList<User> filteredUserList;
    private UserFilter userFilter;

    public FriendsAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.userList = users;
        this.filteredUserList =users;

        getFilter();
    }

    void searchNote() {
        // TODO count the amount of interests that matches current user with the one the search gives
        // todo - implement an advanced search?
        Log.d(TAG, "getting results");
    }

    /**
     * Get size of user list
     * @return userList size
     */
    @Override
    public int getCount() {
        return filteredUserList.size();
    }

    /**
     * Get specific item from user list
     * @param i item index
     * @return list item
     */
    @Override
    public Object getItem(int i) {
        return filteredUserList.get(i);
    }

    /**
     * Get user list item id
     * @param i item index
     * @return current item id
     */
    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        final ViewHolder holder;
        final User user = (User) getItem(position);

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();

            holder.nameLine = (TextView) view.findViewById(R.id.nameId);
            holder.genderLine = (TextView) view.findViewById(R.id.genderId);
            holder.moviesLine = (TextView) view.findViewById(R.id.moviesGenresId);
            holder.musicLine = (TextView) view.findViewById(R.id.musicGenresId);

            view.setTag(holder);
        } else {
            // get view holder back
            holder = (ViewHolder) view.getTag();
        }

        holder.nameLine.setText("Name: " + user.getName());
        holder.genderLine.setText("Gender: " + user.getGender());

        // transform the ArrayList to Array to use Array.toString() with SetText,
        // which prints nicer than append()
        int tempArray0Size = user.getMovieGenres().size();
        String[] tempArray0 = new String[tempArray0Size];
        for (int i = 0; i < tempArray0Size; i++) {
            tempArray0[i] = user.getMovieGenres().get(i);
            holder.moviesLine.setText(Arrays.toString(tempArray0).replaceAll("\\[|\\]", ""));
        }
        int tempArray1Size = user.getMusicGenres().size();
        String[] tempArray1 = new String[tempArray1Size];
        for (int j = 0; j < tempArray1Size; j++) {
            tempArray1[j] = user.getMusicGenres().get(j);
            holder.musicLine.setText(Arrays.toString(tempArray1).replaceAll("\\[|\\]", ""));
        }

        return view;
    }

    /**
     * Keep reference to children view to avoid unnecessary calls
     */
    static class ViewHolder {
        TextView nameLine;
        TextView genderLine;
        TextView moviesLine;
        TextView musicLine;
    }
    /**
     * Get custom filter
     * @return filter
     */
    @Override
    public Filter getFilter() {
        if (userFilter == null) {
            userFilter = new UserFilter();
        }
        return userFilter;
    }

    /**
     * Custom filter for friend list
     * Filter content in friend list according to the search text
     */
    private class UserFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();

            if (constraint!=null && constraint.length()>0) {

                ArrayList<User> tempList = new ArrayList<>();

                // search content in friend list
                for (User user : userList) {
                    if (user.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(user);
                    }
                    ArrayList<String> movieGenres = user.getMovieGenres();
                    for (int i = 0; i < movieGenres.size(); i++) {
                        if (user.getMovieGenres().get(i).toLowerCase().contains(constraint.toString().toLowerCase())) {
                            tempList.add(user);
                        }
                    }
                    ArrayList<String> musicGenres = user.getMusicGenres();
                    for (int i = 0; i < musicGenres.size(); i++) {
                        if (user.getMusicGenres().get(i).toLowerCase().contains(constraint.toString().toLowerCase())) {
                            tempList.add(user);
                        }
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = userList.size();
                filterResults.values = userList;
            }

            return filterResults;
        }

        /**
         * Notify about filtered list to ui
         * @param constraint text
         * @param results filtered result
         */
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredUserList = (ArrayList<User>) results.values;
            //Log.d(TAG, "results: " + results.values.toString());
            notifyDataSetChanged();
        }
    }
}
