package societyhelp.app.mini.creation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import societyhelp.app.R;
import societyhelp.app.advance.ManageFlatWisePayableActivity;
import societyhelp.app.util.PropertyReader;
import societyhelp.app.util.SocietyHelpConstant;
import societyhelp.app.util.Util;
import societyhelp.dao.SocietyHelpDatabaseFactory;
import societyhelp.dao.mysql.impl.Login;
import societyhelp.dao.mysql.impl.SocietyDetails;

public class CreateSocietyMiniActivity extends AppCompatActivity implements SocietyHelpConstant{

    private ProgressDialog progress;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setContentView(R.layout.activity_mini_create_society);
        setCustomToolBar();

        final TextView societyName = (TextView) findViewById(R.id.society_name_CS);
        final TextView emailId = (TextView) findViewById(R.id.emailId_CS);
        final TextView city = (TextView) findViewById(R.id.city_CS);
        final TextView coutry = (TextView) findViewById(R.id.country_CS);
        final TextView mobileNumber = (TextView) findViewById(R.id.mobile_CS);

        final Button createSocietyButton = (Button) findViewById(R.id.CreateSocietyButton);

        createSocietyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (emailId.getText().toString().length() == 0) {
                    Util.CustomToast(getApplicationContext(), "Please write valid email id", 1000);
                    return;
                } else if (emailId.getText().toString().length() > 0 &&
                        !android.util.Patterns.EMAIL_ADDRESS.matcher(emailId.getText().toString()).matches()) {
                    Util.CustomToast(getApplicationContext(), "Invalid Mail Id", 1000);
                    return;
                } else if (societyName.getText().toString().length() == 0) {
                    Util.CustomToast(getApplicationContext(), "Please add society name", 1000);
                    return;
                }

                final SocietyDetails socDetail = new SocietyDetails();
                socDetail.city = city.getText().toString();
                socDetail.country = coutry.getText().toString();
                socDetail.mobileNo = mobileNumber.getText().toString();
                socDetail.emailId = emailId.getText().toString();
                socDetail.societyName = societyName.getText().toString();

                progress = ProgressDialog.show(CreateSocietyMiniActivity.this, null, "Creating society ...", true, false);
                progress.setCancelable(true);
                progress.show();

                Thread taskThread = new Thread(new Runnable() {
                    public void run() {
                        try {
                            initDB();
                            String societyId = SocietyHelpDatabaseFactory.getDBInstance().createSociety(socDetail);
                            Log.d("DEBUG","Created society Id:" + societyId);
                            prefs.edit().putString(CONST_SOCIETY_ID_KEY_PREF_MANAGER, societyId).commit();
                            progress.dismiss();
                            progress.cancel();

                            List<Login> logins = SocietyHelpDatabaseFactory.getMasterDBInstance().getAllLogins(societyId);
                            Log.i("info","all logins:" + logins);
                            Intent intentCreateLogin = new Intent(getApplicationContext(), SetupHintsMiniActivity.class);
                            intentCreateLogin.putExtra(CONST_LOGIN_IDS, Login.getLogIds(logins));
                            intentCreateLogin.putExtra(CONST_SOCIETY_ID_KEY_PREF_MANAGER, societyId);
                            startActivity(intentCreateLogin);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                taskThread.start();
            }
        });
    }

    //Set the Master Database for login
    private void initDB() {
        String dbUrl = PropertyReader.getProperty(SocietyHelpConstant.CONST_DB_URL, getApplicationContext());
        String dbUser = PropertyReader.getProperty(SocietyHelpConstant.CONST_DB_USER, getApplicationContext());
        String dbPass = PropertyReader.getProperty(SocietyHelpConstant.CONST_DB_PASSWORD, getApplicationContext());
        SocietyHelpDatabaseFactory.init(dbUrl, dbUser, dbPass);
        SocietyHelpDatabaseFactory.initMasterDB(dbUrl, dbUser, dbPass);
    }

    protected void setCustomToolBar() {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        //displaying custom ActionBar
        View mActionBarView = getLayoutInflater().inflate(R.layout.login_tool_bar, null);
        actionBar.setCustomView(mActionBarView);
        actionBar.setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
    }
}
