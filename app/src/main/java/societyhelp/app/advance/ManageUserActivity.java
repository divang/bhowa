package societyhelp.app.advance;

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

import societyhelp.app.R;
import societyhelp.app.advance.DashBoardActivity;
import societyhelp.app.util.CustomSerializer;
import societyhelp.dao.mysql.impl.UserDetails;

public class ManageUserActivity extends DashBoardActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_user);
        setHeader(getString(R.string.manageUsersIconText), true, false);
        List<UserDetails> users = new ArrayList<>();
        try {
            byte[] sObjet = (byte[]) getIntent().getSerializableExtra(CONST_ALL_USERS);
            users = (List<UserDetails>) CustomSerializer.deserializeObject(sObjet);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            TableRow.LayoutParams wrapWrapTableRowParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int[] fixedColumnWidths = new int[]{40, 40, 20, 20, 30, 10, 15, 20, 60};
            int[] scrollableColumnWidths = new int[]{40, 40, 20, 20, 30, 10, 15, 20, 60};

            int fixedRowHeight = 50;
            int fixedHeaderHeight = 60;

            TableRow row = new TableRow(this);
            //header (fixed vertically)
            row.setLayoutParams(wrapWrapTableRowParams);
            row.setGravity(Gravity.CENTER);
            row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableStaticHeaderColor));

            row.addView(makeTableRowWithText("User Name", fixedColumnWidths[1], fixedHeaderHeight));
            row.addView(makeTableRowWithText("User Type", fixedColumnWidths[2], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Login Id", fixedColumnWidths[3], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Flat Id", fixedColumnWidths[4], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Email Id", fixedColumnWidths[5], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Mobile", fixedColumnWidths[6], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Address", fixedColumnWidths[7], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Name Alias", fixedColumnWidths[8], fixedHeaderHeight));

            TableLayout fixedColumn = (TableLayout) findViewById(R.id.fixed_column);
            TextView fixedViewUserIdH = makeTableRowWithText("User Id", scrollableColumnWidths[0], fixedHeaderHeight);
            fixedViewUserIdH.setBackgroundColor(ContextCompat.getColor(this, R.color.tableStaticHeaderColor));
            fixedViewUserIdH.setLayoutParams(wrapWrapTableRowParams);
            fixedColumn.addView(fixedViewUserIdH);
            fixedColumn.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow1Color));

            //rest of the table (within a scroll view)
            TableLayout scrollablePart = (TableLayout) findViewById(R.id.reportTableLayout);
            scrollablePart.addView(row);

            boolean rowColorFlip = true;
            for (UserDetails u : users) {
                //Fixed Columns
                TextView fixedViewUserId = makeTableRowWithText(u.userId, scrollableColumnWidths[0], fixedRowHeight);
                fixedColumn.addView(fixedViewUserId);

                //Scrollable columns
                row = new TableRow(this);
                row.setLayoutParams(wrapWrapTableRowParams);
                row.setGravity(Gravity.CENTER);

                if(rowColorFlip){
                    row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow1Color));
                    //fixedColumn.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow1Color));
                } else {
                    row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow2Color));
                    //fixedColumn.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow2Color));
                }

                row.addView(makeTableRowWithText(String.valueOf(u.userName), scrollableColumnWidths[1], fixedRowHeight));
                row.addView(makeTableRowWithText(String.valueOf(u.userType), scrollableColumnWidths[2], fixedRowHeight));
                row.addView(makeTableRowWithText(String.valueOf(u.loginId), scrollableColumnWidths[3], fixedRowHeight));
                row.addView(makeTableRowWithText(String.valueOf(u.flatId), scrollableColumnWidths[4], fixedRowHeight));
                row.addView(makeTableRowWithText(String.valueOf(u.emailId), scrollableColumnWidths[5], fixedRowHeight));
                row.addView(makeTableRowWithText(String.valueOf(u.mobileNo), scrollableColumnWidths[6], fixedRowHeight));
                row.addView(makeTableRowWithText(String.valueOf(u.address), scrollableColumnWidths[7], fixedRowHeight));
                row.addView(makeTableRowWithText(String.valueOf(u.nameAlias), scrollableColumnWidths[8], fixedRowHeight));

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

        /*
        try {
            setContentView(R.layout.activity_manage_user);

            setHeader(getString(R.string.manageUsersIconText), true, false);

            final TableLayout tableL = (TableLayout) findViewById(R.id.allUsersTableLayout);
            tableL.invalidate();

            for (UserDetails user : users) {
                TableRow row = new TableRow(this);
                TextView uId = new TextView(this);
                uId.setText(user.userId);
                row.addView(uId);
                TextView uName = new TextView(this);
                uName.setText(user.userName);
                row.addView(uName);
                TextView alName = new TextView(this);
                alName.setText(user.nameAlias);
                row.addView(alName);
                TextView uActive = new TextView(this);
                uActive.setText(Boolean.toString(user.isActive));
                row.addView(uActive);
                tableL.addView(row);
            }
        }catch (Exception e)
        {
            Log.e("Error", "User list fetch has problem", e);
        }
        */
    }
}
