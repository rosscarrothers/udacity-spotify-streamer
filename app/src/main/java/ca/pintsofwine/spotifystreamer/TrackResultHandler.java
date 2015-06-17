package ca.pintsofwine.spotifystreamer;

import java.util.List;

import kaaes.spotify.webapi.android.models.Track;

/**
 * Created by ross on 6/15/15.
 */
public interface TrackResultHandler {

    public void handleResults(List<Track> results);

}
