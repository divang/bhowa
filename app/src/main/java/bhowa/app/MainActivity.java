package bhowa.app;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import bhowa.dao.BhowaDatabaseFactory;
import bhowa.dao.mysql.impl.*;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView userNameText = (TextView) findViewById(R.id.userNameText);
        final TextView passwordText = (TextView) findViewById(R.id.passwordText);
        final TextView loginLevel = (TextView) findViewById(R.id.LoginLevel);
        final Button loginButton = (Button) findViewById(R.id.LoginButton);

        progress = ProgressDialog.show(MainActivity.this, null, "Connecting to Database  ...", true, false);
        progress.hide();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                progress.show();
                progress.setMessage("Connecting to Database  ...");

                Thread taskThread = new Thread(new Runnable() {
                    public void run() {
                        doTask(v, userNameText, passwordText);
                  }
                });
                taskThread.start();
            }
        });
    }


    private void doTask(View v, TextView userNameText, TextView passwordText)
    {
        try {
            UserDetails userLogin = new UserDetails();
            userLogin.userName = userNameText.getText().toString();
            userLogin.password = passwordText.getText().toString();
            boolean isLoginSuccess = BhowaDatabaseFactory.getDBInstance().login(userLogin);
            if (isLoginSuccess) {
                Intent homeIntent = new Intent(v.getContext(), HomeActivity.class);
                startActivity(homeIntent);
                progress.dismiss();
                progress.cancel();
            } else {
                runOnUiThread(changeMessage);
            }
        }catch (Exception e)
        {
            Log.e("Error", "Login connectivity has problem", e);
        }
    }

    private Runnable changeMessage = new Runnable() {
        @Override
        public void run() {
            progress.show();
            progress.setMessage("Login Failed!!!");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            progress.dismiss();
            progress.cancel();
        }
    };
}
