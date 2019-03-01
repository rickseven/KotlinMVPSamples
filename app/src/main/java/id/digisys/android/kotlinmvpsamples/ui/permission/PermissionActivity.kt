package id.digisys.android.kotlinmvpsamples.ui.permission

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import id.digisys.android.kotlinmvpsamples.R
import id.digisys.android.kotlinmvpsamples.ui.main.MainActivity

class PermissionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)

        if (checkAndRequestPermissions()) {
            Handler().postDelayed({
                val i = Intent(this@PermissionActivity, MainActivity::class.java)
                startActivity(i)
                finish()
            }, SPLASH_TIME_OUT.toLong())
        }
    }

    private fun checkAndRequestPermissions(): Boolean {
        val internetPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
        val readCalendarPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR)
        val writeCalendarPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR)

        val listPermissionsNeeded = ArrayList<String>()

        if (internetPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.INTERNET)
        }
        if (readCalendarPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_CALENDAR)
        }
        if (writeCalendarPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_CALENDAR)
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toTypedArray(), REQUEST_ID_MULTIPLE_PERMISSIONS)
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        Log.d(TAG, "Permission callback called")
        when (requestCode) {
            REQUEST_ID_MULTIPLE_PERMISSIONS -> {

                val perms = HashMap<String, Int>()
                perms[Manifest.permission.INTERNET] = PackageManager.PERMISSION_GRANTED
                perms[Manifest.permission.READ_CALENDAR] = PackageManager.PERMISSION_GRANTED
                perms[Manifest.permission.WRITE_CALENDAR] = PackageManager.PERMISSION_GRANTED

                if (grantResults.isNotEmpty()) {

                    for (i in permissions.indices)
                        perms[permissions[i]] = grantResults[i]

                    if (perms[Manifest.permission.INTERNET] == PackageManager.PERMISSION_GRANTED
                        && perms[Manifest.permission.READ_CALENDAR] == PackageManager.PERMISSION_GRANTED
                        && perms[Manifest.permission.WRITE_CALENDAR] == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "Internet & calendar services permission granted")

                        val i = Intent(this@PermissionActivity, MainActivity::class.java)
                        startActivity(i)
                        finish()
                    } else {
                        Log.d(TAG, "Some permissions are not granted ask again")
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)
                            || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CALENDAR)
                            || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_CALENDAR)) {
                            showDialogOK(getString(R.string.permission_required),
                                DialogInterface.OnClickListener { _, which ->
                                    when (which) {
                                        DialogInterface.BUTTON_POSITIVE -> checkAndRequestPermissions()
                                        DialogInterface.BUTTON_NEGATIVE -> finish()
                                    }
                                })
                        } else {
                            explain(getString(R.string.need_mandatory_permission))
                        }
                    }
                }
            }
        }

    }

    private fun showDialogOK(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", okListener)
            .create()
            .show()
    }

    private fun explain(msg: String) {
        val dialog = AlertDialog.Builder(this)
        dialog.setMessage(msg)
            .setPositiveButton("Yes") { _, _ ->
                startActivity(Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:id.digisys.android.footballclub")))
            }
            .setNegativeButton("Cancel") { _, _ -> finish() }
        dialog.show()
    }

    companion object {
        private const val TAG = "PermissionActivity"
        private const val REQUEST_ID_MULTIPLE_PERMISSIONS = 1
        private const val SPLASH_TIME_OUT = 2000
    }
}
