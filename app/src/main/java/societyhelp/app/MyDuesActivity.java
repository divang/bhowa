package societyhelp.app;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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

        setHeader(getString(R.string.title_activity_my_dues), true, false);

        //setCustomToolBarBack();
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
            int[] fixedColumnWidths = new int[]{30, 20, 50};
            int[] scrollableColumnWidths = new int[]{30, 20, 50};

            int fixedRowHeight = 50;
            int fixedHeaderHeight = 60;

            TableRow row = new TableRow(this);
            //header (fixed vertically)
            row.setLayoutParams(wrapWrapTableRowParams);
            row.setGravity(Gravity.CENTER);
            row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableStaticHeaderColor));

            row.addView(makeTableRowWithText("Amount", fixedColumnWidths[1], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Transaction Date", fixedColumnWidths[2], fixedHeaderHeight));

            TableLayout fixedColumn = (TableLayout) findViewById(R.id.fixed_column);
            TextView fixedViewUserIdH = makeTableRowWithText("Type", scrollableColumnWidths[0], fixedHeaderHeight);
            fixedViewUserIdH.setBackgroundColor(ContextCompat.getColor(this, R.color.tableStaticHeaderColor));
            fixedViewUserIdH.setLayoutParams(wrapWrapTableRowParams);
            fixedColumn.addView(fixedViewUserIdH);
            fixedColumn.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow1Color));
            //rest of the table (within a scroll view)
            TableLayout scrollablePart = (TableLayout) findViewById(R.id.reportTableLayout);
            scrollablePart.addView(row);

            final List<SocietyHelpTransaction> allTransactions = SocietyHelpDatabaseFactory.getDBInstance().getFlatWiseTransactions(getFlatId());

            float totalDues = 0;
            boolean rowColorFlip = true;
            for (SocietyHelpTransaction bt : allTransactions) {

                //Fixed Columns
                TextView fixedViewUserId = makeTableRowWithText(bt.expenseType != null ? bt.expenseType.toString():""+" ("+bt.transactionFlow+")", scrollableColumnWidths[0], fixedRowHeight);
                //TextView fixedViewUserId = makeTableRowWithText(bt.transactionFlow, scrollableColumnWidths[0], fixedRowHeight);
                //fixedViewUserId.setBackgroundColor(Color.BLUE);
                fixedColumn.addView(fixedViewUserId);
                //Fixed Columns

                //Scrollable columns
                row = new TableRow(this);
                row.setLayoutParams(wrapWrapTableRowParams);
                row.setGravity(Gravity.CENTER);

                if(rowColorFlip) row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow1Color));
                else row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow2Color));

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
            fixedViewUserId.setBackgroundColor(Color.WHITE);
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


        } catch (Exception e) {
            Log.e("Error", "Transaction Raw data activity has problem", e);
        }
    }
}