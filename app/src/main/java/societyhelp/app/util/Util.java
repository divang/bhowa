package societyhelp.app.util;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import societyhelp.dao.mysql.impl.ExpenseType;
import societyhelp.dao.mysql.impl.Flat;
import societyhelp.dao.mysql.impl.FlatWisePayable;
import societyhelp.dao.mysql.impl.TransactionOnBalanceSheet;

/**
 * Created by divang.sharma on 9/18/2016.
 */
public class Util {

    protected static Calendar calendar = Calendar.getInstance();

    public static class FlatSummary {

        public String flatId;
        public int receivable;
        public int received;
        public int Balance;
        public int penality;
    }

    static class TransactionOnBalanceSheetComparator implements Comparator<TransactionOnBalanceSheet>
    {
        @Override
        public int compare(TransactionOnBalanceSheet o1, TransactionOnBalanceSheet o2) {
            return o1.transactionDate.compareTo(o2.transactionDate);
        }
    }

    static class FlatWisePayableComparator implements Comparator<FlatWisePayable>
    {
        @Override
        public int compare(FlatWisePayable o1, FlatWisePayable o2) {
            if(o1.year != o2.year) return o1.year - o2.year;
            if(o1.month != o2.month) return o1.month - o2.month;
            return 0;
        }
    }

    static class BalanceSheetDateRage {
        public Date startDate;
        public int startMonth;
        public int startYear;
        public Date endDate;
        public int endMonth;
        public int endYear;
    }


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

    public static TreeMap<String, List<FlatWisePayable>> getFlatAndItsPayables(List<FlatWisePayable> payables, Map<String, FlatSummary> flatSummary, BalanceSheetDateRage dateRage)
    {
        TreeMap<String, List<FlatWisePayable>> data = new TreeMap<>();
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

        int tSum = 0;
        for(String flatId : data.keySet())
        {
            tSum = 0;
            if(!flatSummary.containsKey(flatId))
            {
                FlatSummary flatSum = new FlatSummary();
                flatSum.flatId = flatId;
                flatSummary.put(flatId, flatSum);
            }
            for(FlatWisePayable p : data.get(flatId)){
                tSum += p.amount;

            }
            flatSummary.get(flatId).receivable = tSum;

            data.put(flatId, addMissingDate(data.get(flatId), dateRage));
        }


        for(String flatKey : data.keySet()){
            Collections.sort(data.get(flatKey), new FlatWisePayableComparator());
        }

        return data;
    }

    public static List<FlatWisePayable> addMissingDate(List<FlatWisePayable> initialPayables, BalanceSheetDateRage dateRage)
    {
        List<FlatWisePayable> allMissingDates = new ArrayList<>();
        boolean monthYearFound;
        FlatWisePayable curPayable;
        FlatWisePayable copyOfFirstPayble = null;
        for(int curMonth = dateRage.startMonth, curYear = dateRage.startYear ;(curYear < dateRage.endYear)
                || (curYear == dateRage.endYear && curMonth <= dateRage.endMonth); curMonth++)
        {
            monthYearFound = false;
            for(FlatWisePayable f : initialPayables)
            {
                if(copyOfFirstPayble == null) copyOfFirstPayble = f;
                   if(f.month == curMonth && f.year == curYear) {
                       monthYearFound = true;
                       break;
                   }
            }

            if(!monthYearFound){
                curPayable = new FlatWisePayable();
                curPayable.year = curYear;
                curPayable.month = curMonth;
                curPayable.amount = 0;
                curPayable.flatId = copyOfFirstPayble.flatId;

                allMissingDates.add(curPayable);
            }

            if(curMonth == 12) {
                curMonth = 1;
                curYear++;
            }
        }
        allMissingDates.addAll(initialPayables);
        return allMissingDates;
    }

    public static List<TransactionOnBalanceSheet> addMissingDateInPaid(List<TransactionOnBalanceSheet> initialPaid, BalanceSheetDateRage dateRage)
    {
        List<TransactionOnBalanceSheet> allMissingDates = new ArrayList<>();
        boolean monthYearFound;
        TransactionOnBalanceSheet curPaid;
        int month;
        int year;
        TransactionOnBalanceSheet copyOfFirstTransaction = null;

        for(int curMonth = dateRage.startMonth, curYear = dateRage.startYear ;(curYear < dateRage.endYear)
                || (curYear == dateRage.endYear && curMonth <= dateRage.endMonth); curMonth++)
        {
            monthYearFound = false;
            for(TransactionOnBalanceSheet f : initialPaid)
            {
                if(copyOfFirstTransaction == null)copyOfFirstTransaction = f;
                calendar.clear();
                calendar.setTime(f.transactionDate);
                month = calendar.get(Calendar.MONTH) + 1;
                year = calendar.get(Calendar.YEAR);

                if(month == curMonth && year == curYear) {
                    monthYearFound = true;
                    break;
                }
            }

            if(!monthYearFound){
                curPaid = new TransactionOnBalanceSheet();
                calendar.clear();;
                calendar.set(Calendar.MONTH, curMonth - 1);
                calendar.set(Calendar.YEAR, curYear);
                curPaid.transactionDate = new Date(calendar.getTime().getTime());
                curPaid.flatId = copyOfFirstTransaction.flatId;
                curPaid.amount = 0;
                allMissingDates.add(curPaid);
            }

            if(curMonth == 12) {
                curMonth = 1;
                curYear++;
            }
        }
        allMissingDates.addAll(initialPaid);
        return allMissingDates;
    }


