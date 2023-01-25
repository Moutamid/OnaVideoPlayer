package com.moutamid.demovideoplayer;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.format.DateFormat;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Constants {

    public static String getHumanDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd-MM-yyyy HH:mm", cal).toString();
        return date;
    }

    public static String getHumanTime(long millis) {
        return String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );
    }

    public static String getHumanSize(long bytes) {
        if (-1000 < bytes && bytes < 1000) {
            return bytes + " B";
        }
        CharacterIterator ci = new StringCharacterIterator("kMGTPE");
        while (bytes <= -999_950 || bytes >= 999_950) {
            bytes /= 1000;
            ci.next();
        }
        return String.format("%.1f %cB", bytes / 1000.0, ci.current());
    }

    private ArrayList<String> getAllVideoPath(Context context) {
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Video.VideoColumns.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
/*
        String orderBy = android.provider.MediaStore.Video.Media.DATE_TAKEN;
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, orderBy + " DESC");
*/
        ArrayList<String> pathArrList = new ArrayList<String>();
        //int vidsCount = 0;
        if (cursor != null) {
            //vidsCount = cursor.getCount();
            //Log.d(TAG, "Total count of videos: " + vidsCount);
            while (cursor.moveToNext()) {
                pathArrList.add(cursor.getString(0));
                //Log.d(TAG, cursor.getString(0));
            }
            cursor.close();
        }

        return pathArrList;
    }
}
