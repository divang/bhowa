package societyhelp.app.util;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.os.Handler;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import  jxl.*;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import societyhelp.dao.mysql.impl.TransactionOnBalanceSheet;

/**
 * Created by divang.sharma on 9/18/2016.
 */
public class Util {

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd:MM:yyyy");

    public static void CustomToast(Context context, String msg, int showTimeInMilis)
    {
        final Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, showTimeInMilis);
    }

    public static void generateBalanceSheet(List<TransactionOnBalanceSheet> data) {
        final String fileName = "balanceSheet.xls";

        //Saving file in external storage
        File sdCard = Environment.getExternalStorageDirectory();
        File directory = new File(sdCard.getAbsolutePath() + "/societyhelp");

        //create directory if not exist
        if(!directory.isDirectory()){
            directory.mkdirs();
        }

        //file path
        File file = new File(directory, fileName);

        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));
        WritableWorkbook workbook;

        try {
            workbook = Workbook.createWorkbook(file, wbSettings);
            //Excel sheet name. 0 represents first sheet
            WritableSheet sheet = workbook.createSheet("Apartment Balance Sheet", 0);

            try {
                sheet.addCell(new Label(0, 0, "Subject")); // column and row
                sheet.addCell(new Label(1, 0, "Description"));
                int i=0;
                for(TransactionOnBalanceSheet t : data) {
                    i++;
                    sheet.addCell(new Label(0, i, t.userId));
                    sheet.addCell(new Label(1, i, ""+t.amount));
                }
            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }
            workbook.write();
            try {
                workbook.close();
            } catch (WriteException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
