package societyhelp.app;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
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

public class MyDuesActivity extends DashBoardActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dues);

        try {
            final TextView duesText = (TextView) findViewById(R.id.myDuesText);
            float myDues = (float) getIntent().getSerializableExtra("MyDueAmount");
            SpannableString spanString = new SpannableString("My dues : " + String.valueOf(myDues));
            spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
            spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanString.length(), 0);
            spanString.setSpan(new ForegroundColorSpan(Color.RED), 0, spanString.length(), 0);
            duesText.setText(spanString);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            TableRow.LayoutParams wrapWrapTableRowParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int[] fixedColumnWidths = new int[]{30, 20, 30};
            int[] scrollableColumnWidths = new int[]{30, 20, 30};

            int fixedRowHeight = 50;
            int fixedHeaderHeight = 60;

            TableRow row = new TableRow(this);
            //header (fixed vertically)
            row.setLayoutParams(wrapWrapTableRowParams);
            row.setGravity(Gravity.CENTER);
            row.setBackgroundColor(Color.YELLOW);

            row.addView(makeTableRowWithText("Amount", fixedColumnWidths[1], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Transaction Date", fixedColumnWidths[2], fixedHeaderHeight));

            TableLayout fixedColumn = (TableLayout) findViewById(R.id.fixed_column);
            TextView fixedViewUserIdH = makeTableRowWithText("Type", scrollableColumnWidths[0], fixedHeaderHeight);
            fixedViewUserIdH.setBackgroundColor(Color.YELLOW);
            fixedViewUserIdH.setLayoutParams(wrapWrapTableRowParams);
            fixedColumn.addView(fixedViewUserIdH);
            fixedColumn.setBackgroundColor(Color.GREEN);
            //rest of the table (within a scroll view)
            TableLayout scrollablePart = (TableLayout) findViewById(R.id.reportTableLayout);
            scrollablePart.addView(row);

            final List<SocietyHelpTransaction> allTransactions = SocietyHelpDatabaseFactory.getDBInstance().getFlatWiseTransactions(getFlatId());

            float totalDues = 0;
            for (SocietyHelpTransaction bt : allTransactions) {

                //Fixed Columns
                TextView fixedViewUserId = makeTableRowWithText(bt.transactionFlow, scrollableColumnWidths[0], fixedRowHeight);
                //fixedViewUserId.setBackgroundColor(Color.BLUE);
                fixedColumn.addView(fixedViewUserId);
                //Fixed Columns

                //Scrollable columns
                row = new TableRow(this);
                row.setLayoutParams(wrapWrapTableRowParams);
                row.setGravity(Gravity.CENTER);
                row.setBackgroundColor(Color.WHITE);

                if(bt.transactionFlow.equals("Payable"))
                {
                    TextView amountText = makeTableRowWithText("-" + String.valueOf(bt.amount), scrollableColumnWidths[1], fixedRowHeight);
                    amountText.setGravity(Gravity.RIGHT);
                    row.addView(amountText);
                    totalDues -= bt.amount;
                }
                else
                {
                    TextView amountText = makeTableRowWithText("+" + String.valueOf(bt.amount), scrollableColumnWidths[1], fixedRowHeight);
                    amountText.setGravity(Gravity.RIGHT);
                    row.addView(amountText);
                    totalDues += bt.amount;
                }
                row.addView(makeTableRowWithText(String.valueOf(bt.transactionDate), scrollableColumnWidths[2], fixedRowHeight));

                scrollablePart.addView(row);

            }

            TextView fixedViewUserId = makeTableRowWithText("Total Dues", scrollableColumnWidths[0], fixedRowHeight);
            fixedViewUserId.setBackgroundColor(Color.BLUE);
            fixedColumn.addView(fixedViewUserId);
            //Fixed Columns

            //Scrollable columns
            row = new TableRow(this);
            row.setLayoutParams(wrapWrapTableRowParams);
            row.setGravity(Gravity.CENTER);

            TextView duesAmountText = makeTableRowWithText(String.valueOf(totalDues), scrollableColumnWidths[1], fixedRowHeight);
            SpannableString spanString = new SpannableString(String.valueOf(totalDues));
            spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
            spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanString.length(), 0);
            spanString.setSpan(new ForegroundColorSpan(Color.RED), 0, spanString.length(), 0);
            duesAmountText.setText(spanString);
            row.addView(duesAmountText);

            scrollablePart.addView(row);


            final Button backButton = (Button) findViewById(R.id.backToReportBtn);
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        } catch (Exception e) {
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
        recyclableTextView.setGravity(Gravity.RIGHT);
        return recyclableTextView;
    }
}