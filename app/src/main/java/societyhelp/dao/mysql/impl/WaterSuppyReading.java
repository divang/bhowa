package societyhelp.dao.mysql.impl;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by divang.sharma on 2/13/2017.
 */
public class WaterSuppyReading implements Serializable {

    public int supplierId;
    public String supplierName;
    public int capacityInLiter;
    public Timestamp SupplyTime;
    public int readingBeforeSupply;
    public int readingAfterSupply;

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("supplierId=").append(supplierId);
        str.append(" supplierName=").append(supplierName);
        str.append(" capacityInLiter=").append(capacityInLiter);
        str.append(" SupplyTime=").append(SupplyTime);
        str.append(" readingBeforeSupply=").append(readingBeforeSupply);
        str.append(" readingAfterSupply=").append(readingAfterSupply);
        return str.toString();
    }
}
