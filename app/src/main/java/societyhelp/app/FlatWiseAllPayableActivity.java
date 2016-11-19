package societyhelp.app;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import java.util.List;
import societyhelp.app.util.CustomSerializer;
import societyhelp.dao.mysql.impl.FlatWisePayable;


public class FlatWiseAllPayableActivity extends DashBoardActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat_wise_all_payable);
        setHeader("", true, false);
        List<FlatWisePayable> flatWisePayables = null;

        try {
            byte[] sObjet = (byte[]) getIntent().getSerializableExtra(CONST_FLAT_WISE_PAYABLES);
            flatWisePayables = (List<FlatWisePayable>) CustomSerializer.deserializeObject(sObjet);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            int[] fixedColumnWidths = new int[]{10, 3, 10, 10, 10, 10, 10, 10, 30};
            int[] scrollableColumnWidths = new int[]{10, 3, 10, 10, 10, 10, 10, 10, 30};

            int fixedRowHeight = 50;
            int fixedHeaderHeight = 60;

            TableRow.LayoutParams wrapWrapTableRowParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            //Fixed column
            TableLayout fixedColumn = (TableLayout) findViewById(R.id.fixed_column);
            TextView fixedViewUserIdH = makeTableRowWithText("Flat Id", scrollableColumnWidths[0], fixedHeaderHeight);
            fixedViewUserIdH.setBackgroundColor(ContextCompat.getColor(this, R.color.tableStaticHeaderColor));
            fixedViewUserIdH.setLayoutParams(wrapWrapTableRowParams);
            fixedColumn.addView(fixedViewUserIdH);
            fixedColumn.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow1Color));

            TableRow row = new TableRow(this);
            row.setLayoutParams(wrapWrapTableRowParams);
            row.setGravity(Gravity.CENTER);
            row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableStaticHeaderColor));
            row.addView(makeTableRowWithText("Payment Id", fixedColumnWidths[1], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Status", fixedColumnWidths[2], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Year", fixedColumnWidths[3], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Month", fixedColumnWidths[4], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Amount", fixedColumnWidths[5], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Expense Type", fixedColumnWidths[6], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Payment Status", fixedColumnWidths[7], fixedHeaderHeight));

            //rest of the table (within a scroll view)
            TableLayout scrollablePart = (TableLayout) findViewById(R.id.reportTableLayout);
            scrollablePart.addView(row);

            for (FlatWisePayable fp : flatWisePayables) {

                //Fixed Columns
                TextView fixedViewUserId = makeTableRowWithText(fp.flatId, scrollableColumnWidths[0], fixedRowHeight);
                //fixedViewUserId.setBackgroundColor(Color.BLUE);
                fixedColumn.addView(fixedViewUserId);
                //Fixed Columns
                //Scrollable columns
                row = new TableRow(this);
                row.setLayoutParams(wrapWrapTableRowParams);
                row.setGravity(Gravity.CENTER);

                row.addView(makeTableRowWithText(String.valueOf(fp.paymentId), scrollableColumnWidths[1], fixedRowHeight));
                row.addView(makeTableRowWithText(String.valueOf(fp.status), scrollableColumnWidths[2], fixedRowHeight));
                row.addView(makeTableRowWithText(String.valueOf(fp.year), scrollableColumnWidths[3], fixedRowHeight));
                row.addView(makeTableRowWithText(String.valueOf(fp.month), scrollableColumnWidths[4], fixedRowHeight));
                row.addView(makeTableRowWithText(String.valueOf(fp.amount), scrollableColumnWidths[5], fixedRowHeight));
                row.addView(makeTableRowWithText(String.valueOf(fp.expenseType), scrollableColumnWidths[6], fixedRowHeight));
                row.addView(makeTableRowWithText(String.valueOf(fp.paymentStatus), scrollableColumnWidths[7], fixedRowHeight));

                scrollablePart.addView(row);
            }
            //Fixed Columns

        } catch (Exception e) {
            Log.e("Error", "Transaction Raw data activity has problem", e);
        }
    }
}
