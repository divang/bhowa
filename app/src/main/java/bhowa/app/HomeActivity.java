package bhowa.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;

import bhowa.app.util.FileChooser;
import bhowa.dao.BhowaDatabaseFactory;
import bhowa.dao.mysql.impl.BankStatement;
import bhowa.parser.BhowaParserFactory;

public class HomeActivity extends DashBoardActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        setHeader(getString(R.string.title_activity_home), false);
    }

    public void onButtonClicker(View v)
    {
        Intent intent;
        switch (v.getId())
        {
            case R.id.home_activity_btn_upload_pdf:
                //intent = new Intent(this, UploadPDfActivity.class);
                //startActivity(intent);
                openFileBrowser();
                break;

            case R.id.home_activity_btn_download_raw:
                intent = new Intent(this, UploadPDfActivity.class);
                startActivity(intent);
                break;
        }

    }

    public void openFileBrowser()
    {
        new FileChooser(this).setFileListener(new FileChooser.FileSelectedListener() {
            @Override public void fileSelected(final File file) {
                String path = file.getPath();
                BankStatement bState = (BankStatement) BhowaParserFactory.getDBInstance().getAllTransaction(path);
                //Insert raw data in DB. Table : 'Row_Data'
                BhowaDatabaseFactory.getDBInstance().insertRawData(bState.rowdata);

                //Display Parsed Transactions
                Intent transactionReportIntent = new Intent(getApplicationContext(), TransactionReportActivity.class);
                transactionReportIntent.putExtra("report",bState);
                startActivityForResult(transactionReportIntent, 0);

            }}).showDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
