package societyhelp.app.util;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

/**
 * Created by divang.sharma on 9/18/2016.
 */
public class Util {

    public static void CustomToast(Context context, String msg, int showTimeInMilis)
    {
        final Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, showTimeInMilis);
    }
}
