package bhowa.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.File;
import bhowa.app.util.FileChooser;
import bhowa.dao.BhowaDatabaseFactory;
import bhowa.dao.mysql.impl.BankStatement;
import bhowa.parser.BhowaParserFactory;

public class UploadPDfActivity extends Activity implements View.OnClickListener {

    private Button Browse;
    private TextView filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pdf);

        Browse = (Button) findViewById(R.id.browse);
        filePath = (TextView) findViewById(R.id.file_path);
        Browse = (Button) findViewById(R.id.browse);
        Browse.setOnClickListener(this);

    }

    @Override
    public void onClick(final View v) {

          new FileChooser(this).setFileListener(new FileChooser.FileSelectedListener() {
                @Override public void fileSelected(final File file) {
                    String path = file.getPath();
                    BankStatement bState = (BankStatement) BhowaParserFactory.getDBInstance().getAllTransaction(path);
                    //Insert raw data in DB. Table : 'Row_Data'
                    BhowaDatabaseFactory.getDBInstance().insertRawData(bState.rowdata);

                    //Display Parsed Transactions
                    Intent transactionReportIntent = new Intent(v.getContext(), TransactionReportActivity.class);
                    transactionReportIntent.putExtra("report",bState);
                    startActivityForResult(transactionReportIntent, 0);

                }}).showDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_upload_pdf, menu);
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