    public static TreeMap<String, List<FlatWisePayable>> getFlatAndItsPenalty(List<FlatWisePayable> payables, Map<String, FlatSummary> flatSummary, BalanceSheetDateRage dateRage)
    {
        TreeMap<String, List<FlatWisePayable>> data = new TreeMap<>();
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


        int tSum = 0;
        for(String flatId : data.keySet())
        {
            tSum = 0;
            if(!flatSummary.containsKey(flatId))
            {
                FlatSummary flatSum = new FlatSummary();
                flatSum.flatId = flatId;
                flatSummary.put(flatId, flatSum);
            }
            for(FlatWisePayable p : data.get(flatId)){
                tSum += p.amount;
            }
            flatSummary.get(flatId).penality = tSum;

            data.put(flatId, addMissingDate(data.get(flatId), dateRage));
        }

        for(String flatKey : data.keySet()){
            Collections.sort(data.get(flatKey), new FlatWisePayableComparator());
        }
        return data;
    }

    public static TreeMap<String, List<TransactionOnBalanceSheet>> getFlatAndItsPayment(List<TransactionOnBalanceSheet> paid, Map<String, FlatSummary> flatSummary, BalanceSheetDateRage dateRage)
    {
        TreeMap<String, List<TransactionOnBalanceSheet>> data = new TreeMap<>();

            for (TransactionOnBalanceSheet p : paid) {
                if (p != null && p.flatId != null && p.flatId.length() >0) {
                    try {
                        if (data.containsKey(p.flatId)) {
                            data.get(p.flatId).add(p);
                        } else {
                            List<TransactionOnBalanceSheet> transactions = new ArrayList<>();
                            transactions.add(p);
                            data.put(p.flatId, transactions);
                        }
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }

        int tSum = 0;
        for(String flatId : data.keySet())
        {
            tSum = 0;
            if(!flatSummary.containsKey(flatId))
            {
                FlatSummary flatSum = new FlatSummary();
                flatSum.flatId = flatId;
                flatSummary.put(flatId, flatSum);
            }
            for(TransactionOnBalanceSheet p : data.get(flatId)){
                tSum += p.amount;
            }
            flatSummary.get(flatId).received = tSum;

            data.put(flatId, addMissingDateInPaid(data.get(flatId), dateRage));
        }

        for(String flatKey : data.keySet()) {
            Collections.sort(data.get(flatKey), new TransactionOnBalanceSheetComparator());
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
            try {
                if(t.flatNo == null || t.flatNo.length() == 0) continue;
                f.flatNumber = String.format("%03d", Integer.parseInt(t.flatNo));
            }catch (Exception e) {
                Log.i("info", "flatNo - " + t.flatNo);
                e.printStackTrace();
            }
            f.block = t.block;
            f.area = t.area;
            f.owner = t.userName;
            flats.put(t.flatId, f);
        }
        return flats;
    }

    public static BalanceSheetDateRage getDateRage(List<FlatWisePayable> payables)
    {
        BalanceSheetDateRage rage = new BalanceSheetDateRage();
        Date curDate;
        for(FlatWisePayable f : payables) {
            calendar.clear();
            calendar.set(Calendar.MONTH, f.month);
            calendar.set(Calendar.YEAR, f.year);
            curDate = new Date(calendar.getTime().getTime());

            if(rage.startDate == null) {
                rage.startDate = curDate;
                rage.startMonth = rage.endMonth = f.month;
                rage.endDate= curDate;
                rage.startYear = rage.endYear = f.year;
            } else if(rage.startDate.compareTo(curDate) > 0) {
                rage.startDate = curDate;
                rage.startMonth = f.month;
                rage.startYear = f.year;
            } else if(rage.endDate.compareTo(curDate) < 0) {
                rage.endDate = curDate;
                rage.endMonth = f.month;
                rage.endYear = f.year;
            }
        }
        return rage;
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
            BalanceSheetDateRage dateRange = getDateRage(payables);

            Map<String, Flat> flats = getFlatDetails(data);
            workbook = Workbook.createWorkbook(file, wbSettings);
            //Excel sheet name. 0 represents first sheet
            WritableSheet sheet = workbook.createSheet("Individual wise Details", 0);

            try {
                Map<String, FlatSummary> flatSummary = new HashMap<>();
                Map<String, List<FlatWisePayable>> receivable = getFlatAndItsPayables(payables, flatSummary, dateRange);
                Map<String, List<FlatWisePayable>> penalty = getFlatAndItsPenalty(payables, flatSummary, dateRange);
                Map<String, List<TransactionOnBalanceSheet>> paid = getFlatAndItsPayment(data, flatSummary, dateRange);

                WritableFont headingTop = new WritableFont(WritableFont.COURIER, 14);
                headingTop.setBoldStyle(WritableFont.BOLD);
                WritableCellFormat headingTopCellFormat = new WritableCellFormat(headingTop);

                WritableFont headingColumn = new WritableFont(WritableFont.COURIER, 12);
                headingColumn.setBoldStyle(WritableFont.BOLD);
                WritableCellFormat headingColumnCellFormat = new WritableCellFormat(headingColumn);

                //Static heading
                sheet.addCell(new Label(0, 0, "Amount Receivable", headingTopCellFormat)); // column and row
                sheet.addCell(new Label(0, 1, "Sr no.", headingColumnCellFormat)); // column and row
                sheet.addCell(new Label(1, 1, "Flat no", headingColumnCellFormat));
                sheet.addCell(new Label(2, 1, "Block", headingColumnCellFormat));
                sheet.addCell(new Label(3, 1, "Owner Name", headingColumnCellFormat));
                sheet.addCell(new Label(4, 1, "Sq Ft", headingColumnCellFormat));
                sheet.addCell(new Label(5, 1, "Receivable", headingColumnCellFormat));
                sheet.addCell(new Label(6, 1, "Received", headingColumnCellFormat));
                sheet.addCell(new Label(7, 1, "Balance", headingColumnCellFormat));
                //Month year column heading
                int col=8;
                int srNo = 1;
                for(String flatNo : receivable.keySet()) {
                    if(receivable.containsKey(flatNo) && receivable.get(flatNo) != null) {
                        for (FlatWisePayable curT : receivable.get(flatNo)) {
                            if (curT != null) {
                                if (curT.expenseType.equals(ExpenseType.ExpenseTypeConst.Monthly_Maintenance))
                                    sheet.addCell(new Label(col++, 1, curT.month + "-" + curT.year, headingColumnCellFormat)); //FIx month value while loading initial data
                                else if (curT.expenseType != null)
                                    sheet.addCell(new Label(col++, 1, curT.expenseType.name(), headingColumnCellFormat));
                                else
                                    sheet.addCell(new Label(col++, 1, curT.month + "-" + curT.year, headingColumnCellFormat)); //FIx month value while loading initial data
                            }
                        }
                    }
                    break;
                }

                int i=1;

                boolean setFlatDetails;
                Flat curFlat;
                FlatSummary cFlatSum;
                //Receivable data -----------------------------------------------------------------------
                for(String flatNo : receivable.keySet()) {
                    i++;
                    col=8;
                    setFlatDetails = false;
                    for(FlatWisePayable curT : receivable.get(flatNo)) {
                        if(!setFlatDetails) {
                            curFlat = flats.get(curT.flatId);
                            cFlatSum = flatSummary.get(curT.flatId);
                            sheet.addCell(new Label(0, i, srNo++ + "" ));
                            sheet.addCell(new Label(1, i, curFlat.flatNumber));
                            sheet.addCell(new Label(2, i, curFlat.block));
                            sheet.addCell(new Label(3, i, curFlat.owner));
                            sheet.addCell(new Label(4, i, curFlat.area +""));
                            sheet.addCell(new Label(5, i, (cFlatSum.receivable + cFlatSum.penality) + ""));
                            sheet.addCell(new Label(6, i, cFlatSum.received +""));
                            sheet.addCell(new Label(7, i, (cFlatSum.receivable - cFlatSum.received)+""));
                            setFlatDetails = true;
                        }
                        //5,6,7 column skip
                        sheet.addCell(new Label(col++, i, curT.amount + ""));
                    }
                }

                i++;

                sheet.addCell(new Label(0, i++, "Amount Received", headingTopCellFormat)); // column and row
                //Static heading
                sheet.addCell(new Label(0, i, "Sr no.", headingColumnCellFormat)); // column and row
                sheet.addCell(new Label(1, i, "Flat no", headingColumnCellFormat));
                sheet.addCell(new Label(2, i, "Block", headingColumnCellFormat));
                sheet.addCell(new Label(3, i, "Owner Name", headingColumnCellFormat));
                sheet.addCell(new Label(4, i, "Sq Ft", headingColumnCellFormat));
                sheet.addCell(new Label(5, i, "Receivable", headingColumnCellFormat));
                sheet.addCell(new Label(6, i, "Received", headingColumnCellFormat));
                sheet.addCell(new Label(7, i, "Balance", headingColumnCellFormat));
                //Month year column heading
                col=8;
                for(String flatNo : receivable.keySet()) {
                    if(receivable.containsKey(flatNo) && receivable.get(flatNo) != null) {
                        for (FlatWisePayable curT : receivable.get(flatNo)) {
                            if (curT != null) {
                                if (curT.month >= 0)
                                    sheet.addCell(new Label(col++, i, curT.month + "-" + curT.year, headingColumnCellFormat)); //FIx month value while loading initial data
                                else if (curT.expenseType != null)
                                    sheet.addCell(new Label(col++, i, curT.expenseType.name(), headingColumnCellFormat));
                            }
                        }
                    }
                    break;
                }

                srNo = 1;
                //Amount Received ------------------------------------------------------------------------
                for(String flatNo : paid.keySet()) {
                    i++;
                    col=8;
                    setFlatDetails = false;
                    for(TransactionOnBalanceSheet curBT : paid.get(flatNo)) {
                        if(!setFlatDetails) {
                            curFlat = flats.get(curBT.flatId);
                            sheet.addCell(new Label(0, i, srNo++ + "" ));
                            sheet.addCell(new Label(1, i, curFlat.flatNumber));
                            sheet.addCell(new Label(2, i, curFlat.block));
                            sheet.addCell(new Label(3, i, curFlat.owner));
                            sheet.addCell(new Label(4, i, curFlat.area +""));
                            setFlatDetails = true;
                        }
                        //5,6,7 column skip
                        sheet.addCell(new Label(col++, i, curBT.amount + ""));
                    }
                }

                i++;
                sheet.addCell(new Label(0, i++, "Penalty Receivabled",headingTopCellFormat)); // column and row
                //Static heading
                sheet.addCell(new Label(0, i, "Sr no.", headingColumnCellFormat)); // column and row
                sheet.addCell(new Label(1, i, "Flat no", headingColumnCellFormat));
                sheet.addCell(new Label(2, i, "Block", headingColumnCellFormat));
                sheet.addCell(new Label(3, i, "Owner Name", headingColumnCellFormat));
                sheet.addCell(new Label(4, i, "Sq Ft", headingColumnCellFormat));
                sheet.addCell(new Label(5, i, "Receivable", headingColumnCellFormat));
                sheet.addCell(new Label(6, i, "Received", headingColumnCellFormat));
                sheet.addCell(new Label(7, i, "Balance", headingColumnCellFormat));
                //Month year column heading
                col=8;
                for(String flatNo : receivable.keySet()) {
                    if(receivable.containsKey(flatNo) && receivable.get(flatNo) != null) {
                        for (FlatWisePayable curT : receivable.get(flatNo)) {
                            if (curT != null) {
                                if (curT.month >= 0)
                                    sheet.addCell(new Label(col++, i, curT.month + "-" + curT.year, headingColumnCellFormat)); //FIx month value while loading initial data
                                else if (curT.expenseType != null)
                                    sheet.addCell(new Label(col++, i, curT.expenseType.name(), headingColumnCellFormat));
                            }
                        }
                    }
                    break;
                }

                srNo = 1;
                //Penalty Receivabled
                for(String flatNo : penalty.keySet()) {
                    i++;
                    col=8;
                    setFlatDetails = false;
                    for(FlatWisePayable curPen : penalty.get(flatNo)) {
                        if(!setFlatDetails) {
                            curFlat = flats.get(curPen.flatId);
                            sheet.addCell(new Label(0, i, srNo + "" ));
                            sheet.addCell(new Label(1, i, curFlat.flatNumber));
                            sheet.addCell(new Label(2, i, curFlat.block));
                            sheet.addCell(new Label(3, i, curFlat.owner));
                            sheet.addCell(new Label(4, i, curFlat.area +""));
                            setFlatDetails = true;
                        }
                        //5,6,7 column skip
                        sheet.addCell(new Label(col++, i, curPen.amount + ""));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            workbook.write();
            try {
                workbook.close();
            } catch (WriteException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
