package ca.pintsofwine.spotifystreamer.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.TreeSet;

import ca.pintsofwine.spotifystreamer.utils.ImageComparator;
import ca.pintsofwine.spotifystreamer.R;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Image;

/**
 * Created by ross on 6/15/15.
 */
public class ArtistAdapter extends ArrayAdapter<Artist> {

    private final Context context;
    private final int layoutResourceId;
    private final List<Artist> artists;
    private final TreeSet<Image> sortedImageSet;

    public ArtistAdapter(Context context, int layoutResourceId, List<Artist> artists) {
        super(context, layoutResourceId, artists);
        this.context = context;
        this.artists = artists;
        this.layoutResourceId = layoutResourceId;
        this.sortedImageSet = new TreeSet<Image>(new ImageComparator());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Get the row that we're inflating...
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
        }

        //Get the image and text view for this row
        ImageView imageView = (ImageView)row.findViewById(R.id.list_item_artist_thumbnail);
        TextView textView = (TextView)row.findViewById(R.id.list_item_artist_name_textview);

        //Get the artist for this position and set the textfield to their name...
        Artist artist = artists.get(position);
        textView.setText(artist.name);

        //Now, find the best image for this artist (as defined in our ImageComparator) and use
        //that to populate the imageview
        sortedImageSet.clear();
        sortedImageSet.addAll(artist.images);

        //If there are no images for this artist just do nothing - the layout will default to a
        //generic Android icon
        if (!sortedImageSet.isEmpty()) {
            Picasso.with(context).load(sortedImageSet.first().url).into(imageView);
        }

        return row;
    }
}
