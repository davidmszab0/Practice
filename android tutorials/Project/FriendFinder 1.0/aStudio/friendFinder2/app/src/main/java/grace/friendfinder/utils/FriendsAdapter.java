package grace.friendfinder.utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import grace.friendfinder.R;
import grace.friendfinder.domain.User;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

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
            holder.moviesLine = (TextView) view.findViewById(R.id.moviesGenresId);
            holder.musicLine = (TextView) view.findViewById(R.id.musicGenresId);
            holder.imageView = (ImageView) view.findViewById(R.id.person_icon);

            view.setTag(holder);
        } else {
            // get view holder back
            holder = (ViewHolder) view.getTag();
        }

        holder.nameLine.setText("Name: " + user.getName());

        // transform the ArrayList to Array to use Array.toString() with SetText,
        // which prints nicer than append()
        int tempArray0Size = user.getMovieGenres().size();
        String[] tempArray0 = new String[tempArray0Size];
        for (int i = 0; i < tempArray0Size; i++) {
            tempArray0[i] = user.getMovieGenres().get(i);
        }
        holder.moviesLine.setText(Arrays.toString(tempArray0).replaceAll("\\[|\\]", ""));
        int tempArray1Size = user.getMusicGenres().size();
        String[] tempArray1 = new String[tempArray1Size];
        for (int j = 0; j < tempArray1Size; j++) {
            tempArray1[j] = user.getMusicGenres().get(j);
        }
        holder.musicLine.setText(Arrays.toString(tempArray1).replaceAll("\\[|\\]", ""));

        if (user.getGender().equals("Male")) {
            holder.imageView.setImageResource(R.drawable.icons8_person_48);
        } else {
            holder.imageView.setImageResource(R.drawable.icons8_person_female_48);
        }

        return view;
    }

    /**
     * Keep reference to children view to avoid unnecessary calls
     */
    static class ViewHolder {
        TextView nameLine;
        TextView moviesLine;
        TextView musicLine;
        ImageView imageView;
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

            if (isNotBlank(constraint)) {

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

        private User getCurrentUser () {
            DatabaseHandler db = new DatabaseHandler(context);
            Integer user_id2 = null;
            User currentUser = null;

            HashMap hm = db.getUserDetails();
            user_id2 =  Integer.parseInt((String) hm.get("user_id"));

            for (int i = 0; i < userList.size(); i++) {
                currentUser = userList.get(i);
                if (currentUser.getId() == user_id2) {
                    return currentUser;
                }
            }
            return null;
        }

        private int getCurrentUserId (ArrayList<User> tempList) {
            DatabaseHandler db = new DatabaseHandler(context);
            Integer user_id2 = null;
            User currentUser = null;

            HashMap hm = db.getUserDetails();
            user_id2 =  Integer.parseInt((String) hm.get("user_id"));

            for (int i = 0; i < tempList.size(); i++) {
                currentUser = tempList.get(i);
                if (currentUser.getId() == user_id2) {
                    return i;
                }
            }
            return 0;
        }

        private String matchMsg (ArrayList<User> tempList) {

            User currentUser = getCurrentUser();
            Log.d(TAG, "currentUser: " + currentUser.toString());

            // copy templist to correctList
            ArrayList<User> correctList = new ArrayList<>(tempList);
            int curerntUserIndex = getCurrentUserId(tempList);
            Log.d(TAG, "curerntUserIndex: " + curerntUserIndex);
            correctList.remove(curerntUserIndex);

            Integer countMovieGenres = 0;
            Integer countMusicGenres = 0;
            String match = "";
            ArrayList<String> matchList = new ArrayList<>();

            for (User user : correctList) {
                for (int i = 0; i < user.getMovieGenres().size(); i++) {
                    for (int j = 0; j < currentUser.getMovieGenres().size(); j++) {
                        if (user.getMovieGenres().get(i).equals(currentUser.getMovieGenres().get(j))) {
                            countMovieGenres++;
                        }
                    }
                }
                for (int i = 0; i < user.getMusicGenres().size(); i++) {
                    for (int j = 0; j < currentUser.getMusicGenres().size(); j++) {
                        if (user.getMusicGenres().get(i).equals(currentUser.getMusicGenres().get(j))) {
                            countMusicGenres++;
                        }
                    }
                }
                match = "I have matched " + user.getName() + " with " + countMovieGenres +
                        " similar movieGenres interests and "
                        + countMusicGenres + " similar musicGenres interests. \n";
                matchList.add(match);
            }

            Log.d(TAG, match);
            match = "";
            for (int i = 0; i < matchList.size(); i++) {
                match += matchList.get(i);
            }

            return match;
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

            // don't display the toast message when the search query is empty
            if (isNotBlank(constraint)) {
                String match = matchMsg(filteredUserList);
                Toast.makeText(context, match, Toast.LENGTH_SHORT).show();
            }

            Log.d(TAG, "results: " + results.values.toString());
            notifyDataSetChanged();
        }
    }
}
