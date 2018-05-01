package io.rpng.calibration.managers;


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import io.rpng.calibration.dialogs.ConfirmationDialog;

public class PermissionManager {

    private Activity activity;
    private static final int REQUEST_VIDEO_PERMISSIONS = 1;
    private static final String FRAGMENT_DIALOG = "dialog";

    private String[] VIDEO_PERMISSIONS;


    /**
     * Default constructor, sets our position and activity
     */
    public PermissionManager(Activity activity, String[] VIDEO_PERMISSIONS) {
        this.activity = activity;
        this.VIDEO_PERMISSIONS = VIDEO_PERMISSIONS;
    }


    /**
     * This function handles requesting of permissions
     * If it does not have permissions it will request them as needed
     */
    public boolean handle_permissions() {
        // If we do not have perms, request them
        if(!hasPermissionsGranted(VIDEO_PERMISSIONS)) {
            requestVideoPermissions();
            return true;
        }
        // Else we are good to go
        return false;
    }


    /**
     * This method checks if permissions have been granted to this application
     * If will return false if it does not have the correct permissions
     */
    private boolean hasPermissionsGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * Requests permissions needed for recording video.
     * We can also use this to make sure that we can get images recorded
     */
    private void requestVideoPermissions() {
        if (shouldShowRequestPermissionRationale(VIDEO_PERMISSIONS)) {
            new ConfirmationDialog().show(activity.getFragmentManager(), FRAGMENT_DIALOG);
        } else {
            ActivityCompat.requestPermissions(activity, VIDEO_PERMISSIONS, REQUEST_VIDEO_PERMISSIONS);
        }
    }

    /**
     * Gets whether you should show UI with rationale for requesting permissions.
     *
     * @param permissions The permissions your app wants to request.
     * @return Whether you can show permission rationale UI.
     */
    private boolean shouldShowRequestPermissionRationale(String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                return true;
            }
        }
        return false;
    }
}
