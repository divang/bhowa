package bhowa.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.io.File;

import bhowa.app.util.FileChooser;
import bhowa.dao.BhowaDatabaseFactory;
import bhowa.dao.mysql.impl.BankStatement;
import bhowa.parser.BhowaParserFactory;

public class HomeActivity extends DashBoardActivity {

    private ProgressDialog progress;

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
                openFileBrowser();
                break;

            case R.id.home_activity_btn_download_raw:
                intent = new Intent(this, ViewRawDataActivity.class);
                startActivity(intent);
                break;

            case R.id.home_activity_btn_manage_users:
                intent = new Intent(this, ManageUserActivity.class);
                startActivity(intent);
                break;

            case R.id.home_activity_btn_delete_raw:
                BhowaDatabaseFactory.getDBInstance().deleteAllRawData();
                break;
        }
    }

    public void openFileBrowser()
    {
        new FileChooser(this).setFileListener(new FileChooser.FileSelectedListener() {
            @Override public void fileSelected(final File file) {
                final String path = file.getPath();

                progress = ProgressDialog.show(HomeActivity.this, null, "Parsing PDF ...", true, false);
                progress.show();
                Thread taskThread = new Thread(new Runnable() {
                    public void run() {

                        BankStatement bankStat = (BankStatement) BhowaParserFactory.getDBInstance().getAllTransaction(path);
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
