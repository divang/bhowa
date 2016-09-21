package societyhelp.app;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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
import societyhelp.dao.mysql.impl.SocietyHelpTransaction;

public class DetailTransactionViewActivity extends DashBoardActivity {

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaction_view);
        setHeader("", true, false);
        try {
            TableRow.LayoutParams wrapWrapTableRowParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int[] fixedColumnWidths      = new int[]{20, 5, 5, 40, 20, 70, 60, 20, 20, 20, 20, 20, 30};
            int[] scrollableColumnWidths = new int[]{20, 5, 5, 40, 20, 70, 60, 20, 20, 20, 20, 20, 30};
            int fixedRowHeight = 50;
            int fixedHeaderHeight = 60;

            TableRow row = new TableRow(this);
            //header (fixed vertically)
            row.setLayoutParams(wrapWrapTableRowParams);
            row.setGravity(Gravity.CENTER);
            row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableStaticHeaderColor));

            //row.addView(makeTableRowWithText("Transaction Id", fixedColumnWidths[1], fixedHeaderHeight));
            //row.addView(makeTableRowWithText("Sr", fixedColumnWidths[2], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Name", fixedColumnWidths[3], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Amount", fixedColumnWidths[4], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Reference", fixedColumnWidths[5], fixedHeaderHeight));

            row.addView(makeTableRowWithText("Transaction Date", fixedColumnWidths[6], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Cr / Dr", fixedColumnWidths[7], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Type", fixedColumnWidths[8], fixedHeaderHeight));

            row.addView(makeTableRowWithText("Flat", fixedColumnWidths[9], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Approved", fixedColumnWidths[10], fixedHeaderHeight));
            row.addView(makeTableRowWithText("User Comment", fixedColumnWidths[11], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Admin Comment", fixedColumnWidths[12], fixedHeaderHeight));

            TableLayout fixedColumn = (TableLayout) findViewById(R.id.fixed_column);
            TextView fixedViewUserIdH = makeTableRowWithText("User Id", scrollableColumnWidths[0], fixedHeaderHeight);
            fixedViewUserIdH.setBackgroundColor(ContextCompat.getColor(this, R.color.tableStaticHeaderColor));
            fixedViewUserIdH.setLayoutParams(wrapWrapTableRowParams);
            fixedColumn.addView(fixedViewUserIdH);

            //rest of the table (within a scroll view)
            TableLayout scrollablePart = (TableLayout) findViewById(R.id.reportTableLayout);
            scrollablePart.addView(row);

            final List<SocietyHelpTransaction> allTransactions = SocietyHelpDatabaseFactory.getDBInstance().getAllDetailsTransactions();

            int srNo = 0;
            boolean isdimGreen = true;

            for (SocietyHelpTransaction bt : allTransactions) {
                srNo++;

                //Fixed Columns
                TextView fixedViewUserId = makeTableRowWithText(bt.userId, scrollableColumnWidths[0], fixedRowHeight);
                //fixedViewUserId.setBackgroundColor(Color.BLUE);
                fixedColumn.addView(fixedViewUserId);
                //Fixed Columns

                //Scrollable columns
                row = new TableRow(this);
                row.setLayoutParams(wrapWrapTableRowParams);
                row.setGravity(Gravity.CENTER);
                row.setBackgroundColor(Color.WHITE);

                //row.addView(makeTableRowWithText(String.valueOf(bt.transactionId), scrollableColumnWidths[1], fixedRowHeight));

                //row.addView(makeTableRowWithText(String.valueOf(bt.srNo), scrollableColumnWidths[2], fixedRowHeight));

                row.addView(makeTableRowWithText(bt.name, scrollableColumnWidths[3], fixedRowHeight));

                row.addView(makeTableRowWithText(String.valueOf(bt.amount), scrollableColumnWidths[4], fixedRowHeight));

                row.addView(makeTableRowWithText(bt.reference, scrollableColumnWidths[5], fixedRowHeight));

                row.addView(makeTableRowWithText(String.valueOf(bt.transactionDate), scrollableColumnWidths[6], fixedRowHeight));

                row.addView(makeTableRowWithText(bt.transactionFlow, scrollableColumnWidths[7], fixedRowHeight));

                row.addView(makeTableRowWithText(bt.type, scrollableColumnWidths[8], fixedRowHeight));

                row.addView(makeTableRowWithText(bt.flatId, scrollableColumnWidths[9], fixedRowHeight));

                row.addView(makeTableRowWithText(Boolean.toString(bt.isAdminApproved), scrollableColumnWidths[10], fixedRowHeight));

                row.addView(makeTableRowWithText(bt.adminComment, scrollableColumnWidths[11], fixedRowHeight));

                row.addView(makeTableRowWithText(bt.userComment, scrollableColumnWidths[12], fixedRowHeight));

                scrollablePart.addView(row);


                if (bt.userId == null) {
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
            Log.e("Error", "Transaction Raw data activity has problem", e);
        }
    }
}
