package ca.pintsofwine.spotifystreamer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Image;


/**
 * A placeholder fragment containing a simple view.
 */
public class ArtistSearchActivityFragment extends Fragment implements ArtistResultHandler {

    private static final String LOG_TAG = ArtistSearchActivity.class.getSimpleName();

    private ArrayAdapter<String> artistAdapter;

    public ArtistSearchActivityFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        //Create our adapter for populating the ListView
        artistAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.list_item_artist,
                R.id.list_item_artist_name_textview,
                new ArrayList<String>()
        );

        //Now bind it the the list view
        ListView artistListView = (ListView) rootView.findViewById(R.id.listview_artists);
        artistListView.setAdapter(artistAdapter);

        //When an item in the list is clicked, start a new activity which shows that artist's
        //top tracks
        artistListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String artistName = artistAdapter.getItem(position);
                Intent artistTopTracksIntent = new Intent(getActivity(), TopTracksActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, artistName);
                startActivity(artistTopTracksIntent);
            }
        });

        //Add a listener to the search text field to detect when the user hits Done
        EditText searchText = (EditText) rootView.findViewById(R.id.artist_search_text);
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    populateListForArtist(v.getText().toString());
                }

                //Since our method is simply starting a background task we don't return true
                //and consume the event. This allows the system to minimize the keyboard as it
                //normally would when the Done button is pressed
                return false;
            }
        });

        return rootView;
    }

    private void populateListForArtist(String artistName) {
        Log.v(LOG_TAG, "Searching for artist " + artistName);
        FetchArtistsTask task = new FetchArtistsTask(this);
        task.execute(artistName);
    }

    @Override
    public void handleResults(List<Artist> results) {

        //Handle the case where we didn't get any results
        if (results == null || results.isEmpty()) {
            Toast.makeText(getActivity(), "No results found", Toast.LENGTH_SHORT).show();
            return;
        }

        //Simple code for now that adds the artist's name to the list
        artistAdapter.clear();
        for (Artist artist : results) {
            artistAdapter.add(artist.id);

            String imageUrl = getImageUrl(artist.images);
            Log.v(LOG_TAG, artist.name + " --> " + imageUrl);
        }
    }

    /**
     * Helper method that simply loops through a list of images and returns the URL of the 200x200
     * image which we want to use in our ListView
     * @param images
     * @return
     */
    private String getImageUrl(List<Image> images) {
        TreeSet<Image> sortedSet = new TreeSet<Image>(new ImageComparator());
        sortedSet.addAll(images);
        return sortedSet.isEmpty() ? null : sortedSet.first().url;
    }
}
