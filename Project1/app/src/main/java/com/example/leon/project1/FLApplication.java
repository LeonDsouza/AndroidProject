package com.example.leon.project1;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import com.example.leon.project1.util.Util;
import java.util.Set;

public class FLApplication extends Application {
    private static SharedPreferences pref;
    private static SharedPreferences pref_hook;

    public void onCreate() {
        super.onCreate();
        //retrieve values of class file and hold its preferences
        pref = getSharedPreferences("com.example.leon.project1_preferences", 0);
        pref_hook = getSharedPreferences(Util.MY_PACKAGE_NAME, 1);
    }

    public static boolean showSystemApps() {
        return pref.getBoolean(Util.SHOW_SYSTEM_APPS, false);
    }

    public static boolean useBackupPassword() {
        return pref.getBoolean(Util.BACKUP_PASSWORD, false);
    }

    public static void setTransitionTimeHook(int val) {
        pref_hook.edit().putInt(Util.TRANSITION_TIME, val).apply();
    }

    public static void setHideNotificationsHook(boolean val) {
        pref_hook.edit().putBoolean(Util.MASK_NOTIFICATIONS, val).apply();
    }

    public static long getPermitTimeHook(String val) {
        return pref_hook.getLong(val + "_sec", 0);
    }

    public static void setPermitTimeHook(String val, long val2) {
        pref_hook.edit().putLong(val + "_sec", val2).apply();
    }

    public static boolean isSecureZone() {
        return pref.getBoolean(Util.SECURE_ZONE_SWITCH, false);
    }

    public static boolean isSecureWiFi() {
        return pref.getBoolean(Util.SECURE_ZONE_WIFI, false);
    }

    public static boolean isSecureBT() {
        return pref.getBoolean(Util.SECURE_ZONE_BT, false);
    }

    public static void setSecureWiFi(boolean val) {
        pref.edit().putBoolean(Util.SECURE_ZONE_WIFI, val).apply();
    }

    public static void setSecureBT(boolean val) {
        pref.edit().putBoolean(Util.SECURE_ZONE_BT, val).apply();
    }

    public static void setSecureZoneHook(boolean val) {
        pref_hook.edit().putBoolean(Util.SECURE_ZONE_SWITCH, val).apply();
    }

    public static Set<String> getSsidSet() {
        return pref.getStringSet(Util.SECURE_ZONE_LIST_WIFI, null);
    }

    public static Set<String> getBtSet() {
        return pref.getStringSet(Util.SECURE_ZONE_LIST_BT, null);
    }

    public static void setMasterSwitch(boolean val) {
        pref.edit().putBoolean(Util.MASTER_SWITCH, val).apply();
        pref_hook.edit().putBoolean(Util.MASTER_SWITCH, val).apply();
    }

    public static boolean isMasterSwitch() {
        return pref.getBoolean(Util.MASTER_SWITCH, true);
    }

    public static void setServiceRunning(boolean val) {
        pref_hook.edit().putBoolean("locking", val).apply();
    }

    public static boolean isServiceRunning() {
        return pref_hook.getBoolean("locking", false);
    }

    public static void cleanPrefs() {
        pref.edit().remove(Util.SECURE_ZONE_SWITCH).commit();
        pref_hook.edit().remove(Util.SECURE_ZONE_SWITCH).commit();
        pref.edit().remove(Util.MASK_NOTIFICATIONS).commit();
        pref_hook.edit().remove(Util.MASK_NOTIFICATIONS).commit();
        pref.edit().remove(Util.PREVENT_UNINSTALL).commit();
    }

    public static boolean isDPInstalled(Context context) {
        PackageManager manager = context.getPackageManager();
        if (manager.checkSignatures(context.getPackageName(), Util.MY_PACKAGE_NAME_PRO) == 0) {
            String installer = null;
            try {
                installer = manager.getInstallerPackageName(Util.MY_PACKAGE_NAME_PRO);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (installer != null && "com.android.vending".equals(installer)) {
                return true;
            }
            cleanPrefs();
            return false;
        }
        cleanPrefs();
        return false;
    }
}
