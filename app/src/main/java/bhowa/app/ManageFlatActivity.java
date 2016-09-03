package bhowa.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import bhowa.dao.BhowaDatabaseFactory;
import bhowa.dao.mysql.impl.Flat;
import bhowa.dao.mysql.impl.Login;

public class ManageFlatActivity extends DashBoardActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_flat);
        setHeader("", true);

        try {
            final TableLayout tableL = (TableLayout) findViewById(R.id.allFlatTableLayout);
            tableL.invalidate();
            List<Flat> flats = BhowaDatabaseFactory.getDBInstance().getAllFlats();

            for (Flat l : flats) {
                TableRow row = new TableRow(this);
                TextView fId = new TextView(this); fId.setText(l.flatId);row.addView(fId);
                TextView fNo = new TextView(this); fNo.setText(l.flatNumber);row.addView(fNo);
                TextView fArea = new TextView(this); fArea.setText(String.valueOf(l.area));row.addView(fArea);
                TextView fMaintenanceAmount = new TextView(this); fMaintenanceAmount.setText(String.valueOf(l.maintenanceAmount));row.addView(fMaintenanceAmount);
                TextView fBlock = new TextView(this); fBlock.setText(l.block);row.addView(fBlock);
                TextView fStatus = new TextView(this); fStatus.setText(Boolean.toString(l.status));row.addView(fStatus);
                tableL.addView(row);
            }
        }catch (Exception e)
        {
            Log.e("Error", "Flat list fetch has problem", e);
        }
    }
}
