package societyhelp.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import societyhelp.dao.mysql.impl.ApartmentEarning;
import societyhelp.dao.mysql.impl.ApartmentExpense;
import societyhelp.dao.mysql.impl.ExpenseType;
import societyhelp.dao.mysql.impl.FlatWisePayable;
import societyhelp.dao.mysql.impl.UserPaid;

/**
 * Created by divang.sharma on 12/30/2016.
 */
public class LoadBhowaInitialData {

    static String expenseHeader = "Expense initial data-";
    static String payableHeader = "Payable initial data-";
    static String pendingHeader = "Pending Initial Data-";
    static String paidHeader = "Paid Initial data-";
    static String storeHeader = "Store Room - Club House Initial data-";
    static String headers[] = {expenseHeader, payableHeader, pendingHeader, paidHeader, storeHeader};
    static enum DataTye { EXPENSE, PAYABLE, PENDING, PAID, STORERENT, NONE };
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
    static String Park="Children Park";
    static String SeptickTank="Septick Tank n Pipe cleaning maintenance";
    static String ClubHouse="Club House ";
    static String Alamari="Alamari purchase";

    public static ExpenseType.ExpenseTypeConst getExpenseType(String strExpenseType)
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
        else if(Park.equals(strExpenseType)) type = ExpenseType.ExpenseTypeConst.Children_Park;
        else if(SeptickTank.equals(strExpenseType)) type = ExpenseType.ExpenseTypeConst.Septick_Tank_Pipe_Cleaning;
        else if(ClubHouse.equals(strExpenseType)) type = ExpenseType.ExpenseTypeConst.Club_House;
        else if(Alamari.equals(strExpenseType)) type = ExpenseType.ExpenseTypeConst.Alamari_Purchase;
        return  type;
    }

    public static void addApartmentExpense(String header[], String[] data)
    {
        ApartmentExpense ae = new ApartmentExpense();
        ae.expenseType = getExpenseType(data[0]);
    }

    public static void addPayable(String header[], String[] data)
    {

    }

    public static void addPending(String header[], String[] data)
    {

    }

    public static void addPaid(String header[], String[] data)
    {

    }

    public static void addStoreRent(String[] data)
    {

    }

    public static LoadData loadInitialData(String dataFilePath)
    {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        DataTye currentDataType = DataTye.NONE;


        try {
            br = new BufferedReader(new FileReader(dataFilePath));
            String[] data;
            String[] currentHeader = null;
            boolean nextLineIsHeader = false;

            while ((line = br.readLine()) != null) {
                data = line.split(cvsSplitBy);
                if(data.length == 1)
                {
                    nextLineIsHeader = true;
                    if(data[0] == expenseHeader) currentDataType = DataTye.EXPENSE;
                    else if(data[0] == payableHeader) currentDataType = DataTye.PAYABLE;
                    else if(data[0] == pendingHeader) currentDataType = DataTye.PENDING;
                    else if(data[0] == paidHeader) currentDataType = DataTye.PAID;
                    else if(data[0] == storeHeader) currentDataType = DataTye.STORERENT;
                    continue;
                }

                if(nextLineIsHeader)
                {
                    currentHeader = data;
                    nextLineIsHeader = false;
                    continue;
                }

                switch (currentDataType)
                {
                    case EXPENSE:
                        addApartmentExpense(currentHeader, data);
                        break;
                    case PAYABLE:
                        addPayable(currentHeader, data);
                        break;
                    case PENDING:
                        addPending(currentHeader, data);
                        break;
                    case PAID:
                        addPaid(currentHeader, data);
                        break;
                    case STORERENT:
                        addStoreRent(data);
                        break;
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new LoadData();
    }

    static public class LoadData
    {
        public static List<ApartmentExpense> apartmentExpenses = new ArrayList<>();
        public static List<UserPaid> userPaid = new ArrayList<>();
        public static List<FlatWisePayable> payables = new ArrayList<>();
        public static List<FlatWisePayable> pending = new ArrayList<>();
        public static List<ApartmentEarning> storeRent = new ArrayList<>();

    }

}
