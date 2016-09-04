package bhowa.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import bhowa.dao.BhowaDatabaseFactory;
import bhowa.dao.mysql.impl.BankStatement;
import bhowa.parser.BhowaParserFactory;

public class HomeTransactionActivity extends DashBoardActivity {

    private ProgressDialog progress;

    public void onButtonClicker(View v)
    {
        try {
            final BankStatement bankStat = (BankStatement) getIntent().getSerializableExtra("bankStat");

            Intent intent;
            switch (v.getId()) {

                case R.id.transaction_report_activity_btn_view_map_user_alias:
                    intent = new Intent(this, UserAliasMappingActivity.class);
                    intent.putExtra("bankStat", bankStat);
                    startActivity(intent);
                    break;

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
                            try {
                                BhowaDatabaseFactory.getDBInstance().insertRawData(bankStat.rowdata);
                            }catch (Exception e)
                            {
                                Log.e("Error","Insert Raw data has problem",e);
                            }

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
                            try {
                                BhowaDatabaseFactory.getDBInstance().uploadMonthlyTransactions(bankStat);
                                Intent intentAllTrans = new Intent(getApplicationContext(), DetailTransactionViewActivity.class);
                                startActivity(intentAllTrans);
                            }catch (Exception e)
                            {
                                Log.e("Error", "Upload Monthly transaction has problem", e);
                            }
                            progress.dismiss();
                            progress.cancel();
                        }
                    });
                    transactionsTaskThread.start();
                    break;

                case R.id.transaction_report_activity_btn_save_verified_transactions:

                    progress = ProgressDialog.show(this, null, "Uploading verified transactions to Database ...", true, false);
                    progress.show();
                    final BankStatement verifiedBankStat = new BankStatement();
                    verifiedBankStat.allTransactions = BhowaDatabaseFactory.getDBInstance().getAllDetailsTransactions();
                    Thread verifiedTransTaskThread = new Thread(new Runnable() {
                        public void run() {
                            try {
                                BhowaDatabaseFactory.getDBInstance().saveVerifiedTransactions(verifiedBankStat);
                            }catch (Exception e)
                            {
                                Log.e("Error","Insert verified data has problem",e);
                            }

                            progress.dismiss();
                            progress.cancel();
                        }
                    });
                    verifiedTransTaskThread.start();
                    break;

            }
        }catch (Exception e)
        {
            Log.e("Error", "HomeTransactionActivity has some problem", e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_transaction);
        setHeader("", true);
    }

}
