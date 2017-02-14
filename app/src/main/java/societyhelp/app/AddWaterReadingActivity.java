package societyhelp.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import societyhelp.app.util.CustomSerializer;
import societyhelp.app.util.ListViewAdaptor;
import societyhelp.app.util.Util;
import societyhelp.core.SocietyAuthorization;
import societyhelp.dao.SocietyHelpDatabaseFactory;
import societyhelp.dao.mysql.impl.UserDetails;
import societyhelp.dao.mysql.impl.WaterSuppyReading;

public class AddWaterReadingActivity extends DashBoardActivity {

    protected EditText startReading;
    protected EditText endReading;
    protected Spinner supplierNames;
    protected List<WaterSuppyReading> suppyReadings;
    protected Map<String,WaterSuppyReading> supplierNameWaterMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_water_reading);
        setHeader(getString(R.string.title_activity_add_water_reading), true, false);

        startReading = (EditText) findViewById(R.id.startReadingText);
        endReading = (EditText) findViewById(R.id.endReadingText);
        supplierNames = (Spinner) findViewById(R.id.waterSupplier_Spinner);

        try {
            suppyReadings = getSuppliers();
            supplierNames.setAdapter(new ListViewAdaptor(getApplicationContext(), getSupplierNames(suppyReadings)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        final Button submitButton = (Button) findViewById(R.id.addReadingButton);

        final ProgressDialog progress = ProgressDialog.show(AddWaterReadingActivity.this, null, "Adding water reading ...", true, false);
        progress.setCancelable(true);
        progress.hide();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                //validate values
                if(validationFailed()) return;

                progress.show();

                final WaterSuppyReading wsr = new WaterSuppyReading();

                wsr.readingBeforeSupply = Integer.parseInt(startReading.getText().toString().trim());
                wsr.readingAfterSupply = Integer.parseInt(endReading.getText().toString().trim());
                wsr.supplierName = supplierNames.getSelectedItem().toString();
                wsr.capacityInLiter = supplierNameWaterMap.get(wsr.supplierName).capacityInLiter;
                wsr.supplierId = supplierNameWaterMap.get(wsr.supplierName).supplierId;
                wsr.loginId = getLoginId();
                try {
                    Thread taskThread = new Thread(new Runnable() {
                        public void run() {
                            try {
                                SocietyHelpDatabaseFactory.getDBInstance().insertWaterReading(wsr);
                                List<WaterSuppyReading> readings = SocietyHelpDatabaseFactory.getDBInstance().getAllWaterReading();
                                Intent innerIntent = new Intent(getApplicationContext(), ViewWaterReadingActivity.class);
                                byte[] sObj = CustomSerializer.serializeObject(readings);
                                innerIntent.putExtra(CONST_WATER_READINGS, sObj);
                                startActivity(innerIntent);
                            } catch (Exception e) {
                                Log.e("Error", "Water Reading has some problem", e);
                            }
                            progress.dismiss();
                            progress.cancel();
                        }
                    });
                    taskThread.start();

                } catch (Exception e) {
                    Log.e("Error","Water Reading data has some problem", e);
                }
            }
        });
    }


    protected List<WaterSuppyReading> getSuppliers() throws Exception
    {
        supplierNameWaterMap.clear();
        byte[] obj = (byte[])getIntent().getSerializableExtra(CONST_WATER_SUPPLIERS);
        List<WaterSuppyReading> suppliers = (List<WaterSuppyReading>) CustomSerializer.deserializeObject(obj);
        return  suppliers;
    }

    protected List<String> getSupplierNames(List<WaterSuppyReading> suppliers) throws Exception
    {
        List<String> listSupplier = new ArrayList<>();
        listSupplier.add("Choose Water Supplier");
        for(WaterSuppyReading sp : suppliers)
        {
            listSupplier.add(sp.supplierName);
            supplierNameWaterMap.put(sp.supplierName, sp);
        }
        return  listSupplier;
    }

    private boolean validationFailed() {
        boolean validationFailed = false;

        if (startReading.getText().toString().trim().length() == 0) {
            Util.CustomToast(getApplicationContext(), "Water start reading should not be empty", 3000);
            validationFailed = true;
            return validationFailed;
        } else if (endReading.getText().toString().trim().length() == 0){
            Util.CustomToast(getApplicationContext(), "Water end reading should not be empty", 3000);
            validationFailed = true;
            return validationFailed;
        } else if (supplierNames.getSelectedItemId() == 0) {
            Util.CustomToast(getApplicationContext(), "Water Supplier must select.", 1000);
            validationFailed = true;
        }
        if(Integer.parseInt(startReading.getText().toString().trim()) >=
                Integer.parseInt(endReading.getText().toString().trim())) {
            Util.CustomToast(getApplicationContext(), "Water start reading should be greater then end reading should not be empty", 3000);
            validationFailed = true;
            return validationFailed;
        }

        return validationFailed;
    }

}
