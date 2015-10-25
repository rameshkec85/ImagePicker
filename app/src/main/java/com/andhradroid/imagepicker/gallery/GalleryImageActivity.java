package com.andhradroid.imagepicker.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.andhradroid.imagepicker.R;
import com.andhradroid.imagepicker.util.PickerUtil;

/**
 * Created by Ramesh on 25/10/15.
 */
public class GalleryImageActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private GridView gridView;
    ImageAdapter mGalleryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_picker);
        getSupportActionBar().setTitle(GalleryActivity.mSelectedAlbumEntry.getBucketName());
        getSupportActionBar().setSubtitle(String.format("%d images",
                GalleryActivity.mSelectedAlbumEntry.getAlbumEntryList().size()));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        gridView = (GridView) findViewById(R.id.gridView);
        mGalleryAdapter = new ImageAdapter(this);
        gridView.setAdapter(mGalleryAdapter);
        gridView.setOnItemClickListener(this);
        loadAlbums();
    }

    public void loadAlbums() {
        mGalleryAdapter.addAlbums(GalleryActivity.mSelectedAlbumEntry.getAlbumEntryList());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ImageEntry imageEntry = (ImageEntry) parent.getItemAtPosition(position);
        Intent intent = new Intent();
        intent.putExtra(PickerUtil.IMAGE_PATH, imageEntry.getPath());
        setResult(RESULT_OK, intent);
        finish();
    }
}
