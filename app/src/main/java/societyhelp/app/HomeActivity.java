package societyhelp.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;

import societyhelp.app.util.FileChooser;
import societyhelp.dao.SocietyHelpDatabaseFactory;
import societyhelp.dao.mysql.impl.BankStatement;
import societyhelp.parser.SocietyHelpParserFactory;

public class HomeActivity extends DashBoardActivity {

    private ProgressDialog progress;
    final private int PICKFILE_RESULT_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        setHeader(getString(R.string.title_activity_home), false);
    }

    public void onButtonClicker(View v)
    {
        try {
            Intent intent;
            switch (v.getId()) {

                case R.id.home_activity_btn_create_login:
                    intent = new Intent(this, CreateLoginIdActivity.class);
                    startActivity(intent);
                    break;

                case R.id.home_activity_btn_add_flat:
                    intent = new Intent(this, AddFlatDetailsActivity.class);
                    startActivity(intent);
                    break;

                case R.id.home_activity_btn_add_user:
                    intent = new Intent(this, AddUserActivity.class);
                    startActivity(intent);
                    break;

                case R.id.home_activity_btn_view_all_login:
                    intent = new Intent(this, ManageLoginActivity.class);
                    startActivity(intent);
                    break;

                case R.id.home_activity_btn_view_all_flat:
                    intent = new Intent(this, ManageFlatActivity.class);
                    startActivity(intent);
                    break;

                case R.id.home_activity_btn_manage_users:
                    intent = new Intent(this, ManageUserActivity.class);
                    startActivity(intent);
                    break;

                case R.id.home_activity_btn_upload_pdf:
                    openFileBrowser();
                    break;

                case  R.id.home_activity_btn_detail_transactions:
                    intent = new Intent(this, DetailTransactionViewActivity.class);
                    startActivity(intent);
                    break;

                case  R.id.home_activity_btn_flat_wise_payable:
                    intent = new Intent(this, ManageFlatWisePayableActivity.class);
                    startActivity(intent);
                    break;

               /* case  R.id.home_activity_btn_my_transactions:
                    intent = new Intent(this, MyTransactionsActivity.class);
                    startActivity(intent);
                    break;
                */
                case  R.id.home_activity_btn_my_dues:
                    progress = ProgressDialog.show(this, null, "Fetching My dues from Database ...", true, true);
                    progress.setCancelable(true);
                    progress.show();
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                float myDues = SocietyHelpDatabaseFactory.getDBInstance().getMyDue(getFlatId());
                                Intent intentMyDues = new Intent(getApplicationContext(), MyDuesActivity.class);
                                intentMyDues.putExtra("MyDueAmount", myDues);
                                startActivity(intentMyDues);
                            }catch (Exception e)
                            {
                                Log.e("Error", "Fetching My dues verified data has problem", e);
                            }
                            progress.dismiss();
                            progress.cancel();
                        }
                    }).start();
                    break;

               /* case  R.id.transaction_report_activity_btn_save_verified_transactions:
                    progress = ProgressDialog.show(this, null, "Uploading verified transactions to Database ...", true, false);
                    progress.show();
                    final BankStatement verifiedBankStat = new BankStatement();
                    verifiedBankStat.allTransactions = SocietyHelpDatabaseFactory.getDBInstance().getAllDetailsTransactions();
                    Thread verifiedTransTaskThread = new Thread(new Runnable() {
                        public void run() {
                            try {
                                SocietyHelpDatabaseFactory.getDBInstance().saveVerifiedTransactions(verifiedBankStat);
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
                */
            }
        }catch (Exception e)
        {
            Log.e("Error", "Something goes wrong", e);
        }
    }


    public void openFileBrowser()
    {
        new FileChooser(this).setFileListener(new FileChooser.FileSelectedListener() {
            @Override public void fileSelected(final File file) {
                final String path = file.getPath();

                progress = ProgressDialog.show(HomeActivity.this, null, "Parsing PDF ...", true, false);
                progress.setCancelable(true);
                progress.show();
                Thread taskThread = new Thread(new Runnable() {
                    public void run() {

                        BankStatement bankStat = (BankStatement) SocietyHelpParserFactory.getInstance().getAllTransaction(path);
                        Intent transactionReportIntent = new Intent(getApplicationContext(), HomeTransactionActivity.class);
                        transactionReportIntent.putExtra("bankStat",bankStat);
                        startActivityForResult(transactionReportIntent, 0);
                        progress.dismiss();
                        progress.cancel();
                    }
                });
                taskThread.start();

            }}).showDialog();
    }
}
