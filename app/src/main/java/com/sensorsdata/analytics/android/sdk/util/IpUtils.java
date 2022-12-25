package com.sensorsdata.analytics.android.sdk.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Enumeration;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class IpUtils {
    private static String[] platforms = {"http://pv.sohu.com/cityjson", "http://pv.sohu.com/cityjson?ie=utf-8", "http://ip.chinaz.com/getip.aspx"};

    public static String getIPAddress(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            return null;
        }
        if (activeNetworkInfo.getType() == 0) {
            try {
                Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
                while (networkInterfaces.hasMoreElements()) {
                    Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress nextElement = inetAddresses.nextElement();
                        if (!nextElement.isLoopbackAddress() && (nextElement instanceof Inet4Address)) {
                            return nextElement.getHostAddress();
                        }
                    }
                }
                return null;
            } catch (SocketException e) {
                e.printStackTrace();
                return null;
            }
        } else if (activeNetworkInfo.getType() != 1) {
            return null;
        } else {
            return intIP2StringIP(((WifiManager) context.getSystemService(AopConstants.WIFI)).getConnectionInfo().getIpAddress());
        }
    }

    public static String intIP2StringIP(int i) {
        return (i & 255) + "." + ((i >> 8) & 255) + "." + ((i >> 16) & 255) + "." + ((i >> 24) & 255);
    }

    public static String getOutNetIP(Context context, int i) {
        String[] strArr = platforms;
        if (i < strArr.length) {
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(strArr[i]).openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setUseCaches(false);
                if (httpURLConnection.getResponseCode() == 200) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
                    StringBuilder sb = new StringBuilder();
                    while (true) {
                        String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        }
                        sb.append(readLine);
                    }
                    bufferedReader.close();
                    httpURLConnection.disconnect();
                    if (i != 0 && i != 1) {
                        if (i == 2) {
                            return new JSONObject(sb.toString()).getString("ip");
                        }
                    }
                    return new JSONObject(sb.substring(sb.indexOf("{"), sb.indexOf("}") + 1)).getString("cip");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return getOutNetIP(context, i + 1);
        }
        return getIPAddress(context);
    }

    public static JSONObject getAddress(String str) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("http://ip.taobao.com/service/getIpInfo.php?ip=" + str).openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setUseCaches(false);
            if (httpURLConnection.getResponseCode() != 200) {
                return null;
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    sb.append(readLine);
                } else {
                    bufferedReader.close();
                    httpURLConnection.disconnect();
                    return new JSONObject(sb.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
