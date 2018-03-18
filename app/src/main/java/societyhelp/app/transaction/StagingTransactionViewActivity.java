package societyhelp.app.transaction;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import societyhelp.app.advance.DashBoardActivity;
import societyhelp.app.R;
import societyhelp.app.util.CustomSerializer;
import societyhelp.dao.mysql.impl.StagingTransaction;

public class StagingTransactionViewActivity extends DashBoardActivity {

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaction_view);
        setHeader(getString(R.string.title_activity_verified_cash_payment), false, true);
        try {
            TableRow.LayoutParams wrapWrapTableRowParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int[] fixedColumnWidths      = new int[]{40, 5, 5, 70, 20, 100, 30, 20, 20, 60, 20};
            int[] scrollableColumnWidths = new int[]{40, 5, 5, 70, 20, 100, 30, 20, 20, 60, 20};
            int fixedRowHeight = 70;
            int fixedHeaderHeight = 70;

            TableRow row = new TableRow(this);

            //header (fixed vertically)
            row.setLayoutParams(wrapWrapTableRowParams);
            row.setGravity(Gravity.CENTER);
            row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableStaticHeaderColor));

            row.addView(makeTableRowWithText("Cr / Dr", fixedColumnWidths[7], fixedHeaderHeight, Gravity.RIGHT));
            row.addView(makeTableRowWithText("Amount", fixedColumnWidths[4], fixedHeaderHeight, Gravity.RIGHT));
            row.addView(makeTableRowWithText("Reference", fixedColumnWidths[5], fixedHeaderHeight, Gravity.RIGHT));

            row.addView(makeTableRowWithText("Transaction Date", fixedColumnWidths[6], fixedHeaderHeight, Gravity.RIGHT));

            row.addView(makeTableRowWithText("Type", fixedColumnWidths[8], fixedHeaderHeight, Gravity.RIGHT));

            row.addView(makeTableRowWithText("Uploaded Date", fixedColumnWidths[9], fixedHeaderHeight, Gravity.RIGHT));
            row.addView(makeTableRowWithText("Uploaded By", fixedColumnWidths[10], fixedHeaderHeight, Gravity.RIGHT));

            TableLayout fixedColumn = (TableLayout) findViewById(R.id.fixed_column);
            TextView fixedViewUserIdH = makeTableRowWithText("Name in Bank Statement", scrollableColumnWidths[0], fixedHeaderHeight, Gravity.LEFT);
            fixedViewUserIdH.setBackgroundColor(ContextCompat.getColor(this, R.color.tableStaticHeaderColor));
            fixedViewUserIdH.setLayoutParams(wrapWrapTableRowParams);
            fixedColumn.addView(fixedViewUserIdH);

            //rest of the table (within a scroll view)
            TableLayout scrollablePart = (TableLayout) findViewById(R.id.reportTableLayout);
            scrollablePart.addView(row);

            byte[] sObjet = (byte[]) getIntent().getSerializableExtra(CONST_PDF_ALL_STAGING_TRANSACTIONS);
            final List<StagingTransaction> allTransactions = (List<StagingTransaction>)CustomSerializer.deserializeObject(sObjet);

            boolean isdimGreen = true;
            int oriFixedRowHeight = fixedRowHeight;
            for (StagingTransaction bt : allTransactions) {

                //Fixed Columns
                //TextView fixedViewUserId = makeTableRowWithText(bt.userId, scrollableColumnWidths[0], fixedRowHeight, Gravity.LEFT);
                if(bt.name.length() > 20) fixedRowHeight = oriFixedRowHeight * 2;
                else fixedRowHeight = oriFixedRowHeight;

                TextView fixedViewUserId = makeTableRowWithText(bt.name, scrollableColumnWidths[0], fixedRowHeight, Gravity.LEFT | Gravity.CENTER_VERTICAL);
                //fixedViewUserId.setBackgroundColor(Color.BLUE);
                fixedColumn.addView(fixedViewUserId);
                //Fixed Columns

                //Scrollable columns
                row = new TableRow(this);

                row.setLayoutParams(wrapWrapTableRowParams);
                row.setGravity(Gravity.CENTER);
                row.setBackgroundColor(Color.WHITE);

                row.addView(makeTableRowWithText(bt.transactionFlow, scrollableColumnWidths[7], fixedRowHeight, Gravity.RIGHT | Gravity.CENTER_VERTICAL));

                row.addView(makeTableRowWithText(String.valueOf(bt.amount), scrollableColumnWidths[4], fixedRowHeight, Gravity.RIGHT | Gravity.CENTER_VERTICAL));

                row.addView(makeTableRowWithText(bt.reference, scrollableColumnWidths[5], fixedRowHeight, Gravity.RIGHT | Gravity.CENTER_VERTICAL));

                row.addView(makeTableRowWithText(String.valueOf(bt.transactionDate), scrollableColumnWidths[6], fixedRowHeight, Gravity.RIGHT | Gravity.CENTER_VERTICAL));


                row.addView(makeTableRowWithText(bt.type, scrollableColumnWidths[8], fixedRowHeight, Gravity.RIGHT | Gravity.CENTER_VERTICAL));

                row.addView(makeTableRowWithText(bt.updloadedDate.toString(), scrollableColumnWidths[9], fixedRowHeight, Gravity.RIGHT | Gravity.CENTER_VERTICAL));

                row.addView(makeTableRowWithText(bt.uploadedBy, scrollableColumnWidths[10], fixedRowHeight, Gravity.RIGHT | Gravity.CENTER_VERTICAL));

                if(isdimGreen) {
                    row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow1Color));
                    fixedViewUserId.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow1Color));
                    isdimGreen = !isdimGreen;
                }
                else {
                    row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow2Color));
                    fixedViewUserId.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow2Color));
                    isdimGreen = !isdimGreen;
                }

                scrollablePart.addView(row);

            }

        }catch (Exception e)
        {
            Log.e("Error", "Transaction Raw data activity has problem", e);
        }
    }
}
