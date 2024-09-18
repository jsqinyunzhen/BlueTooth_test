package com.example.caijiapp;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

public class APP extends Application {
    private static final Handler sHandler = new Handler();
    private static Toast sToast; // 单例Toast,避免重复创建，显示时间过长
    public static Context mContext;

    @SuppressLint("ShowToast")
    @Override
    public void onCreate() {
        super.onCreate();
        sToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        mContext = this;
    }
    public static void newtoast(Context context)
    {
        sToast = new Toast(context);
    }
    public static void toast(String txt, int duration) {
        if(sToast != null)
        {
            sToast.setText(txt);
            sToast.setDuration(duration);
            sToast.show();
        }

    }

    public static void runUi(Runnable runnable) {
        sHandler.post(runnable);
    }
}
