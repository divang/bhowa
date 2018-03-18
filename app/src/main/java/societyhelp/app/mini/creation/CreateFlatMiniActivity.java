package societyhelp.app.mini.creation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import societyhelp.app.R;
import societyhelp.app.mini.dashboard.DashBoardMiniActivity;
import societyhelp.app.mini.view.ViewAllFlatMiniActivity;
import societyhelp.app.util.CustomSerializer;
import societyhelp.app.util.Util;
import societyhelp.dao.SocietyHelpDatabaseFactory;
import societyhelp.dao.mysql.impl.Flat;

public class CreateFlatMiniActivity extends DashBoardMiniActivity {

    ProgressDialog  progress;
    TextView flatIdText;
    TextView fNoText;
    TextView fAreaText;
    TextView fMaintenanceAmountText;
    TextView fBlockText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flat_details);

        setHeader("", true, false);

        flatIdText = (TextView) findViewById(R.id.flatId_AFD_Text);
        fNoText = (TextView) findViewById(R.id.flat_Number_AFD_Text);
        fAreaText = (TextView) findViewById(R.id.flat_Area_AFD_Text);
        fMaintenanceAmountText = (TextView) findViewById(R.id.flat_Maintenance_Amount_AFD_Text);
        fBlockText = (TextView) findViewById(R.id.flat_Block_AFD_Text);

        final Button submitButton = (Button) findViewById(R.id.add_flat_details_AFD_Btn);

        progress = ProgressDialog.show(CreateFlatMiniActivity.this, null, "Adding Flat details in Database  ...", true, false);
        progress.setCancelable(true);
        progress.hide();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(validationFailed()) return;
                progress.show();
                final Flat flat = new Flat();
                flat.flatId = flatIdText.getText().toString();
                flat.flatNumber = fNoText.getText().toString();
                flat.area = Integer.valueOf(fAreaText.getText().toString());
                flat.maintenanceAmount = Float.valueOf(fMaintenanceAmountText.getText().toString());
                flat.block = fBlockText.getText().toString();
                flat.societyId = Integer.parseInt(getSocietyId());
                Thread taskThread = new Thread(new Runnable() {
                    public void run() {
                        try {
                            SocietyHelpDatabaseFactory.getDBInstance().addFlatDetails(flat);
                         
	                        List<Flat> flats = SocietyHelpDatabaseFactory.getDBInstance().getAllFlats(getSocietyId());
	                        Intent innerIntent = new Intent(getApplicationContext(), ViewAllFlatMiniActivity.class);
	                        byte[] sObj = CustomSerializer.serializeObject(flats);
	                        innerIntent.putExtra(CONST_ALL_FLATS, sObj);
	                        startActivity(innerIntent);
	
                        } catch (Exception e) {
                            Log.e("Error", "Login creation failed", e);
                        }
                        progress.dismiss();
                        progress.cancel();
                    }
                });
                taskThread.start();
            }
        });
    }

    private boolean validationFailed() {
        boolean validationFailed = false;

        if (flatIdText.getText().toString().length() == 0) {
            Util.CustomToast(getApplicationContext(), "Flat Id should not be empty", 1000);
            validationFailed = true;
            return validationFailed;
        }

        if (fNoText.getText().toString().length() == 0) {
            Util.CustomToast(getApplicationContext(), "Flat number should not be empty", 1000);
            validationFailed = true;
            return validationFailed;
        }

        if (fAreaText.getText().toString().length() == 0) {
            Util.CustomToast(getApplicationContext(), "Flat Area should not be empty", 1000);
            validationFailed = true;
            return validationFailed;
        }

        if (fMaintenanceAmountText.getText().toString().length() == 0) {
            Util.CustomToast(getApplicationContext(), "Maintenance Amount should not be empty", 1000);
            validationFailed = true;
            return validationFailed;
        }

        if (fBlockText.getText().toString().length() == 0) {
            Util.CustomToast(getApplicationContext(), "Block should not be empty", 1000);
            validationFailed = true;
            return validationFailed;
        }
        return validationFailed;
    }
}
