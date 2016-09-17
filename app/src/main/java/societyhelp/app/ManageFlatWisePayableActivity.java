package societyhelp.app;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import societyhelp.dao.SocietyHelpDatabaseFactory;
import societyhelp.dao.mysql.impl.FlatWisePayable;

public class ManageFlatWisePayableActivity extends DashBoardActivity {

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_flat_wise_payable);

        final TextView monthText = (TextView) findViewById(R.id.MonthText_FWP);
        final TextView yearText = (TextView) findViewById(R.id.YearText_FWP);
        Button generateMaintenanceBtn = (Button) findViewById(R.id.GenerateMaintenanceButton_FWP);

        progress = ProgressDialog.show(ManageFlatWisePayableActivity.this, null, "Generating Monthly Maintenance ...", true, false);
        progress.setCancelable(true);
        progress.hide();

        generateMaintenanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                progress.show();

                Thread taskThread = new Thread(new Runnable() {
                    public void run() {

                        FlatWisePayable fwp = new FlatWisePayable();
                        fwp.expenseType = FlatWisePayable.ExpenseType.Monthly_Maintenance;
                        fwp.month = Integer.parseInt(monthText.getText().toString());
                        fwp.year = Integer.parseInt(yearText.getText().toString());
                        try {
                            SocietyHelpDatabaseFactory.getDBInstance().addMonthlyMaintenance(fwp);
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

    }

}
