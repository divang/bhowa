package societyhelp.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import societyhelp.dao.mysql.impl.BankStatement;

public class ViewRawDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_raw_data);

        final TableLayout tableL = (TableLayout)findViewById(R.id.rawDataLinesTableLayout);
        BankStatement bankStat = (BankStatement)getIntent().getSerializableExtra("bankStat");

        for(String  line : bankStat.rowdata)
        {
            TableRow row = new TableRow(this);
            TextView l = new TextView(this); l.setText(line);row.addView(l);
            tableL.addView(row);
        }

        final Button backButton = (Button)findViewById(R.id.backToReportBtn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
