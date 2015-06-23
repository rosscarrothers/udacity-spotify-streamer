package ca.pintsofwine.spotifystreamer;

import android.os.AsyncTask;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kaaes.spotify.webapi.android.models.Track;

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

        return Spotify.getInstance().getArtistTopTrack(params[0], queryMap).tracks;
    }

    @Override
    protected void onPostExecute(List<Track> tracks) {
        delegate.handleResults(tracks);
    }
}
