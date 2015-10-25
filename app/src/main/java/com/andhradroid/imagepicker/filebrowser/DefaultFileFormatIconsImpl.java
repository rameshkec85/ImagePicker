package com.andhradroid.imagepicker.filebrowser;

import com.andhradroid.imagepicker.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ramesh on 25/10/15.
 */
public class DefaultFileFormatIconsImpl implements FileFormatIcons {
    /**
     * @return
     */
    @Override
    public Map<String, Integer> getFileIcons() {
        Map<String, Integer> map = new HashMap<>();
        map.put("jpg", R.drawable.ic_file_jpg);
        map.put("jpeg", R.drawable.ic_file_jpg);
        map.put("png", R.drawable.ic_file_png);
        map.put("pdf", R.drawable.ic_file_pdf);
        map.put("dat", R.drawable.ic_file_dat);
        map.put("doc", R.drawable.ic_file_doc);
        map.put("flv", R.drawable.ic_file_flv);
        map.put("html", R.drawable.ic_file_html);
        map.put("mp3", R.drawable.ic_file_mp3);
        map.put("mp4", R.drawable.ic_file_mp4);
        map.put("ogg", R.drawable.ic_file_ogg);
        map.put("ppt", R.drawable.ic_file_ppt);
        map.put("sql", R.drawable.ic_file_sql);
        map.put("txt", R.drawable.ic_file_txt);
        map.put("zip", R.drawable.ic_file_zip);
        map.put("folder", R.drawable.ic_folder);
        return map;
    }

    @Override
    public int getDrawable(String mimeType) {
        Map<String, Integer> map = getFileIcons();
        if (map.containsKey(mimeType)) {
            return map.get(mimeType);
        }
        //Default icon
        return R.drawable.ic_file_blank;
    }

}
