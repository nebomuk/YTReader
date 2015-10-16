package com.taiko000.ytreader

import android.app.Application
import android.content.Context

import com.pixplicity.easyprefs.library.Prefs


/**
 * this class is used for global initialization stuff

 * for application global preferences use SharedPreferences

 * for global application states use Services
 */

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // initialization (libraries etc.) goes here

        Prefs.Builder().setContext(this).setMode(Context.MODE_PRIVATE).setPrefsName(packageName).setUseDefaultSharedPreference(true).build()
    }

}
