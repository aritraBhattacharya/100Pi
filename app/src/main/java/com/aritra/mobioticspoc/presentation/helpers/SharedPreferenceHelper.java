package com.aritra.mobioticspoc.presentation.helpers;

import android.content.Context;

public class SharedPreferenceHelper {

    public static final String CURRENT_INDEX = "current_index";
    public static final String PI_PREF = "pi_pref";
    private final Context mContext;

    public SharedPreferenceHelper(Context mContext) {
        this.mContext = mContext;
    }

    public void setCurrentIndex(int index){
        mContext.getSharedPreferences(PI_PREF, Context.MODE_PRIVATE)
                .edit().putInt(CURRENT_INDEX, index).apply();
    }
    public int getCurrentIndex(){
        return mContext.getSharedPreferences(PI_PREF, Context.MODE_PRIVATE)
                .getInt(CURRENT_INDEX, 0);
    }
}
