package societyhelp.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import societyhelp.app.util.CustomSerializer;
import societyhelp.app.util.ListViewAdaptor;
import societyhelp.app.util.Util;
import societyhelp.dao.SocietyHelpDatabaseFactory;
import societyhelp.dao.mysql.impl.ExpenseType;
import societyhelp.dao.mysql.impl.Flat;
import societyhelp.dao.mysql.impl.FlatWisePayable;
import societyhelp.dao.DatabaseConstant;

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
        List<Flat> flats = SocietyHelpDatabaseFactory.getDBInstance().getAllFlats();
        for(Flat f : flats) {
            listFlatIds.add(f.flatId);
        }
        return  listFlatIds;
    }

}