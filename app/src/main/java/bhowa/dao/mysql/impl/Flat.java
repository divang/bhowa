package bhowa.dao.mysql.impl;

/**
 * Created by divang.sharma on 9/4/2016.
 */
public class Flat {
    //SELECT `Flat_Id,Flat_Number,Area,Maintenance_Amount,Block_Number,Status` FROM `sql6134070`.`Flat` LIMIT 0, 1000;

    public String flatId;
    public String flatNumber;
    public int area;
    public float maintenanceAmount;
    public String block;
    public boolean status;

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("flatId=").append(flatId);
        str.append(" flatNumber=").append(flatNumber);
        str.append(" area=").append(area);
        str.append(" maintenanceAmount=").append(maintenanceAmount);
        str.append(" block=").append(block);
        str.append(" status=").append(status);
        return str.toString();
    }


    /*
    CREATE TABLE `Flat` (
        `Flat_Id` VARCHAR(10) NOT NULL,
        `Flat_Number` VARCHAR(10) NOT NULL,
        `Area` INT(10) NOT NULL,
        `Maintenance_Amount` FLOAT NOT NULL,
        `Block_Number` VARCHAR(1) NOT NULL,
        `Status` TINYINT(1) NOT NULL DEFAULT '1',
        PRIMARY KEY (`Flat_Id`)
    )
    ENGINE=InnoDB
    ROW_FORMAT=DEFAULT
     */
}
