package societyhelp.dao.mysql.impl;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by divang.sharma on 12/15/2016.
 */
public class TransactionOnBalanceSheet implements Serializable
{
    public int balanceSheetTransactionID;
    public String userId;
    public String userName;
    public String flatId;
    public String flatNo;
    public String block;
    public int area;
    public float amount;
    public float amountInitial;
    public boolean isVerifiedByAdmin;
    public boolean isVerifiedByUser;
    public ExpenseType.ExpenseTypeConst expenseType;
    public int transactionFromBankStatementID;
    public int userCashPaymentID;
    public int transactionExpenseId;
    public int apartmentEarningID;
    public String transactionFlow;
    public int flatWisePayableID;
    public Date transactionDate;
    public DBAction action = DBAction.INSERT;

    public static final int initDataLoadFlatWisePayableId = -2;

    public enum DBAction {
        INSERT,
        UPDATE,
        DELETE
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("balanceSheetTransactionID=").append(balanceSheetTransactionID);
        str.append(", amount=").append(amount);
        str.append(", amountInitial=").append(amountInitial);
        str.append(", isVerifiedByAdmin=").append(isVerifiedByAdmin);
        str.append(", isVerifiedByUser=").append(isVerifiedByUser);
        str.append(", expenseType=").append(expenseType);
        str.append(", transactionFromBankStatementID=").append(transactionFromBankStatementID);
        str.append(", userCashPaymentID=").append(userCashPaymentID);
        str.append(", transactionExpenseId=").append(transactionExpenseId);
        str.append(", transactionFlow=").append(transactionFlow);
        str.append(", flatWisePayableID=").append(flatWisePayableID);
        str.append(", action=").append(action);
        return str.toString();
    }

}
