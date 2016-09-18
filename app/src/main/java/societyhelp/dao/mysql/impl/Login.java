package societyhelp.dao.mysql.impl;

import java.util.List;

/**
 * Created by divang.sharma on 9/4/2016.
 */
public class Login {

    public String loginId;
    public String password;
    public boolean status;
    public String authorisedActivity;

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("loginId=").append(loginId);
        str.append(" password=").append(password);
        str.append(" status=").append(status);
        str.append(" authorisedActivity=").append(authorisedActivity);
        return str.toString();
    }

    public static String getLogIds(List<Login> list)
    {
        StringBuilder str = new StringBuilder();
        for(Login l : list)
        {
            str.append(l.loginId).append(",");
        }

        return str.toString();
    }
    /*
    CREATE TABLE `Login` (
	`Login_Id` VARCHAR(20) NOT NULL,
	`Password` VARCHAR(20) NOT NULL,
	`Status` TINYINT(1) NOT NULL DEFAULT '1',
	`Authorised_Activity` VARCHAR(100) NULL DEFAULT NULL,
	PRIMARY KEY (`Login_Id`)
    )
    ENGINE=InnoDB
    ROW_FORMAT=DEFAULT
     */
}
