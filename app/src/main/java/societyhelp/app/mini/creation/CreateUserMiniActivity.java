package societyhelp.app.mini.creation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import societyhelp.app.R;
import societyhelp.app.advance.DashBoardActivity;
import societyhelp.app.advance.ManageUserActivity;
import societyhelp.app.mini.dashboard.DashBoardMiniActivity;
import societyhelp.app.mini.view.ViewAllUsersMiniActivity;
import societyhelp.app.util.CustomSerializer;
import societyhelp.app.util.DatePickerDialogTheme;
import societyhelp.app.util.ListViewAdaptor;
import societyhelp.app.util.Util;
import societyhelp.core.SocietyAuthorization;
import societyhelp.dao.SocietyHelpDatabaseFactory;
import societyhelp.dao.mysql.impl.UserDetails;

public class CreateUserMiniActivity extends DashBoardMiniActivity {

    protected TextView userIdText;
    protected TextView nameText;
    protected TextView mobileNoText;
    protected TextView emailIdText;
    protected TextView addressText;
    protected Spinner flatIdSpinner;
    protected Spinner loginIdSpinner;
    protected Spinner userTypeSpinner;
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_create_user);
        setHeader("", true, false);

        userIdText = (TextView) findViewById(R.id.userIdText_AUA);
        nameText = (TextView) findViewById(R.id.nameText_AUA);
        mobileNoText = (TextView) findViewById(R.id.mobileNoText_AUA);
        emailIdText = (TextView) findViewById(R.id.emailIdText_AUA);
        addressText = (TextView) findViewById(R.id.AddressText_AUA);
     
        flatIdSpinner = (Spinner) findViewById(R.id.flatIdSpinner_AUA);
        loginIdSpinner = (Spinner) findViewById(R.id.loginIdSpinner_AUA);
        userTypeSpinner = (Spinner) findViewById(R.id.userTypeSpinner_AUA);

        try {
            List<String> userTypes = getUserType();
            userTypeSpinner.setAdapter(new ListViewAdaptor(getApplicationContext(), userTypes));

            List<String> flatIds = getFlatIds();
            flatIdSpinner.setAdapter(new ListViewAdaptor(getApplicationContext(), flatIds));

            List<String> loginIds = getLoginIds();
            loginIdSpinner.setAdapter(new ListViewAdaptor(getApplicationContext(), loginIds));

            final Button submitButton = (Button) findViewById(R.id.addUserBtn_AUA);

            final ProgressDialog progress = ProgressDialog.show(CreateUserMiniActivity.this, null, "Creating login in Database  ...", true, false);
            progress.setCancelable(true);
            progress.hide();

            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    //validate values
                    if(validationFailed()) return;

                    progress.show();

                    final UserDetails ud = new UserDetails();

                    ud.userId = userIdText.getText().toString();
                    ud.userName = nameText.getText().toString();
                    ud.mobileNo = Long.valueOf(mobileNoText.getText().toString());
                    if(emailIdText != null) ud.emailId = emailIdText.getText().toString();
                    ud.address = addressText.getText().toString();
                    ud.flatId = flatIdSpinner.getSelectedItem().toString();
                    ud.loginId = loginIdSpinner.getSelectedItem().toString();
                    ud.userType = userTypeSpinner.getSelectedItem().toString();
                    try {


                        Thread taskThread = new Thread(new Runnable() {
                            public void run() {
                                try {
                                    SocietyHelpDatabaseFactory.getDBInstance().addUserDetails(ud);
                                    List<UserDetails> users = SocietyHelpDatabaseFactory.getDBInstance().getAllUsers(getSocietyId());
                                    Intent innerIntent = new Intent(getApplicationContext(), ViewAllUsersMiniActivity.class);
                                    byte[] sObj = CustomSerializer.serializeObject(users);
                                    innerIntent.putExtra(CONST_ALL_USERS, sObj);
                                    startActivity(innerIntent);
                                } catch (Exception e) {
                                    Log.e("Error", "Login creation failed", e);
                                }
                                progress.dismiss();
                                progress.cancel();
                            }
                        });
                        taskThread.start();

                    } catch (Exception e) {
                        Log.e("Error","User details dates have some problem", e);
                    }
                }
            });

        }catch (Exception e)
        {
            Log.e("Error","User details adding has some problem", e);
        }
    }

    protected  List<String> getUserType() throws Exception
    {
        List<String> types = new ArrayList<>();
        types.add("Select User Type");
        types.add(UserDetails.OWNER);
        types.add(UserDetails.TENANT);
        return  types;
    }

    protected  List<String> getFlatIds() throws Exception
    {
        String strList = (String) getIntent().getSerializableExtra(CONST_FLAT_IDS);
        List<String> listFlatIds = new ArrayList<>();
        for(String flatId : strList.split(","))
        {
            listFlatIds.add(flatId);
        }
        return  listFlatIds;
    }

    protected  List<String> getLoginIds() throws Exception
    {
        List<String> listIds = new ArrayList<>();
        String strList = (String) getIntent().getSerializableExtra(CONST_LOGIN_IDS);

        for(String loginId : strList.split(","))
        {
            listIds.add(loginId);
        }
        return  listIds;
    }




    private boolean validationFailed() {
        boolean validationFailed = false;

        if (userIdText.getText().toString().length() == 0) {
            Util.CustomToast(getApplicationContext(), "User Id should not be empty", 1000);
            validationFailed = true;
            return validationFailed;
        } else if (userIdText.getText().toString().trim().length() > 0){
            String allUserIds = (String) getIntent().getSerializableExtra(CONST_USER_IDS);
            for(String userId : allUserIds.split(","))
            {
                if(userId.contentEquals(userIdText.getText().toString().trim()))
                {
                    Util.CustomToast(getApplicationContext(), "User Id is already exist.", 1000);
                    validationFailed = true;
                    return validationFailed;
                }
            }
        }

        if (nameText.getText().toString().length() == 0) {
            Util.CustomToast(getApplicationContext(), "User Name should not be empty", 1000);
            validationFailed = true;
        } else if (mobileNoText.getText().toString().length() > 0 &&
                mobileNoText.getText().toString().length() < 5 && mobileNoText.getText().toString().length() > 12) {
            Util.CustomToast(getApplicationContext(), "Mobile number should be inbetween 6 to 12 digits", 1000);
            validationFailed = true;
        } else if (emailIdText.getText().toString().length() > 0 &&
                !android.util.Patterns.EMAIL_ADDRESS.matcher(emailIdText.getText().toString()).matches()) {
            Util.CustomToast(getApplicationContext(), "Invalid Mail Id", 1000);
            validationFailed = true;
        }  else if (flatIdSpinner.getSelectedItemId() == 0) {
            Util.CustomToast(getApplicationContext(), "Flat Id must select.", 1000);
            validationFailed = true;
        } else if (loginIdSpinner.getSelectedItemId() == 0) {
            Util.CustomToast(getApplicationContext(), "Login Id must select.", 1000);
            validationFailed = true;
        } else if (userTypeSpinner.getSelectedItemId() == 0) {
            Util.CustomToast(getApplicationContext(), "User Type must select.", 1000);
            validationFailed = true;
        }

        return validationFailed;
    }
}
