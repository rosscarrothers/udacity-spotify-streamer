package ca.pintsofwine.spotifystreamer;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import kaaes.spotify.webapi.android.models.Artist;

/**
 * Created by ross on 6/14/15.
 */
public class FetchArtistsTask extends AsyncTask<String, Void, List<Artist>> {

    private static final String LOG_TAG = FetchArtistsTask.class.getSimpleName();

    private final ArtistResultHandler delegate;

    public FetchArtistsTask(ArtistResultHandler delegate) {
        this.delegate = delegate;
    }


    @Override
    protected List<Artist> doInBackground(String... params) {

        //Can't do anything if there's no params
        if (params.length == 0) {
            Log.w(LOG_TAG, "Unable to search for artist - no artist name given.");
            return null;
        }

        return Spotify.getInstance().searchArtists(params[0]).artists.items;
    }

    @Override
    protected void onPostExecute(List<Artist> artists) {
        delegate.handleResults(artists);
    }
}
