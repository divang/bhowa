package bhowa.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import bhowa.dao.BhowaDatabaseFactory;
import bhowa.dao.mysql.impl.BankStatement;
import bhowa.parser.BhowaParserFactory;

public class HomeTransactionActivity extends DashBoardActivity {

    private ProgressDialog progress;

    public void onButtonClicker(View v)
    {
        final BankStatement bankStat = (BankStatement)getIntent().getSerializableExtra("bankStat");

        Intent intent;
        switch (v.getId())
        {
            case R.id.transaction_report_activity_btn_view_raw_data:
                intent = new Intent(this, ViewRawDataActivity.class);
                intent.putExtra("bankStat", bankStat);
                startActivity(intent);
                break;

            case R.id.transaction_report_activity_btn_view_transactions:
                intent = new Intent(this, TransactionRawDataActivity.class);
                intent.putExtra("bankStat", bankStat);
                startActivity(intent);
                break;

            case R.id.transaction_report_activity_btn_upload_raw_data:

                progress = ProgressDialog.show(this, null, "Uploading Raw data to Database ...", true, false);
                progress.show();
                Thread rawDataTaskThread = new Thread(new Runnable() {
                    public void run() {
                        BhowaDatabaseFactory.getDBInstance().insertRawData(bankStat.rowdata);
                        progress.dismiss();
                        progress.cancel();
                    }
                });
                rawDataTaskThread.start();
                break;

            case R.id.transaction_report_activity_btn_upload_transactions:

                progress = ProgressDialog.show(this, null, "Uploading transactions to Databases ...", true, false);
                progress.show();
                Thread transactionsTaskThread = new Thread(new Runnable() {
                    public void run() {
                        BhowaDatabaseFactory.getDBInstance().uploadMonthlyTransactions(bankStat);
                        progress.dismiss();
                        progress.cancel();
                    }
                });
                transactionsTaskThread.start();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_transaction);
        setHeader("", true);
    }

}
