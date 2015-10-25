package com.andhradroid.imagepicker.gallery;

import android.database.Cursor;
import android.provider.MediaStore;

/**
 * Created by Ramesh on 25/10/15.
 */
public class ImageEntry {
    private int id;
    private String path;
    private long dateModified;

    public ImageEntry() {

    }

    public ImageEntry(Cursor cursor) {
        from(cursor);
    }


    public void from(Cursor cursor) {
        final int dataColumn = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
        final int imageIdColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID);
        final int dateAddedColumn = cursor.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED);
        final int imageId = cursor.getInt(imageIdColumn);
        final String path = cursor.getString(dataColumn);
        final long dateAdded = cursor.getLong(dateAddedColumn);
        //
        setId(imageId);
        setPath(path);
        setDateModified(dateAdded);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getDateModified() {
        return dateModified;
    }

    public void setDateModified(long dateModified) {
        this.dateModified = dateModified;
    }
}
