package societyhelp.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import societyhelp.dao.SocietyHelpDatabaseFactory;

public class CreateLoginIdActivity extends DashBoardActivity {

    ProgressDialog progress;
    Button loginButton;
    boolean isLoginIdValidate = false;
    boolean isPasswordValidate = false;
    boolean isConfirmPasswordEnter = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_login_id);
        setHeader("", true, false);

        final EditText loginIdText = (EditText) findViewById(R.id.loginId_CLA_Text);
        final EditText passwordText = (EditText) findViewById(R.id.password_CLA_Text);
        final EditText confirmPasswordText = (EditText) findViewById(R.id.confirm_password_CLA_Text);
        loginButton = (Button) findViewById(R.id.submit_CLA_Btn);

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
                            SocietyHelpDatabaseFactory.getMasterDBInstance().createLogin(loginIdText.getText().toString(), passwordText.getText().toString(), getLoginId());
                            Intent intent = new Intent(getApplicationContext(), ManageLoginActivity.class);
                            startActivity(intent);
                        } catch (Exception e) {
                            Log.e("Error", "Login creation failed", e);
                        }
                        progress.dismiss();
                        progress.cancel();
                    }
                });
                taskThread.start();
            }
        });

        loginIdText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginButton.setEnabled(false);
                loginButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.btnDisableColor));
                if (s.toString().length() > 0 && s.toString().length() < 10) {

                    isLoginIdValidate = true;
                    if (isPasswordValidate) {
                        loginButton.setEnabled(true);
                        loginButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.btnEnableColor));
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Login Id length should be in between 0 and 10.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        passwordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginButton.setEnabled(false);
                loginButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.btnDisableColor));
                if (s.toString().length() > 5 && s.toString().length() <= 10) {

                    if (isConfirmPasswordEnter) {
                        if (confirmPasswordText.getText().toString().equals(passwordText.getText().toString())) {
                            isPasswordValidate = true;
                            if (isLoginIdValidate) {
                                loginButton.setEnabled(true);
                                loginButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.btnEnableColor));
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Password and Confirm Password should match.", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Login Id length should be in between 0 and 10.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        confirmPasswordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                loginButton.setEnabled(false);
                loginButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.btnDisableColor));
                if(s.toString().length() > 5 && s.toString().length() <= 10) {

                    if(confirmPasswordText.getText().toString().equals(passwordText.getText().toString()))
                    {
                        isPasswordValidate = true;
                        isConfirmPasswordEnter = true;
                        if(isLoginIdValidate) {
                            loginButton.setEnabled(true);
                            loginButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.btnEnableColor));
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Password and Confirm Password should match.", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Login Id length should be in between 0 and 10.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
