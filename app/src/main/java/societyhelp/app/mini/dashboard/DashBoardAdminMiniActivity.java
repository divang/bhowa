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
import societyhelp.app.mini.creation.SetupSocietyMiniActivity;
import societyhelp.app.mini.view.ViewAllFlatMiniActivity;
import societyhelp.app.reports.AllExpenseReportActivity;
import societyhelp.app.transaction.TransactionHomeActivity;
import societyhelp.app.util.CustomSerializer;
import societyhelp.app.util.Util;
import societyhelp.core.SocietyAuthorization;
import societyhelp.dao.SocietyHelpDatabaseFactory;
import societyhelp.dao.mysql.impl.ExpenseType;
import societyhelp.dao.mysql.impl.Flat;
import societyhelp.dao.mysql.impl.TransactionOnBalanceSheet;
import societyhelp.dao.mysql.impl.UserPaid;

public class DashBoardAdminMiniActivity extends DashBoardMiniActivity {

    private ProgressDialog progress;
    final private int PICKFILE_RESULT_CODE = 1000;
    protected List<Integer> userAuthActivityIds = new ArrayList<>();
    protected boolean isAdmin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_admin_dash_board);
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
                case TRANSACTION_HOME_VIEW: //9
                    userAuthActivityIds.add(R.id.home_activity_btn_transaction_view);
                    break;
                case MONTHLY_MAINTENANCE_GENERATOR: //15
                    userAuthActivityIds.add(R.id.home_activity_btn_flat_wise_payable);
                    break;
                case ADD_USER_EXPEND: //17
                    userAuthActivityIds.add(R.id.home_activity_btn_user_expense);
                    break;
                case VIEW_REPORT: //18
                    userAuthActivityIds.add(R.id.home_activity_btn_view_report);
                    break;
            }
        }
    }

    public void executeAdminRequest(View v)
    {
        try {
            Intent intent;
            switch (v.getId()) {

                case R.id.home_activity_btn_view_all_flat:
                    if (isAdmin || userAuthActivityIds.contains(R.id.home_activity_btn_view_all_flat)) {
                        progress = ProgressDialog.show(this, null, "Fetching all Flats' details ...", true, true);
                        progress.setCancelable(true);
                        progress.show();
                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    List<Flat> flats = SocietyHelpDatabaseFactory.getDBInstance().getAllFlats(getSocietyId());
                                    Intent innerIntent = new Intent(getApplicationContext(), ViewAllFlatMiniActivity.class);
                                    byte[] sObj = CustomSerializer.serializeObject(flats);
                                    innerIntent.putExtra(CONST_ALL_FLATS, sObj);
                                    startActivity(innerIntent);

                                } catch (Exception e) {
                                    Log.e("Error", "Fetching all flat data has problem", e);
                                }
                                progress.dismiss();
                                progress.cancel();
                            }
                        }).start();
                    } else {
                        Toast.makeText(this, "Permission denied to access this link (view all flat). Ask your Admin!", Toast.LENGTH_LONG).show();
                    }
                    break;


                case R.id.home_activity_btn_transaction_view:
                    /*if(isAdmin || userAuthActivityIds.contains(R.id.home_activity_btn_upload_pdf))
                    {
                        openFileBrowser();
                    } else{
                        Toast.makeText(this, "Permission denied to access this link (upload month statement). Ask your Admin!", Toast.LENGTH_LONG).show();
                    }
                    break;
                    */
                    if (isAdmin || userAuthActivityIds.contains(R.id.home_activity_btn_transaction_view)) {
                        intent = new Intent(this, TransactionHomeActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "Permission denied to access this link (generate monthly maintenance). Ask your Admin!", Toast.LENGTH_LONG).show();
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

                case R.id.home_activity_btn_setup_society:
                    if (isAdmin || userAuthActivityIds.contains(R.id.home_activity_btn_setup_society)) {
                        intent = new Intent(this, SetupSocietyMiniActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "Permission denied to access this link (set up). Ask your Admin!", Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }catch (Exception e)
        {
            Log.e("Error", "Something goes wrong", e);
        }
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
