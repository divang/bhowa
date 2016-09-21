package societyhelp.app;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import societyhelp.app.util.CustomSerializer;
import societyhelp.dao.SocietyHelpDatabaseFactory;
import societyhelp.dao.mysql.impl.SocietyHelpTransaction;
import societyhelp.dao.mysql.impl.UserCashPaid;

public class VerifiedCashPaymentActivity extends DashBoardActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verified_cash_payment);
        setHeader("", true, false);
        //setCustomToolBarBack();

        List<UserCashPaid> userCashPaids = null;
        try {
            byte[] sObjet = (byte[]) getIntent().getSerializableExtra(CONST_UN_VERIFIED_PAYMENT);
            userCashPaids = (List<UserCashPaid>) CustomSerializer.deserializeObject(sObjet);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            TableRow.LayoutParams wrapWrapTableRowParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //User_ID,Payment_ID,Flat_ID,Amount,Paid_Date,Type,User_Comment,Admin_Comment
            int[] fixedColumnWidths = new int[]{20, 5, 20, 20, 20, 30, 40, 40};
            int[] scrollableColumnWidths = new int[]{20, 5, 20, 20, 20, 30, 40, 40};

            int fixedRowHeight = 50;
            int fixedHeaderHeight = 60;


            TableLayout fixedColumn = (TableLayout) findViewById(R.id.fixed_column);
            TextView fixedViewUserIdH = makeTableRowWithText("User ID", scrollableColumnWidths[0], fixedHeaderHeight);
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
            row.addView(makeTableRowWithText("Payment ID", fixedColumnWidths[1], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Flat ID", fixedColumnWidths[2], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Amount", fixedColumnWidths[3], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Paid Date", fixedColumnWidths[4], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Expense Type", fixedColumnWidths[5], fixedHeaderHeight));
            row.addView(makeTableRowWithText("User Comment", fixedColumnWidths[6], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Admin Comment", fixedColumnWidths[7], fixedHeaderHeight));
            scrollablePart.addView(row);

            boolean rowColorFlip = true;

            for (UserCashPaid paid : userCashPaids) {

                rowColorFlip = !rowColorFlip;
                //Fixed Columns
                TextView fixedViewUserId = makeTableRowWithText(paid.userId, scrollableColumnWidths[0], fixedRowHeight);
                //fixedViewUserId.setBackgroundColor(Color.BLUE);
                fixedColumn.addView(fixedViewUserId);
                //Fixed Columns

                //Scrollable columns
                row = new TableRow(this);
                row.setLayoutParams(wrapWrapTableRowParams);
                row.setGravity(Gravity.CENTER);
                if(rowColorFlip) row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow1Color));
                else row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow2Color));
                //User_ID,Payment_ID,Flat_ID,Amount,Paid_Date,Type,User_Comment,Admin_Comment
                row.addView(makeTableRowWithText(String.valueOf(paid.paymentId), scrollableColumnWidths[1], fixedRowHeight));
                row.addView(makeTableRowWithText(String.valueOf(paid.flatId), scrollableColumnWidths[2], fixedRowHeight));
                row.addView(makeTableRowWithText(String.valueOf(paid.amount), scrollableColumnWidths[3], fixedRowHeight));
                row.addView(makeTableRowWithText(String.valueOf(paid.expendDate), scrollableColumnWidths[4], fixedRowHeight));
                row.addView(makeTableRowWithText(String.valueOf(paid.expenseType), scrollableColumnWidths[5], fixedRowHeight));
                row.addView(makeTableRowWithText(String.valueOf(paid.userComment), scrollableColumnWidths[6], fixedRowHeight));
                row.addView(makeTableRowWithText(String.valueOf(paid.adminComment), scrollableColumnWidths[7], fixedRowHeight));

                scrollablePart.addView(row);
            }

        } catch (Exception e) {
            Log.e("Error", "Transaction Raw data activity has problem", e);
        }
    }
}
