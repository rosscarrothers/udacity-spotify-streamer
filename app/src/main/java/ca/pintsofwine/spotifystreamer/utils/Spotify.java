package ca.pintsofwine.spotifystreamer.utils;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;

/**
 * Created by ross on 6/14/15.
 */
public class Spotify {

    private static final SpotifyService spotify = new SpotifyApi().getService();

    private Spotify() { }

    public static SpotifyService getInstance() {
        return spotify;
    }

}
