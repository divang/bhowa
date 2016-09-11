package societyhelp.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import societyhelp.dao.SocietyHelpDatabaseFactory;

public class CreateLoginIdActivity extends DashBoardActivity {

    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_login_id);
        setHeader("", true);

        final TextView loginIdText = (TextView) findViewById(R.id.loginId_CLA_Text);
        final TextView passwordText = (TextView) findViewById(R.id.password_CLA_Text);
        final Button loginButton = (Button) findViewById(R.id.submit_CLA_Btn);

        progress = ProgressDialog.show(CreateLoginIdActivity.this, null, "Creating login in Database  ...", true, false);
        progress.setCancelable(true);
        progress.hide();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                progress.show();

                Thread taskThread = new Thread(new Runnable() {
                    public void run() {
                        try {
                            SocietyHelpDatabaseFactory.getDBInstance().createLogin(loginIdText.getText().toString(), passwordText.getText().toString());
                            Intent intent = new Intent(getApplicationContext(), ManageLoginActivity.class);
                            startActivity(intent);
                        }catch (Exception e)
                        {
                            Log.e("Error", "Login creation failed", e);
                        }
                        progress.dismiss();
                        progress.cancel();
                    }
                });
                taskThread.start();
            }
        });


    }

}
