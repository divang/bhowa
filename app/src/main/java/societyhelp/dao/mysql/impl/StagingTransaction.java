package societyhelp.dao.mysql.impl;

/**
 * PDF transaction data which is uploaded in staging table of database.
 * Created by divang.sharma on 12/10/2016.
 */
public class StagingTransaction extends  SocietyHelpTransaction {

    public java.sql.Timestamp updloadedDate;
    public String uploadedBy ="";

}
