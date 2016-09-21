package societyhelp.app;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import societyhelp.app.util.SocietyHelpConstant;

public abstract class DashBoardActivity extends AppCompatActivity implements SocietyHelpConstant {

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


    public String getAuthIds() {
        String v = prefs.getString(CONST_USER_AUTHS_PREF_MANAGER, "");
        Log.d("info","flat id from preference manager :" + v);
        return v;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        setCustomToolBar();

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }


    public void setHeader(String title, boolean btnHomeVisible, boolean btnBackVisible)
    {
        View mActionBarView = getLayoutInflater().inflate(R.layout.custom_tool_bar, null);

        TextView txtActionBar = (TextView)mActionBarView.findViewById(R.id.action_bar_text);
        txtActionBar.setText(title);
        View btnHome = mActionBarView.findViewById(R.id.action_bar_home);

        if(btnHomeVisible) {
            setCustomToolBarHome();
        }
        else if(btnBackVisible) {
            btnHome.setVisibility(View.INVISIBLE);
        }
        else
        {
            btnHome.setVisibility(View.INVISIBLE);
        }
    }

    public void btnHomeClick(View v)
    {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

        View mActionBarView = getLayoutInflater().inflate(R.layout.custom_tool_bar, null);

        View btnHome = mActionBarView.findViewById(R.id.action_bar_home);
        btnHome.setVisibility(View.INVISIBLE);
    }

    protected void setCustomToolBar()
    {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        //displaying custom ActionBar
        View mActionBarView = getLayoutInflater().inflate(R.layout.custom_tool_bar, null);
        actionBar.setCustomView(mActionBarView);
        actionBar.setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
    }

    protected void setCustomToolBarBack()
    {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        View mActionBarView = getLayoutInflater().inflate(R.layout.custom_tool_bar, null);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setCustomView(mActionBarView);

        View btnHome = mActionBarView.findViewById(R.id.action_bar_home);
        btnHome.setVisibility(View.GONE);
    }

    protected void setCustomToolBarHome()
    {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        View mActionBarView = getLayoutInflater().inflate(R.layout.custom_tool_bar, null);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setCustomView(mActionBarView);

        View btnHome = mActionBarView.findViewById(R.id.action_bar_home);
        btnHome.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Used only for setDisplayHomeAsUpEnabled(true) case
        finish();
        return super.onOptionsItemSelected(item);
    }

    public TextView makeTableRowWithText(String text, int widthInPercentOfScreenWidth, int fixedHeightInPixels) {
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        TextView recyclableTextView = new TextView(this);
        recyclableTextView.setText(text);
        //recyclableTextView.setTextColor(Color.BLACK);
        //recyclableTextView.setTextSize(10);
        recyclableTextView.setWidth(widthInPercentOfScreenWidth * screenWidth / 100);
        recyclableTextView.setHeight(fixedHeightInPixels);
        recyclableTextView.setGravity(Gravity.CENTER);
        return recyclableTextView;
    }

    public CheckBox makeTableRowWithCheckBox(String id, int widthInPercentOfScreenWidth, int fixedHeightInPixels) {
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        CheckBox recyclableCheckBox = new CheckBox(this);
        recyclableCheckBox.setTag(id);
        //recyclableCheckBox.setBackgroundColor(Color.WHITE);
        recyclableCheckBox.setHighlightColor(Color.BLUE);
        recyclableCheckBox.setWidth(widthInPercentOfScreenWidth * screenWidth / 100);
        recyclableCheckBox.setHeight(fixedHeightInPixels);
        recyclableCheckBox.setGravity(Gravity.CENTER);

        return recyclableCheckBox;
    }

}
