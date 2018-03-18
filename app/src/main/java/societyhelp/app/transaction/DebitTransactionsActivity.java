package societyhelp.app.transaction;

import android.os.Bundle;

import societyhelp.app.advance.DashBoardActivity;
import societyhelp.app.R;

public class DebitTransactionsActivity extends DashBoardActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debit_transactions);
        setHeader(getString(R.string.title_activity_debit_transactions), false, true);
    }

}
