package societyhelp.app;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import societyhelp.dao.SocietyHelpDatabaseFactory;
import societyhelp.dao.mysql.impl.BankStatement;
import societyhelp.dao.mysql.impl.SocietyHelpTransaction;
import societyhelp.dao.mysql.impl.UserDetails;

public class TransactionRawDataActivity extends AppCompatActivity {

    List<UserDetails> users;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_transaction_raw_data);

            TableRow.LayoutParams wrapWrapTableRowParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int[] fixedColumnWidths = new int[]{20, 5, 5, 40, 20, 70, 60, 20, 20};
            int[] scrollableColumnWidths = new int[]{20, 5, 5, 40, 20, 70, 60, 20, 20};
            int fixedRowHeight = 50;
            int fixedHeaderHeight = 60;

            TableRow row = new TableRow(this);
            //header (fixed vertically)
            TableLayout header = (TableLayout) findViewById(R.id.table_header);
            row.setLayoutParams(wrapWrapTableRowParams);
            row.setGravity(Gravity.CENTER);
            row.setBackgroundColor(Color.YELLOW);
            //row.addView(makeTableRowWithText("User Id", fixedColumnWidths[0], fixedHeaderHeight));
          //  row.addView(makeTableRowWithText("Approved", fixedColumnWidths[1], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Sr", fixedColumnWidths[2], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Name in transaction", fixedColumnWidths[3], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Amount", fixedColumnWidths[4], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Transaction Date", fixedColumnWidths[5], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Reference", fixedColumnWidths[6], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Cr / Dr", fixedColumnWidths[7], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Type", fixedColumnWidths[8], fixedHeaderHeight));
            //header.addView(row);

            TableLayout fixedColumn = (TableLayout) findViewById(R.id.fixed_column);
            TextView fixedViewUserIdH = makeTableRowWithText("User Id", scrollableColumnWidths[0], fixedHeaderHeight);
            fixedViewUserIdH.setBackgroundColor(Color.YELLOW);
            fixedViewUserIdH.setLayoutParams(wrapWrapTableRowParams);
            fixedColumn.addView(fixedViewUserIdH);


            //rest of the table (within a scroll view)
            TableLayout scrollablePart = (TableLayout) findViewById(R.id.reportTableLayout);
            scrollablePart.addView(row);


            final BankStatement bankStat = (BankStatement) getIntent().getSerializableExtra("bankStat");
            users = SocietyHelpDatabaseFactory.getDBInstance().getAllUsers();

            int srNo = 0;
            boolean isdimGreen = true;

            for (SocietyHelpTransaction bt : bankStat.allTransactions) {
                srNo++;

                //Fixed Columns
                String userId = getUserID(bt.name, users);
                TextView fixedViewUserId = makeTableRowWithText(userId, scrollableColumnWidths[0], fixedRowHeight);
                //fixedViewUserId.setBackgroundColor(Color.BLUE);
                fixedColumn.addView(fixedViewUserId);
                //Fixed Columns

                //Scrollable columns
                row = new TableRow(this);
                row.setLayoutParams(wrapWrapTableRowParams);
                row.setGravity(Gravity.CENTER);
                row.setBackgroundColor(Color.WHITE);
/*
                if (userId != null) {
                    CheckBox approvedCheck = new CheckBox(this);
                    approvedCheck.setOnClickListener(new View.OnClickListener() {
                                                         @Override
                                                         public void onClick(View v) {
                                                             Log.d("info", "Approved");
                                                             //insert to  DB
                                                         }
                                                     }
                    );
                    fixedColumn.addView(approvedCheck);
                }
*/
                row.addView(makeTableRowWithText(String.valueOf(srNo), scrollableColumnWidths[2], fixedRowHeight));

                row.addView(makeTableRowWithText(bt.name, scrollableColumnWidths[3], fixedRowHeight));

                row.addView(makeTableRowWithText(String.valueOf(bt.amount), scrollableColumnWidths[4], fixedRowHeight));

                row.addView(makeTableRowWithText(String.valueOf(bt.transactionDate), scrollableColumnWidths[5], fixedRowHeight));

                row.addView(makeTableRowWithText(bt.reference, scrollableColumnWidths[6], fixedRowHeight));

                row.addView(makeTableRowWithText(bt.transactionFlow, scrollableColumnWidths[7], fixedRowHeight));

                row.addView(makeTableRowWithText(bt.type, scrollableColumnWidths[8], fixedRowHeight));

                scrollablePart.addView(row);


                if (userId == null) {
                    row.setBackgroundColor(Color.RED);
                    fixedViewUserId.setBackgroundColor(Color.RED);
                }
                else if(isdimGreen) {
                    row.setBackgroundColor(Color.argb(100, 143, 196, 162));
                    fixedViewUserId.setBackgroundColor(Color.argb(100, 143, 196, 162));
                    isdimGreen = !isdimGreen;
                }
                else {
                    row.setBackgroundColor(Color.argb(100, 178, 243, 202));
                    fixedViewUserId.setBackgroundColor(Color.argb(100, 178, 243, 202));
                    isdimGreen = !isdimGreen;
                }

            }

            final Button backButton = (Button) findViewById(R.id.backToReportBtn);
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        }catch (Exception e)
        {
            Log.e("Error","Transaction Raw data activity has problem", e);
        }
    }

    private String getUserID(String tName, List<UserDetails> users)
    {
        if(tName == null) return null;
        for(UserDetails u : users)
        {
            Log.d("info",tName + " " + u.toString());
            if(u.userName != null && tName.toLowerCase().equals(u.userName.toLowerCase())) return u.userId;
            if(u.nameAlias != null && u.nameAlias.toLowerCase().contains(tName.toLowerCase().trim())) return u.userId;
        }
        return null;
    }


    //util method
    private TextView recyclableTextView;

    public TextView makeTableRowWithText(String text, int widthInPercentOfScreenWidth, int fixedHeightInPixels) {
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        recyclableTextView = new TextView(this);
        recyclableTextView.setText(text);
        recyclableTextView.setTextColor(Color.BLACK);
        recyclableTextView.setTextSize(10);
        recyclableTextView.setWidth(widthInPercentOfScreenWidth * screenWidth / 100);
        recyclableTextView.setHeight(fixedHeightInPixels);
        return recyclableTextView;
    }

}
