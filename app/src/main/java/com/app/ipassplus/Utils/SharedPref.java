package com.app.ipassplus.Utils;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
public class SharedPref {

    private static String pref_name = "ipass_processing_preference";
    private static SharedPreferences pref;
    private Context context;

    ////**** language

    private static String prefLanguage_name = "ipass__language_preference";
    private static SharedPreferences prefLanguage;

    public SharedPref(Context ctx) {
        this.context = ctx;
    }

    public  void setString(String var_name, String var_value) {
        SharedPreferences pref = context.getSharedPreferences (pref_name,
                Context.MODE_PRIVATE);
        Editor edit = pref.edit ();
        edit.putString (var_name, var_value);
        edit.apply ();
    }

    public  String getString(String var_name) {
        SharedPreferences pref = context.getSharedPreferences (pref_name,
                Context.MODE_PRIVATE);
        return pref.getString (var_name, "");
    }

    public  void setInt(String var_name, int var_value) {
        SharedPreferences pref = context.getSharedPreferences (pref_name,
                Context.MODE_PRIVATE);
        Editor edit = pref.edit ();
        edit.putInt (var_name, var_value);
        edit.apply ();
    }

    public int getInt(String var_name) {
        SharedPreferences pref = context.getSharedPreferences (pref_name,
                Context.MODE_PRIVATE);
        return pref.getInt (var_name, 0);
    }

    public  void setBoolean(String var_name, boolean var_value) {
        SharedPreferences pref = context.getSharedPreferences (pref_name,
                Context.MODE_PRIVATE);
        Editor edit = pref.edit ();
        edit.putBoolean (var_name, var_value);
        edit.apply ();
    }

    public  boolean getBoolean(String var_name) {
        SharedPreferences pref = context.getSharedPreferences (pref_name,
                Context.MODE_PRIVATE);
        return pref.getBoolean (var_name, false);
    }

    public int getSelectedPosition(String var_name) {
        SharedPreferences pref=context.getSharedPreferences(pref_name,
                Context.MODE_PRIVATE);
        return pref.getInt(var_name,0);
    }

    public void saveSelectedPosition(int position) {
        SharedPreferences pref=context.getSharedPreferences(pref_name,context.MODE_PRIVATE);
        Editor edit = pref.edit ();
        edit.putInt ("",0);
        edit.apply ();
    }


    public  boolean getDefaultTrue(String var_name) {
        SharedPreferences pref = context.getSharedPreferences (pref_name,
                Context.MODE_PRIVATE);
        return pref.getBoolean (var_name, false);
    }

    public  void clearData() {
        pref = context.getSharedPreferences (pref_name, Context.MODE_PRIVATE);
        Editor edit = pref.edit ();
        edit.clear ();
        edit.apply ();
    }

    public void saveStoreId(String store, String storeId) {

        SharedPreferences pref = context.getSharedPreferences (pref_name,
                Context.MODE_PRIVATE);
        Editor edit = pref.edit ();
        edit.putString (store, storeId);
        edit.apply ();
    }

    public String getStoreId(String store) {
        SharedPreferences pref = context.getSharedPreferences (pref_name,
                Context.MODE_PRIVATE);
        return pref.getString (store, "");
    }


    public void setPushChatButton(String chatPushNotification, Boolean statusEnableDisable) {

        SharedPreferences pref = context.getSharedPreferences (pref_name,
                Context.MODE_PRIVATE);
        Editor edit = pref.edit();
        edit.putBoolean (chatPushNotification, statusEnableDisable);
        edit.apply();
    }

    public Boolean getPushChatButton(String chatPushNotification) {
        SharedPreferences pref = context.getSharedPreferences (pref_name,
                Context.MODE_PRIVATE);
        return pref.getBoolean(chatPushNotification, true);
    }



             //////******* for language

    public  void clearLanguageData() {
        prefLanguage = context.getSharedPreferences (prefLanguage_name, Context.MODE_PRIVATE);
        Editor edit = prefLanguage.edit ();
        edit.clear ();
        edit.apply ();
    }

    public void saveLanguage(String key, String value) {

        SharedPreferences pref = context.getSharedPreferences (prefLanguage_name,
                Context.MODE_PRIVATE);
        Editor edit = pref.edit ();
        edit.putString (key, value);
        edit.apply ();
    }

    public String getLanguage(String key) {
        SharedPreferences pref = context.getSharedPreferences (prefLanguage_name, Context.MODE_PRIVATE);
        return pref.getString (key, "en");
    }




}