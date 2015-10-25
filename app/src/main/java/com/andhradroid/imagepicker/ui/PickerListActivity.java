package com.andhradroid.imagepicker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.andhradroid.imagepicker.R;
import com.andhradroid.imagepicker.filebrowser.FilePickerActivity;
import com.andhradroid.imagepicker.gallery.GalleryActivity;

/**
 * Created by Ramesh on 25/10/15.
 */
public class PickerListActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picker_list_activity);
        findViewById(R.id.button_capture).setOnClickListener(this);
        findViewById(R.id.button_file_picker).setOnClickListener(this);
        findViewById(R.id.button_gallery_picker).setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_capture) {
            startActivity(new Intent(this, ImageCaptureActivity.class));
        } else if (v.getId() == R.id.button_file_picker) {
            startActivity(new Intent(this, FilePickerActivity.class));
        } else if (v.getId() == R.id.button_gallery_picker) {
            startActivity(new Intent(this, GalleryActivity.class));
        }
    }
}
