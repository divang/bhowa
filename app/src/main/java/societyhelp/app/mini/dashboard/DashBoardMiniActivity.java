package societyhelp.app.mini.dashboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import societyhelp.app.R;
import societyhelp.app.util.ListViewAdaptor;
import societyhelp.app.util.SocietyHelpConstant;

public abstract class DashBoardMiniActivity extends AppCompatActivity implements SocietyHelpConstant {

    private SharedPreferences prefs;
    protected ProgressDialog progress;

    public String getSocietyId() {
        String v = prefs.getString(CONST_SOCIETY_ID_KEY_PREF_MANAGER, "");
        Log.d("info", "society id from preference manager :" + v);
        return v;
    }

    public String getLoginId() {
        String v = prefs.getString(CONST_LOGIN_ID_KEY_PREF_MANAGER, "");
        Log.d("info", "login id from preference manager :" + v);
        return v;
    }

    public String getFlatId() {
        String v = prefs.getString(CONST_FLAT_ID_KEY_PREF_MANAGER, "");
        Log.d("info", "flat id from preference manager :" + v);
        return v;
    }

    public boolean isOwner() {
        Boolean v = prefs.getBoolean(CONST_OWNER_KEY_PREF_MANAGER, false);
        Log.d("info", "Is owner from preference manager :" + v);
        return v;
    }

    public String getAuthIds() {
        String v = prefs.getString(CONST_USER_AUTHS_PREF_MANAGER, "");
        Log.d("info", "flat id from preference manager :" + v);
        return v;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_dash_board);
        //setCustomToolBar();

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }


    public void setHeader(String title, boolean btnHomeVisible, boolean btnBackVisible) {
        View mActionBarView = getLayoutInflater().inflate(R.layout.custom_tool_bar, null);

        TextView txtActionBar = (TextView) mActionBarView.findViewById(R.id.action_bar_text);
        txtActionBar.setText(title);
        ImageButton btnHome = (ImageButton)mActionBarView.findViewById(R.id.action_bar_home);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        if (btnHomeVisible) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(false);
            btnHome.setVisibility(View.VISIBLE);
        } else if (btnBackVisible) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            btnHome.setImageResource(R.drawable.ic_back);
            btnHome.setVisibility(View.VISIBLE);
            btnHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } else {
            //actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
            btnHome.setImageResource(R.drawable.ic_logo);
            btnHome.setVisibility(View.VISIBLE);
        }

        actionBar.setCustomView(mActionBarView);
        actionBar.setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
    }

    public void btnHomeClick(View v) {
	    try {
		
		    String dashboradClass = prefs.getString(CONST_DASHBOARD_CLASS_PREF_MANAGER, "");
		    Intent intent = new Intent(getApplicationContext(), Class.forName(dashboradClass));
		    
		    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        startActivity(intent);
	
	        View mActionBarView = getLayoutInflater().inflate(R.layout.custom_tool_bar, null);
	
	        View btnHome = mActionBarView.findViewById(R.id.action_bar_home);
	        btnHome.setVisibility(View.INVISIBLE);
	    } catch (ClassNotFoundException e) {
		    Log.e("error","Error in home button click", e);
	    }
    }

    public TextView makeTableRowWithText(String text, int widthInPercentOfScreenWidth, int fixedHeightInPixels) {
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        TextView recyclableTextView = new TextView(this);
        recyclableTextView.setText(text);
        recyclableTextView.setTextColor(Color.BLACK);
        //recyclableTextView.setTextSize(10);
        recyclableTextView.setWidth(widthInPercentOfScreenWidth * screenWidth / 100);
        recyclableTextView.setHeight(fixedHeightInPixels);
        recyclableTextView.setGravity(Gravity.CENTER);
        return recyclableTextView;
    }

    public EditText makeTableRowWithEditText(int widthInPercentOfScreenWidth, int fixedHeightInPixels) {
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        EditText recyclableTextView = new EditText(this);
        recyclableTextView.setTextColor(Color.BLACK);
        //recyclableTextView.setTextSize(10);
        recyclableTextView.setWidth(widthInPercentOfScreenWidth * screenWidth / 100);
        recyclableTextView.setHeight(fixedHeightInPixels);
        recyclableTextView.setGravity(Gravity.CENTER);
        return recyclableTextView;
    }

    public Spinner makeTableRowWithSpinner(List<String> list, int widthInPercentOfScreenWidth, int fixedHeightInPixels) {
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        Spinner recyclableSpinnerView = new Spinner(this);
        try {
            recyclableSpinnerView.setAdapter(new ListViewAdaptor(getApplicationContext(), list));
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*recyclableTextView.setText(text);
        recyclableTextView.setTextColor(Color.BLACK);
        //recyclableTextView.setTextSize(10);
        recyclableTextView.setWidth(widthInPercentOfScreenWidth * screenWidth / 100);
        recyclableTextView.setHeight(fixedHeightInPixels);
        recyclableTextView.setGravity(Gravity.CENTER);*/
        return recyclableSpinnerView;
    }

    public TextView makeTableRowWithText(String text, int iGravity) {
        int fixedRowHeight = 70;
        int fixedHeaderHeight = 70;

        return makeTableRowWithText(text, fixedRowHeight, fixedHeaderHeight, iGravity);
    }

    public TextView makeTableRowWithText(String text, int widthInPercentOfScreenWidth, int fixedHeightInPixels, int iGravity) {
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        TextView recyclableTextView = new TextView(this);
        recyclableTextView.setText(text);
        recyclableTextView.setTextColor(ContextCompat.getColor(this, R.color.tableTextColor));
        // recyclableTextView.setTextSize(R.dimen.test_size_medium);
        recyclableTextView.setWidth(widthInPercentOfScreenWidth * screenWidth / 100);
        recyclableTextView.setHeight(fixedHeightInPixels);
        recyclableTextView.setGravity(iGravity);

        //recyclableTextView.setPadding(0,5,0,5);
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

    public TableRow getTableRow() {
        TableRow.LayoutParams wrapWrapTableRowParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TableRow row = new TableRow(this);
        row.setLayoutParams(wrapWrapTableRowParams);
        row.setGravity(Gravity.CENTER);
        row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableStaticHeaderColor));

        return row;
    }
}
