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
            int[] fixedColumnWidths      = new int[]{30, 5, 5, 70, 20, 100, 30, 20, 20, 20, 20, 20, 30};
            int[] scrollableColumnWidths = new int[]{30, 5, 5, 70, 20, 100, 30, 20, 20, 20, 20, 20, 30};
            int fixedRowHeight = 70;
            int fixedHeaderHeight = 70;

            TableRow row = new TableRow(this);

            //header (fixed vertically)
            row.setLayoutParams(wrapWrapTableRowParams);
            row.setGravity(Gravity.CENTER);
            row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableStaticHeaderColor));

            row.addView(makeTableRowWithText("Name", fixedColumnWidths[3], fixedHeaderHeight, Gravity.LEFT));
            row.addView(makeTableRowWithText("Amount", fixedColumnWidths[4], fixedHeaderHeight, Gravity.RIGHT));
            row.addView(makeTableRowWithText("Reference", fixedColumnWidths[5], fixedHeaderHeight, Gravity.RIGHT));

            row.addView(makeTableRowWithText("Transaction Date", fixedColumnWidths[6], fixedHeaderHeight, Gravity.RIGHT));
            row.addView(makeTableRowWithText("Cr / Dr", fixedColumnWidths[7], fixedHeaderHeight, Gravity.RIGHT));
            row.addView(makeTableRowWithText("Type", fixedColumnWidths[8], fixedHeaderHeight, Gravity.RIGHT));

            row.addView(makeTableRowWithText("Flat", fixedColumnWidths[9], fixedHeaderHeight, Gravity.RIGHT));
            row.addView(makeTableRowWithText("Verified By", fixedColumnWidths[10], fixedHeaderHeight, Gravity.RIGHT));
            row.addView(makeTableRowWithText("Splitted", fixedColumnWidths[11], fixedHeaderHeight, Gravity.RIGHT));

            TableLayout fixedColumn = (TableLayout) findViewById(R.id.fixed_column);
            TextView fixedViewUserIdH = makeTableRowWithText("User Id", scrollableColumnWidths[0], fixedHeaderHeight, Gravity.LEFT);
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
                TextView fixedViewUserId = makeTableRowWithText(bt.userId, scrollableColumnWidths[0], fixedRowHeight, Gravity.LEFT);
                //fixedViewUserId.setBackgroundColor(Color.BLUE);
                fixedColumn.addView(fixedViewUserId);
                //Fixed Columns

                //Scrollable columns
                row = new TableRow(this);

                row.setLayoutParams(wrapWrapTableRowParams);
                row.setGravity(Gravity.CENTER);
                row.setBackgroundColor(Color.WHITE);

                row.addView(makeTableRowWithText(bt.name, scrollableColumnWidths[3], fixedRowHeight, Gravity.LEFT));

                row.addView(makeTableRowWithText(String.valueOf(bt.amount), scrollableColumnWidths[4], fixedRowHeight, Gravity.RIGHT));

                row.addView(makeTableRowWithText(bt.reference, scrollableColumnWidths[5], fixedRowHeight, Gravity.RIGHT));

                row.addView(makeTableRowWithText(String.valueOf(bt.transactionDate), scrollableColumnWidths[6], fixedRowHeight, Gravity.RIGHT));

                row.addView(makeTableRowWithText(bt.transactionFlow, scrollableColumnWidths[7], fixedRowHeight, Gravity.RIGHT));

                row.addView(makeTableRowWithText(bt.type, scrollableColumnWidths[8], fixedRowHeight, Gravity.RIGHT));

                row.addView(makeTableRowWithText(bt.flatId, scrollableColumnWidths[9], fixedRowHeight, Gravity.RIGHT));

                row.addView(makeTableRowWithText(bt.verifiedBy, scrollableColumnWidths[10], fixedRowHeight, Gravity.RIGHT));

                row.addView(makeTableRowWithText(Boolean.toString(bt.splitted), scrollableColumnWidths[11], fixedRowHeight, Gravity.RIGHT));


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
