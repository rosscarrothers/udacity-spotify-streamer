package ca.pintsofwine.spotifystreamer;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by ross on 6/15/15.
 */
public class ImageTextAdapter extends ArrayAdapter<ArtistWrapper> {

    private List<ArtistWrapper> artists;

    public ImageTextAdapter(Context context, int resource, int textViewResourceId,
                            List<ArtistWrapper> artists) {
        super(context, resource, textViewResourceId, artists);
        this.artists = artists;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ArtistWrapper artist = artists.get(position);
        //TODO finish all this

        return null;
    }
}
