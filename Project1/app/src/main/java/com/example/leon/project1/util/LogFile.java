package com.example.leon.project1.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Leon on 3/7/2016.
 */
import android.content.Context;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.util.Date;

/**
 * Cretes a log file and displays the log in log tab
 */
public class LogFile {

    private static final String LOG_FILE = "log.txt";

    public static void i(Context c, String msg) {

        String out = "[" + DateFormat.getDateTimeInstance().format(new Date()) + "] " + msg;
        try {
            FileOutputStream fOut = c.openFileOutput(LOG_FILE, Context.MODE_APPEND);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);

            osw.append(out).append("\n");
            osw.flush();
            osw.close();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void delete(Context c) {
        c.deleteFile(LOG_FILE);
    }
}
