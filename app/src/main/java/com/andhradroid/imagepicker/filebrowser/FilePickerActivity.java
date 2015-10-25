package com.andhradroid.imagepicker.filebrowser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.andhradroid.imagepicker.util.PickerUtil;
import com.andhradroid.imagepicker.R;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

/**
 * https://www.iconfinder.com/icons/709492/document_documents_file_files_folder_open_opened_icon#size=128
 * https://www.google.com/design/icons/
 */
public class FilePickerActivity extends AppCompatActivity {

    private ListView listView;

    public ListView getListView() {
        return listView;
    }

    private class FilePickerListAdapter extends BaseAdapter {
        private final LayoutInflater mInflater;
        private final ArrayList<File> mFiles;

        public FilePickerListAdapter(Context context) {
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mFiles = new ArrayList<File>();
        }

        public ArrayList<File> getFiles() {
            return mFiles;
        }

        @Override
        public int getCount() {
            return mFiles.size();
        }

        @Override
        public File getItem(int position) {
            return mFiles.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView != null ? convertView :
                    mInflater.inflate(R.layout.file_picker_list_item,
                            parent,
                            false);
            ImageView icon = (ImageView) v.findViewById(R.id.icon);
            TextView name = (TextView) v.findViewById(R.id.nameTextView);
            TextView type = (TextView) v.findViewById(R.id.typeTextView);
            File file = getItem(position);
            type.setText(PickerUtil.getReadableSize(file.length()));
            name.setText(file.getName());
            if (file.isDirectory()) {
                icon.setImageResource(R.drawable.ic_folder);
            } else {
                String fileName = file.getName();
                String mimeType = fileName.substring(fileName.lastIndexOf(".") + 1);
                int resId = FileFormatIconsHelper.getFileFormatIcons().getDrawable(mimeType);
                icon.setImageResource(resId);
            }
            return v;
        }
    }

    public static final int PICK_FILE_REQUEST = 0;
    public static final String EXTRA_FILE_PATH = "filePath";

    private File mCurrentFolder;
    private Stack<File> mPrevFolders;
    private FilePickerListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_picker);
        setTitle("File Picker");
        mPrevFolders = new Stack<File>();

        listView = (ListView) findViewById(android.R.id.list);
        listView.setTextFilterEnabled(true);
        mAdapter = new FilePickerListAdapter(FilePickerActivity.this);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File file = (File) parent.getItemAtPosition(position);

                if (file.isDirectory()) {
                    mPrevFolders.push(mCurrentFolder);
                    loadFolder(file);
                } else {
                    Intent data = new Intent();
                    data.putExtra(EXTRA_FILE_PATH, file.getPath());
                    setResult(Activity.RESULT_OK, data);
                    Log.i("File Picker", "Selected file: " + file.getName());
                    finish();
                }
            }
        });
        listView.setEmptyView(findViewById(R.id.empty));
        listView.setAdapter(mAdapter);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && !mPrevFolders.isEmpty()) {
            File folder = mPrevFolders.pop();
            loadFolder(folder);
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        loadFolder(Environment.getExternalStorageDirectory());
    }

    private void loadFolder(File folder) {

        mCurrentFolder = folder;
        TextView pathTxtView = (TextView) findViewById(R.id.path);
        if (folder != null) {
            String absPath = folder.getAbsolutePath();
            absPath = absPath.replaceAll("\\/", " > ");
            pathTxtView.setText(absPath);
            makeScroll();
        }

        ProgressDialog progressDialog =
                ProgressDialog.show(this, "", "Loading. Please wait...", true);
        ArrayList<File> adapterFiles = mAdapter.getFiles();
        adapterFiles.clear();
        File[] files = folder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (pathname.isHidden()) {
                    return false;
                }
                return true;
            }
        });
        if (files != null) {
            List<File> allFiles = Arrays.asList(files);
            Collections.sort(allFiles, new Comparator<File>() {
                @Override
                public int compare(File lhs, File rhs) {
                    return lhs.getName().toLowerCase().compareTo(rhs.getName().toLowerCase());
                }
            });

            Collections.sort(allFiles, new Comparator<File>() {
                @Override
                public int compare(File lhs, File rhs) {
                    return PickerUtil.convertBooleanToInt(lhs.isDirectory()) - PickerUtil.convertBooleanToInt(rhs.isDirectory());
                }
            });
            adapterFiles.addAll(allFiles);
        }

        mAdapter.notifyDataSetChanged();
        getListView().setAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        progressDialog.dismiss();
    }

    private void makeScroll() {
//        path.setMovementMethod(new ScrollingMovementMethod());
        //TODO: logic to scroll end of the text view.
        final HorizontalScrollView scrollView = (HorizontalScrollView) findViewById(R.id.scrollView);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_RIGHT);
            }
        });
    }
}
