package bhowa.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private static boolean isInit = false;

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

        if(isInit) loginLevel.setText("Login : Failed try again!  ");

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                progress.show();

                Thread taskThread = new Thread(new Runnable() {
                    public void run() {
                        doTask(v, userNameText, passwordText);
                        progress.dismiss();
                        progress.cancel();
                  }
                });
                taskThread.start();
            }
        });
    }


    private void doTask(View v, TextView userNameText, TextView passwordText)
    {
        UserDetails userLogin = new UserDetails();
        userLogin.userName = userNameText.getText().toString();
        userLogin.password = passwordText.getText().toString();
        boolean isLoginSuccess = BhowaDatabaseFactory.getDBInstance().login(userLogin);
        if (isLoginSuccess) {
            Intent homeIntent = new Intent(v.getContext(), HomeActivity.class);
            startActivity(homeIntent);
        }
    }
}
