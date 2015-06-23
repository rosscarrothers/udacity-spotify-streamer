package ca.pintsofwine.spotifystreamer;

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

import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Track;

/**
 * Created by ross on 6/17/15.
 */
public class TrackAdapter extends ArrayAdapter<Track> {

    private final Context context;
    private final int layoutResourceId;
    private final List<Track> tracks;
    private final TreeSet<Image> sortedImageSet;

    public TrackAdapter(Context context, int layoutResourceId, List<Track> tracks) {
        super(context, layoutResourceId, tracks);
        this.context = context;
        this.tracks = tracks;
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

        //Get the image and text views for this row
        ImageView imageView = (ImageView)row.findViewById(R.id.list_item_track_thumbnail);
        TextView albumTextView = (TextView)row.findViewById(R.id.list_item_album_name);
        TextView trackNameTextView = (TextView)row.findViewById(R.id.list_item_track_name);

        //Get the track at this position and set the textfields with its name and album
        Track track = tracks.get(position);
        trackNameTextView.setText(track.name);
        albumTextView.setText(track.album.name);

        //Now, find the best image for this artist (as defined in our ImageComparator) and use
        //that to populate the imageview
        sortedImageSet.clear();
        sortedImageSet.addAll(track.album.images);

        //If there are no images for this album just do nothing - the layout will default to a
        //generic Android icon
        if (!sortedImageSet.isEmpty()) {
            Picasso.with(context).load(sortedImageSet.first().url).into(imageView);
        }

        return row;
    }
}
