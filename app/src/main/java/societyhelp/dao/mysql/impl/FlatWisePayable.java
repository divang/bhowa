package societyhelp.dao.mysql.impl;

import java.util.Comparator;

/**
 * Created by divang.sharma on 9/11/2016.
 */
public class FlatWisePayable implements Comparable<FlatWisePayable>{

    public int paymentId;
    public String flatId;
    public boolean status;
    public int month;
    public int year;
    public float amount;
    public String comments;
    public int expenseTypeId;
    public int payablePriority;
    public ExpenseType.ExpenseTypeConst expenseType;
    public ExpenseType.PaymentStatusConst paymentStatus;
    public String paymentIds;

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("paymentId=").append(paymentId);
        str.append(" status=").append(status);
        str.append(" month=").append(month);
        str.append(" year=").append(year);
        str.append(" amount=").append(amount);
        str.append(" comments=").append(comments);
        str.append(" expenseType=").append(expenseType);
        str.append(" expenseTypeId=").append(expenseTypeId);
        str.append(" payablePriority=").append(payablePriority);
        str.append(" paymentIds=").append(paymentIds);
        return str.toString();
    }

    @Override
    public int compareTo(FlatWisePayable another) {

        if(this.payablePriority < another.payablePriority)
        {
            return 1;
        }
        else return -1;
    }
}
