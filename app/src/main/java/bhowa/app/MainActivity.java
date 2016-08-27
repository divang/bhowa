package bhowa.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import bhowa.dao.BhowaDatabaseFactory;
import bhowa.dao.mysql.impl.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView userNameText = (TextView) findViewById(R.id.userNameText);
        final TextView passwordText = (TextView) findViewById(R.id.passwordText);
        final TextView loginLevel = (TextView) findViewById(R.id.LoginLevel);

        final Button loginButton = (Button) findViewById(R.id.LoginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDetails userLogin = new UserDetails();
                userLogin.userName = userNameText.getText().toString();
                userLogin.password = passwordText.getText().toString();

                boolean isLoginSuccess = BhowaDatabaseFactory.getDBInstance().login( userLogin);
                //boolean isLoginSuccess = true;
                if (isLoginSuccess) {
                    //Intent uploadPDFIntent = new Intent(v.getContext(), UploadPDfActivity.class);
                    //startActivityForResult(uploadPDFIntent, 0);
                    Intent homeIntent = new Intent(v.getContext(), HomeActivity.class);
                    startActivity(homeIntent);
                } else {
                    loginLevel.setText("Login : Try again!");
                }
            }
        });
    }
}
