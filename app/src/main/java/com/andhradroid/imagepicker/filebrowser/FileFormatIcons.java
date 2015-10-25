package com.andhradroid.imagepicker.filebrowser;

import java.util.Map;

public interface FileFormatIcons {
    /**
     * @return
     */
    Map<String, Integer> getFileIcons();

    int getDrawable(String mimeType);
}
