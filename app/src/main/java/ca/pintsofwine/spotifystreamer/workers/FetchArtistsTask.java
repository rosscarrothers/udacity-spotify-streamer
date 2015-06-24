package ca.pintsofwine.spotifystreamer.workers;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import ca.pintsofwine.spotifystreamer.activities.ArtistResultHandler;
import ca.pintsofwine.spotifystreamer.utils.Spotify;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import retrofit.RetrofitError;

/**
 * Created by ross on 6/14/15.
 */
public class FetchArtistsTask extends AsyncTask<String, Void, List<Artist>> {

    private static final String LOG_TAG = FetchArtistsTask.class.getSimpleName();

    private final ArtistResultHandler handler;

    public FetchArtistsTask(ArtistResultHandler delegate) {
        this.handler = delegate;
    }

    @Override
    protected List<Artist> doInBackground(String... params) {

        //Can't do anything if there's no params
        if (params.length == 0) {
            Log.w(LOG_TAG, "Unable to search for artist - no artist name given.");
            return null;
        }

        SpotifyService spotify = Spotify.getInstance();
        List<Artist> artistList = null;

        try {
            ArtistsPager spotifyResult = spotify.searchArtists(params[0]);
            artistList = spotifyResult.artists.items;
        } catch (RetrofitError e) {
            Log.e(LOG_TAG, "Error retrieving artist search results for " + params[0], e);
        }

        return artistList;
    }

    @Override
    protected void onPostExecute(List<Artist> artists) {
        handler.handleResults(artists);
    }
}
