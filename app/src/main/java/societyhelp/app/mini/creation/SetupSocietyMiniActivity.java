package societyhelp.app.mini.creation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import societyhelp.app.R;
import societyhelp.app.mini.dashboard.DashBoardMiniActivity;
import societyhelp.app.mini.view.ViewAllFlatMiniActivity;
import societyhelp.app.mini.view.ViewAllLoginsMiniActivity;
import societyhelp.app.mini.view.ViewAllUsersMiniActivity;
import societyhelp.app.util.CustomSerializer;
import societyhelp.core.SocietyAuthorization;
import societyhelp.dao.SocietyHelpDatabaseFactory;
import societyhelp.dao.mysql.impl.Flat;
import societyhelp.dao.mysql.impl.Login;
import societyhelp.dao.mysql.impl.UserDetails;

public class SetupSocietyMiniActivity extends DashBoardMiniActivity {

    private ProgressDialog progress;
    final private int PICKFILE_RESULT_CODE = 1000;
    protected List<Integer> userAuthActivityIds = new ArrayList<>();
    protected boolean isAdmin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_admin_society_setup);
        setHeader(getString(R.string.title_activity_home), false, false);
        setAuthorizedActivity();
    }

    protected void setAuthorizedActivity()
    {
        String userAuths = getAuthIds();
        Log.d("info", " userAuths - " + userAuths);
        if(userAuths == null || userAuths.length() == 0 ) return;
        for(String auth : userAuths.split(","))
        {
            //SocietyAuthorization.Type authActivity = SocietyAuthorization.Type.values()[Integer.parseInt(auth)];
            switch (SocietyAuthorization.Type.valueOf(auth))
            {
                case ADMIN: //0
                    isAdmin = true;
                    return;
                case USER_DETAIL_VIEW: //3
                    userAuthActivityIds.add(R.id.home_activity_btn_manage_users);
                    break;
                case USER_DETAIL_CREATE: //4
                    userAuthActivityIds.add(R.id.home_activity_btn_add_user);
                    break;
                case FLAT_DETAIL_VIEW: //5
                    userAuthActivityIds.add(R.id.home_activity_btn_view_all_flat);
                    break;
                case FLAT_DETAIL_CREATE: //6
                    userAuthActivityIds.add(R.id.home_activity_btn_add_flat);
                    break;
                case LOGIN_VIEW: //7
                    userAuthActivityIds.add(R.id.home_activity_btn_view_all_login);
                    break;
                case LOGIN_CREATE: //8
                    userAuthActivityIds.add(R.id.home_activity_btn_create_login);
                    break;
             }
        }
    }

    public void setupSociety(View v)
    {
        try {
            Intent intent;
            switch (v.getId()) {

                case R.id.home_activity_btn_create_login:
                    if (isAdmin || userAuthActivityIds.contains(R.id.home_activity_btn_create_login)) {
                        progress = ProgressDialog.show(this, null, "Login creation activity ...", true, true);
                        progress.setCancelable(true);
                        progress.show();
                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    List<Login> logins = SocietyHelpDatabaseFactory.getMasterDBInstance().getAllLogins(getLoginId());
                                    Intent intentCreateLogin = new Intent(getApplicationContext(), CreateLoginMiniActivity.class);
                                    intentCreateLogin.putExtra(CONST_LOGIN_IDS, Login.getLogIds(logins));
                                    startActivity(intentCreateLogin);
                                } catch (Exception e) {
                                    Log.e("Error", "Fetching My dues verified data has problem", e);
                                }
                                progress.dismiss();
                                progress.cancel();
                            }
                        }).start();
                    } else {
                        Toast.makeText(this, "Permission denied to access this link (create login). Ask your Admin!", Toast.LENGTH_LONG).show();
                    }
                    break;

                case R.id.home_activity_btn_add_flat:
                    if (isAdmin || userAuthActivityIds.contains(R.id.home_activity_btn_add_flat)) {
                        intent = new Intent(this, CreateFlatMiniActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "Permission denied to access this link (add flat). Ask your Admin!", Toast.LENGTH_LONG).show();
                    }
                    break;

                case R.id.home_activity_btn_add_user:
                    if (isAdmin || userAuthActivityIds.contains(R.id.home_activity_btn_add_user)) {
                        progress = ProgressDialog.show(this, null, "Going to add User activity ...", true, true);
                        progress.setCancelable(true);
                        progress.show();
                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    Intent innerIntent = new Intent(getApplicationContext(), CreateUserMiniActivity.class);
                                    innerIntent.putExtra(CONST_LOGIN_IDS, getLoginIds());
                                    innerIntent.putExtra(CONST_USER_IDS, getUserIds());
                                    innerIntent.putExtra(CONST_FLAT_IDS, getFlatIds());
                                    startActivity(innerIntent);

                                } catch (Exception e) {
                                    Log.e("Error", "Fetching My dues verified data has problem", e);
                                }
                                progress.dismiss();
                                progress.cancel();
                            }
                        }).start();


                    } else {
                        Toast.makeText(this, "Permission denied to access this link (add user). Ask your Admin!", Toast.LENGTH_LONG).show();
                    }
                    break;

                case R.id.home_activity_btn_view_all_login:
                    if (isAdmin || userAuthActivityIds.contains(R.id.home_activity_btn_view_all_login)) {
                        progress = ProgressDialog.show(this, null, "Fetching all login ...", true, true);
                        progress.setCancelable(true);
                        progress.show();
                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    List<Login> login = SocietyHelpDatabaseFactory.getMasterDBInstance().getAllLogins(getLoginId());
                                    Intent innerIntent = new Intent(getApplicationContext(), ViewAllLoginsMiniActivity.class);

                                    byte[] sObj = CustomSerializer.serializeObject(login);
                                    innerIntent.putExtra(CONST_ALL_LOGINS, sObj);

                                    startActivity(innerIntent);

                                } catch (Exception e) {
                                    Log.e("Error", "Fetching all login data has problem", e);
                                }
                                progress.dismiss();
                                progress.cancel();
                            }
                        }).start();
                    } else {
                        Toast.makeText(this, "Permission denied to access this link (view all login). Ask your Admin!", Toast.LENGTH_LONG).show();
                    }
                    break;

                case R.id.home_activity_btn_view_all_flat:
                    if (isAdmin || userAuthActivityIds.contains(R.id.home_activity_btn_view_all_flat)) {
                        progress = ProgressDialog.show(this, null, "Fetching all Flats' details ...", true, true);
                        progress.setCancelable(true);
                        progress.show();
                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    List<Flat> flats = SocietyHelpDatabaseFactory.getDBInstance().getAllFlats(getSocietyId());
                                    Intent innerIntent = new Intent(getApplicationContext(), ViewAllFlatMiniActivity.class);
                                    byte[] sObj = CustomSerializer.serializeObject(flats);
                                    innerIntent.putExtra(CONST_ALL_FLATS, sObj);
                                    startActivity(innerIntent);

                                } catch (Exception e) {
                                    Log.e("Error", "Fetching all flat data has problem", e);
                                }
                                progress.dismiss();
                                progress.cancel();
                            }
                        }).start();
                    } else {
                        Toast.makeText(this, "Permission denied to access this link (view all flat). Ask your Admin!", Toast.LENGTH_LONG).show();
                    }
                    break;

                case R.id.home_activity_btn_manage_users:
                    if (isAdmin || userAuthActivityIds.contains(R.id.home_activity_btn_manage_users)) {
                        progress = ProgressDialog.show(this, null, "Fetching all Users' details ...", true, true);
                        progress.setCancelable(true);
                        progress.show();
                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    List<UserDetails> users = SocietyHelpDatabaseFactory.getDBInstance().getAllUsers(getSocietyId());
                                    Intent innerIntent = new Intent(getApplicationContext(), ViewAllUsersMiniActivity.class);
                                    byte[] sObj = CustomSerializer.serializeObject(users);
                                    innerIntent.putExtra(CONST_ALL_USERS, sObj);
                                    startActivity(innerIntent);

                                } catch (Exception e) {
                                    Log.e("Error", "Fetching all user data has problem", e);
                                }
                                progress.dismiss();
                                progress.cancel();
                            }
                        }).start();
                    } else {
                        Toast.makeText(this, "Permission denied to access this link (manage users). Ask your Admin!", Toast.LENGTH_LONG).show();
                    }
                    break;

            }
        }catch (Exception e)
        {
            Log.e("Error", "Something goes wrong", e);
        }
    }

  protected String getFlatIds() throws Exception
    {
        StringBuilder listFlatIds = new StringBuilder();
        listFlatIds.append("Select Flat Id").append(",");
        List<Flat> flats = SocietyHelpDatabaseFactory.getDBInstance().getAllFlats(getSocietyId());
        for(Flat f : flats)
        {
            listFlatIds.append(f.flatId).append(",");
        }
        return  listFlatIds.toString();
    }

    protected String getLoginIds() throws Exception
    {
        StringBuilder listIds = new StringBuilder();
        listIds.append("Select Login Id").append(",");
        List<Login> login = SocietyHelpDatabaseFactory.getMasterDBInstance().getAllLogins(getLoginId());
        List<Login> assignedLogin = SocietyHelpDatabaseFactory.getDBInstance().getAllAssignedLogins();

        boolean found;
        for(Login l : login)
        {
            found = false;
            for(Login assignedL : assignedLogin){
                if(assignedL.loginId.contentEquals(l.loginId))
                {
                    found = true;
                    break;
                }
            }
            if(!found) listIds.append(l.loginId).append(",");
        }
        return  listIds.toString();
    }

    protected String getUserIds() throws Exception
    {
        StringBuilder listIds = new StringBuilder();
        listIds.append("Select Login Id").append(",");
        List<UserDetails> users = SocietyHelpDatabaseFactory.getDBInstance().getAllUsers(getSocietyId());

        for(UserDetails u : users)
        {
            listIds.append(u.userId).append(",");
        }
        return  listIds.toString();
    }

}
