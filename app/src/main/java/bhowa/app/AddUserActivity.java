package bhowa.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import bhowa.app.R;
import bhowa.dao.BhowaDatabaseFactory;
import bhowa.dao.mysql.impl.Flat;
import bhowa.dao.mysql.impl.Login;
import bhowa.dao.mysql.impl.UserDetails;

public class AddUserActivity extends DashBoardActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        setHeader("", true);

        final TextView userIdText = (TextView) findViewById(R.id.userIdText_AUA);
        final TextView nameText = (TextView) findViewById(R.id.nameText_AUA);
        final TextView nameAliasText = (TextView) findViewById(R.id.nameAliasText_AUA);
        final TextView mobileNoText = (TextView) findViewById(R.id.mobileNoText_AUA);
        final TextView mobileNoAlternateText = (TextView) findViewById(R.id.mobileNoAlternateText_AUA);
        final TextView emailIdText = (TextView) findViewById(R.id.emailIdText_AUA);
        final TextView flatJoinDateText = (TextView) findViewById(R.id.flatJoinDateText_AUA);
        final TextView flatLeftDateText = (TextView) findViewById(R.id.flatLeftDateText_AUA);

        final Spinner flatIdSpinner = (Spinner) findViewById(R.id.flatIdSpinner_AUA);
        final Spinner loginIdSpinner = (Spinner) findViewById(R.id.loginIdSpinner_AUA);
        final Spinner userTypeSpinner = (Spinner) findViewById(R.id.userTypeSpinner_AUA);


        try {
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
                    progress.show();

                    final UserDetails ud = new UserDetails();

                    ud.userId = nameText.getText().toString();
                    ud.userName = nameText.getText().toString();
                    ud.nameAlias = nameAliasText.getText().toString();
                    ud.mobileNo = Long.valueOf(mobileNoText.getText().toString());
                    ud.mobileNoAlternative = Long.valueOf(mobileNoAlternateText.getText().toString());
                    ud.emailId = emailIdText.getText().toString();
                    //ud.flatJoinDate = Date.valueOf(flatJoinDateText.getText().toString());
                    //ud.flatLeftDate = Date.valueOf(flatLeftDateText.getText().toString());
                    ud.flatId = flatIdSpinner.getSelectedItem().toString();
                    ud.loginId = loginIdSpinner.getSelectedItem().toString();
                    ud.userType = userTypeSpinner.getSelectedItem().toString();

                    Thread taskThread = new Thread(new Runnable() {
                        public void run() {
                            try {
                                BhowaDatabaseFactory.getDBInstance().addUserDetails(ud);
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
                }
            });

        }catch (Exception e)
        {
            Log.e("Error","User details adding has some problem", e);
        }
    }

    protected  List<String> getFlatIds() throws Exception
    {
        List<String> listFlatIds = new ArrayList<>();
        listFlatIds.add("Select Flat Id");
        List<Flat> flats = BhowaDatabaseFactory.getDBInstance().getAllFlats();
        for(Flat f : flats)
        {
            listFlatIds.add(f.flatId);
        }
        return  listFlatIds;
    }

    protected  List<String> getLoginIds() throws Exception
    {
        List<String> listIds = new ArrayList<>();
        listIds.add("Select Login Id");
        List<Login> login = BhowaDatabaseFactory.getDBInstance().getAllLogins();
        for(Login l : login)
        {
            listIds.add(l.loginId);
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
}
