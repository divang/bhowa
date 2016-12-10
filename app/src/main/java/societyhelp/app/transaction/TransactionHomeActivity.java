package societyhelp.app.transaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import societyhelp.app.util.CustomSerializer;
import societyhelp.app.util.FileChooser;
import societyhelp.dao.SocietyHelpDatabaseFactory;
import societyhelp.dao.mysql.impl.BankStatement;
import societyhelp.dao.mysql.impl.Login;
import societyhelp.dao.mysql.impl.SocietyHelpTransaction;
import societyhelp.dao.mysql.impl.StagingTransaction;
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
}
