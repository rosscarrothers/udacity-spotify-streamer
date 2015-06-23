package ca.pintsofwine.spotifystreamer;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.models.Track;


/**
 * A placeholder fragment containing a simple view.
 */
public class TopTracksActivityFragment extends Fragment implements TrackResultHandler {

    private TrackAdapter tracksAdapter;
    private List<Track> currentTracks;

    public TopTracksActivityFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentTracks = new ArrayList<Track>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_top_tracks, container, false);

        //Create adapter here
        tracksAdapter = new TrackAdapter(
                getActivity(),
                R.layout.list_item_track,
                currentTracks
        );

        //Bind the listview and adapter
        ListView trackList = (ListView) rootView.findViewById(R.id.listview_top_tracks);
        trackList.setAdapter(tracksAdapter);

        //This activity expects to receive the name of an artist from the intent that started it
        //Grab the name of the artist and populate the list with their top tracks
        String artistId = getActivity().getIntent().getStringExtra(Intent.EXTRA_TEXT);
        if (artistId != null) {
            new FetchTopTracksTask(this).execute(artistId);
        }

        //We also expect to get the artist's name through the intent, check for it and use it to
        //populate the action bar's subtitle
        String artistName = getActivity().getIntent().getStringExtra(Intent.EXTRA_TITLE);
        if (artistName != null) {
            getActivity().getActionBar().setSubtitle(artistName);
        }

        return rootView;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (tracksAdapter.isEmpty() && !currentTracks.isEmpty()) {
            tracksAdapter.addAll(currentTracks);
        }
    }

    @Override
    public void handleResults(List<Track> results) {
        //Handle the case where we didn't get any results
        if (results == null || results.isEmpty()) {
            Toast.makeText(getActivity(), "No results found", Toast.LENGTH_SHORT).show();
            return;
        }

        //Simple code for now that adds the artist's name to the list
        tracksAdapter.clear();
        tracksAdapter.addAll(results);
    }
}
