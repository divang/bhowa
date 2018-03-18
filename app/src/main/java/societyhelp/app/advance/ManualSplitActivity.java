package societyhelp.app.advance;

import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import societyhelp.app.R;
import societyhelp.app.advance.DashBoardActivity;
import societyhelp.app.util.CustomSerializer;
import societyhelp.dao.mysql.impl.SocietyHelpTransaction;
import societyhelp.dao.mysql.impl.TransactionOnBalanceSheet;
import societyhelp.dao.mysql.impl.UserPaid;

public class ManualSplitActivity extends DashBoardActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_split);
        setHeader(getString(R.string.title_activity_user_revert_auto_split), true, false);

        List<TransactionOnBalanceSheet> autoSplittedTransactions = null;
        List<SocietyHelpTransaction> unSplittedTransactions = null;

        /*
        innerIntent.putExtra(CONST_UNSPLITTED_TRANSACTIONS, CustomSerializer.serializeObject(unSplittedTransactions));
        innerIntent.putExtra(CONST_SPLITTED_TRANSACTIONS, CustomSerializer.serializeObject(autoSplittedTransactions));*/

        List<UserPaid> userPaids = null;
        try {
            byte[] sObjet = (byte[]) getIntent().getSerializableExtra(CONST_UNSPLITTED_TRANSACTIONS);
            unSplittedTransactions = (List<SocietyHelpTransaction>) CustomSerializer.deserializeObject(sObjet);
            sObjet = (byte[]) getIntent().getSerializableExtra(CONST_SPLITTED_TRANSACTIONS);
            autoSplittedTransactions = (List<TransactionOnBalanceSheet>) CustomSerializer.deserializeObject(sObjet);

        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            TableRow.LayoutParams wrapWrapTableRowParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //Total,Splitted Amount,Expense Type,Date,Reference,Action
            int[] fixedColumnWidths = new int[]{20, 20, 20, 20, 20, 20};
            int[] scrollableColumnWidths = new int[]{20, 20, 20, 20, 20, 20};

            int fixedRowHeight = 50;
            int fixedHeaderHeight = 60;

            TableLayout fixedColumn = (TableLayout) findViewById(R.id.fixed_column);
            TextView fixedViewUserIdH = makeTableRowWithText("Amount", scrollableColumnWidths[0], fixedHeaderHeight);
            fixedViewUserIdH.setBackgroundColor(ContextCompat.getColor(this, R.color.tableStaticHeaderColor));
            fixedViewUserIdH.setLayoutParams(wrapWrapTableRowParams);
            fixedColumn.addView(fixedViewUserIdH);
            fixedColumn.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow1Color));

          //rest of the table (within a scroll view)
            TableLayout scrollablePart = (TableLayout) findViewById(R.id.reportTableLayout);
            TableRow row = new TableRow(this);
            //header (fixed vertically)
            row.setLayoutParams(wrapWrapTableRowParams);
            row.setGravity(Gravity.CENTER);
            row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableStaticHeaderColor));
            row.addView(makeTableRowWithText("Splitted Amount", fixedColumnWidths[1], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Expense Type", fixedColumnWidths[2], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Date", fixedColumnWidths[3], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Reference", fixedColumnWidths[4], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Action", fixedColumnWidths[5], fixedHeaderHeight));
            scrollablePart.addView(row);

            boolean rowColorFlip = true;

            for (SocietyHelpTransaction unSplitTransaction: unSplittedTransactions) {

                rowColorFlip = !rowColorFlip;
                //Fixed Columns
                TextView fixedViewUserId = makeTableRowWithText(String.valueOf(unSplitTransaction.amount), scrollableColumnWidths[0], fixedRowHeight);
                //fixedViewUserId.setBackgroundColor(Color.BLUE);
                fixedColumn.addView(fixedViewUserId);
                //Fixed Columns

                //Scrollable columns
                row = new TableRow(this);
                row.setLayoutParams(wrapWrapTableRowParams);
                row.setGravity(Gravity.CENTER);
                if (rowColorFlip)
                    row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow1Color));
                else row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow2Color));
                //User_ID,Payment_ID,Flat_ID,Amount,Paid_Date,Type,User_Comment,Admin_Comment
                //Total,Splitted Amount,Expense Type,Date,Reference,Action
                row.addView(makeTableRowWithEditText(scrollableColumnWidths[1], fixedRowHeight));
                row.addView(makeTableRowWithSpinner(getExpenseTypes(), scrollableColumnWidths[2], fixedRowHeight));
                row.addView(makeTableRowWithText(String.valueOf(unSplitTransaction.transactionDate), scrollableColumnWidths[3], fixedRowHeight));
                row.addView(makeTableRowWithText(String.valueOf(unSplitTransaction.reference), scrollableColumnWidths[4], fixedRowHeight));
                //row.addView(makeTableRowWithText(String.valueOf(paid.userComment), scrollableColumnWidths[8], fixedRowHeight));
                //row.addView(makeTableRowWithText(String.valueOf(paid.adminComment), scrollableColumnWidths[9], fixedRowHeight));

                scrollablePart.addView(row);
            }

        } catch (Exception e) {
            Log.e("Error", "Transaction Raw data activity has problem", e);
        }
    }

    protected  List<String> getExpenseTypes() throws Exception
    {
        String strList = (String) getIntent().getSerializableExtra(CONST_EXPENSE_TYPES);
        List<String> listTypes = new ArrayList<>();
        for(String flatId : strList.split(","))
        {
            listTypes.add(flatId);
        }
        return  listTypes;
    }

}
