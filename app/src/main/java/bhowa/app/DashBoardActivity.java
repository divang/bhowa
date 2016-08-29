package bhowa.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public abstract class DashBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
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
