package societyhelp.app.mini.creation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import societyhelp.app.advance.ManageLoginActivity;
import societyhelp.app.R;
import societyhelp.app.mini.dashboard.DashBoardMiniActivity;
import societyhelp.app.util.CustomSerializer;
import societyhelp.app.util.Util;
import societyhelp.dao.SocietyHelpDatabaseFactory;
import societyhelp.dao.mysql.impl.Login;

public class CreateLoginMiniActivity extends DashBoardMiniActivity {

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
        final String loginIds = (String) getIntent().getSerializableExtra(CONST_LOGIN_IDS);
        final EditText loginIdText = (EditText) findViewById(R.id.loginId_CLA_Text);
        final EditText passwordText = (EditText) findViewById(R.id.password_CLA_Text);
        final EditText confirmPasswordText = (EditText) findViewById(R.id.confirm_password_CLA_Text);
        loginButton = (Button) findViewById(R.id.submit_CLA_Btn);

        progress = ProgressDialog.show(CreateLoginMiniActivity.this, null, "Creating login in Database  ...", true, false);
        progress.setCancelable(true);
        progress.hide();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                progress.show();

                Thread taskThread = new Thread(new Runnable() {
                    public void run() {
                        try {
                            Login login = new Login();
                            login.isAdmin = false;
                            login.loginId = loginIdText.getText().toString();
                            login.password = passwordText.getText().toString();
                            login.societyId = getSocietyId();
                            SocietyHelpDatabaseFactory.getMasterDBInstance().createLogin(login);
                            Intent intent = new Intent(getApplicationContext(), ManageLoginActivity.class);

                            List<Login> logins = SocietyHelpDatabaseFactory.getMasterDBInstance().getAllLogins(getLoginId());
                            byte[] sObj = CustomSerializer.serializeObject(logins);
                            intent.putExtra(CONST_ALL_LOGINS, sObj);

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
                if (s.toString().length() > 0 && s.toString().length() <= 10) {

                    if (!loginIds.contains(s.toString())) {
                        isLoginIdValidate = true;
                        if (isPasswordValidate) {
                            loginButton.setEnabled(true);
                            loginButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.btnEnableColor));
                        }
                    } else {
                        Util.CustomToast(getApplicationContext(), "Login Id already exist.", 800);
                    }

                } else {
                    Util.CustomToast(getApplicationContext(), "Login Id length should be in between 1 and 10.", 800);
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
                            Util.CustomToast(getApplicationContext(), "Password and Confirm Password should match.", 800);
                        }
                    }

                } else {
                    Util.CustomToast(getApplicationContext(), "Login Id length should be in between 6 and 10.", 800);
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
                        Util.CustomToast(getApplicationContext(), "Password and Confirm Password should match.", 800);
                    }
                }
                else{
                    Util.CustomToast(getApplicationContext(), "Login Id length should be in between 6 and 10.", 800);
                }
            }
        });
    }
}
