package societyhelp.dao.mysql.impl;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by divang.sharma on 9/21/2016.
 */
public class UserPaid implements Serializable {

    public int paymentId;
    public String userId;
    public String flatId;
    public float amount;
    public float amountInitial;
    public java.sql.Date expendDate;
    public ExpenseType.ExpenseTypeConst expenseType;
    public String userComment;
    public String adminComment;
    public boolean isVerified;
    public String verifiedBy;
    public boolean isUserVerified;


    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("paymentId=").append(paymentId);
        str.append(" amount=").append(amount);
        str.append(" amountInitial=").append(amountInitial);
        str.append(" userComment=").append(userComment);
        str.append(" expendDate=").append(expendDate);
        str.append(" expenseType=").append(expenseType);
        str.append(" userId=").append(userId);
        str.append(" adminComment=").append(adminComment);
        str.append(" flatId=").append(flatId);
        str.append(" isVerified=").append(isVerified);
        str.append(" verifiedBy=").append(verifiedBy);
        str.append(" isUserVerified=").append(isUserVerified);
        return str.toString();
    }
}
