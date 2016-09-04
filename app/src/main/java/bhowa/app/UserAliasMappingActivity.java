package bhowa.app;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bhowa.dao.BhowaDatabaseFactory;
import bhowa.dao.mysql.impl.BankStatement;
import bhowa.dao.mysql.impl.BhowaTransaction;
import bhowa.dao.mysql.impl.UserDetails;

public class UserAliasMappingActivity extends AppCompatActivity {

    List<UserDetails> users;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_transaction_raw_data);

            TableLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(10, 10, 10, 10);

            final BankStatement bankStat = (BankStatement) getIntent().getSerializableExtra("bankStat");
            final TableLayout tableL = (TableLayout) findViewById(R.id.reportTableLayout);
            users = BhowaDatabaseFactory.getDBInstance().getAllUsers();
            tableL.invalidate();

            TextView userIdTextHeading = new TextView(this);
            userIdTextHeading.setText("User Id list");
            tableL.addView(userIdTextHeading);

            TextView nameColHeading = new TextView(this);
            nameColHeading.setText("Not mapped Users");
            tableL.addView(nameColHeading);


            for (String btName : getUniqueNamesInTransaction(bankStat)) {
                TableRow row = new TableRow(this);

                row.setLayoutParams(layoutParams);

                String userId = getUserID(btName, users);
                if (userId != null) {
                    TextView userIdText = new TextView(this);
                    userIdText.setText(userId);
                    row.addView(userIdText);

                    TextView nameCol = new TextView(this);
                    nameCol.setText(btName);
                    row.addView(nameCol);

                } else {

                    row.addView(getUserIDListView(users, btName));

                    TextView nameCol = new TextView(this);
                    nameCol.setText(btName);
                    row.addView(nameCol);
                }

                tableL.addView(row);
            }

            final Button backButton = (Button) findViewById(R.id.backToReportBtn);
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        }catch (Exception e)
        {
            Log.e("Error", "Transaction Raw data activity has problem", e);
        }
    }

    private Spinner getUserIDListView(List<UserDetails> users, final String aliasName)
    {
        List<String> userIds = new ArrayList<>();
        userIds.add("Select User Id");
        for(UserDetails u : users) if(!userIds.contains(u.userId)) userIds.add(u.userId);

        ArrayAdapter<String> userIdsAdapter = new UserListViewAdaptor(userIds);
        userIdsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final Spinner spinnerUserId = new Spinner(this);
        spinnerUserId.setAdapter(userIdsAdapter);
        spinnerUserId.setOnItemSelectedListener(new UserMappingAdapter(spinnerUserId, aliasName));
        spinnerUserId.setBackgroundColor(Color.GRAY);

        return spinnerUserId;
    }


    private String getUserID(String tName, List<UserDetails> users)
    {
        if(tName == null) return null;
        for(UserDetails u : users)
        {
            Log.d("info",tName + " " + u.toString());
            if(u.userName != null && tName.toLowerCase().equals(u.userName.toLowerCase())) return u.userId;
            if(u.nameAlias != null && u.nameAlias.toLowerCase().contains(tName.toLowerCase().trim())) return u.userId;
        }
        return null;
    }

    private UserDetails getUser(String userId)
    {
        for(UserDetails u : users) if(u.userId.equals(userId)) return u;
        return null;
    }

    private List<String> getUniqueNamesInTransaction(BankStatement bankStat)
    {
        List<String> unameList = new ArrayList<>();
        for(BhowaTransaction bt : bankStat.allTransactions) if(!unameList.contains(bt.name.toUpperCase().trim())) unameList.add(bt.name.toUpperCase().trim());
        return unameList;
    }

    class UserMappingAdapter implements AdapterView.OnItemSelectedListener {

        final Spinner spinnerUserId;
        final String aliasName;
        boolean initialized = false;

        UserMappingAdapter(Spinner spinnerUserId, String aliasName){
            this.spinnerUserId = spinnerUserId;
            this.aliasName = aliasName;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            final String userId = spinnerUserId.getSelectedItem().toString();

            if(!initialized)
            {
                initialized = true;
                return;
            }

            UserDetails u = getUser(userId);
            final String alias = u.nameAlias != null ? u.nameAlias + "," + aliasName : aliasName;

            progress = ProgressDialog.show(UserAliasMappingActivity.this, null, "Updating alias for UserId : " + userId + " alias : "+alias, true, false);
            progress.show();
            Thread taskThread = new Thread(new Runnable() {
                public void run() {
                    try {
                        BhowaDatabaseFactory.getDBInstance().setAliasOfUserId(userId, alias);
                    } catch (Exception e) {
                        Log.e("Error", "Update alias has problem", e);
                    }
                    progress.dismiss();
                    progress.cancel();
                }
            });
            taskThread.start();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    class UserListViewAdaptor extends  ArrayAdapter{

        UserListViewAdaptor(List<String> userIds) {
            super(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, userIds);
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
