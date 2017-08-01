package grace.friendfinder.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import grace.friendfinder.R;

/**
 * Created by grace on 01/08/17.
 */

public class FriendsAdapter extends ArrayAdapter<String> implements Filterable {
    private Context context;
    private ArrayList<String> nameList;
    private ArrayList<String> genderList;
    private ArrayList<ArrayList<String>> movieGenresList;
    private ArrayList<ArrayList<String>> musicGenresList;
    private ArrayList<String> filteredNameList;
    private NameFilter nameFilter;

    public FriendsAdapter(Context context, int textViewResourceId, ArrayList<String> name, ArrayList<String> gender,
                          ArrayList<ArrayList<String>> movies, ArrayList<ArrayList<String>> music) {
        super(context, textViewResourceId, name);
        this.context = context;
        this.nameList = name;
        this.genderList = gender;
        this.movieGenresList = movies;
        this.musicGenresList = music;
        this.filteredNameList = name;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        final ViewHolder holder;

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

        holder.nameLine.setText("Name: " + nameList.get(position));
        holder.genderLine.setText("Gender: " + genderList.get(position));

        String[] tempArray0 = new String[movieGenresList.get(position).size()];
        for (int i = 0; i < movieGenresList.get(position).size(); i++) {
            tempArray0[i] = movieGenresList.get(position).get(i);
            //holder.moviesLine.append(movieGenresList.get(position).get(i) + ", ");
            holder.moviesLine.setText(Arrays.toString(tempArray0).replaceAll("\\[|\\]", ""));
        }
        String[] tempArray1 = new String[musicGenresList.get(position).size()];
        for (int j = 0; j < musicGenresList.get(position).size(); j++) {
            tempArray1[j] = musicGenresList.get(position).get(j);
            //holder.musicLine.append(musicGenresList.get(position).get(j) + ", ");
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
        if (nameFilter == null) {
            nameFilter = new NameFilter();
        }
        return nameFilter;
    }

    /**
     * Custom filter for friend list
     * Filter content in friend list according to the search text
     */
    private class NameFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();

            if (constraint!=null && constraint.length()>0) {

                ArrayList<String> tempList = new ArrayList<>();

                // search content in friend list
                for (String name : nameList) {
                    if (name.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(name);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = nameList.size();
                filterResults.values = nameList;
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
            filteredNameList = (ArrayList<String>) results.values;
            notifyDataSetChanged();
        }
    }
}
