package com.mzhou.merchant.utlis;

import com.mzhou.merchant.activity.R;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * ���������Ƿ��������
 * 
 * @author user
 * 
 */
public class WebIsConnectUtil extends Activity {

	public static boolean isNetAvailable(Context paramContext) {
		return (isWiFiActive(paramContext))
				|| (isNetworkAvailable(paramContext));
	}

	// �����Ƿ������
	public static boolean isNetworkAvailable(Context paramContext) {
		ConnectivityManager connectivitymanager = (ConnectivityManager) paramContext
				.getSystemService("connectivity");
		NetworkInfo networkinfo = connectivitymanager.getActiveNetworkInfo();
		if (connectivitymanager != null)
			if ((networkinfo != null && networkinfo.isAvailable()))
				return true;
		return false;
	}

	public static boolean isWiFiActive(Context paramContext) {
		WifiManager wifimanager = (WifiManager) paramContext
				.getSystemService("wifi");
		WifiInfo wifiinfo = wifimanager.getConnectionInfo();
		int i;
		boolean flag1 = false;
		if (wifiinfo == null) {
			i = 0;
		} else {
			i = wifiinfo.getIpAddress();
		}

		if (wifimanager.isWifiEnabled()) {
			flag1 = false;
			if (i != 0) {
				flag1 = true;
			}
		}
		return flag1;
	}

	/**
	 * ��ʾ����״̬
	 * 
	 * @param context
	 * @return ��� Ϊtrue ��ô��������
	 */
	public static boolean showNetState(final Context context) {
		if (!isNetAvailable(context)) {
			MyUtlis.toastInfo(context,
					context.getResources().getString(R.string.nowifi));
			return false;
		} else {
			return true;
		}
	}

	public static boolean showNetStateNoDialog(Context paramContext) {
		if (!isNetAvailable(paramContext)) {
			return false;
		} else {
			return true;
		}
	}

}
