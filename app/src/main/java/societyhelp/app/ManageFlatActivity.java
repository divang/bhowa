package societyhelp.app;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import societyhelp.app.util.CustomSerializer;
import societyhelp.dao.SocietyHelpDatabaseFactory;
import societyhelp.dao.mysql.impl.Flat;
import societyhelp.dao.mysql.impl.Login;
import societyhelp.dao.mysql.impl.WaterSuppyReading;

public class ManageFlatActivity extends DashBoardActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_flat);
        setHeader(getString(R.string.viewAllFlatIconText), true, false);
        List<Flat> flats = new ArrayList<>();
        try {
            byte[] sObjet = (byte[]) getIntent().getSerializableExtra(CONST_ALL_FLATS);
            flats = (List<Flat>) CustomSerializer.deserializeObject(sObjet);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            TableRow.LayoutParams wrapWrapTableRowParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int[] fixedColumnWidths = new int[]{20, 15, 40, 10, 30, 10, 15 };
            int[] scrollableColumnWidths = new int[]{20, 15, 40, 10, 30, 10, 15};

            int fixedRowHeight = 50;
            int fixedHeaderHeight = 60;

            TableRow row = new TableRow(this);
            //header (fixed vertically)
            row.setLayoutParams(wrapWrapTableRowParams);
            row.setGravity(Gravity.CENTER);
            row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableStaticHeaderColor));

            row.addView(makeTableRowWithText("Flat No", fixedColumnWidths[1], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Owner", fixedColumnWidths[2], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Area", fixedColumnWidths[3], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Maintenance Amount", fixedColumnWidths[4], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Block", fixedColumnWidths[5], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Status", fixedColumnWidths[6], fixedHeaderHeight));

            TableLayout fixedColumn = (TableLayout) findViewById(R.id.fixed_column);
            TextView fixedViewUserIdH = makeTableRowWithText("Flat Id", scrollableColumnWidths[0], fixedHeaderHeight);
            fixedViewUserIdH.setBackgroundColor(ContextCompat.getColor(this, R.color.tableStaticHeaderColor));
            fixedViewUserIdH.setLayoutParams(wrapWrapTableRowParams);
            fixedColumn.addView(fixedViewUserIdH);
            fixedColumn.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow1Color));

            //rest of the table (within a scroll view)
            TableLayout scrollablePart = (TableLayout) findViewById(R.id.reportTableLayout);
            scrollablePart.addView(row);

            boolean rowColorFlip = true;
            for (Flat l : flats) {
                //Fixed Columns
                TextView fixedViewUserId = makeTableRowWithText(l.flatId, scrollableColumnWidths[0], fixedRowHeight);
                fixedColumn.addView(fixedViewUserId);

                //Scrollable columns
                row = new TableRow(this);
                row.setLayoutParams(wrapWrapTableRowParams);
                row.setGravity(Gravity.CENTER);

                if(rowColorFlip){
                    row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow1Color));
                    //fixedColumn.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow1Color));
                }
                else {
                    row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow2Color));
                    //fixedColumn.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow2Color));
                }

                if(Integer.parseInt(l.flatNumber) < 10) {
                    row.addView(makeTableRowWithText("00"+String.valueOf(l.flatNumber), scrollableColumnWidths[1], fixedRowHeight));
                }else {
                    row.addView(makeTableRowWithText(String.valueOf(l.flatNumber), scrollableColumnWidths[1], fixedRowHeight));
                }
                row.addView(makeTableRowWithText(String.valueOf(l.owner), scrollableColumnWidths[2], fixedRowHeight));
                row.addView(makeTableRowWithText(String.valueOf(l.area), scrollableColumnWidths[3], fixedRowHeight));
                row.addView(makeTableRowWithText(String.valueOf(l.maintenanceAmount), scrollableColumnWidths[4], fixedRowHeight));
                row.addView(makeTableRowWithText(String.valueOf(l.block), scrollableColumnWidths[5], fixedRowHeight));
                row.addView(makeTableRowWithText(String.valueOf(l.status), scrollableColumnWidths[6], fixedRowHeight));
                scrollablePart.addView(row);

                rowColorFlip = !rowColorFlip;
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
