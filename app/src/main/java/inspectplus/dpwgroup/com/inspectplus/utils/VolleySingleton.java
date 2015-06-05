package inspectplus.dpwgroup.com.inspectplus.utils;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by skatgroovey on 26/05/2015.
 */
public class VolleySingleton {
    private  static  VolleySingleton sInstance=null;
    private RequestQueue mRequestQueue;

    private VolleySingleton(){
     mRequestQueue= Volley.newRequestQueue(MyApplication.getAppContext());
    }

    public static  VolleySingleton getInstance(){

        if(sInstance==null)
        {
            sInstance = new VolleySingleton();
        }
        return  sInstance;
    }
    public RequestQueue getRequestQueue(){
        return mRequestQueue;
    }
}
