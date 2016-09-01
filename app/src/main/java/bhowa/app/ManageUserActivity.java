package bhowa.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import bhowa.dao.BhowaDatabaseFactory;
import bhowa.dao.mysql.impl.UserDetails;

public class ManageUserActivity extends DashBoardActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_manage_user);

            setHeader(getString(R.string.title_activity_home), true);

            final TableLayout tableL = (TableLayout) findViewById(R.id.allUsersTableLayout);
            List<UserDetails> users = BhowaDatabaseFactory.getDBInstance().getAllUsers();

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
