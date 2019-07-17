package com.vipin.assessortesta.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefsManager {

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    public PrefsManager(Context context){
        sharedPref = context.getSharedPreferences(PrefsManager.class.getSimpleName(), Context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }

    public void putBoolean(String key, boolean value){
        editor.putBoolean(key, value);
        editor.commit();
    }

    public Boolean getBoolean(String key){
        return sharedPref.getBoolean(key, false);
    }

    public void putString(String key, String value){
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key){
        return sharedPref.getString(key, "");
    }

    public void putInt(String key, int value){
        editor.putInt(key, value);
        editor.commit();
    }

    public int getInt(String key){
        return sharedPref.getInt(key, -1);
    }

    public void putFloat(String key, float value){
        editor.putFloat(key, value);
        editor.commit();
    }

    public float getFloat(String key){
        return sharedPref.getFloat(key, 0.0f);
    }

    public void remove(String key){
        editor.remove(key);
        editor.commit();
    }

    public void removeAll(){
        editor.clear();
        editor.commit();
    }






}
