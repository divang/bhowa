package bhowa.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

        final TextView userIdLevel = (TextView) findViewById(R.id.textView);
        final TextView paswwordLevel = (TextView) findViewById(R.id.textView2);


        final Button loginButton = (Button) findViewById(R.id.LoginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserLogin userLogin = new UserLogin();
                userLogin.userName = userNameText.getText().toString();
                userLogin.password = passwordText.getText().toString();
                boolean isLoginSuccess = BhowaDatabaseFactory.getDBInstance().login( userLogin);
//              boolean isLoginSuccess = true;
                if (isLoginSuccess) {
                    loginLevel.setText("Successful login!!!");
                    passwordText.setVisibility(View.INVISIBLE);
                    userNameText.setVisibility(View.INVISIBLE);
                    loginButton.setVisibility(View.INVISIBLE);
                    userIdLevel.setVisibility(View.INVISIBLE);
                    paswwordLevel.setVisibility(View.INVISIBLE);
                } else {
                    loginLevel.setText("Login : Try again!");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
