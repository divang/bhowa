package societyhelp.app.advance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import societyhelp.app.R;
import societyhelp.app.util.CustomSerializer;
import societyhelp.app.util.Util;
import societyhelp.dao.SocietyHelpDatabaseFactory;
import societyhelp.dao.mysql.impl.BankStatement;
import societyhelp.dao.mysql.impl.SocietyHelpTransaction;
import societyhelp.dao.mysql.impl.StagingTransaction;
import societyhelp.dao.mysql.impl.UserDetails;
import societyhelp.dao.mysql.impl.UserPaid;
import societyhelp.dao.mysql.impl.WaterSuppyReading;

public class ManageExpenditureTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_expenditure_type);
    }


    public static class UserAliasMappingActivity extends DashBoardActivity {

        List<UserDetails> users;
        private ProgressDialog progress;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            try {
                setContentView(R.layout.activity_transaction_raw_data);

                TableLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
                layoutParams.setMargins(10, 10, 10, 10);

                final byte[] data = (byte[]) getIntent().getSerializableExtra(CONST_PDF_ALL_STAGING_TRANSACTIONS);
                final List<StagingTransaction> sTrans = (List<StagingTransaction>) CustomSerializer.deserializeObject(data);
                final TableLayout tableL = (TableLayout) findViewById(R.id.reportTableLayout);
                users = SocietyHelpDatabaseFactory.getDBInstance().getAllUsers(getSocietyId());
                tableL.invalidate();

                TextView userIdTextHeading = new TextView(this);
                userIdTextHeading.setText("User Id list");
                tableL.addView(userIdTextHeading);

                TextView nameColHeading = new TextView(this);
                nameColHeading.setText("Not mapped Users");
                tableL.addView(nameColHeading);


                for (String btName : getUniqueNamesInTransaction(sTrans)) {
                    TableRow row = new TableRow(this);

                    row.setLayoutParams(layoutParams);

                    String userId = getUserID(btName, users);
                    if (userId != null) {
                        TextView userIdText = new TextView(this);
                        userIdText.setText(userId);
                        row.addView(userIdText);

                        TextView nameCol = new TextView(this);
                        nameCol.setText(btName);
                        row.addView(nameCol);

                    } else {

                        row.addView(getUserIDListView(users, btName));

                        TextView nameCol = new TextView(this);
                        nameCol.setText(btName);
                        row.addView(nameCol);
                    }

                    tableL.addView(row);
                }

            }catch (Exception e)
            {
                Log.e("Error", "Transaction Raw data activity has problem", e);
            }
        }

        private Spinner getUserIDListView(List<UserDetails> users, final String aliasName)
        {
            List<String> userIds = new ArrayList<>();
            userIds.add("Select User Id");
            for(UserDetails u : users) if(!userIds.contains(u.userId)) userIds.add(u.userId);

            ArrayAdapter<String> userIdsAdapter = new UserListViewAdaptor(userIds);
            userIdsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            final Spinner spinnerUserId = new Spinner(this);
            spinnerUserId.setAdapter(userIdsAdapter);
            spinnerUserId.setOnItemSelectedListener(new UserMappingAdapter(spinnerUserId, aliasName));
            spinnerUserId.setBackgroundColor(Color.GRAY);

            return spinnerUserId;
        }


        private String getUserID(String tName, List<UserDetails> users)
        {
            if(tName == null) return null;
            for(UserDetails u : users)
            {
                Log.d("info",tName + " " + u.toString());
                if(u.userName != null && tName.toLowerCase().equals(u.userName.toLowerCase())) return u.userId;
                if(u.nameAlias != null && u.nameAlias.toLowerCase().contains(tName.toLowerCase().trim())) return u.userId;
            }
            return null;
        }

        private UserDetails getUser(String userId)
        {
            for(UserDetails u : users) if(u.userId.equals(userId)) return u;
            return null;
        }

        private List<String> getUniqueNamesInTransaction(BankStatement bankStat)
        {
            List<String> unameList = new ArrayList<>();
            for(SocietyHelpTransaction bt : bankStat.allTransactions) if(!unameList.contains(bt.name.toUpperCase().trim())) unameList.add(bt.name.toUpperCase().trim());
            return unameList;
        }


        private List<String> getUniqueNamesInTransaction(List<StagingTransaction> transactions)
        {
            List<String> unameList = new ArrayList<>();
            for(StagingTransaction bt : transactions) if(!unameList.contains(bt.name.toUpperCase().trim())) unameList.add(bt.name.toUpperCase().trim());
            return unameList;
        }

        class UserMappingAdapter implements AdapterView.OnItemSelectedListener {

            final Spinner spinnerUserId;
            final String aliasName;
            boolean initialized = false;

            UserMappingAdapter(Spinner spinnerUserId, String aliasName){
                this.spinnerUserId = spinnerUserId;
                this.aliasName = aliasName;
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
                final String alias = u.nameAlias != null ? u.nameAlias + "," + aliasName : aliasName;

                progress = ProgressDialog.show(UserAliasMappingActivity.this, null, "Updating alias for UserId : " + userId + " alias : "+alias, true, false);
                progress.setCancelable(true);
                progress.show();
                Thread taskThread = new Thread(new Runnable() {
                    public void run() {
                        try {
                            SocietyHelpDatabaseFactory.getDBInstance().setAliasOfUserId(userId, alias);
                        } catch (Exception e) {
                            Log.e("Error", "Update alias has problem", e);
                        }
                        progress.dismiss();
                        progress.cancel();
                    }
                });
                taskThread.start();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
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

    }

    public static class VerifiedCashPaymentActivity extends DashBoardActivity {

        private ProgressDialog progress;
        private List<Integer> verifiedPaymentIds = new ArrayList<>();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_verified_cash_payment);
            setHeader(getString(R.string.title_activity_verify_user_cash_deposit), false, true);


            List<UserPaid> userPaids = null;
            try {
                byte[] sObjet = (byte[]) getIntent().getSerializableExtra(CONST_UN_VERIFIED_PAYMENT);
                userPaids = (List<UserPaid>) CustomSerializer.deserializeObject(sObjet);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                TableRow.LayoutParams wrapWrapTableRowParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                //User_ID,Payment_ID,Flat_ID,Amount,Paid_Date,Type,User_Comment,Admin_Comment
                int[] fixedColumnWidths = new int[]{20, 10, 5, 20, 20, 20, 30, 40, 40};
                int[] scrollableColumnWidths = new int[]{20, 10, 5, 20, 20, 20, 30, 40, 40};

                int fixedRowHeight = 50;
                int fixedHeaderHeight = 60;

                TableLayout fixedColumn = (TableLayout) findViewById(R.id.fixed_column);
                TextView fixedViewUserIdH = makeTableRowWithText("User ID", scrollableColumnWidths[0], fixedHeaderHeight);
                fixedViewUserIdH.setBackgroundColor(ContextCompat.getColor(this, R.color.tableStaticHeaderColor));
                fixedViewUserIdH.setLayoutParams(wrapWrapTableRowParams);
                fixedColumn.addView(fixedViewUserIdH);
                fixedColumn.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow1Color));

                TableLayout fixedVerificationColumn = (TableLayout) findViewById(R.id.fixed_verification_column);
                TextView fixedVerificationH = makeTableRowWithText("Verified", scrollableColumnWidths[1], fixedHeaderHeight);
                fixedVerificationH.setBackgroundColor(ContextCompat.getColor(this, R.color.tableStaticHeaderColor));
                fixedVerificationH.setLayoutParams(wrapWrapTableRowParams);
                fixedVerificationColumn.addView(fixedVerificationH);
                fixedVerificationColumn.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow1Color));

                //rest of the table (within a scroll view)
                TableLayout scrollablePart = (TableLayout) findViewById(R.id.reportTableLayout);
                TableRow row = new TableRow(this);
                //header (fixed vertically)
                row.setLayoutParams(wrapWrapTableRowParams);
                row.setGravity(Gravity.CENTER);
                row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableStaticHeaderColor));
                row.addView(makeTableRowWithText("Payment ID", fixedColumnWidths[2], fixedHeaderHeight));
                row.addView(makeTableRowWithText("Flat ID", fixedColumnWidths[3], fixedHeaderHeight));
                row.addView(makeTableRowWithText("Amount", fixedColumnWidths[4], fixedHeaderHeight));
                row.addView(makeTableRowWithText("Paid Date", fixedColumnWidths[5], fixedHeaderHeight));
                row.addView(makeTableRowWithText("Expense Type", fixedColumnWidths[6], fixedHeaderHeight));
                row.addView(makeTableRowWithText("User Comment", fixedColumnWidths[7], fixedHeaderHeight));
                row.addView(makeTableRowWithText("Admin Comment", fixedColumnWidths[8], fixedHeaderHeight));
                scrollablePart.addView(row);

                boolean rowColorFlip = true;

                for (UserPaid paid : userPaids) {

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

                    CheckBox verified = makeTableRowWithCheckBox(String.valueOf(paid.paymentId), scrollableColumnWidths[1], fixedRowHeight);
                    verified.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("info","msg....... payment id-" + v.getTag());
                            if(verifiedPaymentIds.contains(Integer.valueOf((String) v.getTag()))) {
                                Log.d("info","deselect ....... payment id-" + v.getTag());
                                verifiedPaymentIds.remove(Integer.valueOf((String) v.getTag()));
                            }
                            else {
                                Log.d("info","Select ....... payment id-" + v.getTag());
                                verifiedPaymentIds.add(Integer.valueOf((String) v.getTag()));
                            }
                        }
                    });
                    fixedVerificationColumn.addView(verified);

                    row.addView(makeTableRowWithText(String.valueOf(paid.paymentId), scrollableColumnWidths[2], fixedRowHeight));
                    row.addView(makeTableRowWithText(String.valueOf(paid.flatId), scrollableColumnWidths[3], fixedRowHeight));
                    row.addView(makeTableRowWithText(String.valueOf(paid.amount), scrollableColumnWidths[4], fixedRowHeight));
                    row.addView(makeTableRowWithText(String.valueOf(paid.expendDate), scrollableColumnWidths[5], fixedRowHeight));
                    row.addView(makeTableRowWithText(String.valueOf(paid.expenseType), scrollableColumnWidths[6], fixedRowHeight));
                    row.addView(makeTableRowWithText(String.valueOf(paid.userComment), scrollableColumnWidths[7], fixedRowHeight));
                    row.addView(makeTableRowWithText(String.valueOf(paid.adminComment), scrollableColumnWidths[8], fixedRowHeight));

                    scrollablePart.addView(row);
                }

                Button saveVerifiedBtn = (Button) findViewById(R.id.save_verified_payment);
                progress = ProgressDialog.show(this, null, "Saving verified cash payments...", true, true);
                progress.setCancelable(true);
                progress.hide();

                saveVerifiedBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String paymentIds = getStrPaymentIds();
                        if(paymentIds.length() == 0) {
                            Util.CustomToast(getApplicationContext(), "You have not verified any payment.", 1000);
                            return;
                        }
                        progress.show();
                        try {

                            new Thread(new Runnable() {
                                public void run() {
                                    try {
                                        SocietyHelpDatabaseFactory.getDBInstance().saveVerifiedCashPayment(getLoginId(), getStrPaymentIds());
                                        List<UserPaid> payments = SocietyHelpDatabaseFactory.getDBInstance().getUnVerifiedCashPayment();
                                        Intent intentInner = new Intent(getApplicationContext(), VerifiedCashPaymentActivity.class);
                                        byte[] sObj = CustomSerializer.serializeObject(payments);
                                        intentInner.putExtra(CONST_UN_VERIFIED_PAYMENT, sObj);
                                        startActivity(intentInner);

                                    } catch (Exception e)
                                    {
                                        Log.e("Error", "Fetching My dues verified data has problem", e);
                                    }
                                    progress.dismiss();
                                    progress.cancel();
                                }
                            }).start();
                        } catch (Exception e) {
                            Log.d("Error", "Verified transactions have some problem.");
                        }
                    }
                });

            } catch (Exception e) {
                Log.e("Error", "Transaction Raw data activity has problem", e);
            }
        }

        private String getStrPaymentIds()
        {
            StringBuilder ids = new StringBuilder();
            boolean first = true;
            for(Integer id : verifiedPaymentIds) {
                if(first){
                    first = false;
                }
                else ids.append(",");
                ids.append(id);
            }
            return ids.toString();
        }
    }

    public static class ViewRawDataActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_view_raw_data);

            final TableLayout tableL = (TableLayout)findViewById(R.id.rawDataLinesTableLayout);
            BankStatement bankStat = (BankStatement)getIntent().getSerializableExtra("bankStat");

            for(String  line : bankStat.rowdata)
            {
                TableRow row = new TableRow(this);
                TextView l = new TextView(this); l.setText(line);row.addView(l);
                tableL.addView(row);
            }

        }

    }

    public static class ViewWaterReadingActivity extends DashBoardActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_view_water_reading);
            setHeader(getString(R.string.title_activity_view_water_reading), true, false);

            try {
                TableRow.LayoutParams wrapWrapTableRowParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                int[] fixedColumnWidths = new int[]{30, 20, 20, 30, 50, 30, 30};
                int[] scrollableColumnWidths = new int[]{30, 20, 20, 30, 50, 30, 30};

                int fixedRowHeight = 50;
                int fixedHeaderHeight = 60;

                TableRow row = new TableRow(this);
                //header (fixed vertically)
                row.setLayoutParams(wrapWrapTableRowParams);
                row.setGravity(Gravity.CENTER);
                row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableStaticHeaderColor));
                row.addView(makeTableRowWithText("Meter Start", fixedColumnWidths[1], fixedHeaderHeight));
                row.addView(makeTableRowWithText("Meter Stop", fixedColumnWidths[2], fixedHeaderHeight));
                row.addView(makeTableRowWithText("Feed By", fixedColumnWidths[3], fixedHeaderHeight));
                row.addView(makeTableRowWithText("Supply Time", fixedColumnWidths[4], fixedHeaderHeight));
                row.addView(makeTableRowWithText("Tanker Capacity", fixedColumnWidths[5], fixedHeaderHeight));
                row.addView(makeTableRowWithText("Actual Capacity", fixedColumnWidths[6], fixedHeaderHeight));


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
                    row.addView(makeTableRowWithText(String.valueOf(wsr.readingBeforeSupply), scrollableColumnWidths[1], fixedRowHeight));
                    row.addView(makeTableRowWithText(String.valueOf(wsr.readingAfterSupply), scrollableColumnWidths[2], fixedRowHeight));
                    row.addView(makeTableRowWithText(String.valueOf(wsr.loginId), scrollableColumnWidths[3], fixedRowHeight));
                    row.addView(makeTableRowWithText(String.valueOf(wsr.SupplyTime), scrollableColumnWidths[4], fixedRowHeight));
                    row.addView(makeTableRowWithText(String.valueOf(wsr.capacityInLiter), scrollableColumnWidths[5], fixedRowHeight));
                    row.addView(makeTableRowWithText(String.valueOf(wsr.readingAfterSupply - wsr.readingBeforeSupply), scrollableColumnWidths[6], fixedRowHeight));
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
}
