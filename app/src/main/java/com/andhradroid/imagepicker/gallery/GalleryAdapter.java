package com.andhradroid.imagepicker.gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.andhradroid.imagepicker.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ramesh on 25/10/15.
 */
public class GalleryAdapter extends BaseAdapter {
    List<AlbumEntry> albumEntries;
    Context mContext;

    public GalleryAdapter(Context context) {
        albumEntries = new ArrayList<>();
        mContext = context;
    }

    public void addAlbums(List<AlbumEntry> albumEntryList) {
        albumEntries.addAll(albumEntryList);
        notifyDataSetChanged();
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return albumEntries.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public AlbumEntry getItem(int position) {
        return albumEntries.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.gallery_album_grid_row, null);
        TextView albumName = (TextView) view.findViewById(R.id.album_name);
        TextView albumCount = (TextView) view.findViewById(R.id.album_count);
        ImageView albumImageView = (ImageView) view.findViewById(R.id.album_thumbnail);

        AlbumEntry albumEntry = getItem(position);
        albumName.setText(albumEntry.getBucketName());
        albumCount.setText(String.valueOf(albumEntry.getAlbumEntryList().size()));
        if (albumEntry.getCover() != null) {
            Glide.with(mContext)
                    .load(albumEntry.getCover())
                    .centerCrop()
                    .crossFade()
                    .placeholder(R.drawable.empty_photo)
                    .into(albumImageView);
        }
        return view;
    }
}
