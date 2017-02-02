package societyhelp.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import societyhelp.app.util.PropertyReader;
import societyhelp.app.util.SocietyHelpConstant;
import societyhelp.app.util.Util;
import societyhelp.dao.SocietyHelpDatabaseFactory;
import societyhelp.dao.mysql.impl.SocietyDetails;
import societyhelp.dao.mysql.impl.UserDetails;

public class CreateSocietyActivity extends AppCompatActivity {

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_society);
        setCustomToolBar();

        final TextView societyName = (TextView) findViewById(R.id.society_name_CS);
        final TextView emailId = (TextView) findViewById(R.id.emailId_CS);
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
                socDetail.city = "";
                socDetail.country = "";
                socDetail.emailId = emailId.getText().toString();
                socDetail.societyName = societyName.getText().toString();

                progress = ProgressDialog.show(CreateSocietyActivity.this, null, "Storing society details and we will get back to you soon over mail ...", true, false);
                progress.setCancelable(true);
                progress.show();

                Thread taskThread = new Thread(new Runnable() {
                    public void run() {
                        try {
                            initDB();
                            SocietyHelpDatabaseFactory.getDBInstance().createSociety(socDetail);
                            progress.dismiss();
                            progress.cancel();

                            Intent homeIntent = new Intent(v.getContext(), StatusMessageActivity.class);
                            startActivity(homeIntent);

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
        Log.d("----------  Database", "dbUrl-" + dbUrl + " dbUser-" + dbUser + " dbPass-" + dbPass);
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
