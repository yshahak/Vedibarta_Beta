package org.vedibarta.app.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by e560 on 07/06/17.
 */

public class Utils {

    public static boolean isConnected(Context context) {
        NetworkInfo info = ((ConnectivityManager)context.getSystemService(CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return (info != null) && (info.isConnected());
    }

    public static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}
