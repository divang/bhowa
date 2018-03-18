package societyhelp.app.transaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import societyhelp.app.advance.DashBoardActivity;
import societyhelp.app.R;
import societyhelp.app.util.CustomSerializer;
import societyhelp.dao.SocietyHelpDatabaseFactory;
import societyhelp.dao.mysql.impl.StagingTransaction;
import societyhelp.dao.mysql.impl.UserDetails;

public class UnSettledCreditActivity extends DashBoardActivity {

    private List<UserDetails> users;
    private ProgressDialog progress;
    private List<StagingTransaction> mappedTransactions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_un_settled_credit);
        setHeader(getString(R.string.title_activity_unshettled_credit_transactions), false, true);

        try {

            byte[] obj = (byte[])getIntent().getSerializableExtra(CONST_UN_SETTLED_TRANSACTIONS);
            List<StagingTransaction> stagingTransaction = (List<StagingTransaction>) CustomSerializer.deserializeObject(obj);

            obj = (byte[])getIntent().getSerializableExtra(CONST_ALL_USERS);
            users = (List<UserDetails>) CustomSerializer.deserializeObject(obj);

            TableRow.LayoutParams wrapWrapTableRowParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int[] fixedColumnWidths = new int[]{20, 40, 20, 30, 30, 30, 30};
            int[] scrollableColumnWidths = new int[]{20, 40, 20, 30, 30, 30, 30};

            int fixedRowHeight = 120;
            int fixedHeaderHeight = 100;

            TableRow row = new TableRow(this);
            //header (fixed vertically)
            row.setLayoutParams(wrapWrapTableRowParams);
            row.setGravity(Gravity.CENTER);
            row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableStaticHeaderColor));
            row.addView(makeTableRowWithText("Map User Id", fixedColumnWidths[1], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Date", fixedColumnWidths[2], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Reference", fixedColumnWidths[3], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Name", fixedColumnWidths[4], fixedHeaderHeight));


            TableLayout fixedColumn = (TableLayout) findViewById(R.id.fixed_column);
            TextView fixedViewUserIdH = makeTableRowWithText("Amount", scrollableColumnWidths[0], fixedHeaderHeight);
            fixedViewUserIdH.setBackgroundColor(ContextCompat.getColor(this, R.color.tableStaticHeaderColor));
            fixedViewUserIdH.setLayoutParams(wrapWrapTableRowParams);
            fixedColumn.addView(fixedViewUserIdH);
            fixedColumn.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow1Color));

            //rest of the table (within a scroll view)
            TableLayout scrollablePart = (TableLayout) findViewById(R.id.reportTableLayout);
            scrollablePart.addView(row);

            boolean rowColorFlip = true;

            for (StagingTransaction st : stagingTransaction) {

                //Fixed Columns
                TextView fixedViewUserId = makeTableRowWithText(String.valueOf(st.amount), scrollableColumnWidths[0], fixedRowHeight);
                fixedColumn.addView(fixedViewUserId);

                //Scrollable columns
                row = new TableRow(this);
                row.setLayoutParams(wrapWrapTableRowParams);
                row.setGravity(Gravity.CENTER);

                if(rowColorFlip) row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow1Color));
                else row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow2Color));
                row.addView(getUserIDListView(users, st), scrollableColumnWidths[1], fixedRowHeight);
                row.addView(makeTableRowWithText(String.valueOf(st.transactionDate), scrollableColumnWidths[2], fixedRowHeight));
                row.addView(makeTableRowWithText(String.valueOf(st.reference), scrollableColumnWidths[3], fixedRowHeight));
                row.addView(makeTableRowWithText(String.valueOf(st.name), scrollableColumnWidths[4], fixedRowHeight));

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

    private Spinner getUserIDListView(List<UserDetails> users, StagingTransaction transaction)
    {
        List<String> userIds = new ArrayList<>();
        userIds.add("Select User Id");
        for(UserDetails u : users) if(!userIds.contains(u.userId)) userIds.add(u.userId);

        ArrayAdapter<String> userIdsAdapter = new UserListViewAdaptor(userIds);
        userIdsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final Spinner spinnerUserId = new Spinner(this);
        spinnerUserId.setAdapter(userIdsAdapter);
        spinnerUserId.setOnItemSelectedListener(new UserMappingAdapter(spinnerUserId, transaction));

        return spinnerUserId;
    }

    class UserListViewAdaptor extends  ArrayAdapter{

        UserListViewAdaptor(List<String> userIds) {
            super(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, userIds);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v = super.getView(position, convertView, parent);
            ((TextView) v).setTextSize(16);
            ((TextView) v).setTextColor(Color.BLACK);

            return v;
        }

        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View v = super.getDropDownView(position, convertView, parent);
            ((TextView) v).setTextColor(Color.BLACK);
            ((TextView) v).setGravity(Gravity.LEFT);

            return v;
        }
    }

    class UserMappingAdapter implements AdapterView.OnItemSelectedListener {

        final Spinner spinnerUserId;
        final StagingTransaction transaction;
        boolean initialized = false;

        UserMappingAdapter(Spinner spinnerUserId, StagingTransaction t){
            this.spinnerUserId = spinnerUserId;
            this.transaction = t;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            final String userId = spinnerUserId.getSelectedItem().toString();

            if(!initialized)
            {
                initialized = true;
                return;
            }

            UserDetails u = getUser(userId);
            transaction.userId = u.userId;
            transaction.flatId = u.flatId;
            transaction.splitted = true;
            transaction.verifiedBy = getLoginId();
            mappedTransactions.add(transaction);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private UserDetails getUser(String userId)
    {
        for(UserDetails u : users) if(u.userId.equals(userId)) return u;
        return null;
    }


    public void saveSettledCreditTransaction(View v) {

        progress = ProgressDialog.show(UnSettledCreditActivity.this, null, "Update credit transaction", true, false);
        progress.setCancelable(true);
        progress.show();
        Thread taskThread = new Thread(new Runnable() {
            public void run() {
                try {
                    SocietyHelpDatabaseFactory.getDBInstance().settledCreditTransaction(mappedTransactions);

                    Intent nextIntent = new Intent(getApplicationContext(), TransactionHomeActivity.class);
                    startActivityForResult(nextIntent, 0);
                    finish();
                } catch (Exception e) {
                    Log.e("Error", "Mapped unsettled credit transaction has problem", e);
                }
                progress.dismiss();
                progress.cancel();
            }
        });
        taskThread.start();
    }
}
