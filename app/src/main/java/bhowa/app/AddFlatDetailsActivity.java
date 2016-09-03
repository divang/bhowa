package bhowa.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import bhowa.dao.BhowaDatabaseFactory;
import bhowa.dao.mysql.impl.Flat;

public class AddFlatDetailsActivity extends DashBoardActivity {

    ProgressDialog  progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flat_details);

        setHeader("", true);

        final TextView flatIdText = (TextView) findViewById(R.id.flatId_AFD_Text);
        final TextView fNoText = (TextView) findViewById(R.id.flat_Number_AFD_Text);
        final TextView fAreaText = (TextView) findViewById(R.id.flat_Area_AFD_Text);
        final TextView fMaintenanceAmountText = (TextView) findViewById(R.id.flat_Maintenance_Amount_AFD_Text);
        final TextView fBlockText = (TextView) findViewById(R.id.flat_Block_AFD_Text);

        final Button submitButton = (Button) findViewById(R.id.add_flat_details_AFD_Btn);

        progress = ProgressDialog.show(AddFlatDetailsActivity.this, null, "Adding Flat details in Database  ...", true, false);
        progress.hide();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                progress.show();
                final Flat flat = new Flat();
                flat.flatId = flatIdText.getText().toString();
                flat.flatNumber = fNoText.getText().toString();
                flat.area = Integer.valueOf(fAreaText.getText().toString());
                flat.maintenanceAmount = Float.valueOf(fMaintenanceAmountText.getText().toString());
                flat.block = fBlockText.getText().toString();

                Thread taskThread = new Thread(new Runnable() {
                    public void run() {
                        try {
                            BhowaDatabaseFactory.getDBInstance().addFlatDetails(flat);
                            Intent intent = new Intent(getApplicationContext(), ManageFlatActivity.class);
                            startActivity(intent);
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

}
