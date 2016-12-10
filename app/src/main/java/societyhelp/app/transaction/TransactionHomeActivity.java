package societyhelp.app.transaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import societyhelp.app.CreateLoginIdActivity;
import societyhelp.app.DashBoardActivity;
import societyhelp.app.DetailTransactionViewActivity;
import societyhelp.app.HomeTransactionActivity;
import societyhelp.app.R;
import societyhelp.app.SplitTransactionsFlatWiseActivity;
import societyhelp.app.VerifiedCashPaymentActivity;
import societyhelp.app.util.CustomSerializer;
import societyhelp.app.util.FileChooser;
import societyhelp.dao.SocietyHelpDatabaseFactory;
import societyhelp.dao.mysql.impl.BankStatement;
import societyhelp.dao.mysql.impl.Login;
import societyhelp.dao.mysql.impl.SocietyHelpTransaction;
import societyhelp.dao.mysql.impl.StagingTransaction;
import societyhelp.dao.mysql.impl.UserPaid;
import societyhelp.parser.SocietyHelpParserFactory;

public class TransactionHomeActivity extends DashBoardActivity {

    protected ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_home);
        setHeader(getString(R.string.title_activity_transaction_home), true, false);
    }

    public void openFileBrowser(View view)
    {
        new FileChooser(this).setFileListener(new FileChooser.FileSelectedListener() {
            @Override
            public void fileSelected(final File file) {
                final String path = file.getPath();

                progress = ProgressDialog.show(TransactionHomeActivity.this, null, "Parsing PDF and uploading to database ...", true, false);
                progress.setCancelable(true);
                progress.show();
                Thread taskThread = new Thread(new Runnable() {
                    public void run() {

                        try {
                            BankStatement bankStat = (BankStatement) SocietyHelpParserFactory.getInstance().getAllTransaction(path);
                            bankStat.uploadedLoginId = getLoginId();
                            SocietyHelpDatabaseFactory.getDBInstance().uploadMonthlyTransactions(bankStat);
                            Intent nextIntent = new Intent(getApplicationContext(), CreditTransactionsActivity.class);
                            nextIntent.putExtra("bankStat", bankStat);
                            startActivityForResult(nextIntent, 0);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        progress.dismiss();
                        progress.cancel();
                    }
                });
                taskThread.start();

            }
        }).showDialog();
    }

    public void goToCreditTransactionActivity(View view)
    {
        Intent nextIntent = new Intent(getApplicationContext(), CreditTransactionsActivity.class);
        startActivity(nextIntent);
    }

    public void goToDebitTransactionActivity(View view)
    {
        Intent nextIntent = new Intent(getApplicationContext(), DebitTransactionsActivity.class);
        startActivity(nextIntent);
    }

    public void viewPDFStagingTransactions(View v)
    {
        progress = ProgressDialog.show(TransactionHomeActivity.this, null, "Getting uploaded PDF transactions from database (not verified) ...", true, false);
        progress.setCancelable(true);
        progress.show();
        Thread taskThread = new Thread(new Runnable() {
            public void run() {

                try {
                    List<StagingTransaction> stagingTransaction = SocietyHelpDatabaseFactory.getDBInstance().getAllStaggingTransaction();
                    Intent nextIntent = new Intent(getApplicationContext(), StagingTransactionViewActivity.class);
                    nextIntent.putExtra(CONST_PDF_ALL_STAGING_TRANSACTIONS, CustomSerializer.serializeObject(stagingTransaction));
                    startActivityForResult(nextIntent, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                progress.dismiss();
                progress.cancel();
            }
        });
        taskThread.start();

    }

    public void autoSplitAndVerified(View v) {

        progress = ProgressDialog.show(TransactionHomeActivity.this, null, "Auto mapping and saving to verified Table of database ...", true, false);
        progress.setCancelable(true);
        progress.show();
        Thread taskThread = new Thread(new Runnable() {
            public void run() {

                try {
                    BankStatement verifiedBankStat = new BankStatement();
                    verifiedBankStat.allTransactions = SocietyHelpDatabaseFactory.getDBInstance().getAllDetailsTransactions();
                    List<SocietyHelpTransaction> verifiedTransactions = SocietyHelpDatabaseFactory.getDBInstance().saveVerifiedTransactions(verifiedBankStat);

                    List<UserPaid> splipttedUserPaid = SocietyHelpDatabaseFactory.getDBInstance().generateSplittedTransactionsFlatWise();
                    // Insert to new balance sheet table
                    Intent innerIntent = new Intent(getApplicationContext(), SplitTransactionsFlatWiseActivity.class);
                    byte[] sObj = CustomSerializer.serializeObject(splipttedUserPaid);
                    innerIntent.putExtra(CONST_SPLITTED_TRANSACTION, sObj);
                    startActivity(innerIntent);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                progress.dismiss();
                progress.cancel();
            }
        });
        taskThread.start();
    }

    public void validateUserCashDeposit(View v) {

        progress = ProgressDialog.show(this, null, "Getting unverified user's cash payment ...", true, true);
        progress.setCancelable(true);
        progress.show();
        new Thread(new Runnable() {
            public void run() {
                try {
                    List<UserPaid> payments = SocietyHelpDatabaseFactory.getDBInstance().getUnVerifiedCashPayment();
                    Intent intentMyDues = new Intent(getApplicationContext(), VerifiedCashPaymentActivity.class);
                    byte[] sObj = CustomSerializer.serializeObject(payments);
                    intentMyDues.putExtra(CONST_UN_VERIFIED_PAYMENT, sObj);

                    startActivity(intentMyDues);
                }catch (Exception e)
                {
                    Log.e("Error", "Fetching My dues verified data has problem", e);
                }
                progress.dismiss();
                progress.cancel();
            }
        }).start();
    }
}
