package societyhelp.dao.mysql.impl;

import java.io.Serializable;
import java.util.List;

/**
 * Created by divang.sharma on 9/4/2016.
 */
public class Login implements Serializable {

    public String loginId;
    public String password;
    public boolean status;
    public String societyId;
    public String societyName;
    public boolean isAdmin;

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("loginId=").append(loginId);
        str.append(" password=").append(password);
        str.append(" status=").append(status);
        str.append(" societyId=").append(societyId);
        str.append(" societyName=").append(societyName);
        str.append(" isAdmin=").append(isAdmin);
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
}
