package bhowa.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import bhowa.app.util.BhowaConstant;

public abstract class DashBoardActivity extends AppCompatActivity implements BhowaConstant{

    private SharedPreferences prefs;

    public String getLoginId() {
        String v = prefs.getString(CONST_LOGIN_ID_KEY_PREF_MANAGER, "");
        Log.d("info","login id from preference manager :" + v);
        return v;
    }

    public String getFlatId() {
        String v = prefs.getString(CONST_FLAT_ID_KEY_PREF_MANAGER, "");
        Log.d("info","flat id from preference manager :" + v);
        return v;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    public void setHeader(String title, boolean btnHomeVisible)
    {
        ViewStub stub = (ViewStub) findViewById(R.id.vsHeader);
        View infalted = stub.inflate();

        Button btnHome = (Button) infalted.findViewById(R.id.btnHome);
        if(!btnHomeVisible) btnHome.setVisibility(View.INVISIBLE);
    }

    public void btnHomeClick(View v)
    {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
