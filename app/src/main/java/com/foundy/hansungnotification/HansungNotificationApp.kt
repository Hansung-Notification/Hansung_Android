package com.foundy.hansungnotification

import android.Manifest
import android.app.Application
import com.eazypermissions.coroutinespermission.PermissionManager
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HansungNotificationApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Firebase.database.setPersistenceEnabled(true)
    }
}
