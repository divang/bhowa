package societyhelp.parser;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import societyhelp.dao.mysql.impl.ExpenseType;
import societyhelp.dao.mysql.impl.ExpenseType.ExpenseTypeConst;

import com.opencsv.CSVReader;

/**
 * Created by divang.sharma on 12/30/2016.
 */
public class LoadBhowaInitialData {

    static LoadData loadData;
    static Calendar calendar = Calendar.getInstance();

    public static LoadData loadInitialData(String dataFilePath)
    {
        loadData = new LoadData();
        CSVReader reader = null;
        DataTye currentDataType = DataTye.NONE;


        try {
            reader = new CSVReader(new FileReader(dataFilePath));
            String[] data;
            String[] currentHeader = null;
            boolean nextLineIsHeader = false;

            while ((data = reader.readNext()) != null) {

                if(data.length == 1)
                {
                    try{
                        //System.out.println("Current Data Type from data file (str): " + data[0]);
                        currentDataType = DataTye.valueOf(data[0].trim());
                        //System.out.println("Current Data Type : " + currentDataType);
                        nextLineIsHeader = true;
                        continue;
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                        break;
                    }
                }

                if(nextLineIsHeader)
                {
                    currentHeader = data;
                    nextLineIsHeader = false;
                    continue;
                }

                switch (currentDataType)
                {
                    case EXPENSE_INITIAL_DATA:
                        addApartmentExpense(currentHeader, data);
                        break;
                    case PAYABLE_INITIAL_DATA:
                        addPayable(currentHeader, data);
                        break;
                    case PENALTY_INITIAL_DATA:
                        addPenalty(currentHeader, data);
                        break;
                    case PAID_INITIAL_DATA:
                        addPaid(currentHeader, data);
                        break;
                    case STORERENT_INITIAL_DATA:
                        addStoreRent(currentHeader, data);
                        break;
                    case TENANT_INITIAL_DATA:
                        addFlatTenant(data);
                        break;
                    case NONE:
                        break;
                }
            }

            //System.out.println("apartmentExpenses : " + loadData.apartmentExpenses);
            //System.out.println("payables : " + loadData.payables);
            //System.out.println("penalty : " + loadData.penalty);
            //System.out.println("userPaid : " + loadData.userPaid);
            //System.out.println("storeRent : " + loadData.storeRent);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(reader != null)
            {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return loadData;
    }

    static String dateFormat = "dd-MMM-yy";
    static SimpleDateFormat sdFormat = new SimpleDateFormat(dateFormat);
    static String dateFormatMMMYY = "MMM-yy";
    static SimpleDateFormat sdFormatMMMYY = new SimpleDateFormat(dateFormatMMMYY);
    static String expenseHeader = "Expense initial data-";
    static String payableHeader = "Payable initial data-";
    static String pendingHeader = "Pending Initial Data-";
    static String paidHeader = "Paid Initial data-";
    static String storeHeader = "Store Room - Club House Initial data-";
    static String headers[] = {expenseHeader, payableHeader, pendingHeader, paidHeader, storeHeader};
    static enum DataTye { EXPENSE_INITIAL_DATA, PAYABLE_INITIAL_DATA, PENALTY_INITIAL_DATA, PAID_INITIAL_DATA, STORERENT_INITIAL_DATA, TENANT_INITIAL_DATA, NONE };
    static LoadData finalData = new LoadData();

    //Expense string in csv
    static String SecurityGuards="Security Guards";
    static String HouseLabour="House Keeping Labour";
    static String CommonElectricity="Common Electricity - BESCOM";
    static String WaterTank="Water Tankers ";
    static String Gardening="Gardening";
    static String Waste="Waste Disposal";
    static String Deisel="Deisel for Generator";
    static String HouseConsumables="House Keeping consumables";
    static String Pest="Pest Control";
    static String GeneratorAMC="Generator AMC";
    static String GeneratorRepair="Generator Repair/Servicing";
    static String Motor="Motor Purchase/Repair";
    static String LiftAMC="Lift AMC";
    static String LiftRepair="Lift Repair";
    static String Plumbing="Plumbing";
    static String ElectricalRepair="Electrical Repair";
    static String TankCleaning="Tank Cleaning/Repairing";
    static String Lawyer="Lawyer";
    static String Miscellaneous="Miscellaneous";
    static String IntercomAMC="Intercom AMC";
    static String IntercomRepair="Intercom Purchase/Repair";
    static String Fire="Fire Extinguisher";
    static String SecurityRelated="Security  Related (Gril,Mat etc.)";
    static String Infra="Apartment Infra Structure Repair";
    static String Park="Children";
    static String ParkLBox="Children Park and L Box";
    static String SeptickTank="Septick Tank n Pipe cleaning maintenance";
    static String ClubHouse="Club House ";
    static String Alamari="Alamari purchase";

    static String Intercom="Intercom";
    static String Deposit="Deposit";
    static String SanitaryWork="Sanitary Work";
    static String ClubHouseDev="Club-House Dev";
    static String PipeRepair="Pipe Repair";
    static String OC="OC, Children Park and L Box";
    static String OC_Only="OC";
    static String SeptickTankCleaning="Septick Tank Cleaning";

    protected static ExpenseType.ExpenseTypeConst getExpenseType(String strExpenseType)
    {
        ExpenseType.ExpenseTypeConst type = null;
        if(SecurityGuards.equals(strExpenseType)) type = ExpenseType.ExpenseTypeConst.Security_Guards;
        else if(HouseLabour.equals(strExpenseType)) type = ExpenseType.ExpenseTypeConst.House_Keeping_Labour;
        else if(CommonElectricity.equals(strExpenseType)) type = ExpenseType.ExpenseTypeConst.Common_Electricity;
        else if(WaterTank.equals(strExpenseType)) type = ExpenseType.ExpenseTypeConst.Water_Tankers;
        else if(Gardening.equals(strExpenseType)) type = ExpenseType.ExpenseTypeConst.Gardening;
        else if(Waste.equals(strExpenseType)) type = ExpenseType.ExpenseTypeConst.Waste_Disposal;
        else if(Deisel.equals(strExpenseType)) type = ExpenseType.ExpenseTypeConst.Deisel_For_Generator;
        else if(HouseConsumables.equals(strExpenseType)) type = ExpenseType.ExpenseTypeConst.House_Keeping_Consumables;
        else if(Pest.equals(strExpenseType)) type = ExpenseType.ExpenseTypeConst.Pest_Control;
        else if(GeneratorAMC.equals(strExpenseType)) type = ExpenseType.ExpenseTypeConst.Generator_AMC;
        else if(GeneratorRepair.equals(strExpenseType)) type = ExpenseType.ExpenseTypeConst.Generator_Repair_Servicing;
        else if(Motor.equals(strExpenseType)) type = ExpenseType.ExpenseTypeConst.MotorRepair;
        else if(LiftAMC.equals(strExpenseType)) type = ExpenseType.ExpenseTypeConst.Lift_AMC;
        else if(LiftRepair.equals(strExpenseType)) type = ExpenseType.ExpenseTypeConst.Lift_Repair;
        else if(Plumbing.equals(strExpenseType)) type = ExpenseType.ExpenseTypeConst.Plumbing;
        else if(ElectricalRepair.equals(strExpenseType)) type = ExpenseType.ExpenseTypeConst.Electrical_Repair;
        else if(TankCleaning.equals(strExpenseType)) type = ExpenseType.ExpenseTypeConst.Tank_Cleaning_Repairing;
        else if(Lawyer.equals(strExpenseType)) type = ExpenseType.ExpenseTypeConst.Lawyer;
        else if(Miscellaneous.equals(strExpenseType)) type = ExpenseType.ExpenseTypeConst.Miscellaneous;
        else if(IntercomAMC.equals(strExpenseType)) type = ExpenseType.ExpenseTypeConst.Intercom_AMC;
        else if(IntercomRepair.equals(strExpenseType)) type = ExpenseType.ExpenseTypeConst.Intercom_Purchase_Repair;
        else if(Fire.equals(strExpenseType)) type = ExpenseType.ExpenseTypeConst.Fire_Extinguisher;
        else if(SecurityRelated.equals(strExpenseType)) type = ExpenseType.ExpenseTypeConst.Security_Related;
        else if(Infra.equals(strExpenseType)) type = ExpenseType.ExpenseTypeConst.Apartment_InfraStructure_Repair;
        else if(ParkLBox.equals(strExpenseType)) type = ExpenseType.ExpenseTypeConst.Children_Park;
        else if(SeptickTank.equals(strExpenseType)) type = ExpenseType.ExpenseTypeConst.Septick_Tank_Pipe_Cleaning;
        else if(ClubHouse.equals(strExpenseType)) type = ExpenseType.ExpenseTypeConst.Club_House;
        else if(Alamari.equals(strExpenseType)) type = ExpenseType.ExpenseTypeConst.Alamari_Purchase;
        else if(Intercom.equals(strExpenseType)) type = ExpenseType.ExpenseTypeConst.Intercom_Purchase_Repair;
        else if(Deposit.equals(strExpenseType)) type = ExpenseType.ExpenseTypeConst.Miscellaneous;
        else if(SanitaryWork.equals(strExpenseType)) type = ExpenseType.ExpenseTypeConst.Septick_Tank_Pipe_Cleaning;
        else if(ClubHouseDev.equals(strExpenseType)) type = ExpenseType.ExpenseTypeConst.Club_House;
        else if(PipeRepair.equals(strExpenseType)) type = ExpenseType.ExpenseTypeConst.Apartment_InfraStructure_Repair;
        else if(OC.equals(strExpenseType)) type = ExpenseType.ExpenseTypeConst.Khata_Payable;
        else if(OC_Only.equals(strExpenseType)) type = ExpenseType.ExpenseTypeConst.Khata_Payable;
        else if(SeptickTankCleaning.equals(strExpenseType)) type = ExpenseType.ExpenseTypeConst.Septick_Tank_Pipe_Cleaning;
        else if(strExpenseType.contains(Park)) type = ExpenseType.ExpenseTypeConst.Children_Park;
        else {
            type = ExpenseTypeConst.UNKNOWN;
        }
        return  type;
    }

    protected static void addFlatTenant(String[] data)
    {
        try
        {
           loadData.tenantUser.add(new LoadTenantUser(data[0], data[1]));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    protected static void addApartmentExpense(String[] dateHeader, String[] amount)
    {
        try
        {
            LoadApartmentExpense ae = new LoadApartmentExpense(amount[0]);
            int cIndex = 1;
            Date cExpenseDate;
            float cAmount;
            for(;cIndex < amount.length; cIndex++)
            {
                cAmount = 0;
                try {
                    cExpenseDate = new Date(sdFormat.parse(dateHeader[cIndex]).getTime());
                    if(amount[cIndex].trim().length() > 0) {
                        cAmount = Float.parseFloat(amount[cIndex].replace(",",""));
                    }
                    ae.addData(cExpenseDate, cAmount);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            loadData.apartmentExpenses.add(ae);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    protected static void addPayable(String[] dateHeader, String[] data)
    {
        try
        {
            int flatArea = 0;
            float maintenanceAmount = 0;
            if(data[4].trim().length() > 0) flatArea = Integer.parseInt(data[4].trim().replace(",", ""));
            if(data[5].trim().length() > 0) maintenanceAmount = Float.parseFloat(data[5].trim().replace(",", ""));
            LoadFlatWisePayble parsedData = new LoadFlatWisePayble(data[1], data[2], data[3], flatArea, maintenanceAmount);

            int cIndex = 9;
            Date cExpenseDate = null;
            Date previousPayableDate = cExpenseDate;
            ExpenseAmountMap currentPayble;
            String strDate = null;
            for(;cIndex < data.length; cIndex++)
            {
                currentPayble = new ExpenseAmountMap();
                cExpenseDate = null;
                try {
                    try {
                        strDate = dateHeader[cIndex].trim();
                        cExpenseDate = new Date(sdFormat.parse(strDate).getTime());
                        previousPayableDate = cExpenseDate;
                        currentPayble.expenseType = ExpenseTypeConst.Monthly_Maintenance;
                    } catch(Exception e){
                        //Expense type wise payable
                        try {
                            cExpenseDate = new Date(sdFormatMMMYY.parse(strDate).getTime());
                            previousPayableDate = cExpenseDate;
                            currentPayble.expenseType = ExpenseTypeConst.Monthly_Maintenance;
                        }catch(Exception inner) {
                            currentPayble.expenseType = getExpenseType(dateHeader[cIndex].trim());
                        }
                    }

                    if(data[cIndex].trim().length() > 0) {
                        currentPayble.amount = Float.parseFloat(data[cIndex].replace(",",""));
                    }

                    if(cExpenseDate == null) {
                        cExpenseDate = previousPayableDate;
                        calendar.setTime(previousPayableDate);
                        calendar.add(Calendar.DAY_OF_MONTH, 1);
                        previousPayableDate = new Date(calendar.getTimeInMillis());
                    }
                    parsedData.addData(cExpenseDate, currentPayble);

                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            loadData.payables.add(parsedData);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    protected static void addPenalty(String[] dateHeader, String[] data)
    {
        try
        {
            LoadFlatWisePayble parsedData = new LoadFlatWisePayble(data[1], data[2], data[3], 0, 0);
            int cIndex = 4;
            Date cExpenseDate = null;
            Date previousPayableDate = cExpenseDate;
            ExpenseAmountMap currentPayble;

            for(;cIndex < data.length; cIndex++)
            {
                currentPayble = new ExpenseAmountMap();
                cExpenseDate = null;
                try {
                    try {
                        //System.out.println("addPenalty -> dateHeader[cIndex]-"+dateHeader[cIndex]);
                        cExpenseDate = new Date(sdFormat.parse(dateHeader[cIndex].trim()).getTime());
                        previousPayableDate = cExpenseDate;
                        currentPayble.expenseType = ExpenseTypeConst.Penalty;
                    } catch(Exception e){
                        //e.printStackTrace();
                        try {
                            cExpenseDate = new Date(sdFormatMMMYY.parse(dateHeader[cIndex].trim()).getTime());
                            previousPayableDate = cExpenseDate;
                            currentPayble.expenseType = ExpenseTypeConst.Penalty;
                        }catch(Exception inner) {
                            currentPayble.expenseType = getExpenseType(dateHeader[cIndex].trim());
                        }
                    }

                    if(data[cIndex].trim().length() > 0 && !data[cIndex].trim().equals("-")) {
                        currentPayble.amount = Float.parseFloat(data[cIndex].replace(",",""));
                    }

                    if(cExpenseDate == null) {
                        cExpenseDate = previousPayableDate;
                        calendar.setTime(previousPayableDate);
                        calendar.add(Calendar.DAY_OF_MONTH, 1);
                        previousPayableDate = new Date(calendar.getTimeInMillis());
                    }

                    parsedData.addData(cExpenseDate, currentPayble);

                    /*
                    if(cExpenseDate != null && currentPayble != null)
                    {
                        parsedData.addData(cExpenseDate, currentPayble);
                    }
                    else
                    {
                        System.err.println("addPenalty -> cExpenseDate-"+cExpenseDate+ " currentPayble-"+ currentPayble);
                    }
                    */
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            loadData.penalty.add(parsedData);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    protected static void addPaid(String[] dateHeader, String[] data)
    {
        try
        {
            LoadUserPaid parsedData = new LoadUserPaid(data[1], data[2], data[3]);
            int cIndex = 4;
            Date cExpenseDate = null;
            Date previousPayableDate = cExpenseDate;
            ExpenseAmountMap currentPayble;

            for(;cIndex < data.length; cIndex++)
            {
                currentPayble = new ExpenseAmountMap();
                cExpenseDate = null;
                try {
                    try {
                        cExpenseDate = new Date(sdFormat.parse(dateHeader[cIndex].trim()).getTime());
                        previousPayableDate = cExpenseDate;
                        currentPayble.expenseType = ExpenseTypeConst.Monthly_Maintenance;
                    } catch(Exception e){
                        try {
                            cExpenseDate = new Date(sdFormatMMMYY.parse(dateHeader[cIndex].trim()).getTime());
                            previousPayableDate = cExpenseDate;
                            currentPayble.expenseType = ExpenseTypeConst.Monthly_Maintenance;
                        } catch(Exception inner) {
                            //Expense type wise payable
                            currentPayble.expenseType = getExpenseType(dateHeader[cIndex].trim());
                        }
                    }
                    //System.out.println("data[cIndex]-" + data[cIndex]);
                    if(data[cIndex].trim().length() > 0 && !data[cIndex].trim().equals("-")) {
                        currentPayble.amount = Float.parseFloat(data[cIndex].replace(",",""));
                    }

                    if(cExpenseDate == null) {
                        cExpenseDate = previousPayableDate;
                        calendar.setTime(previousPayableDate);
                        calendar.add(Calendar.DAY_OF_MONTH, 1);
                        previousPayableDate = new Date(calendar.getTimeInMillis());
                    }
                    parsedData.addData(cExpenseDate, currentPayble);

                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            loadData.userPaid.add(parsedData);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    protected static void addStoreRent(String[] dateHeader, String[] amount)
    {
        try
        {
            LoadApartmentEarning obj = new LoadApartmentEarning(ExpenseType.ExpenseTypeConst.Club_Store_Earning);
            int cIndex = 0;
            Date cExpenseDate;
            float cAmount;
            for(;cIndex < amount.length; cIndex++)
            {
                cAmount = 0;
                try {
                    cExpenseDate = new Date(sdFormat.parse(dateHeader[cIndex]).getTime());
                    if(amount[cIndex].trim().length() > 0) {
                        cAmount = Float.parseFloat(amount[cIndex].replace(",",""));
                    }
                    obj.addData(cExpenseDate, cAmount);
                } catch (Exception e)
                {
                    //e.printStackTrace();
                }
            }

            loadData.storeRent.add(obj);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    static public class LoadData
    {
        public List<LoadApartmentExpense> apartmentExpenses = new ArrayList<>();
        public List<LoadUserPaid> userPaid = new ArrayList<>();
        public List<LoadFlatWisePayble> payables = new ArrayList<>();
        public List<LoadFlatWisePayble> penalty = new ArrayList<>();
        public List<LoadApartmentEarning> storeRent = new ArrayList<>();
        public List<LoadTenantUser> tenantUser = new ArrayList<>();
    }

    static public class LoadApartmentExpense
    {
        public ExpenseType.ExpenseTypeConst expenseType;
        public SortedMap<Date, Float> dateAmountMapping = new TreeMap<>();

        public LoadApartmentExpense(String strType)
        {
            expenseType = getExpenseType(strType);
        }

        public void addData(Date expenseDate, Float amount)
        {
            dateAmountMapping.put(expenseDate, amount);
        }

        @Override
        public String toString() {
            StringBuilder str = new StringBuilder();
            str.append("==>expenseType:").append(expenseType);
            str.append(" dateAmountMapping:").append(dateAmountMapping);
            return str.toString();
        }
    }

    static public class LoadApartmentEarning
    {
        public ExpenseType.ExpenseTypeConst expenseType;
        public SortedMap<Date, Float> dateAmountMapping = new TreeMap<>();

        public LoadApartmentEarning(ExpenseType.ExpenseTypeConst type)
        {
            expenseType = type;
        }

        public void addData(Date expenseDate, Float amount)
        {
            dateAmountMapping.put(expenseDate, amount);
        }

        @Override
        public String toString() {
            StringBuilder str = new StringBuilder();
            str.append("==>expenseType:").append(expenseType);
            str.append(" dateAmountMapping:").append(dateAmountMapping);
            return str.toString();
        }
    }

    static public class LoadTenantUser
    {
        public String userName;
        public String flatNo;
        public String userId;
        public String flatId;

        public LoadTenantUser(String name, String flat)
        {
            userName = name.trim();
            flatNo = flat;
        }

        @Override
        public String toString() {
            StringBuilder str = new StringBuilder();
            str.append("==>flatNo:").append(flatNo);
            str.append(" userName:").append(userName);
            str.append(" userId:").append(userId);
            return str.toString();
        }
    }

    static public class LoadUserPaid
    {
        public String userName;
        public String flatNo;
        public String blockNo;
        public SortedMap<Date, ExpenseAmountMap> dateAmountMapping = new TreeMap<>();

        public LoadUserPaid(String flat, String block, String name)
        {
            userName = name.trim();
            flatNo = flat;
            blockNo = block;
        }

        public void addData(Date expenseDate, ExpenseAmountMap object)
        {
            dateAmountMapping.put(expenseDate, object);
        }

        @Override
        public String toString() {
            StringBuilder str = new StringBuilder();
            str.append("==>flatNo:").append(flatNo);
            str.append(" userName:").append(userName);
            str.append(" blockNo:").append(blockNo);
            str.append(" dateAmountMapping:").append(dateAmountMapping);
            return str.toString();
        }
    }

    static public class LoadFlatWisePayble
    {
        public String userName;
        public String loginId;
        public String flatId;
        public String userId;
        public String flatNo;
        public String blockNo;
        public int area;
        public float maintenanceAmount;

        public SortedMap<Date, ExpenseAmountMap> dateAmountMapping = new TreeMap<>();

        public LoadFlatWisePayble(String flat, String block, String name, int srqFeetArea, float amount)
        {
            userName = name.trim();
            flatNo = flat;
            blockNo = block.trim();
            area = srqFeetArea;
            maintenanceAmount = amount;
        }

        public void addData(Date payableDate, ExpenseAmountMap object)
        {
            dateAmountMapping.put(payableDate, object);
        }

        @Override
        public String toString() {
            StringBuilder str = new StringBuilder();
            str.append("==>flatNo:").append(flatNo);
            str.append(" userName:").append(userName);
            str.append(" loginId:").append(loginId);
            str.append(" blockNo:").append(blockNo);
            str.append(" area:").append(area);
            str.append(" maintenanceAmount:").append(maintenanceAmount);
            str.append(" dateAmountMapping:").append(dateAmountMapping);
            return str.toString();
        }
    }

    static public class ExpenseAmountMap
    {
        public ExpenseType.ExpenseTypeConst expenseType;
        public Float amount;

        @Override
        public String toString() {
            StringBuilder str = new StringBuilder();
            str.append("expenseType:").append(expenseType);
            str.append(" amount:").append(amount);
            return str.toString();
        }
    }
}
