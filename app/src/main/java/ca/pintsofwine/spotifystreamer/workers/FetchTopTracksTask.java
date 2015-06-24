package ca.pintsofwine.spotifystreamer.workers;

import android.os.AsyncTask;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.pintsofwine.spotifystreamer.activities.TrackResultHandler;
import ca.pintsofwine.spotifystreamer.utils.Spotify;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;
import retrofit.RetrofitError;

/**
 * Created by ross on 6/15/15.
 */
public class FetchTopTracksTask extends AsyncTask<String, Void, List<Track>> {

    private static final Map<String, Object> queryMap = new HashMap<String,Object>();
    private static final String LOG_TAG = FetchTopTracksTask.class.getSimpleName();

    private final TrackResultHandler delegate;

    static {
        queryMap.put("country", "US");
    }

    public FetchTopTracksTask(TrackResultHandler delegate) {
        this.delegate = delegate;
    }

    @Override
    protected List<Track> doInBackground(String... params) {

        //Nothing to do if there's no params
        if (params.length == 0) {
            Log.w(LOG_TAG, "Unable to search for artist's top tracks - no artist ID given.");
            return null;
        }

        SpotifyService spotify = Spotify.getInstance();
        List<Track> trackList = null;

        try {
            Tracks spotifyResult = spotify.getArtistTopTrack(params[0], queryMap);
            trackList = spotifyResult.tracks;
        } catch (RetrofitError e) {
            Log.e(LOG_TAG, "Error retrieving top tracks for " + params[0], e);
        }

        return trackList;
    }

    @Override
    protected void onPostExecute(List<Track> tracks) {
        delegate.handleResults(tracks);
    }
}
