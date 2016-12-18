package dualshock.gabriel.gankio.util;

import android.app.Application;

import com.tencent.smtt.sdk.QbSdk;


public class GankApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        QbSdk.initX5Environment(this, null);
    }
}
