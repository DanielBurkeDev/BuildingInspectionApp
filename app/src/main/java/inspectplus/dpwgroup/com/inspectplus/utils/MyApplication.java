package inspectplus.dpwgroup.com.inspectplus.utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by skatgroovey on 26/05/2015.
 */
public class MyApplication extends Application {
    public static final String API_KEY_TMDB = "0615a2092a2e315bc3708253bf4c0dc8";
    public static final String API_COMMAND = "login";
    public static final String API_USERNAME = "jsmith@dpw-group.com";
    public static final String API_PW = "f5489cd12b8b28f2825bff08c709ae6be7193707";

    private static  MyApplication sInstance;

    @Override
    public  void onCreate(){
        super.onCreate();
        sInstance=this;
    }

    public static  MyApplication getsInstance(){
        return  sInstance;
    }

    public  static Context getAppContext(){
        return  sInstance.getApplicationContext();

    }
}
