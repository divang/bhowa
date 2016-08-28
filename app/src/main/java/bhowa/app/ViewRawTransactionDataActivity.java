package bhowa.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import bhowa.dao.BhowaDatabaseFactory;
import bhowa.dao.mysql.impl.BhowaTransaction;

public class ViewRawTransactionDataActivity extends DashBoardActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_raw_transaction_data);
        setHeader(getString(R.string.title_activity_home), true);

        final TableLayout tableL = (TableLayout)findViewById(R.id.rawDataLinesTableLayout);
        List<String> rawData = BhowaDatabaseFactory.getDBInstance().showRawData();

        for(String  line : rawData)
        {
            TableRow row = new TableRow(this);
            TextView l = new TextView(this); l.setText(line);row.addView(l);
            tableL.addView(row);
        }
    }

}
