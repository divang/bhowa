package societyhelp.app.mini.dashboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import societyhelp.app.R;
import societyhelp.app.mini.creation.CreateFlatMiniActivity;
import societyhelp.app.mini.creation.CreateLoginMiniActivity;
import societyhelp.app.mini.creation.CreateUserMiniActivity;
import societyhelp.app.mini.report.MyDuesMiniActivity;
import societyhelp.app.reports.AllExpenseReportActivity;
import societyhelp.app.util.CustomSerializer;
import societyhelp.app.util.Util;
import societyhelp.core.SocietyAuthorization;
import societyhelp.dao.SocietyHelpDatabaseFactory;
import societyhelp.dao.mysql.impl.ExpenseType;
import societyhelp.dao.mysql.impl.Flat;
import societyhelp.dao.mysql.impl.Login;
import societyhelp.dao.mysql.impl.TransactionOnBalanceSheet;
import societyhelp.dao.mysql.impl.UserDetails;

public class DashBoardUserMiniActivity extends DashBoardMiniActivity {

    private ProgressDialog progress;
    final private int PICKFILE_RESULT_CODE = 1000;
    protected List<Integer> userAuthActivityIds = new ArrayList<>();
    protected boolean isAdmin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_user_dash_board);
        setHeader(getString(R.string.title_activity_home), false, false);
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
                case ADD_USER_EXPEND: //17
                    userAuthActivityIds.add(R.id.home_activity_btn_user_expense);
                    break;
                case VIEW_REPORT: //18
                    userAuthActivityIds.add(R.id.home_activity_btn_view_report);
                    break;
                case VIEW_WATER_READING: //21
                    userAuthActivityIds.add(R.id.home_activity_btn_view_water_reading);
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
                    if (isAdmin || userAuthActivityIds.contains(R.id.home_activity_btn_create_login)) {
                        progress = ProgressDialog.show(this, null, "Login creation activity ...", true, true);
                        progress.setCancelable(true);
                        progress.show();
                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    List<Login> logins = SocietyHelpDatabaseFactory.getMasterDBInstance().getAllLogins(getLoginId());
                                    Intent intentCreateLogin = new Intent(getApplicationContext(), CreateLoginMiniActivity.class);
                                    intentCreateLogin.putExtra(CONST_LOGIN_IDS, Login.getLogIds(logins));
                                    startActivity(intentCreateLogin);
                                } catch (Exception e) {
                                    Log.e("Error", "Fetching My dues verified data has problem", e);
                                }
                                progress.dismiss();
                                progress.cancel();
                            }
                        }).start();
                    } else {
                        Toast.makeText(this, "Permission denied to access this link (create login). Ask your Admin!", Toast.LENGTH_LONG).show();
                    }
                    break;

                case R.id.home_activity_btn_add_flat:
                    if (isAdmin || userAuthActivityIds.contains(R.id.home_activity_btn_add_flat)) {
                        intent = new Intent(this, CreateFlatMiniActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "Permission denied to access this link (add flat). Ask your Admin!", Toast.LENGTH_LONG).show();
                    }
                    break;

                case R.id.home_activity_btn_add_user:
                    if (isAdmin || userAuthActivityIds.contains(R.id.home_activity_btn_add_user)) {
                        progress = ProgressDialog.show(this, null, "Going to add User activity ...", true, true);
                        progress.setCancelable(true);
                        progress.show();
                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    Intent innerIntent = new Intent(getApplicationContext(), CreateUserMiniActivity.class);
                                    innerIntent.putExtra(CONST_LOGIN_IDS, getLoginIds());
                                    innerIntent.putExtra(CONST_USER_IDS, getUserIds());
                                    innerIntent.putExtra(CONST_FLAT_IDS, getFlatIds());
                                    startActivity(innerIntent);

                                } catch (Exception e) {
                                    Log.e("Error", "Fetching My dues verified data has problem", e);
                                }
                                progress.dismiss();
                                progress.cancel();
                            }
                        }).start();


                    } else {
                        Toast.makeText(this, "Permission denied to access this link (add user). Ask your Admin!", Toast.LENGTH_LONG).show();
                    }
                    break;

                case R.id.home_activity_btn_my_dues:
                    if (isAdmin || userAuthActivityIds.contains(R.id.home_activity_btn_my_dues)) {
                        progress = ProgressDialog.show(this, null, "Fetching My dues from Database ...", true, true);
                        progress.setCancelable(true);
                        progress.show();
                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    float myDues = SocietyHelpDatabaseFactory.getDBInstance().getMyDue(getFlatId());
                                    Intent intentMyDues = new Intent(getApplicationContext(), MyDuesMiniActivity.class);
                                    intentMyDues.putExtra("MyDueAmount", myDues);
                                    startActivity(intentMyDues);
                                } catch (Exception e) {
                                    Log.e("Error", "Fetching My dues verified data has problem", e);
                                }
                                progress.dismiss();
                                progress.cancel();
                            }
                        }).start();
                    } else {
                        Toast.makeText(this, "Permission denied to access this link (pay dues). Ask your Admin!", Toast.LENGTH_LONG).show();
                    }
                    break;

                case R.id.home_activity_btn_view_report:
                    if (isAdmin || userAuthActivityIds.contains(R.id.home_activity_btn_view_report)) {
                        progress = ProgressDialog.show(this, null, "Downloading Apartment Expense from Database ...", true, false);
                        progress.show();
                        Thread expenseThread = new Thread(new Runnable() {
                            public void run() {
                                try {
                                    List<TransactionOnBalanceSheet> balanceSheetData = SocietyHelpDatabaseFactory.getDBInstance().getBalanceSheetData();
                                    TreeMap<ExpenseType.ExpenseTypeConst, List<TransactionOnBalanceSheet>> apartmentExpense = Util.getApartmentExpense(balanceSheetData, null);
                                    //Intent innerIntent = new Intent(getApplicationContext(), ExpenseReportInPieChartActivity.class);
                                    Intent innerIntent = new Intent(getApplicationContext(), AllExpenseReportActivity.class);
                                    innerIntent.putExtra(CONST_APARTMENT_EXPENSE_DATA, CustomSerializer.serializeObject(apartmentExpense));
                                    startActivity(innerIntent);

                                } catch (Exception e) {
                                    Log.e("Error", "Insert verified data has problem", e);
                                }

                                progress.dismiss();
                                progress.cancel();
                            }
                        });
                        expenseThread.start();
                    }
                    else {
                        Toast.makeText(this, "Permission denied to access this link (view report). Ask your Admin!", Toast.LENGTH_LONG).show();
                    }
                    break;


            }
        }catch (Exception e)
        {
            Log.e("Error", "Something goes wrong", e);
        }
    }

  protected String getFlatIds() throws Exception
    {
        StringBuilder listFlatIds = new StringBuilder();
        listFlatIds.append("Select Flat Id").append(",");
        List<Flat> flats = SocietyHelpDatabaseFactory.getDBInstance().getAllFlats(getSocietyId());
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
        List<UserDetails> users = SocietyHelpDatabaseFactory.getDBInstance().getAllUsers(getSocietyId());

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

    
}
