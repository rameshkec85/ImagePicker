package com.andhradroid.imagepicker.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.andhradroid.imagepicker.util.PickerUtil;

import java.io.File;

/**
 * Created by Ramesh on 25/10/15.
 */
public class ImageCaptureActivity extends AppCompatActivity {


    private static final String STATE_FILE_URI = "state_file_uri";
    private static final int BROWSE_CAMERA = 111;
    private String fileUri;
    private final String TAG = ImageCaptureActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fileUri = getIntent().getStringExtra(PickerUtil.IMAGE_PATH);
        takePictureIntent();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_FILE_URI, fileUri);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        fileUri = savedInstanceState.getString(STATE_FILE_URI);
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void takePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (fileUri == null) {
            fileUri = PickerUtil.getOutputMediaFile().getAbsolutePath();
        }
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(fileUri)));
        startActivityForResult(takePictureIntent, BROWSE_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == BROWSE_CAMERA) {
                if (data == null) {
                    onDeliveryPicture(Uri.fromFile(new File(fileUri)));
                } else {
                    onDeliveryPicture(data.getData());
                }
            }
        }
        finish();
    }

    private void onDeliveryPicture(Uri uri) {
        Log.i(TAG, "Uri: " + uri);
        if (uri != null) {
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            Intent intent = new Intent();
            intent.setData(uri);
            intent.putExtra(PickerUtil.IMAGE_PATH, uri.toString());
            setResult(Activity.RESULT_OK, intent);
        } else {
            Toast.makeText(this, "Camera error while returning path.", Toast.LENGTH_LONG).show();
        }

    }
}
