package inspectplus.dpwgroup.com.inspectplus.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by skatgroovey on 26/05/2015.
 */
public class L {
    public static void m(String message){
        Log.d("Dan", " " + message);}

    public static  void t(Context context, String message){
        Toast.makeText(context, message + "", Toast.LENGTH_LONG).show();

    }
}
