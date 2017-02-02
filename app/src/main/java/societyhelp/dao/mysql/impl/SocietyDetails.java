package societyhelp.dao.mysql.impl;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by divang.sharma on 2/2/2017.
 */
public class SocietyDetails implements Serializable{

    public int societyId;
    public String societyName;
    public String databaseURL;
    public String databaseUser;
    public String databasePassword;
    public String city;
    public String address;
    public String country;
    public String mobileNo;
    public String emailId;
    public Timestamp createdDate;
    public Timestamp serviceStartDate;
    public Timestamp serviceRenewalDate;
    public float chargePerFlat;

}
