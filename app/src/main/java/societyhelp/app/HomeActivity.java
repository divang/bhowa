package societyhelp.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import societyhelp.app.util.FileChooser;
import societyhelp.app.util.CustomSerializer;
import societyhelp.core.SocietyAuthorization;
import societyhelp.dao.SocietyHelpDatabaseFactory;
import societyhelp.dao.mysql.impl.BankStatement;
import societyhelp.dao.mysql.impl.ExpenseType;
import societyhelp.dao.mysql.impl.Flat;
import societyhelp.dao.mysql.impl.Login;
import societyhelp.dao.mysql.impl.UserPaid;
import societyhelp.dao.mysql.impl.UserDetails;
import societyhelp.parser.SocietyHelpParserFactory;

public class HomeActivity extends DashBoardActivity {

    private ProgressDialog progress;
    final private int PICKFILE_RESULT_CODE = 1000;
    protected List<Integer> userAuthActivityIds = new ArrayList<>();
    protected boolean isAdmin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        setHeader(getString(R.string.title_activity_home), false, false);
        setCustomToolBar();
        setAuthorizedActivity();
    }

    protected void setAuthorizedActivity()
    {
        String userAuths = getAuthIds();
        Log.d("info", " userAuths - " + userAuths);
        if(userAuths == null || userAuths.length() == 0 ) return;
        for(String auth : userAuths.split(","))
        {
            //SocietyAuthorization.Type authActivity = SocietyAuthorization.Type.values()[Integer.parseInt(auth)];
            switch (SocietyAuthorization.Type.valueOf(auth))
            {
                case ADMIN: //0
                    isAdmin = true;
                    return;
                case MY_DUES_VIEWS: //1
                    userAuthActivityIds.add(R.id.home_activity_btn_my_dues);
                    break;
                case NOTIFICATION_SEND: //2
//                    userAuthActivityIds.add(R.id.home_activit_btn_);
                    break;
                case USER_DETAIL_VIEW: //3
                    userAuthActivityIds.add(R.id.home_activity_btn_manage_users);
                    break;
                case USER_DETAIL_CREATE: //4
                    userAuthActivityIds.add(R.id.home_activity_btn_add_user);
                    break;
                case FLAT_DETAIL_VIEW: //5
                    userAuthActivityIds.add(R.id.home_activity_btn_view_all_flat);
                    break;
                case FLAT_DETAIL_CREATE: //6
                    userAuthActivityIds.add(R.id.home_activity_btn_add_flat);
                    break;
                case LOGIN_VIEW: //7
                    userAuthActivityIds.add(R.id.home_activity_btn_view_all_login);
                    break;
                case LOGIN_CREATE: //8
                    userAuthActivityIds.add(R.id.home_activity_btn_create_login);
                    break;
                case MONTHLY_STATEMENT_UPLOAD: //9
                    userAuthActivityIds.add(R.id.home_activity_btn_upload_pdf);
                    break;
                case RAW_DATA_VIEW: //10
                    userAuthActivityIds.add(R.id.transaction_report_activity_btn_view_raw_data);
                    break;
                case TRANSACTIONS_DETAIL_VIEW: //11
                    userAuthActivityIds.add(R.id.home_activity_btn_detail_transactions);
                    break;
                case PDF_TRANSACTION_VIEW: //12
                    break;
                case PDF_TRANSACTION_UPLOAD_TO_STAGING_TABLE: //13
                    userAuthActivityIds.add(R.id.transaction_report_activity_btn_save_verified_transactions);
                    break;
                case MAP_USER_WITH_MONTHLY_PDF_NAME: //14
                    userAuthActivityIds.add(R.id.transaction_report_activity_btn_view_map_user_alias);
                    break;
                case MONTHLY_MAINTENANCE_GENERATOR: //15
                    userAuthActivityIds.add(R.id.home_activity_btn_flat_wise_payable);
                    break;
                case VERIFIED_PDF_TRANSACTIONS_UPLOAD: //16
                    userAuthActivityIds.add(R.id.transaction_report_activity_btn_upload_transactions);
                    break;
                case ADD_USER_EXPEND:
                    userAuthActivityIds.add(R.id.home_activity_btn_user_expense);
                    break;
            }
        }
    }

    public void onButtonClicker(View v)
    {
        try {
            Intent intent;
            switch (v.getId()) {

                case R.id.home_activity_btn_create_login:
                    if(isAdmin || userAuthActivityIds.contains(R.id.home_activity_btn_create_login))
                    {
                        progress = ProgressDialog.show(this, null, "Login creation activity ...", true, true);
                        progress.setCancelable(true);
                        progress.show();
                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    List<Login> logins = SocietyHelpDatabaseFactory.getMasterDBInstance().getAllLogins(getLoginId());
                                    Intent intentCreateLogin = new Intent(getApplicationContext(), CreateLoginIdActivity.class);
                                    intentCreateLogin.putExtra(CONST_LOGIN_IDS, Login.getLogIds(logins));
                                    startActivity(intentCreateLogin);
                                }catch (Exception e)
                                {
                                    Log.e("Error", "Fetching My dues verified data has problem", e);
                                }
                                progress.dismiss();
                                progress.cancel();
                            }
                        }).start();
                    } else{
                        Toast.makeText(this, "Permission denied to access this link (create login). Ask your Admin!", Toast.LENGTH_LONG).show();
                    }
                    break;

                case R.id.home_activity_btn_add_flat:
                    if(isAdmin || userAuthActivityIds.contains(R.id.home_activity_btn_add_flat))
                    {
                        intent = new Intent(this, AddFlatDetailsActivity.class);
                        startActivity(intent);
                    } else{
                        Toast.makeText(this, "Permission denied to access this link (add flat). Ask your Admin!", Toast.LENGTH_LONG).show();
                    }
                    break;

                case R.id.home_activity_btn_add_user:
                    if(isAdmin || userAuthActivityIds.contains(R.id.home_activity_btn_add_user)) {
                        progress = ProgressDialog.show(this, null, "Going to add User activity ...", true, true);
                        progress.setCancelable(true);
                        progress.show();
                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    Intent innerIntent = new Intent(getApplicationContext(), AddUserActivity.class);
                                    innerIntent.putExtra(CONST_LOGIN_IDS, getLoginIds());
                                    innerIntent.putExtra(CONST_USER_IDS, getUserIds());
                                    innerIntent.putExtra(CONST_FLAT_IDS, getFlatIds());
                                    startActivity(innerIntent);

                                }catch (Exception e)
                                {
                                    Log.e("Error", "Fetching My dues verified data has problem", e);
                                }
                                progress.dismiss();
                                progress.cancel();
                            }
                        }).start();


                    } else{
                        Toast.makeText(this, "Permission denied to access this link (add user). Ask your Admin!", Toast.LENGTH_LONG).show();
                    }
                    break;

                case R.id.home_activity_btn_view_all_login:
                    if(isAdmin || userAuthActivityIds.contains(R.id.home_activity_btn_view_all_login))
                    {
                    intent = new Intent(this, ManageLoginActivity.class);
                    startActivity(intent);
                    } else{
                        Toast.makeText(this, "Permission denied to access this link (view all login). Ask your Admin!", Toast.LENGTH_LONG).show();
                    }
                    break;

                case R.id.home_activity_btn_view_all_flat:
                    if(isAdmin || userAuthActivityIds.contains(R.id.home_activity_btn_view_all_flat))
                    {
                    intent = new Intent(this, ManageFlatActivity.class);
                    startActivity(intent);
                    } else{
                        Toast.makeText(this, "Permission denied to access this link (view all flat). Ask your Admin!", Toast.LENGTH_LONG).show();
                    }
                    break;

                case R.id.home_activity_btn_manage_users:
                    if(isAdmin || userAuthActivityIds.contains(R.id.home_activity_btn_manage_users))
                    {
                    intent = new Intent(this, ManageUserActivity.class);
                    startActivity(intent);
                    } else{
                        Toast.makeText(this, "Permission denied to access this link (manage users). Ask your Admin!", Toast.LENGTH_LONG).show();
                    }
                    break;


                case R.id.home_activity_btn_user_expense:
                    if(isAdmin || userAuthActivityIds.contains(R.id.home_activity_btn_user_expense))
                    {
                        progress = ProgressDialog.show(this, null, "Waiting for add user expend activity ...", true, true);
                        progress.setCancelable(true);
                        progress.show();
                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    String expenseTypes = getExpenseTypes();
                                    Intent intentMyDues = new Intent(getApplicationContext(), AddCashExpenseActivity.class);
                                    intentMyDues.putExtra(CONST_EXPENSE_TYPES, expenseTypes);
                                    startActivity(intentMyDues);
                                }catch (Exception e)
                                {
                                    Log.e("Error", "Fetching My dues verified data has problem", e);
                                }
                                progress.dismiss();
                                progress.cancel();
                            }
                        }).start();
                    } else{
                        Toast.makeText(this, "Permission denied to access this link (user expend). Ask your Admin!", Toast.LENGTH_LONG).show();
                    }
                    break;

                case R.id.home_activity_btn_validate_user_expense:
                    if(isAdmin || userAuthActivityIds.contains(R.id.home_activity_btn_validate_user_expense))
                    {
                        progress = ProgressDialog.show(this, null, "Getting unverified user's cash payment ...", true, true);
                        progress.setCancelable(true);
                        progress.show();
                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    List<UserPaid> payments = SocietyHelpDatabaseFactory.getDBInstance().getUnVerifiedCashPayment();
                                    Intent intentMyDues = new Intent(getApplicationContext(), VerifiedCashPaymentActivity.class);
                                    byte[] sObj = CustomSerializer.serializeObject(payments);
                                    intentMyDues.putExtra(CONST_UN_VERIFIED_PAYMENT, sObj);

                                    startActivity(intentMyDues);
                                }catch (Exception e)
                                {
                                    Log.e("Error", "Fetching My dues verified data has problem", e);
                                }
                                progress.dismiss();
                                progress.cancel();
                            }
                        }).start();
                    } else{
                        Toast.makeText(this, "Permission denied to access this link (user expend). Ask your Admin!", Toast.LENGTH_LONG).show();
                    }
                    break;

                case R.id.home_activity_btn_upload_pdf:
                    if(isAdmin || userAuthActivityIds.contains(R.id.home_activity_btn_upload_pdf))
                    {
                        openFileBrowser();
                    } else{
                        Toast.makeText(this, "Permission denied to access this link (upload month statement). Ask your Admin!", Toast.LENGTH_LONG).show();
                    }
                    break;

                case  R.id.home_activity_btn_detail_transactions:
                    if(isAdmin || userAuthActivityIds.contains(R.id.home_activity_btn_detail_transactions))
                    {
                        intent = new Intent(this, DetailTransactionViewActivity.class);
                        startActivity(intent);
                    } else{
                        Toast.makeText(this, "Permission denied to access this link (view detail transactions). Ask your Admin!", Toast.LENGTH_LONG).show();
                    }
                    break;

                case  R.id.home_activity_btn_flat_wise_payable:
                    if(isAdmin || userAuthActivityIds.contains(R.id.home_activity_btn_flat_wise_payable))
                    {
                    intent = new Intent(this, ManageFlatWisePayableActivity.class);
                    startActivity(intent);
                    } else{
                        Toast.makeText(this, "Permission denied to access this link (generate monthly maintenance). Ask your Admin!", Toast.LENGTH_LONG).show();
                    }
                    break;

               /* case  R.id.home_activity_btn_my_transactions:
                    intent = new Intent(this, MyTransactionsActivity.class);
                    startActivity(intent);
                    break;
                */
                case  R.id.home_activity_btn_my_dues:
                    if(isAdmin || userAuthActivityIds.contains(R.id.home_activity_btn_my_dues))
                    {
                        progress = ProgressDialog.show(this, null, "Fetching My dues from Database ...", true, true);
                        progress.setCancelable(true);
                        progress.show();
                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    float myDues = SocietyHelpDatabaseFactory.getDBInstance().getMyDue(getFlatId());
                                    Intent intentMyDues = new Intent(getApplicationContext(), MyDuesActivity.class);
                                    intentMyDues.putExtra("MyDueAmount", myDues);
                                    startActivity(intentMyDues);
                                }catch (Exception e)
                                {
                                    Log.e("Error", "Fetching My dues verified data has problem", e);
                                }
                                progress.dismiss();
                                progress.cancel();
                                }
                        }).start();
                    } else{
                        Toast.makeText(this, "Permission denied to access this link (pay dues). Ask your Admin!", Toast.LENGTH_LONG).show();
                    }
                    break;

               /* case  R.id.transaction_report_activity_btn_save_verified_transactions:
                    progress = ProgressDialog.show(this, null, "Uploading verified transactions to Database ...", true, false);
                    progress.show();
                    final BankStatement verifiedBankStat = new BankStatement();
                    verifiedBankStat.allTransactions = SocietyHelpDatabaseFactory.getDBInstance().getAllDetailsTransactions();
                    Thread verifiedTransTaskThread = new Thread(new Runnable() {
                        public void run() {
                            try {
                                SocietyHelpDatabaseFactory.getDBInstance().saveVerifiedTransactions(verifiedBankStat);
                            }catch (Exception e)
                            {
                                Log.e("Error","Insert verified data has problem",e);
                            }

                            progress.dismiss();
                            progress.cancel();
                        }
                    });
                    verifiedTransTaskThread.start();
                    break;
                */
            }
        }catch (Exception e)
        {
            Log.e("Error", "Something goes wrong", e);
        }
    }


    public void openFileBrowser()
    {
        new FileChooser(this).setFileListener(new FileChooser.FileSelectedListener() {
            @Override public void fileSelected(final File file) {
                final String path = file.getPath();

                progress = ProgressDialog.show(HomeActivity.this, null, "Parsing PDF ...", true, false);
                progress.setCancelable(true);
                progress.show();
                Thread taskThread = new Thread(new Runnable() {
                    public void run() {

                        BankStatement bankStat = (BankStatement) SocietyHelpParserFactory.getInstance().getAllTransaction(path);
                        Intent transactionReportIntent = new Intent(getApplicationContext(), HomeTransactionActivity.class);
                        transactionReportIntent.putExtra("bankStat",bankStat);
                        startActivityForResult(transactionReportIntent, 0);
                        progress.dismiss();
                        progress.cancel();
                    }
                });
                taskThread.start();

            }}).showDialog();
    }

    protected String getFlatIds() throws Exception
    {
        StringBuilder listFlatIds = new StringBuilder();
        listFlatIds.append("Select Flat Id").append(",");
        List<Flat> flats = SocietyHelpDatabaseFactory.getDBInstance().getAllFlats();
        for(Flat f : flats)
        {
            listFlatIds.append(f.flatId).append(",");
        }
        return  listFlatIds.toString();
    }

    protected String getLoginIds() throws Exception
    {
        StringBuilder listIds = new StringBuilder();
        listIds.append("Select Login Id").append(",");
        List<Login> login = SocietyHelpDatabaseFactory.getMasterDBInstance().getAllLogins(getLoginId());
        List<Login> assignedLogin = SocietyHelpDatabaseFactory.getDBInstance().getAllAssignedLogins();

        boolean found;
        for(Login l : login)
        {
            found = false;
            for(Login assignedL : assignedLogin){
                if(assignedL.loginId.contentEquals(l.loginId))
                {
                    found = true;
                    break;
                }
            }
            if(!found) listIds.append(l.loginId).append(",");
        }
        return  listIds.toString();
    }

    protected String getUserIds() throws Exception
    {
        StringBuilder listIds = new StringBuilder();
        listIds.append("Select Login Id").append(",");
        List<UserDetails> users = SocietyHelpDatabaseFactory.getDBInstance().getAllUsers();

        for(UserDetails u : users)
        {
            listIds.append(u.userId).append(",");
        }
        return  listIds.toString();
    }

    protected String getExpenseTypes() throws Exception
    {
        StringBuilder listIds = new StringBuilder();
        listIds.append("Select Expense Type").append(",");
        List<ExpenseType> eList = SocietyHelpDatabaseFactory.getDBInstance().getExpenseType();

        for(ExpenseType e : eList)
        {
            listIds.append(e.type).append(",");
        }
        return  listIds.toString();
    }

    protected void setCustomToolBar()
    {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        //displaying custom ActionBar
        View mActionBarView = getLayoutInflater().inflate(R.layout.login_tool_bar, null);
        actionBar.setCustomView(mActionBarView);
        actionBar.setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
    }

}
