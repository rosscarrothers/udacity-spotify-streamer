package ca.pintsofwine.spotifystreamer;

import java.util.Comparator;

import kaaes.spotify.webapi.android.models.Image;

/**
 * Created by ross on 6/15/15.
 */
public class ImageComparator implements Comparator<Image> {
    @Override
    public int compare(Image lhs, Image rhs) {
        int lhsTotal = lhs.height.intValue() + lhs.width.intValue();
        int rhsTotal = rhs.height.intValue() + lhs.width.intValue();

        int lhsAbs = Math.abs(lhsTotal - 400);
        int rhsAbs = Math.abs(rhsTotal - 400);

        return Integer.valueOf(lhsAbs).compareTo(Integer.valueOf(rhsAbs));
    }
}
