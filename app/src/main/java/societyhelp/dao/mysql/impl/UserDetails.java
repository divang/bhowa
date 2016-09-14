package societyhelp.dao.mysql.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import societyhelp.core.SocietyAuthorization;

public class UserDetails {

	public String userId;
	public String password;
	public String userType;
	public String userName;
	public String loginId;
	public boolean isActive;
	public String flatId;
	public String nameAlias;
	public String emailId;
	public String address;
	public long  mobileNo;
	public long  mobileNoAlternative;
	public Date flatJoinDate;
	public Date flatLeftDate;
	public List<SocietyAuthorization.Type> sAuthorizations = new ArrayList<>();

	public String getAuthorizationList()
	{
		StringBuilder str = new StringBuilder();
		for(SocietyAuthorization.Type a : sAuthorizations) str.append(a).append(",");
		return  str.toString();
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("userId=").append(userId);
		str.append(" userType=").append(userType);
		str.append(" userName").append(userName);
		str.append(" loginId=").append(loginId);
		str.append(" isActive=").append(isActive);
		str.append(" flatId=").append(flatId);
		str.append(" nameAlias=").append(nameAlias);
		str.append(" emailId=").append(emailId);
		str.append(" address=").append(address);
		str.append(" mobileNo=").append(mobileNo);
		str.append(" mobileNoAlternative=").append(mobileNoAlternative);
		str.append(" flatJoinDate=").append(flatJoinDate);
		str.append(" flatLeftDate=").append(flatLeftDate);
		str.append(" sAuthorizations=").append(sAuthorizations);
		return str.toString();
	}
}
