package bhowa.app;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import bhowa.dao.BhowaDatabaseFactory;
import bhowa.dao.mysql.impl.BhowaTransaction;

public class MyTransactionsActivity extends DashBoardActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_transactions);
     //   setHeader("", true);

        try {
            TableRow.LayoutParams wrapWrapTableRowParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int[] fixedColumnWidths      = new int[]{30, 5, 5, 20, 20, 20, 20, 20, 20, 20, 20, 20, 30};
            int[] scrollableColumnWidths = new int[]{30, 5, 5, 20, 20, 20, 20, 20, 20, 20, 20, 20, 30};
            int fixedRowHeight = 50;
            int fixedHeaderHeight = 60;

            TableRow row = new TableRow(this);
            //header (fixed vertically)
            row.setLayoutParams(wrapWrapTableRowParams);
            row.setGravity(Gravity.CENTER);
            row.setBackgroundColor(Color.YELLOW);

            row.addView(makeTableRowWithText("Transaction Id", fixedColumnWidths[1], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Amount", fixedColumnWidths[2], fixedHeaderHeight));

            row.addView(makeTableRowWithText("Transaction Date", fixedColumnWidths[3], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Cr / Dr", fixedColumnWidths[4], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Type", fixedColumnWidths[5], fixedHeaderHeight));

            row.addView(makeTableRowWithText("Flat", fixedColumnWidths[6], fixedHeaderHeight));
            row.addView(makeTableRowWithText("User Comment", fixedColumnWidths[7], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Admin Comment", fixedColumnWidths[8], fixedHeaderHeight));

            TableLayout fixedColumn = (TableLayout) findViewById(R.id.fixed_column);
            TextView fixedViewUserIdH = makeTableRowWithText("Reference", scrollableColumnWidths[0], fixedHeaderHeight);
            fixedViewUserIdH.setBackgroundColor(Color.YELLOW);
            fixedViewUserIdH.setLayoutParams(wrapWrapTableRowParams);
            fixedColumn.addView(fixedViewUserIdH);
            fixedColumn.setBackgroundColor(Color.GREEN);
            //rest of the table (within a scroll view)
            TableLayout scrollablePart = (TableLayout) findViewById(R.id.reportTableLayout);
            scrollablePart.addView(row);

            final List<BhowaTransaction> allTransactions = BhowaDatabaseFactory.getDBInstance().getMyTransactions(getLoginId());


            for (BhowaTransaction bt : allTransactions) {

                //Fixed Columns
                TextView fixedViewUserId = makeTableRowWithText(bt.reference, scrollableColumnWidths[0], fixedRowHeight);
                //fixedViewUserId.setBackgroundColor(Color.BLUE);
                fixedColumn.addView(fixedViewUserId);
                //Fixed Columns

                //Scrollable columns
                row = new TableRow(this);
                row.setLayoutParams(wrapWrapTableRowParams);
                row.setGravity(Gravity.CENTER);
                row.setBackgroundColor(Color.WHITE);

                row.addView(makeTableRowWithText(String.valueOf(bt.transactionId), scrollableColumnWidths[1], fixedRowHeight));

                row.addView(makeTableRowWithText(String.valueOf(bt.amount), scrollableColumnWidths[2], fixedRowHeight));

                row.addView(makeTableRowWithText(String.valueOf(bt.transactionDate), scrollableColumnWidths[3], fixedRowHeight));

                row.addView(makeTableRowWithText(bt.transactionFlow, scrollableColumnWidths[4], fixedRowHeight));

                row.addView(makeTableRowWithText(bt.type, scrollableColumnWidths[5], fixedRowHeight));

                row.addView(makeTableRowWithText(bt.flatId, scrollableColumnWidths[6], fixedRowHeight));

                row.addView(makeTableRowWithText(bt.adminComment, scrollableColumnWidths[7], fixedRowHeight));

                row.addView(makeTableRowWithText(bt.userComment, scrollableColumnWidths[8], fixedRowHeight));

                scrollablePart.addView(row);

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
            Log.e("Error", "Transaction Raw data activity has problem", e);
        }
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
