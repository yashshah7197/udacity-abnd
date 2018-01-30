package io.yashshah.tourguide;

/**
 * Created by yashshah on 31/12/16.
 */

public class Place {

    private final int NO_IMAGE_PROVIDED = -1;
    // Resource id of the place name
    private int mPlaceNameResourceID;
    // Resource id of the place description
    private int mPlaceDescriptionResourceID;
    // Resource id of the place image
    private int mImageResourceID = NO_IMAGE_PROVIDED;

    /**
     * @param placeNameResourceID       is the name of the place
     * @param placeDescriptionResourceID is a short description of the place
     */
    public Place(int placeNameResourceID, int placeDescriptionResourceID) {
        this.mPlaceNameResourceID = placeNameResourceID;
        this.mPlaceDescriptionResourceID = placeDescriptionResourceID;
    }

    /**
     * @param placeNameResourceID        is the name of the place
     * @param placeDescriptionResourceID is a short description of the place
     * @param imageResourceID            is the image of the place
     */
    public Place(int placeNameResourceID, int placeDescriptionResourceID, int imageResourceID) {
        this.mPlaceNameResourceID = placeNameResourceID;
        this.mPlaceDescriptionResourceID = placeDescriptionResourceID;
        this.mImageResourceID = imageResourceID;
    }

    // Gets the resource ID of the name of the place
    public int getPlaceNameResourceID() {
        return mPlaceNameResourceID;
    }

    // Gets the resource ID of the description of the place
    public int getPlaceDescriptionResourceID() {
        return mPlaceDescriptionResourceID;
    }

    // Gets the resource ID of the image of the place
    public int getImageResourceID() {
        return mImageResourceID;
    }

    public boolean hasImage() {
        return mImageResourceID != NO_IMAGE_PROVIDED;
    }
}
