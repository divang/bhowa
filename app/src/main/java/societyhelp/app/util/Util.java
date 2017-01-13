package societyhelp.app.util;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.os.Handler;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import  jxl.*;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import societyhelp.dao.mysql.impl.ExpenseType;
import societyhelp.dao.mysql.impl.Flat;
import societyhelp.dao.mysql.impl.FlatWisePayable;
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

    public static Map<String, List<FlatWisePayable>> getFlatAndItsPayables(List<FlatWisePayable> payables)
    {
        HashMap<String, List<FlatWisePayable>> data = new HashMap<>();
        for(FlatWisePayable fp : payables){
            if(fp.expenseType.equals(ExpenseType.ExpenseTypeConst.Penalty)) continue;
            if(data.containsKey(fp.flatId)) {
                data.get(fp.flatId).add(fp);
            }
            else{
                List<FlatWisePayable> transactions = new ArrayList<>();
                transactions.add(fp);
                data.put(fp.flatId, transactions);
            }
        }

        return data;
    }


    public static Map<String, List<FlatWisePayable>> getFlatAndItsPenalty(List<FlatWisePayable> payables)
    {
        HashMap<String, List<FlatWisePayable>> data = new HashMap<>();
        for(FlatWisePayable fp : payables){
            if(fp.expenseType.equals(ExpenseType.ExpenseTypeConst.Penalty)) {
                if (data.containsKey(fp.flatId)) {
                    data.get(fp.flatId).add(fp);
                } else {
                    List<FlatWisePayable> transactions = new ArrayList<>();
                    transactions.add(fp);
                    data.put(fp.flatId, transactions);
                }
            }
        }

        return data;
    }

    public static Map<String, List<TransactionOnBalanceSheet>> getFlatAndItsPayment(List<TransactionOnBalanceSheet> paid)
    {
        HashMap<String, List<TransactionOnBalanceSheet>> data = new HashMap<>();
        for(TransactionOnBalanceSheet p : paid){
            if(data.containsKey(p.flatId)) {
                data.get(p.flatId).add(p);
            }
            else{
                List<TransactionOnBalanceSheet> transactions = new ArrayList<>();
                transactions.add(p);
                data.put(p.flatId, transactions);
            }
        }
        return data;
    }

    public static Map<String, Flat> getFlatDetails(List<TransactionOnBalanceSheet> data)
    {
        Map<String, Flat> flats = new HashMap<>();
        for(TransactionOnBalanceSheet t : data){
            if(flats.containsKey(t.flatId)) continue;
            Flat f = new Flat();
            f.flatId = t.flatId;
            f.block = t.block;
            f.area = t.area;
            f.owner = t.userName;
            flats.put(t.flatId, f);
        }
        return flats;
    }

    public static void generateBalanceSheet(List<TransactionOnBalanceSheet> data, List<FlatWisePayable> payables) {
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
            Map<String, Flat> flats = getFlatDetails(data);
            workbook = Workbook.createWorkbook(file, wbSettings);
            //Excel sheet name. 0 represents first sheet
            WritableSheet sheet = workbook.createSheet("Individual wise Details", 0);

            try {
                sheet.addCell(new Label(0, 0, "Amount Receivable")); // column and row

                sheet.addCell(new Label(0, 1, "Sr no.")); // column and row
                sheet.addCell(new Label(1, 1, "Flat no"));
                sheet.addCell(new Label(2, 1, "Block"));
                sheet.addCell(new Label(3, 1, "Owner Name"));
                sheet.addCell(new Label(4, 1, "Sq Ft"));
                sheet.addCell(new Label(5, 1, "Receivable"));
                sheet.addCell(new Label(6, 1, "Received"));
                sheet.addCell(new Label(7, 1, "Balance"));

                Map<String, List<FlatWisePayable>> receivable = getFlatAndItsPayables(payables);

                int col=8;
                for(String flatNo : receivable.keySet()) {
                    for (FlatWisePayable curT : receivable.get(flatNo)) {
                        if (curT.month > 0)
                            sheet.addCell(new Label(col++, 1, curT.month + "-" + curT.year));
                        else
                            sheet.addCell(new Label(col++, 1, curT.expenseType.name()));
                    }
                }

                int i=1;

                boolean setFlatDetails;
                Flat curFlat;
                for(String flatNo : receivable.keySet()) {
                    i++;
                    col=8;
                    setFlatDetails = false;
                    for(FlatWisePayable curT : receivable.get(flatNo)) {
                        if(!setFlatDetails) {
                            curFlat = flats.get(curT.flatId);
                            sheet.addCell(new Label(0, i, i -1 + "" ));
                            sheet.addCell(new Label(1, i, curT.flatId));
                            sheet.addCell(new Label(2, i, curFlat.block));
                            sheet.addCell(new Label(3, i, curFlat.owner));
                            sheet.addCell(new Label(4, i, curFlat.area +""));
                            setFlatDetails = true;
                        }
                        //5,6,7 column skip
                        sheet.addCell(new Label(col++, i, curT.amount + ""));
                    }
                }

                sheet.addCell(new Label(0, i++, "Amount Received")); // column and row

                Map<String, List<TransactionOnBalanceSheet>> paid = getFlatAndItsPayment(data);

                for(String flatNo : paid.keySet()) {
                    i++;
                    col=8;
                    setFlatDetails = false;
                    for(TransactionOnBalanceSheet curBT : paid.get(flatNo)) {
                        if(!setFlatDetails) {
                            curFlat = flats.get(curBT.flatId);
                            sheet.addCell(new Label(0, i, i -1 + "" ));
                            sheet.addCell(new Label(1, i, curBT.flatId));
                            sheet.addCell(new Label(2, i, curFlat.block));
                            sheet.addCell(new Label(3, i, curFlat.owner));
                            sheet.addCell(new Label(4, i, curFlat.area +""));
                            setFlatDetails = true;
                        }
                        //5,6,7 column skip
                        sheet.addCell(new Label(col++, i, curBT.amount + ""));
                    }
                }

                //
                sheet.addCell(new Label(0, i++, "Penalty Receivabled")); // column and row

                Map<String, List<FlatWisePayable>> penalty = getFlatAndItsPenalty(payables);

                for(String flatNo : penalty.keySet()) {
                    i++;
                    col=8;
                    setFlatDetails = false;
                    for(FlatWisePayable curPen : penalty.get(flatNo)) {
                        if(!setFlatDetails) {
                            curFlat = flats.get(curPen.flatId);
                            sheet.addCell(new Label(0, i, i -1 + "" ));
                            sheet.addCell(new Label(1, i, curPen.flatId));
                            sheet.addCell(new Label(2, i, curFlat.block));
                            sheet.addCell(new Label(3, i, curFlat.owner));
                            sheet.addCell(new Label(4, i, curFlat.area +""));
                            setFlatDetails = true;
                        }
                        //5,6,7 column skip
                        sheet.addCell(new Label(col++, i, curPen.amount + ""));
                    }
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
