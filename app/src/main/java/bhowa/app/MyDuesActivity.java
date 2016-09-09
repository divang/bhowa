package bhowa.app;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import bhowa.dao.BhowaDatabaseFactory;
import bhowa.dao.mysql.impl.BankStatement;

public class MyDuesActivity extends DashBoardActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dues);

        try {
            final TextView duesText = (TextView) findViewById(R.id.myDuesText);
            float myDues = (float) getIntent().getSerializableExtra("MyDueAmount");
            SpannableString spanString = new SpannableString("My dues : " + String.valueOf(myDues));
            spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
            spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanString.length(), 0);
            spanString.setSpan(new ForegroundColorSpan(Color.RED), 0, spanString.length(), 0);
            duesText.setText(spanString);

        } catch (Exception e) {
            e.printStackTrace();
        }

        final Button backButton = (Button) findViewById(R.id.backToReportBtn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
