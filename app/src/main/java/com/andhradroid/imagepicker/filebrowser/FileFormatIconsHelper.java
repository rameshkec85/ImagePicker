package com.andhradroid.imagepicker.filebrowser;

/**
 * Created by Ramesh on 25/10/15.
 */
public class FileFormatIconsHelper {

    private static FileFormatIcons fileFormatIcons;

    public static FileFormatIcons setFileFormatIcons(FileFormatIcons fileFormatIcons) {
        FileFormatIconsHelper.fileFormatIcons = fileFormatIcons;
        return fileFormatIcons;
    }

    public static FileFormatIcons getFileFormatIcons() {
        if (fileFormatIcons == null) {
            fileFormatIcons = new DefaultFileFormatIconsImpl();
        }
        return fileFormatIcons;
    }

}
