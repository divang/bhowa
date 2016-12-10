package societyhelp.app.transaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import societyhelp.app.DashBoardActivity;
import societyhelp.app.DetailTransactionViewActivity;
import societyhelp.app.R;
import societyhelp.app.UserAliasMappingActivity;
import societyhelp.dao.SocietyHelpDatabaseFactory;
import societyhelp.dao.mysql.impl.BankStatement;
import societyhelp.dao.mysql.impl.UserDetails;

public class CreditTransactionsActivity extends DashBoardActivity {

    protected ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_transactions);
        setHeader(getString(R.string.title_activity_credit_transactions), false, true);
    }

    public void mapNameToUserId(View v) {
        progress = ProgressDialog.show(CreditTransactionsActivity.this, null, "Getting unknown PDF name and send to mapping ...", true, false);
        progress.setCancelable(true);
        progress.show();
        Thread taskThread = new Thread(new Runnable() {
            public void run() {

                try {
                    List<UserDetails> users = SocietyHelpDatabaseFactory.getDBInstance().getAllUsers();
                    BankStatement bankStat = (BankStatement) getIntent().getSerializableExtra("bankStat");
                    Intent intent = new Intent(getApplicationContext(), UserAliasMappingActivity.class);
                    intent.putExtra("bankStat", bankStat);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                progress.dismiss();
                progress.cancel();
            }
        });

        taskThread.start();

    }


    public void verifyPDFStagingTransactions(View v)
    {
        try {
            progress = ProgressDialog.show(this, null, "Uploading verified transactions to Database ...", true, false);
            progress.setCancelable(true);
            progress.show();
            final BankStatement verifiedBankStat = new BankStatement();
            verifiedBankStat.allTransactions = SocietyHelpDatabaseFactory.getDBInstance().getAllDetailsTransactions();
            Thread verifiedTransTaskThread = new Thread(new Runnable() {
                public void run() {
                    try {
                        SocietyHelpDatabaseFactory.getDBInstance().saveVerifiedTransactions(verifiedBankStat);
                    } catch (Exception e) {
                        Log.e("Error", "Insert verified data has problem", e);
                    }

                    progress.dismiss();
                    progress.cancel();
                }
            });
            verifiedTransTaskThread.start();
        }
        catch (Exception e)
        {
            Log.e("Error", "Insert verified data has problem", e);
        }
    }
}
