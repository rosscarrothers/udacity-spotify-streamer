package ca.pintsofwine.spotifystreamer;

import java.util.List;

import kaaes.spotify.webapi.android.models.Artist;

/**
 * Created by ross on 6/14/15.
 */
public interface ArtistResultHandler {

    public void handleResults(List<Artist> results);

}
