package societyhelp.app.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by divang.sharma on 9/13/2016.
 */
public class PropertyReader {

    public static String getProperty(String key,Context context) {
        try {
            Properties properties = new Properties();
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open("do_not_commit_config.properties");
            properties.load(inputStream);
            return properties.getProperty(key);
        } catch (Exception e)
        {
            Log.e("Error", "do_not_commit_config.properties file has some problem.", e);
        }
        return null;
    }
}
