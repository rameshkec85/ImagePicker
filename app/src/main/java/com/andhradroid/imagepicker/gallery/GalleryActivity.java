package com.andhradroid.imagepicker.gallery;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.andhradroid.imagepicker.R;
import com.andhradroid.imagepicker.util.PickerUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ramesh on 25/10/15.
 */
public class GalleryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private GridView gridView;
    GalleryAdapter mGalleryAdapter;
    public static AlbumEntry mSelectedAlbumEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_picker);
        setTitle("Gallery");
        gridView = (GridView) findViewById(R.id.gridView);
        mGalleryAdapter = new GalleryAdapter(this);
        gridView.setAdapter(mGalleryAdapter);
        gridView.setOnItemClickListener(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadAlbums();
            }
        }, 500);
    }

    public void loadAlbums() {
        Cursor cursor = PickerUtil.queryImages(this);
        Map<Integer, AlbumEntry> albumEntryMap = new HashMap<>();
        List<AlbumEntry> mAlbumEntryList = new ArrayList<>();
        while (cursor.moveToNext()) {
            String colBucketId = MediaStore.Images.Media.BUCKET_ID;
            String colBucketName = MediaStore.Images.Media.BUCKET_DISPLAY_NAME;
            int bucketId = cursor.getInt(cursor.getColumnIndex(colBucketId));
            String bucketName = cursor.getString(cursor.getColumnIndex(colBucketName));
            AlbumEntry albumEntry;
            if (!albumEntryMap.containsKey(bucketId)) {
                albumEntry = new AlbumEntry(bucketId, bucketName);
                albumEntryMap.put(bucketId, albumEntry);
                mAlbumEntryList.add(albumEntry);
            } else {
                albumEntry = albumEntryMap.get(bucketId);
            }
            albumEntry.addImageEntry(new ImageEntry(cursor));
        }
        mGalleryAdapter.addAlbums(mAlbumEntryList);
    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p/>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AlbumEntry albumEntry = (AlbumEntry) parent.getItemAtPosition(position);
        mSelectedAlbumEntry = albumEntry;
        startActivityForResult(new Intent(this, GalleryImageActivity.class), 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            setResult(RESULT_OK, data);
            finish();
        }
    }
}
