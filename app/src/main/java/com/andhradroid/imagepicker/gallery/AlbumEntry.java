package com.andhradroid.imagepicker.gallery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ramesh on 25/10/15.
 */
public class AlbumEntry {
    private List<ImageEntry> imageEntryList;
    private int bucketId;
    private String bucketName;

    public AlbumEntry(int bucketId, String bucketName) {
        this.bucketId = bucketId;
        this.bucketName = bucketName;
        imageEntryList = new ArrayList<>();
    }

    public void addImageEntry(ImageEntry imageEntry) {
        imageEntryList.add(imageEntry);
    }


    public int getBucketId() {
        return bucketId;
    }

    public void setBucketId(int bucketId) {
        this.bucketId = bucketId;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public List<ImageEntry> getAlbumEntryList() {
        return imageEntryList;
    }

    public String getCover() {
        if (imageEntryList.isEmpty()) {
            return null;
        }
        return imageEntryList.get(0).getPath();
    }

}
