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
import societyhelp.dao.mysql.impl.WaterSuppyReading;

public class ViewWaterReadingActivity extends DashBoardActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_water_reading);
        setHeader(getString(R.string.title_activity_view_water_reading), true, false);

        try {
            TableRow.LayoutParams wrapWrapTableRowParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int[] fixedColumnWidths = new int[]{30, 20, 20, 20, 20, 20,30};
            int[] scrollableColumnWidths = new int[]{30, 20, 20, 20, 20, 20,30};

            int fixedRowHeight = 50;
            int fixedHeaderHeight = 60;

            TableRow row = new TableRow(this);
            //header (fixed vertically)
            row.setLayoutParams(wrapWrapTableRowParams);
            row.setGravity(Gravity.CENTER);
            row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableStaticHeaderColor));

            row.addView(makeTableRowWithText("Supply Time", fixedColumnWidths[1], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Capacity", fixedColumnWidths[2], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Start Meter Reading", fixedColumnWidths[3], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Stop Meter Reading", fixedColumnWidths[4], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Monitored By", fixedColumnWidths[5], fixedHeaderHeight));

            TableLayout fixedColumn = (TableLayout) findViewById(R.id.fixed_column);
            TextView fixedViewUserIdH = makeTableRowWithText("Water Supplier", scrollableColumnWidths[0], fixedHeaderHeight);
            fixedViewUserIdH.setBackgroundColor(ContextCompat.getColor(this, R.color.tableStaticHeaderColor));
            fixedViewUserIdH.setLayoutParams(wrapWrapTableRowParams);
            fixedColumn.addView(fixedViewUserIdH);
            fixedColumn.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow1Color));

            //rest of the table (within a scroll view)
            TableLayout scrollablePart = (TableLayout) findViewById(R.id.reportTableLayout);
            scrollablePart.addView(row);

            byte[] obj = (byte[])getIntent().getSerializableExtra(CONST_WATER_READINGS);
            List<WaterSuppyReading> readings = (List<WaterSuppyReading>) CustomSerializer.deserializeObject(obj);

            float totalDues = 0;
            boolean rowColorFlip = true;
            for (WaterSuppyReading wsr : readings) {
                //Fixed Columns
                TextView fixedViewUserId = makeTableRowWithText(wsr.supplierName, scrollableColumnWidths[0], fixedRowHeight);
                fixedColumn.addView(fixedViewUserId);

                //Scrollable columns
                row = new TableRow(this);
                row.setLayoutParams(wrapWrapTableRowParams);
                row.setGravity(Gravity.CENTER);

                if(rowColorFlip) row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow1Color));
                else row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow2Color));

                row.addView(makeTableRowWithText(String.valueOf(wsr.SupplyTime), scrollableColumnWidths[1], fixedRowHeight));
                row.addView(makeTableRowWithText(String.valueOf(wsr.capacityInLiter), scrollableColumnWidths[2], fixedRowHeight));
                row.addView(makeTableRowWithText(String.valueOf(wsr.readingBeforeSupply), scrollableColumnWidths[3], fixedRowHeight));
                row.addView(makeTableRowWithText(String.valueOf(wsr.readingAfterSupply), scrollableColumnWidths[4], fixedRowHeight));
                row.addView(makeTableRowWithText(String.valueOf(wsr.loginId), scrollableColumnWidths[5], fixedRowHeight));
                scrollablePart.addView(row);

            }

            //Scrollable columns
            row = new TableRow(this);
            row.setLayoutParams(wrapWrapTableRowParams);
            row.setGravity(Gravity.CENTER);

        } catch (Exception e) {
            Log.e("Error", "Transaction Raw data activity has problem", e);
        }
    }


}
