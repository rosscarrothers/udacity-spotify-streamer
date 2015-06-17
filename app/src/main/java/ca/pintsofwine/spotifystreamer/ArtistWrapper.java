package ca.pintsofwine.spotifystreamer;

import kaaes.spotify.webapi.android.models.Artist;

/**
 * Created by ross on 6/15/15.
 */
public class ArtistWrapper {

    private final Artist artist;

    public ArtistWrapper(Artist artist) {
        this.artist = artist;
    }

    public Artist getArtist() {
        return artist;
    }

    @Override
    public String toString() {
        return artist.name;
    }

}
