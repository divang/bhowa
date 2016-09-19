package societyhelp.app;

import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import societyhelp.dao.SocietyHelpDatabaseFactory;
import societyhelp.dao.mysql.impl.Login;

public class ManageLoginActivity extends DashBoardActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_login);
        setHeader("", true, false);

        try {
            final TableLayout tableL = (TableLayout) findViewById(R.id.allLoginTableLayout);
            tableL.invalidate();
            List<Login> logins = SocietyHelpDatabaseFactory.getMasterDBInstance().getAllLogins(getLoginId());

            for (Login l : logins) {
                TableRow row = new TableRow(this);
                TextView lId = new TextView(this);
                lId.setText(l.loginId);
                row.addView(lId);
                TextView pass = new TextView(this);
                pass.setText(l.password);
                row.addView(pass);
                TextView status = new TextView(this);
                status.setText(Boolean.toString(l.status));
                row.addView(status);
                TextView auth = new TextView(this);
                auth.setText(l.societyId);
                row.addView(auth);
                tableL.addView(row);
            }
        }catch (Exception e)
        {
            Log.e("Error", "Login list fetch has problem", e);
        }
    }


}
