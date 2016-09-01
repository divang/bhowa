package bhowa.app;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
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

    //Permision code that will be checked in the method onRequestPermissionsResult
    private int STORAGE_PERMISSION_CODE = 23;

    //We are calling this method to check the permission status
    private boolean isReadStorageAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }

    //Requesting permission
    private void requestStoragePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if(requestCode == STORAGE_PERMISSION_CODE){

            //If permission is granted
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                //Displaying a toast
                Toast.makeText(this,"Permission granted now you can read the storage",Toast.LENGTH_LONG).show();
            }else{
                //Displaying another toast if permission is not granted
                Toast.makeText(this,"Oops you just denied the permission",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //First checking if the app is already having the permission
        if(isReadStorageAllowed()){
            //If permission is already having then showing the toast
            Toast.makeText(MainActivity.this,"You already have the permission",Toast.LENGTH_LONG).show();
            //Existing the method with return
        }
        else {
            //If the app has not the permission then asking for the permission
            requestStoragePermission();
            return;
        }

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
