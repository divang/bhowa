package societyhelp.app.mini.creation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.List;

import societyhelp.app.advance.CreateAdminLoginIdActivity;
import societyhelp.app.R;
import societyhelp.app.util.SocietyHelpConstant;
import societyhelp.dao.SocietyHelpDatabaseFactory;
import societyhelp.dao.mysql.impl.Login;

public class SetupHintsMiniActivity extends AppCompatActivity implements SocietyHelpConstant {

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_status_message);
        setCustomToolBar();
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String societyId = prefs.getString(CONST_SOCIETY_ID_KEY_PREF_MANAGER, "");
        Log.d("info", "societyId : " + societyId);

        List<Login> logins = null;
        try {
            logins = SocietyHelpDatabaseFactory.getMasterDBInstance().getAllLogins(societyId);
            Log.d("info", "all logins : " + societyId);

        } catch (Exception e) {
            Log.e("error", e.getMessage(),e);
        }
        Log.i("info","all logins:" + logins);


    }

    protected void setCustomToolBar() {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        //displaying custom ActionBar
        View mActionBarView = getLayoutInflater().inflate(R.layout.login_tool_bar, null);
        actionBar.setCustomView(mActionBarView);
        actionBar.setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
    }

    public void createAdminLoginId(View v) {
        try {
            String societyId = prefs.getString(CONST_SOCIETY_ID_KEY_PREF_MANAGER, "");
            Log.d("info", "societyId : " + societyId);

            List<Login> logins = SocietyHelpDatabaseFactory.getMasterDBInstance().getAllLogins(societyId);
            Log.i("info","all logins:" + logins);

            Log.d("DEBUG","creating admin login...");
            Intent societyIntent = new Intent(v.getContext(), CreateAdminLoginMiniActivity.class);
            startActivity(societyIntent);
        } catch (Exception e) {
            Log.e("Error", "Admin Login creation failed", e);
        }
    }
}
