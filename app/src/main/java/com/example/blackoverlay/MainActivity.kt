package com.example.blackoverlay


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private val REQUEST_CODE_SYSTEM_ALERT_WINDOW = 1234
    private val REQUEST_CODE_PERMISSIONS = 5678

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkAndRequestPermissions()
    }

    private fun checkAndRequestPermissions() {
        if (checkPermissions()) {
            checkOverlayPermission()
            startYourService()
        } else {
            // Handle case when permission is not granted.
            // You might want to request the permissions again or inform the user.
        }
    }

    private fun checkPermissions(): Boolean {
        val permissionsToRequest = arrayListOf<String>()
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SYSTEM_ALERT_WINDOW) != PackageManager.PERMISSION_GRANTED) {
            permissionsToRequest.add(Manifest.permission.SYSTEM_ALERT_WINDOW)
        }

        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsToRequest.toTypedArray(), REQUEST_CODE_PERMISSIONS)
            return false
        }
        return true
    }

    private fun checkOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
            startActivityForResult(intent, REQUEST_CODE_SYSTEM_ALERT_WINDOW)
        }
    }

    private fun startYourService() {
        val serviceIntent = Intent(this, TimerService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent)
        } else {
            startService(serviceIntent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SYSTEM_ALERT_WINDOW) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(this)) {
                // Permission granted, proceed with your code.
            } else {
                // Permission denied, handle accordingly.
            }
        }
    }
}



//
////import TimerService
//import android.Manifest
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.net.Uri
//import android.os.Build
//import android.os.Bundle
//import android.provider.Settings
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//
//class MainActivity : AppCompatActivity() {
//    private val REQUEST_CODE_SYSTEM_ALERT_WINDOW = 1234
//    private val REQUEST_CODE_PERMISSIONS = 5678
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        if (checkPermissions()) {
//            checkOverlayPermission()
//            startYourService()
//        } else {
//            // Handle case when permission is not granted.
//            // You might want to request the permissions again or inform the user.
//        }
//    }
//
//    private fun checkPermissions(): Boolean {
//        val permissionsToRequest = arrayListOf<String>()
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SYSTEM_ALERT_WINDOW) != PackageManager.PERMISSION_GRANTED) {
//            permissionsToRequest.add(Manifest.permission.SYSTEM_ALERT_WINDOW)
//        }
//
//        if (permissionsToRequest.isNotEmpty()) {
//            ActivityCompat.requestPermissions(this, permissionsToRequest.toTypedArray(), REQUEST_CODE_PERMISSIONS)
//            return false
//        }
//        return true
//    }
//
//    private fun checkOverlayPermission() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
//            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
//            startActivityForResult(intent, REQUEST_CODE_SYSTEM_ALERT_WINDOW)
//        }
//    }
//
//    private fun startYourService() {
//        val serviceIntent = Intent(this, TimerService::class.java)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            startForegroundService(serviceIntent)
//        } else {
//            startService(serviceIntent)
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQUEST_CODE_SYSTEM_ALERT_WINDOW) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(this)) {
//                // Permission granted, proceed with your code.
//            } else {
//                // Permission denied, handle accordingly.
//            }
//        }
//    }
//}

//
//private val REQUEST_CODE_SYSTEM_ALERT_WINDOW = 1234
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        // Check for SYSTEM_ALERT_WINDOW permission
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
//            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
//            startActivityForResult(intent, REQUEST_CODE_SYSTEM_ALERT_WINDOW)
//        } else {
//            // Permission is already granted, proceed with your code.
//            // You might place your overlay code here or a method call for handling the overlays.
//            // Be sure to do this according to your application logic.
//        }
//        // Start the background service
//        val serviceIntent = Intent(this, TimerService::class.java)
//        startService(serviceIntent)
//    }
//}
// fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//    super.onActivityResult(requestCode, resultCode, data)
//
//    if (requestCode == REQUEST_CODE_SYSTEM_ALERT_WINDOW) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(this)) {
//            // Permission granted, proceed with your code.
//            // You might place your overlay code here or a method call for handling the overlays.
//            // Be sure to do this according to your application logic.
//        } else {
//            // Permission denied, handle accordingly.
//        }
//    }
//}
//
////@Composable
////fun Greeting(name: String, modifier: Modifier = Modifier) {
////    Text(
////        text = "Hello $name!",
////        modifier = modifier
////    )
////}
////
////@Preview(showBackground = true)
////@Composable
////fun GreetingPreview() {
////    BlackOverlayTheme {
////        Greeting("Android")
////    }
////}