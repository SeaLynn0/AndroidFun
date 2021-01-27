/**
 * @ProjectName: AndroidFun
 * @Package: xyz.sealynn.androidfun.utils
 * @ClassName: NetWorkUtil
 * @Description: //TODO
 * @Author: Michael Lynn
 * @CreateDate: 2020/6/21 1:27
 */
package cn.mlynn.androidfun.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import cn.mlynn.androidfun.net.interceptor.CacheInterceptor;

public class NetWorkUtil {

    /**  * 判断是否有网络连接  */  public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            // 获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            // 获取NetworkInfo对象
            NetworkInfo networkInfo = null;
            if (manager != null) {
                networkInfo = manager.getActiveNetworkInfo();
            }
            //判断NetworkInfo对象是否为空
            return null != networkInfo && networkInfo.isAvailable();
        }
        return false;
    }

    /**  * 判断WIFI网络是否可用  */  public static boolean isWifiConnected(Context context) {
        if (context != null) {
            // 获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            // 获取NetworkInfo对象
            NetworkInfo networkInfo = null;
            if (manager != null) {
                networkInfo = manager.getActiveNetworkInfo();
            }
            //判断NetworkInfo对象是否为空 并且类型是否为WIFI
            if (null != networkInfo && networkInfo.getType() == ConnectivityManager.TYPE_WIFI)
                return networkInfo.isAvailable();
        }
        return false;
    }

    /**  * 判断MOBILE网络是否可用  */  public static boolean isMobileConnected(Context context) {
        if (context != null) {
            //获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            //获取NetworkInfo对象
            NetworkInfo networkInfo = null;
            if (manager != null) {
                networkInfo = manager.getActiveNetworkInfo();
            }
            //判断NetworkInfo对象是否为空 并且类型是否为MOBILE
            if (null != networkInfo && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
                return networkInfo.isAvailable();
        }
        return false;
    }
}
