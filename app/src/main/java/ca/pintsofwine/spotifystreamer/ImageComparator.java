package ca.pintsofwine.spotifystreamer;

import java.util.Comparator;

import kaaes.spotify.webapi.android.models.Image;

/**
 * An implementation of the Comparator interface for comparing two {@link Image} objects. Returns
 * the image which is closest (by area) to our ideal image size of 200x200
 */
public class ImageComparator implements Comparator<Image> {

    private static final int IDEAL_IMAGE_AREA = 200 * 200;

    @Override
    public int compare(Image lhs, Image rhs) {
        int lhsArea = lhs.height.intValue() * lhs.width.intValue();
        int rhsArea = rhs.height.intValue() * lhs.width.intValue();

        int lhsAbs = Math.abs(lhsArea - IDEAL_IMAGE_AREA);
        int rhsAbs = Math.abs(rhsArea - IDEAL_IMAGE_AREA);

        return Integer.valueOf(lhsAbs).compareTo(Integer.valueOf(rhsAbs));
    }
}
