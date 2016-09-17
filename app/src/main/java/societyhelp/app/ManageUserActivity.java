package societyhelp.app;

import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import societyhelp.dao.SocietyHelpDatabaseFactory;
import societyhelp.dao.mysql.impl.UserDetails;

public class ManageUserActivity extends DashBoardActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_manage_user);

            setHeader("", true, false);

            final TableLayout tableL = (TableLayout) findViewById(R.id.allUsersTableLayout);
            tableL.invalidate();
            List<UserDetails> users = SocietyHelpDatabaseFactory.getDBInstance().getAllUsers();

            for (UserDetails user : users) {
                TableRow row = new TableRow(this);
                TextView uId = new TextView(this);
                uId.setText(user.userId);
                row.addView(uId);
                TextView uName = new TextView(this);
                uName.setText(user.userName);
                row.addView(uName);
                TextView alName = new TextView(this);
                alName.setText(user.nameAlias);
                row.addView(alName);
                TextView uActive = new TextView(this);
                uActive.setText(Boolean.toString(user.isActive));
                row.addView(uActive);
                tableL.addView(row);
            }
        }catch (Exception e)
        {
            Log.e("Error", "User list fetch has problem", e);
        }
    }
}
