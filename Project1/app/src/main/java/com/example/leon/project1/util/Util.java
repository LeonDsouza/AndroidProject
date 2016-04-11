package com.example.leon.project1.util;

/**
 * Created by Leon on 3/7/2016.
 */
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
//import com.stericson.RootTools.*;


public class Util {
    public static final String APP_PREFS = "_preferences";
    public static final String BACKGROUND_COLOR = "background";
    public static final String BACKUP_PASSWORD = "backup_password";
    public static final String BROADCAST_REBOOT = "com.example.leon.project1.REBOOT";
    public static final String BROADCAST_SECURE_ZONE_SWITCH_UPDATE = "com.example.leon.project1.SECURE_ZONE_SWITCH_CHANGED";
    public static final String BROADCAST_SOFT_REBOOT = "com.example.leon.project1.SOFT_REBOOT";
    public static final String LOCK = "to_lock";
    public static final String MASK_NOTIFICATIONS = "mask_notifications";
    public static final String MASTER_SWITCH = "master_switch";
    public static final String MASTER_SWITCH_UPDATE = "com.example.leon.project1.MASTER_SWITCH_UPDATE";
    public static final int MAX_TRANSITION_TIME_MS = 2000;
    public static final String MY_PACKAGE_NAME = "com.example.leon.project1";
    public static final String MY_PACKAGE_NAME_PRO = "com.example.leon.project1.pro";
    public static final String ORIG_INTENT = "orig_intent";
    public static final String PREVENT_UNINSTALL = "prevent_uninstall";
    public static final String SECURE_ZONE_BT = "secure_zone_bt";
    public static final String SECURE_ZONE_LIST_BT = "secure_zone_list_bt";
    public static final String SECURE_ZONE_LIST_WIFI = "secure_zone_list_wifi";
    public static final String SECURE_ZONE_SWITCH = "secure_zone_switch";
    public static final String SECURE_ZONE_WIFI = "secure_zone_wifi";
    public static final String SHOW_SYSTEM_APPS = "show_system_apps";
    public static final String TRANSITION_TIME = "transition_time";


   /* static class C02781 extends CommandCapture {
        C02781(int x0, String... x1) {
            //super(x0, x1);
        }

        public void commandCompleted(int id, int exitcode) {
            //super.commandCompleted(id, exitcode);
        }
    }*/
    public static Boolean execute (String command)
    {
        System.out.println("Reached for root");
        //write for rooted devices
        return Boolean.TRUE;
    }
    /*
    public static Boolean execute(String command) {
        //CommandCapture cmd = new C02781(0, command);
        try {
            //RootTools.getShell(true);
            //Shell.runRootCommand(cmd);
            while (!cmd.isFinished()) {
                synchronized (cmd) {
                    try {
                        if (!cmd.isFinished()) {
                            cmd.wait(1000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return Boolean.valueOf(false);
                    }
                }
            }
            RootTools.closeAllShells();
            return Boolean.valueOf(true);
        } catch (Exception e2) {
            e2.printStackTrace();
            return Boolean.valueOf(false);
        }

    }
*/
    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        int width = drawable.getIntrinsicWidth();
        if (width <= 0) {
            width = 1;
        }
        int height = drawable.getIntrinsicHeight();
        if (height <= 0) {
            height = 1;
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}