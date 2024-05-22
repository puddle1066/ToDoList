package com.paullanducci.todolist.util

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

object PermissionUtils {
    private val TAG = PermissionUtils::class.java.simpleName

    /**
     * @param context the Android context
     * @param permissions an array of permissions to check (can be empty)
     * @return true if ALL of the provided permissions are granted when checking with [ActivityCompat.checkSelfPermission], false otherwise. So returns
     * true if the provided permissions array is empty.
     */
    fun checkPermissions(context: Context, vararg permissions: String): Boolean {
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission)
                != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

}