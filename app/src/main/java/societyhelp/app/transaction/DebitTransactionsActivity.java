package societyhelp.app.transaction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import societyhelp.app.DashBoardActivity;
import societyhelp.app.R;

public class DebitTransactionsActivity extends DashBoardActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debit_transactions);
        setHeader(getString(R.string.title_activity_debit_transactions), false, true);
    }

}
