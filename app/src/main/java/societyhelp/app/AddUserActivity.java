package societyhelp.app;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import societyhelp.app.util.Util;
import societyhelp.dao.SocietyHelpDatabaseFactory;
import societyhelp.dao.mysql.impl.Flat;
import societyhelp.dao.mysql.impl.Login;
import societyhelp.dao.mysql.impl.UserDetails;

public class AddUserActivity extends DashBoardActivity {

    protected TextView userIdText;
    protected TextView nameText;
    protected TextView nameAliasText;
    protected TextView mobileNoText;
    protected TextView mobileNoAlternateText;
    protected TextView emailIdText;
    protected TextView addressText;
    protected EditText flatJoinDateText;
    protected EditText flatLeftDateText;
    protected Spinner flatIdSpinner;
    protected Spinner loginIdSpinner;
    protected Spinner userTypeSpinner;


    protected final SimpleDateFormat dateFormat = new SimpleDateFormat("dd:MM:yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        setHeader("", true, false);

        userIdText = (TextView) findViewById(R.id.userIdText_AUA);
        nameText = (TextView) findViewById(R.id.nameText_AUA);
        nameAliasText = (TextView) findViewById(R.id.nameAliasText_AUA);
        mobileNoText = (TextView) findViewById(R.id.mobileNoText_AUA);
        mobileNoAlternateText = (TextView) findViewById(R.id.mobileNoAlternateText_AUA);
        emailIdText = (TextView) findViewById(R.id.emailIdText_AUA);
        addressText = (TextView) findViewById(R.id.AddressText_AUA);
        flatJoinDateText = (EditText) findViewById(R.id.flatJoinDateText_AUA);

        flatJoinDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialogTheme() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day){
                        flatJoinDateText.setText(day + ":" + (month + 1) + ":" + year);
                    }
                }.show(getFragmentManager(), "Calendar Theme");
            }
        });

        flatLeftDateText = (EditText) findViewById(R.id.flatLeftDateText_AUA);
        flatLeftDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialogTheme() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day){
                        flatLeftDateText.setText(day + ":" + (month + 1) + ":" + year);
                    }
                }.show(getFragmentManager(), "Calendar Theme");
            }
        });

        flatIdSpinner = (Spinner) findViewById(R.id.flatIdSpinner_AUA);
        loginIdSpinner = (Spinner) findViewById(R.id.loginIdSpinner_AUA);
        userTypeSpinner = (Spinner) findViewById(R.id.userTypeSpinner_AUA);

        try {
            List<String> userTypes = getUserType();
            userTypeSpinner.setAdapter(new ListViewAdaptor(userTypes));

            List<String> flatIds = getFlatIds();
            flatIdSpinner.setAdapter(new ListViewAdaptor(flatIds));

            List<String> loginIds = getLoginIds();
            loginIdSpinner.setAdapter(new ListViewAdaptor(loginIds));

            final Button submitButton = (Button) findViewById(R.id.addUserBtn_AUA);

            final ProgressDialog progress = ProgressDialog.show(AddUserActivity.this, null, "Creating login in Database  ...", true, false);
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
                    ud.nameAlias = nameAliasText.getText().toString();
                    ud.mobileNo = Long.valueOf(mobileNoText.getText().toString());
                    ud.mobileNoAlternative = Long.valueOf(mobileNoAlternateText.getText().toString());
                    ud.emailId = emailIdText.getText().toString();
                    ud.address = addressText.getText().toString();
                    ud.flatId = flatIdSpinner.getSelectedItem().toString();
                    ud.loginId = loginIdSpinner.getSelectedItem().toString();
                    ud.userType = userTypeSpinner.getSelectedItem().toString();

                    try {
                        ud.flatJoinDate = new Date(dateFormat.parse(flatJoinDateText.getText().toString()).getTime());

                        if(flatLeftDateText.getText().toString().trim().length() > 0)
                            ud.flatLeftDate = new Date(dateFormat.parse(flatLeftDateText.getText().toString()).getTime());

                        Thread taskThread = new Thread(new Runnable() {
                            public void run() {
                                try {
                                    SocietyHelpDatabaseFactory.getDBInstance().addUserDetails(ud);
                                    Intent intent = new Intent(getApplicationContext(), ManageUserActivity.class);
                                    startActivity(intent);
                                } catch (Exception e) {
                                    Log.e("Error", "Login creation failed", e);
                                }
                                progress.dismiss();
                                progress.cancel();
                            }
                        });
                        taskThread.start();

                    } catch (ParseException e) {
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
        types.add("Owner");
        types.add("Tenant");
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

    class ListViewAdaptor extends  ArrayAdapter{

        ListViewAdaptor(List<String> ids) {
            super(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, ids);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v = super.getView(position, convertView, parent);
            ((TextView) v).setTextSize(16);
            ((TextView) v).setTextColor(Color.BLACK);

            return v;
        }

        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View v = super.getDropDownView(position, convertView, parent);
            ((TextView) v).setTextColor(Color.BLACK);
            ((TextView) v).setGravity(Gravity.LEFT);

            return v;
        }

    }

    public abstract class DatePickerDialogTheme extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datepickerdialog = new DatePickerDialog(getActivity(),
                    R.style.CalendarDialogTheme,this,year,month,day);

            return datepickerdialog;
        }

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
                if(userId.contains(userIdText.getText().toString().trim()))
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
        } else if (mobileNoAlternateText.getText().toString().length() > 0 &&
                mobileNoAlternateText.getText().toString().length() < 5 || mobileNoAlternateText.getText().toString().length() > 12) {
            Util.CustomToast(getApplicationContext(), "Alternate Mobile number should be inbetween 6 to 12 digits", 1000);
            validationFailed = true;
        } else if (emailIdText.getText().toString().length() > 0 &&
                !android.util.Patterns.EMAIL_ADDRESS.matcher(emailIdText.getText().toString()).matches()) {
            Util.CustomToast(getApplicationContext(), "Invalid Mail Id", 1000);
            validationFailed = true;
        } else if (flatJoinDateText.getText().toString().trim().length() == 0) {
            Util.CustomToast(getApplicationContext(), "Flat join date should enter.", 1000);
            validationFailed = true;
        } else if (flatIdSpinner.getSelectedItemId() == 0) {
            Util.CustomToast(getApplicationContext(), "Flat Id must select.", 1000);
            validationFailed = true;
        } else if (loginIdSpinner.getSelectedItemId() == 0) {
            Util.CustomToast(getApplicationContext(), "Login Id must select.", 1000);
            validationFailed = true;
        } else if (userTypeSpinner.getSelectedItemId() == 0) {
            Util.CustomToast(getApplicationContext(), "User Type must select.", 1000);
            validationFailed = true;
        } else if (flatJoinDateText.getText().toString().trim().length() > 0) {

            java.util.Date joinDate = null;
            try {
                joinDate = dateFormat.parse(flatJoinDateText.getText().toString());
                if(joinDate.compareTo(new java.util.Date(System.currentTimeMillis())) > 0) {
                    Util.CustomToast(getApplicationContext(), "Flat join date should lesser then the current date.", 1000);
                    validationFailed = true;
                }
            } catch (ParseException e) {
                Util.CustomToast(getApplicationContext(), "Flat join date is in incorrect format.", 1000);
                validationFailed = true;
            }

            if(joinDate != null && flatLeftDateText.getText().toString().length() > 0)
            {
                java.util.Date leftDate = null;
                try {
                    leftDate = dateFormat.parse(flatLeftDateText.getText().toString());
                    if(joinDate.compareTo(leftDate) > 0) {
                        Util.CustomToast(getApplicationContext(), "Left join date should lesser then the join date.", 1000);
                        validationFailed = true;
                    } else if(leftDate.compareTo(new Date(System.currentTimeMillis())) > 0) {
                        Util.CustomToast(getApplicationContext(), "Left join date should lesser then the current date.", 1000);
                        validationFailed = true;
                    }
                } catch (ParseException e) {
                    Util.CustomToast(getApplicationContext(), "Flat left date is in incorrect format.", 1000);
                    validationFailed = true;
                }
            }
        }
        return validationFailed;
    }
}
