package root.assign1;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by root on 2016-09-11.
 */

public class BeerFragment extends Fragment {
    final static String IMAGE_TEXT = "image_text";
    final static String HEADER_TEXT = "header_text";
    final static String BODY_TEXT = "body_text";
    private ViewGroup rootView;



    public static BeerFragment create(String header, int image, String body) {
        BeerFragment newFragment = new BeerFragment();
        Bundle args = new Bundle();
        args.putString(HEADER_TEXT, header);
        args.putInt(IMAGE_TEXT, image);
        args.putString(BODY_TEXT, body);
        newFragment.setArguments(args);
        return newFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout containing a header and body text.
        rootView = (ViewGroup) inflater.inflate(R.layout.example_fragment, container, false);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the header and body text.

        String bodyText, headerText;
        int imageText;
        Bundle args = getArguments();
        if (args != null) {
            headerText = args.getString(HEADER_TEXT);
            imageText = args.getInt(IMAGE_TEXT);
            bodyText = args.getString(BODY_TEXT);
        }
        else {
            headerText = "Default Header";
            imageText = 0;
            bodyText = "Default Body";
        }

        // Set the header and body text.
        ((TextView) rootView.findViewById(R.id.header_view)).setText(headerText);
        ((ImageView) rootView.findViewById(R.id.beer_icon)).setImageResource(imageText);
        ((TextView) rootView.findViewById(R.id.body_view)).setText(bodyText);
    }
}