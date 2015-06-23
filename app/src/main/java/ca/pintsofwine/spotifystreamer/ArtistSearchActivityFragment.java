package ca.pintsofwine.spotifystreamer;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.models.Artist;

public class ArtistSearchActivityFragment extends Fragment implements ArtistResultHandler {

    private static final String LOG_TAG = ArtistSearchActivity.class.getSimpleName();

    private ArtistAdapter artistAdapter;
    private List<Artist> currentArtists;

    public ArtistSearchActivityFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentArtists = new ArrayList<Artist>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        //Create our adapter for populating the ListView
        artistAdapter = new ArtistAdapter(
                getActivity(),
                R.layout.list_item_artist,
                currentArtists
        );

        //Now bind it the the list view
        ListView artistListView = (ListView) rootView.findViewById(R.id.listview_artists);
        artistListView.setAdapter(artistAdapter);

        //When an item in the list is clicked, start a new activity which shows that artist's
        //top tracks
        artistListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Artist artist = artistAdapter.getItem(position);
                Intent artistTopTracksIntent = new Intent(getActivity(), TopTracksActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, artist.id)
                        .putExtra(Intent.EXTRA_TITLE, artist.name);
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        //Re-populate the adapter when the screen orientation changes
        if (artistAdapter.isEmpty() && !currentArtists.isEmpty()) {
            artistAdapter.addAll(currentArtists);
        }
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

        artistAdapter.clear();
        artistAdapter.addAll(results);
    }
}
