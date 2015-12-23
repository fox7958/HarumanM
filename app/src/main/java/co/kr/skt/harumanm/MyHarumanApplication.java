package co.kr.skt.harumanm;

import android.app.Application;
import android.content.Context;

/**
 * Created by T on 2015-12-01.
 */
public class MyHarumanApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
    public static Context getContext(){
        return mContext;
    }
}
