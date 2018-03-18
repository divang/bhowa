package societyhelp.app.mini.creation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import societyhelp.app.R;
import societyhelp.app.advance.CreateAdminLoginIdActivity;
import societyhelp.app.advance.ManageLoginActivity;
import societyhelp.app.mini.MainActivity;
import societyhelp.app.mini.dashboard.DashBoardMiniActivity;
import societyhelp.app.util.CustomSerializer;
import societyhelp.app.util.Util;
import societyhelp.dao.SocietyHelpDatabaseFactory;
import societyhelp.dao.mysql.impl.Login;

public class SocietyFinalPageMiniActivity extends DashBoardMiniActivity {

    ProgressDialog progress;
    Button loginButton;
    boolean isLoginIdValidate = false;
    boolean isPasswordValidate = false;
    boolean isConfirmPasswordEnter = false;
    private SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_mini_admin_final_message);
	    setHeader("", true, false);
	}
	
	
	public void goToLogin(View v) {
		try {
			Intent societyIntent = new Intent(v.getContext(), MainActivity.class);
			startActivity(societyIntent);
		} catch (Exception e) {
			Log.e("Error", "Admin Login creation failed", e);
		}
	}
}
