package societyhelp.dao.mysql.impl;

import java.io.Serializable;

/**
 * Created by divang.sharma on 9/20/2016.
 */
public class ExpenseType implements Serializable{

    public int expenseTypeId;
    public String type;
    public int transactionPriority;

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("expenseTypeId=").append(expenseTypeId);
        str.append(" type=").append(type);
        str.append(" transactionPriority=").append(transactionPriority);
        return str.toString();
    }

    public enum ExpenseTypeConst implements Serializable {
        Advance_Payment
        ,Monthly_Maintenance
        ,Initial_Payable
        ,Khata_Payable
        ,Generator_Repair_Servicing
        ,Lift_AMC
        ,Lift_Repair
        ,Plumbing
        ,Electrical_Repair
        ,Tank_Cleaning_Repairing
        ,Lawyer
        ,Miscellaneous
        ,Intercom_AMC
        ,Intercom_Purchase_Repair
        ,Fire_Extinguisher
        ,Security_Related
        ,Apartment_InfraStructure_Repair
        ,Children_Park
        ,Septick_Tank_Pipe_Cleaning
        ,Club_House
        ,Alamari_Purchase
        ,Security_Guards
        ,House_Keeping_Labour
        ,Common_Electricity
        ,Water_Tankers
        ,Gardening
        ,Deisel_For_Generator
        ,House_Keeping_Consumables
        ,Pest_Control
        ,Generator_AMC
        ,Flat_Old_Dues
        ,Waste_Disposal
        ,Gym
        ,MotorRepair
        ,Penalty
        ,Club_Store_Earning
        ,Interest_Income
        ,UNKNOWN
    }

    public enum PaymentStatusConst  implements Serializable {
        Unknown,
        Not_Paid,
        Full_Paid,
        Partial_Paid,
        Initial_Load_Data,
        Initial_Load_Advance_Paid
    }
}
