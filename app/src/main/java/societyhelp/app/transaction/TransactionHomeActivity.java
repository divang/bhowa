package societyhelp.app.transaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.File;

import societyhelp.app.CreateLoginIdActivity;
import societyhelp.app.DashBoardActivity;
import societyhelp.app.HomeTransactionActivity;
import societyhelp.app.R;
import societyhelp.app.util.FileChooser;
import societyhelp.dao.SocietyHelpDatabaseFactory;
import societyhelp.dao.mysql.impl.BankStatement;
import societyhelp.dao.mysql.impl.Login;
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
}
