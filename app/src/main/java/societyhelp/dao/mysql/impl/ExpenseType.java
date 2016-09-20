package societyhelp.dao.mysql.impl;

/**
 * Created by divang.sharma on 9/20/2016.
 */
public class ExpenseType {

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

    public enum ExpenseTypeConst {
        Intial_Payable,
        Monthly_Maintenance
    }
}
