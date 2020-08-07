package com.example.breast_app;

import android.content.Context;
import android.preference.PreferenceManager;

public class Utils {

	public static final boolean putBoolean(Context context, String key, boolean value) {
		return PreferenceManager.getDefaultSharedPreferences(context).edit()
				.putBoolean(key, value).commit();
	}

	public static final boolean getBoolean(Context context, String key, boolean defaultBool) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(key, defaultBool);
	}

	public static final boolean putLong(Context context, String key, long value) {
		return PreferenceManager.getDefaultSharedPreferences(context).edit()
				.putLong(key, value).commit();
	}

	public static final long getLong(Context context, String key) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getLong(key, 0);
	}



}
