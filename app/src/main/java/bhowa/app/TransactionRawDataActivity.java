package bhowa.app;

import android.app.ListActivity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bhowa.dao.BhowaDatabaseFactory;
import bhowa.dao.mysql.impl.BankStatement;
import bhowa.dao.mysql.impl.BhowaTransaction;
import bhowa.dao.mysql.impl.UserDetails;

public class TransactionRawDataActivity extends AppCompatActivity {

    List<UserDetails> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_transaction_raw_data);

            final BankStatement bankStat = (BankStatement) getIntent().getSerializableExtra("bankStat");
            final TableLayout tableL = (TableLayout) findViewById(R.id.reportTableLayout);
            users = BhowaDatabaseFactory.getDBInstance().getAllUsers();

            for (String btName : getUniqueNamesInTransaction(bankStat)) {
                TableRow row = new TableRow(this);
                TableLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(5, 10, 5, 10);
                row.setLayoutParams(layoutParams);

                String userId = getUserID(btName, users);
                if (userId != null) {
                    TextView flagText = new TextView(this);
                    flagText.setBackgroundColor(Color.GREEN);
                    flagText.setText("Matched");
                    row.addView(flagText);

                    TextView userIdText = new TextView(this);
                    userIdText.setText(userId);
                    userIdText.setBackgroundColor(Color.GREEN);
                    row.addView(userIdText);

                    TextView nameCol = new TextView(this);
                    nameCol.setText(btName);
                    nameCol.setBackgroundColor(Color.GREEN);
                    row.addView(nameCol);

                } else {
                    TextView flagText = new TextView(this);
                    flagText.setBackgroundColor(Color.RED);
                    flagText.setText("Not Matched");
                    row.addView(flagText);

                    row.addView(getUserIDListView(users, btName));

                    TextView nameCol = new TextView(this);
                    nameCol.setText(btName);
                    nameCol.setBackgroundColor(Color.RED);
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
            Log.e("Error","Transaction Raw data activity has problem", e);
        }
    }

    private Spinner getUserIDListView(List<UserDetails> users, final String aliasName)
    {
        List<String> userIds = new ArrayList<>();
        for(UserDetails u : users)
        {
            if(!userIds.contains(u.userId)) userIds.add(u.userId);
        }

        final Spinner spinnerUserId = new Spinner(this);
        spinnerUserId.setMinimumHeight(10);
        spinnerUserId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String userId = spinnerUserId.getSelectedItem().toString();
                Log.d("info", "user_id -" + userId + " aliasName-" + aliasName);
                //Call sql query
                UserDetails u = getUser(userId);
                String alias = u.nameAlias != null ? u.nameAlias+","+aliasName : aliasName;
                try {
                    BhowaDatabaseFactory.getDBInstance().setAliasOfUserId(userId, alias);
                } catch (Exception e) {
                    Log.e("Error", "Update alias has problem", e);
                }
                Log.d("info", "user_id alias-" + userId + " updated");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> userIdsAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, userIds);
        userIdsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUserId.setAdapter(userIdsAdapter);
        spinnerUserId.setPrompt("Select UserId");
        spinnerUserId.setBackgroundColor(Color.BLUE);


        return spinnerUserId;
    }

    private String getUserID(String tName, List<UserDetails> users)
    {
        if(tName == null) return null;
        for(UserDetails u : users)
        {
            //Log.d("info",tName + " " + u.toString());
            if(u.userName != null && tName.toLowerCase().equals(u.userName.toLowerCase())) return u.userId;
            if(u.nameAlias != null && u.nameAlias.toLowerCase().contains(tName)) return u.userId;
        }
        return null;
    }

    private UserDetails getUser(String userId)
    {
        for(UserDetails u : users)
        {
            if(u.userId.equals(userId)) return u;
        }
        return null;
    }

    private List<String> getUniqueNamesInTransaction(BankStatement bankStat)
    {
        List<String> unameList = new ArrayList<>();
        for(BhowaTransaction bt : bankStat.allTransactions)
        {
            if(!unameList.contains(bt.name)) unameList.add(bt.name);
        }
        return unameList;
    }
}
