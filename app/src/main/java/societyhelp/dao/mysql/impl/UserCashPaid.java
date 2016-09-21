package societyhelp.dao.mysql.impl;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by divang.sharma on 9/21/2016.
 */
public class UserCashPaid implements Serializable {
    
    public int paymentId;
    public float amount;
    public String userComment;
    public Date expendDate;
    public String expenseType;
    public String userId;
    public String flatId;
    public String adminComment;
    public boolean isVerified;
    public String verifiedBy;

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("paymentId=").append(paymentId);
        str.append(" amount=").append(amount);
        str.append(" userComment=").append(userComment);
        str.append(" expendDate=").append(expendDate);
        str.append(" expenseType=").append(expenseType);
        str.append(" userId=").append(userId);
        str.append(" adminComment=").append(adminComment);
        str.append(" flatId=").append(flatId);
        str.append(" isVerified=").append(isVerified);
        str.append(" verifiedBy=").append(verifiedBy);
        return str.toString();
    }
}
