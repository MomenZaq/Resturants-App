package com.resturants.resturantsapp.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.TypedValue;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.resturants.resturantsapp.R;


public class PermissionUtil {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 123;

    public static boolean checkPermissionLocation(
            final Activity activity) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(activity,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            ) {

                showDialog(activity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION});

//                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }


    private static void showDialog(final Activity activity,
                                   final String[] permission) {

        try {

            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity);
            alertBuilder.setCancelable(true);
            alertBuilder.setTitle(activity.getResources().getString(R.string.permession_needed));
            alertBuilder.setMessage(activity.getResources().getString(R.string.storage_permession_needed_to_capture_image_and_share_it));
            alertBuilder.setPositiveButton(android.R.string.yes,
                    (dialog, which) -> ActivityCompat.requestPermissions((Activity) activity,
                            permission,
                            MY_PERMISSIONS_REQUEST_LOCATION));

            AlertDialog dialog = alertBuilder.create();
            dialog.show();
            TextView textView = dialog.findViewById(android.R.id.message);
            textView.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6f, activity.getResources().getDisplayMetrics()), 1.0f);
        } catch (Exception e) {
        }

    }

}
