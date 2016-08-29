package bhowa.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import bhowa.dao.BhowaDatabaseFactory;
import bhowa.dao.mysql.impl.BankStatement;
import bhowa.dao.mysql.impl.BhowaTransaction;

public class TransactionRawDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_raw_data);

        final BankStatement bankStat = (BankStatement)getIntent().getSerializableExtra("bankStat");
        final TableLayout tableL = (TableLayout)findViewById(R.id.reportTableLayout);

        for(BhowaTransaction bt : bankStat.allTransactions)
        {
            TableRow row = new TableRow(this);

            TextView srNoCol = new TextView(this); srNoCol.setText(String.valueOf(bt.srNo + ""));row.addView(srNoCol);
            TextView nameCol = new TextView(this); nameCol.setText(bt.name); row.addView(nameCol);
            TextView amountCol = new TextView(this); amountCol.setText(String.valueOf(bt.amount)); row.addView(amountCol);
            TextView dateCol = new TextView(this); dateCol.setText(String.valueOf(bt.transactionDate)); row.addView(dateCol);

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
