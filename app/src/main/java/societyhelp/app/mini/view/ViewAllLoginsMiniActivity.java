package societyhelp.app.mini.view;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import societyhelp.app.R;
import societyhelp.app.advance.DashBoardActivity;
import societyhelp.app.mini.dashboard.DashBoardMiniActivity;
import societyhelp.app.util.CustomSerializer;
import societyhelp.dao.mysql.impl.Login;

public class ViewAllLoginsMiniActivity extends DashBoardMiniActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_view_all_logins);
        setHeader(getString(R.string.viewAllLoginIconText), true, false);
	    
        List<Login> allLogin = null;

        try {
            byte[] sObjet = (byte[]) getIntent().getSerializableExtra(CONST_ALL_LOGINS);
            allLogin = (List<Login>) CustomSerializer.deserializeObject(sObjet);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            TableRow.LayoutParams wrapWrapTableRowParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int[] fixedColumnWidths = new int[]{30, 20, 20, 20};
            int[] scrollableColumnWidths = new int[]{30, 20, 20, 20};

            int fixedRowHeight = 50;
            int fixedHeaderHeight = 60;

            TableLayout fixedColumn = (TableLayout) findViewById(R.id.fixed_column);
            TextView fixedViewUserIdH = makeTableRowWithText("Login Id", scrollableColumnWidths[0], fixedHeaderHeight);
            fixedViewUserIdH.setBackgroundColor(ContextCompat.getColor(this, R.color.tableStaticHeaderColor));
            fixedViewUserIdH.setLayoutParams(wrapWrapTableRowParams);
            fixedColumn.addView(fixedViewUserIdH);
            fixedColumn.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow1Color));

            TableRow row = new TableRow(this);
            row.setLayoutParams(wrapWrapTableRowParams);
            row.setGravity(Gravity.CENTER);
            row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableStaticHeaderColor));
            row.addView(makeTableRowWithText("Password", fixedColumnWidths[1], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Society Id", fixedColumnWidths[2], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Status", fixedColumnWidths[3], fixedHeaderHeight));

            //rest of the table (within a scroll view)
            TableLayout scrollablePart = (TableLayout) findViewById(R.id.reportTableLayout);
            scrollablePart.addView(row);

            boolean rowColorFlip = true;
            for (Login l : allLogin) {

                //Fixed Columns
                TextView fixedViewUserId = makeTableRowWithText(l.loginId, scrollableColumnWidths[0], fixedRowHeight);
                //fixedViewUserId.setBackgroundColor(Color.BLUE);
                fixedColumn.addView(fixedViewUserId);
                //Fixed Columns

                //Scrollable columns
                row = new TableRow(this);
                row.setLayoutParams(wrapWrapTableRowParams);
                row.setGravity(Gravity.CENTER);

                if(rowColorFlip) row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow1Color));
                else row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow2Color));

                row.addView(makeTableRowWithText(String.valueOf(l.password), scrollableColumnWidths[1], fixedRowHeight));
                row.addView(makeTableRowWithText(String.valueOf(l.societyId), scrollableColumnWidths[2], fixedRowHeight));
                row.addView(makeTableRowWithText(String.valueOf(l.status), scrollableColumnWidths[3], fixedRowHeight));

                scrollablePart.addView(row);
            }
        } catch (Exception e) {
            Log.e("Error", "All login has problem", e);
        }
    }


}
