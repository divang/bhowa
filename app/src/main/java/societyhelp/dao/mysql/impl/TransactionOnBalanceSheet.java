package societyhelp.dao.mysql.impl;

import java.io.Serializable;

/**
 * Created by divang.sharma on 12/15/2016.
 */
public class TransactionOnBalanceSheet implements Serializable
{
    public int balanceSheetTransactionID;
    public float amount;
    public boolean isVerifiedByAdmin;
    public boolean isVerifiedByUser;
    public ExpenseType.ExpenseTypeConst expenseType;
    public int transactionFromBankStatementID;
    public int userCashPaymentID;
    public int transactionExpenseId;
    public String transactionFlow;
    public int flatWisePayableID;
    public DBAction action = DBAction.INSERT;

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
