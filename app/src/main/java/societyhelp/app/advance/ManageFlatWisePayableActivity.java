package societyhelp.app.advance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import societyhelp.app.R;
import societyhelp.app.util.CustomSerializer;
import societyhelp.app.util.ListViewAdaptor;
import societyhelp.app.util.SocietyHelpConstant;
import societyhelp.app.util.Util;
import societyhelp.dao.SocietyHelpDatabaseFactory;
import societyhelp.dao.mysql.impl.BankStatement;
import societyhelp.dao.mysql.impl.ExpenseType;
import societyhelp.dao.mysql.impl.Flat;
import societyhelp.dao.mysql.impl.FlatWisePayable;
import societyhelp.dao.DatabaseConstant;
import societyhelp.dao.mysql.impl.Login;
import societyhelp.dao.mysql.impl.SocietyHelpTransaction;
import societyhelp.dao.mysql.impl.UserDetails;
import societyhelp.dao.mysql.impl.UserPaid;

public class ManageFlatWisePayableActivity extends DashBoardActivity implements DatabaseConstant {

    private ProgressDialog progress;
    TextView monthText;
    TextView yearText;
    TextView amountText;
    Spinner expenseTypeSpinner;
    Spinner flatIdSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_flat_wise_payable);
        try {
                monthText = (TextView) findViewById(R.id.MonthText_FWP);
                yearText = (TextView) findViewById(R.id.YearText_FWP);
                expenseTypeSpinner = (Spinner) findViewById(R.id.expense_type_FWP);
                flatIdSpinner = (Spinner) findViewById(R.id.flatIdSpinner_FWP);
                amountText = (TextView) findViewById(R.id.amount_FWP);

                List<String> expTypes = new ArrayList<>();
                expTypes.add("Select the Expense Type");
                for(ExpenseType.ExpenseTypeConst eType : ExpenseType.ExpenseTypeConst.values()) {
                    if(eType.compareTo(ExpenseType.ExpenseTypeConst.Advance_Payment)!= 0) expTypes.add(eType.name());
                }
                expenseTypeSpinner.setAdapter(new ListViewAdaptor(getApplicationContext(), expTypes));

                List<String> flatIds = getFlatIds();
                flatIdSpinner.setAdapter(new ListViewAdaptor(getApplicationContext(), flatIds));

                Button generateMaintenanceBtn = (Button) findViewById(R.id.GenerateMaintenanceButton_FWP);

                progress = ProgressDialog.show(ManageFlatWisePayableActivity.this, null, "Generating Monthly Maintenance ...", true, false);
                progress.setCancelable(true);
                progress.hide();

                generateMaintenanceBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        if(validationFailed()) return;
                        progress.show();

                        Thread taskThread = new Thread(new Runnable() {
                            public void run() {

                                FlatWisePayable fwp = new FlatWisePayable();
                                fwp.expenseType = ExpenseType.ExpenseTypeConst.valueOf(expenseTypeSpinner.getSelectedItem().toString());
                                fwp.month = Integer.parseInt(monthText.getText().toString());
                                fwp.year = Integer.parseInt(yearText.getText().toString());
                                fwp.flatId = flatIdSpinner.getSelectedItem().toString();
                                fwp.amount = Float.parseFloat(amountText.getText().toString());

                                try {
                                    SocietyHelpDatabaseFactory.getDBInstance().addMonthlyMaintenance(fwp);
                                    Intent innerIntent = new Intent(getApplicationContext(), FlatWiseAllPayableActivity.class);
                                    List<FlatWisePayable> flatAllPayables = SocietyHelpDatabaseFactory.getDBInstance().getFlatWisePayables();
                                    byte[] sObj = CustomSerializer.serializeObject(flatAllPayables);
                                    innerIntent.putExtra(CONST_FLAT_WISE_PAYABLES, sObj);
                                    startActivity(innerIntent);
                                } catch (Exception e) {
                                    Log.e("Error", "Monthly Maintenance Generation has problem", e);
                                }
                                progress.dismiss();
                                progress.cancel();
                            }
                        });
                        taskThread.start();
                    }
                });

        }catch (Exception e)
        {
            Log.e("Error","Flat wise payable has some problem", e);
        }
    }

    private boolean validationFailed() {
        boolean validationFailed = false;

        if (monthText.getText().toString().length() == 0) {
            Util.CustomToast(getApplicationContext(), "Month should not be empty", 1000);
            validationFailed = true;
            return validationFailed;
        }

        try {
            int imonth = Integer.parseInt(monthText.getText().toString());
            if(imonth < 1 || imonth > 12)
            {
                Util.CustomToast(getApplicationContext(), "Month should be between 1 to 12.", 1000);
                validationFailed = true;
            }
        }catch (Exception e)
        {
            Util.CustomToast(getApplicationContext(), "Month should be a number.", 1000);
            validationFailed = true;
        }

        if (yearText.getText().toString().length() == 0) {
            Util.CustomToast(getApplicationContext(), "Year should not be empty", 1000);
            validationFailed = true;
            return validationFailed;
        }
        try {
            int iYear = Integer.parseInt(yearText.getText().toString());
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            if(!(iYear == currentYear || iYear == currentYear -1))
            {
                Util.CustomToast(getApplicationContext(), "Year should be current year or previous year.", 1000);
                validationFailed = true;
            }
        }catch (Exception e)
        {
            Util.CustomToast(getApplicationContext(), "Year should be a number.", 1000);
            validationFailed = true;
        }

        if(expenseTypeSpinner.getSelectedItemId() <=0)
        {
            Util.CustomToast(getApplicationContext(), "Expense type is not selected.", 1000);
            validationFailed = true;
        }
        else
        {
            if(!ExpenseType.ExpenseTypeConst.Monthly_Maintenance.equals(
                    ExpenseType.ExpenseTypeConst.valueOf(expenseTypeSpinner.getSelectedItem().toString())))
            {
                if (amountText.getText().toString().length() == 0) {
                    Util.CustomToast(getApplicationContext(), "Amount should not be empty", 1000);
                    validationFailed = true;
                    return validationFailed;
                }
                else {
                    try
                    {
                        float fAmount = Float.valueOf(amountText.getText().toString());
                        if(fAmount <= 0)
                        {
                            Util.CustomToast(getApplicationContext(), "Amount should be greater then zero.", 1000);
                            validationFailed = true;
                        }
                    }
                    catch (Exception e) {
                        Util.CustomToast(getApplicationContext(), "Amount should be a number.", 1000);
                        validationFailed = true;
                    }
                }
            }
        }

        if(flatIdSpinner.getSelectedItemId()<= 0)
        {
            Util.CustomToast(getApplicationContext(), "Flat is not selected.", 1000);
            validationFailed = true;
        }

        return validationFailed;
    }

    protected List<String> getFlatIds() throws Exception
    {
        List<String> listFlatIds = new ArrayList<>();
        listFlatIds.add("Select Flat Id");
        listFlatIds.add(CONST_LIST_STR_ALL_FLATS);
        List<Flat> flats = SocietyHelpDatabaseFactory.getDBInstance().getAllFlats(getSocietyId());
        for(Flat f : flats) {
            listFlatIds.add(f.flatId);
        }
        return  listFlatIds;
    }

    public static class NotificationActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_notification);
        }

    }

    public static class SplitTransactionsFlatWiseActivity extends DashBoardActivity {

        private ProgressDialog progress;
        private List<Integer> verifiedPaymentIds = new ArrayList<>();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_split_transactions_flat_wise);
            setHeader(getString(R.string.title_activity_verify_splitted_transaction), false, true);

            List<UserPaid> userPaids = null;
            try {
                byte[] sObjet = (byte[]) getIntent().getSerializableExtra(CONST_SPLITTED_TRANSACTION);
                userPaids = (List<UserPaid>) CustomSerializer.deserializeObject(sObjet);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                TableRow.LayoutParams wrapWrapTableRowParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                //User_ID,Payment_ID,Flat_ID,Amount,Paid_Date,Type,User_Comment,Admin_Comment
                int[] fixedColumnWidths = new int[]{20, 10, 5, 20, 20, 20, 30, 20, 40, 40};
                int[] scrollableColumnWidths = new int[]{20, 10, 5, 20, 20, 20, 30, 20, 40, 40};

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
                row.addView(makeTableRowWithText("User Comment", fixedColumnWidths[8], fixedHeaderHeight));
                row.addView(makeTableRowWithText("Admin Comment", fixedColumnWidths[9], fixedHeaderHeight));
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
                    if (rowColorFlip)
                        row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow1Color));
                    else row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow2Color));
                    //User_ID,Payment_ID,Flat_ID,Amount,Paid_Date,Type,User_Comment,Admin_Comment

                    CheckBox verified = makeTableRowWithCheckBox(String.valueOf(paid.paymentId), scrollableColumnWidths[1], fixedRowHeight);
                    verified.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("info", "msg....... payment id-" + v.getTag());
                            if (verifiedPaymentIds.contains(Integer.valueOf((String) v.getTag()))) {
                                Log.d("info", "deselect ....... payment id-" + v.getTag());
                                verifiedPaymentIds.remove(Integer.valueOf((String) v.getTag()));
                            } else {
                                Log.d("info", "Select ....... payment id-" + v.getTag());
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
                    row.addView(makeTableRowWithText(String.valueOf(paid.userComment), scrollableColumnWidths[8], fixedRowHeight));
                    row.addView(makeTableRowWithText(String.valueOf(paid.adminComment), scrollableColumnWidths[9], fixedRowHeight));

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
                        if (paymentIds.length() == 0) {
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
                                        Intent intentInner = new Intent(getApplicationContext(), ManageExpenditureTypeActivity.VerifiedCashPaymentActivity.class);
                                        byte[] sObj = CustomSerializer.serializeObject(payments);
                                        intentInner.putExtra(CONST_UN_VERIFIED_PAYMENT, sObj);
                                        startActivity(intentInner);

                                    } catch (Exception e) {
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

        private String getStrPaymentIds() {
            StringBuilder ids = new StringBuilder();
            boolean first = true;
            for (Integer id : verifiedPaymentIds) {
                if (first) {
                    first = false;
                } else ids.append(",");
                ids.append(id);
            }
            return ids.toString();
        }
    }

    public static class StatusMessageActivity extends AppCompatActivity implements SocietyHelpConstant {

        private SharedPreferences prefs;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_status_message);
            setCustomToolBar();
            prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            String societyId = prefs.getString(CONST_SOCIETY_ID_KEY_PREF_MANAGER, "");
            Log.d("info", "societyId : " + societyId);

            List<Login> logins = null;
            try {
                logins = SocietyHelpDatabaseFactory.getMasterDBInstance().getAllLogins(societyId);
                Log.d("info", "all logins : " + societyId);

            } catch (Exception e) {
                Log.e("error", e.getMessage(),e);
            }
            Log.i("info","all logins:" + logins);


        }

        protected void setCustomToolBar() {
            android.support.v7.app.ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayShowHomeEnabled(true);
            //displaying custom ActionBar
            View mActionBarView = getLayoutInflater().inflate(R.layout.login_tool_bar, null);
            actionBar.setCustomView(mActionBarView);
            actionBar.setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        }

        public void createAdminLogin(View v) {
            try {
                String societyId = prefs.getString(CONST_SOCIETY_ID_KEY_PREF_MANAGER, "");
                Log.d("info", "societyId : " + societyId);

                List<Login> logins = SocietyHelpDatabaseFactory.getMasterDBInstance().getAllLogins(societyId);
                Log.i("info","all logins:" + logins);

                Log.d("DEBUG","creating admin login...");
                Intent societyIntent = new Intent(v.getContext(), CreateAdminLoginIdActivity.class);
                startActivity(societyIntent);
            } catch (Exception e) {
                Log.e("Error", "Admin Login creation failed", e);
            }
        }
    }

    public static class TransactionRawDataActivity extends DashBoardActivity {

        List<UserDetails> users;
        private ProgressDialog progress;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            try {
                setContentView(R.layout.activity_transaction_raw_data);
                int[] fixedColumnWidths = new int[]{20, 5, 5, 70, 20, 70, 100, 20, 20};
                int[] scrollableColumnWidths = new int[]{20, 5, 5, 70, 20, 70, 100, 20, 20};
                int fixedRowHeight = 70;
                int fixedHeaderHeight = 70;

                TableRow.LayoutParams wrapWrapTableRowParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                TableRow row = getTableRow();

                row.addView(makeTableRowWithText("Sr", fixedColumnWidths[2], fixedHeaderHeight, Gravity.LEFT));
                row.addView(makeTableRowWithText("Name in transaction", fixedColumnWidths[3], fixedHeaderHeight, Gravity.LEFT));
                row.addView(makeTableRowWithText("Amount", fixedColumnWidths[4], fixedHeaderHeight, Gravity.RIGHT));
                row.addView(makeTableRowWithText("Transaction Date", fixedColumnWidths[5], fixedHeaderHeight, Gravity.RIGHT));
                row.addView(makeTableRowWithText("Reference", fixedColumnWidths[6], fixedHeaderHeight, Gravity.RIGHT));
                row.addView(makeTableRowWithText("Cr / Dr", fixedColumnWidths[7], fixedHeaderHeight, Gravity.RIGHT));
                row.addView(makeTableRowWithText("Type", fixedColumnWidths[8], fixedHeaderHeight, Gravity.RIGHT));

                TableLayout fixedColumn = (TableLayout) findViewById(R.id.fixed_column);
                TextView fixedViewUserIdH = makeTableRowWithText("User Id", scrollableColumnWidths[0], fixedHeaderHeight, Gravity.LEFT);
                fixedViewUserIdH.setBackgroundColor(ContextCompat.getColor(this, R.color.tableStaticHeaderColor));
                fixedViewUserIdH.setLayoutParams(wrapWrapTableRowParams);
                fixedColumn.addView(fixedViewUserIdH);

                //rest of the table (within a scroll view)
                TableLayout scrollablePart = (TableLayout) findViewById(R.id.reportTableLayout);
                scrollablePart.addView(row);


                final BankStatement bankStat = (BankStatement) getIntent().getSerializableExtra("bankStat");
                users = SocietyHelpDatabaseFactory.getDBInstance().getAllUsers(getSocietyId());

                int srNo = 0;
                boolean isdimGreen = true;

                for (SocietyHelpTransaction bt : bankStat.allTransactions) {
                    srNo++;

                    //Fixed Columns
                    String userId = getUserID(bt.name, users);
                    TextView fixedViewUserId = makeTableRowWithText(userId, scrollableColumnWidths[0], fixedRowHeight, Gravity.LEFT);
                    //fixedViewUserId.setBackgroundColor(Color.BLUE);
                    fixedColumn.addView(fixedViewUserId);
                    //Fixed Columns

                    //Scrollable columns
                    row = getTableRow();

                    row.addView(makeTableRowWithText(String.valueOf(srNo), scrollableColumnWidths[2], fixedRowHeight, Gravity.LEFT));

                    row.addView(makeTableRowWithText(bt.name, scrollableColumnWidths[3], fixedRowHeight, Gravity.LEFT));

                    row.addView(makeTableRowWithText(String.valueOf(bt.amount), scrollableColumnWidths[4], fixedRowHeight, Gravity.RIGHT));

                    row.addView(makeTableRowWithText(String.valueOf(bt.transactionDate), scrollableColumnWidths[5], fixedRowHeight, Gravity.RIGHT));

                    row.addView(makeTableRowWithText(bt.reference, scrollableColumnWidths[6], fixedRowHeight, Gravity.RIGHT));

                    row.addView(makeTableRowWithText(bt.transactionFlow, scrollableColumnWidths[7], fixedRowHeight, Gravity.RIGHT));

                    row.addView(makeTableRowWithText(bt.type, scrollableColumnWidths[8], fixedRowHeight, Gravity.RIGHT));

                    scrollablePart.addView(row);


                    if (userId == null) {
                        row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRowNotVerifiedColor));
                        fixedViewUserId.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRowNotVerifiedColor));
                    }
                    else if(isdimGreen) {
                        row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow1Color));
                        fixedViewUserId.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow1Color));
                        isdimGreen = !isdimGreen;
                    }
                    else {
                        row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow2Color));
                        fixedViewUserId.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow2Color));
                        isdimGreen = !isdimGreen;
                    }

                }

            }catch (Exception e)
            {
                Log.e("Error","Transaction Raw data activity has problem", e);
            }
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



    }
}