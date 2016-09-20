package societyhelp.dao.mysql.impl;

/**
 * Created by divang.sharma on 9/11/2016.
 */
public class FlatWisePayable {

    public int month;
    public int year;
    public float amount;
    public String comments;
    public ExpenseType.ExpenseTypeConst expenseType;

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("month=").append(month);
        str.append(" year=").append(year);
        str.append(" amount=").append(amount);
        str.append(" comments=").append(comments);
        str.append(" expenseType=").append(expenseType);
        return str.toString();
    }


}
