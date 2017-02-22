package societyhelp.app;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
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

public class TransactionRawDataActivity extends DashBoardActivity {

    List<UserDetails> users;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_transaction_raw_data);
            int[] fixedColumnWidths = new int[]{20, 5, 5, 70, 20, 70, 100, 20, 20};
            int[] scrollableColumnWidths = new int[]{20, 5, 5, 70, 20, 70, 100, 20, 20};
            int fixedRowHeight = 70;
            int fixedHeaderHeight = 70;

            TableRow.LayoutParams wrapWrapTableRowParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            TableRow row = getTableRow();

            row.addView(makeTableRowWithText("Sr", fixedColumnWidths[2], fixedHeaderHeight, Gravity.LEFT));
            row.addView(makeTableRowWithText("Name in transaction", fixedColumnWidths[3], fixedHeaderHeight, Gravity.LEFT));
            row.addView(makeTableRowWithText("Amount", fixedColumnWidths[4], fixedHeaderHeight, Gravity.RIGHT));
            row.addView(makeTableRowWithText("Transaction Date", fixedColumnWidths[5], fixedHeaderHeight, Gravity.RIGHT));
            row.addView(makeTableRowWithText("Reference", fixedColumnWidths[6], fixedHeaderHeight, Gravity.RIGHT));
            row.addView(makeTableRowWithText("Cr / Dr", fixedColumnWidths[7], fixedHeaderHeight, Gravity.RIGHT));
            row.addView(makeTableRowWithText("Type", fixedColumnWidths[8], fixedHeaderHeight, Gravity.RIGHT));

            TableLayout fixedColumn = (TableLayout) findViewById(R.id.fixed_column);
            TextView fixedViewUserIdH = makeTableRowWithText("User Id", scrollableColumnWidths[0], fixedHeaderHeight, Gravity.LEFT);
            fixedViewUserIdH.setBackgroundColor(ContextCompat.getColor(this, R.color.tableStaticHeaderColor));
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
                TextView fixedViewUserId = makeTableRowWithText(userId, scrollableColumnWidths[0], fixedRowHeight, Gravity.LEFT);
                //fixedViewUserId.setBackgroundColor(Color.BLUE);
                fixedColumn.addView(fixedViewUserId);
                //Fixed Columns

                //Scrollable columns
                row = getTableRow();

                row.addView(makeTableRowWithText(String.valueOf(srNo), scrollableColumnWidths[2], fixedRowHeight, Gravity.LEFT));

                row.addView(makeTableRowWithText(bt.name, scrollableColumnWidths[3], fixedRowHeight, Gravity.LEFT));

                row.addView(makeTableRowWithText(String.valueOf(bt.amount), scrollableColumnWidths[4], fixedRowHeight, Gravity.RIGHT));

                row.addView(makeTableRowWithText(String.valueOf(bt.transactionDate), scrollableColumnWidths[5], fixedRowHeight, Gravity.RIGHT));

                row.addView(makeTableRowWithText(bt.reference, scrollableColumnWidths[6], fixedRowHeight, Gravity.RIGHT));

                row.addView(makeTableRowWithText(bt.transactionFlow, scrollableColumnWidths[7], fixedRowHeight, Gravity.RIGHT));

                row.addView(makeTableRowWithText(bt.type, scrollableColumnWidths[8], fixedRowHeight, Gravity.RIGHT));

                scrollablePart.addView(row);


                if (userId == null) {
                    row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRowNotVerifiedColor));
                    fixedViewUserId.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRowNotVerifiedColor));
                }
                else if(isdimGreen) {
                    row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow1Color));
                    fixedViewUserId.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow1Color));
                    isdimGreen = !isdimGreen;
                }
                else {
                    row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow2Color));
                    fixedViewUserId.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow2Color));
                    isdimGreen = !isdimGreen;
                }

            }

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



}
