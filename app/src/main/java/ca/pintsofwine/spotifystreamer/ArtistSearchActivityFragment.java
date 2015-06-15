package ca.pintsofwine.spotifystreamer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import kaaes.spotify.webapi.android.models.Artist;


/**
 * A placeholder fragment containing a simple view.
 */
public class ArtistSearchActivityFragment extends Fragment implements ArtistResultHandler {

    private static final String LOG_TAG = ArtistSearchActivity.class.getSimpleName();

    public ArtistSearchActivityFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        //Add a listener to the search text field to detect when the user hits Done
        EditText searchText = (EditText) rootView.findViewById(R.id.artist_search_text);
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    populateListForArtist(v.getText().toString());
                }

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

        for (Artist artist : results) {
            Log.v(LOG_TAG, artist.name);
        }

    }
}
