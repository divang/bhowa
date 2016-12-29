package societyhelp.dao.mysql.impl;

import java.io.Serializable;

/**
 * Created by divang.sharma on 12/30/2016.
 */
public class ApartmentEarning implements Serializable{

    public int apartmentCashExpenseId;
    public ExpenseType.ExpenseTypeConst expenseType;
    public float amount;
    public float amountInitial;
    public java.sql.Date expendDate;
    public String expendByUserId;
    public boolean isVerified;
    public String verifiedBy;
    public String expendyComment;
    public String adminComment;
    public boolean splitted;

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("apartmentCashExpenseId=").append(apartmentCashExpenseId);
        str.append(" expenseType=").append(expenseType);
        str.append(" amount=").append(amount);
        str.append(" amountInitial=").append(amountInitial);
        str.append(" expendDate=").append(expendDate);
        str.append(" expendByUserId=").append(expendByUserId);
        str.append(" isVerified=").append(isVerified);
        str.append(" verifiedBy=").append(verifiedBy);
        str.append(" expendyComment=").append(expendyComment);
        str.append(" adminComment=").append(adminComment);
        str.append(" splitted=").append(splitted);

        return str.toString();
    }

}
