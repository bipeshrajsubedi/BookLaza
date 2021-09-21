package com.example.booklaza;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

public class Config {
    public static String USER_ID = "";
    public static String API_BOOKS = "https://www.googleapis.com/books/v1/volumes?q=ai&key=AIzaSyBPQzP_WaKpdMMs691mrT0rYFWZ0VqEZA4";

    // Check network connection
    public static boolean checkNetworkStatus(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            Network nw = cm.getActiveNetwork();
            if(nw == null) return false;
            NetworkCapabilities activeNetwork = cm.getNetworkCapabilities(nw);
            return activeNetwork !=null && (activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));
        }
        else{
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
    }
}
