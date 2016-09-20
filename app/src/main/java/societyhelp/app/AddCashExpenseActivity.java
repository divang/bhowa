package societyhelp.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import societyhelp.app.util.DatePickerDialogTheme;
import societyhelp.app.util.ListViewAdaptor;
import societyhelp.app.util.Util;
import societyhelp.dao.SocietyHelpDatabaseFactory;
import societyhelp.dao.mysql.impl.UserCashPaid;

public class AddCashExpenseActivity extends DashBoardActivity {

    protected EditText amountText;
    protected EditText commentText;
    protected Spinner expenseType;
    protected EditText dateText;
    ProgressDialog  progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cash_expense);
        setHeader("", true, false);

        amountText = (EditText) findViewById(R.id.amount_ACE);
        dateText = (EditText) findViewById(R.id.expend_DateText_AUA);
        commentText = (EditText) findViewById(R.id.user_comment_ACE);
        expenseType = (Spinner) findViewById(R.id.expense_type_AUA);
        final Button submit = (Button) findViewById(R.id.addMyPaidBtn_AUA);

        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialogTheme() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day){
                        dateText.setText(day + ":" + (month + 1) + ":" + year);
                    }
                }.show(getFragmentManager(), "Calendar Theme");
            }
        });

        List<String> expenseTypes;
        try {
            expenseTypes = getExpenseTypes();
            expenseType.setAdapter(new ListViewAdaptor(getApplicationContext(), expenseTypes));
        } catch (Exception e) {
            e.printStackTrace();
        }

        progress = ProgressDialog.show(AddCashExpenseActivity.this, null, "Adding Payment details in Database  ...", true, false);
        progress.setCancelable(true);
        progress.hide();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validationFailed()) return;

                final UserCashPaid payment = new UserCashPaid();
                payment.userId = getLoginId();
                payment.flatId = getFlatId();
                payment.amount = Float.valueOf(amountText.getText().toString());
                payment.userComment = commentText.getText().toString();
                payment.expenseType = expenseType.getSelectedItem().toString();
                java.util.Date eDate;
                try {
                    eDate = Util.dateFormat.parse(dateText.getText().toString());
                    payment.expendDate = new Date(eDate.getTime());

                    progress.show();

                    Thread taskThread = new Thread(new Runnable() {
                        public void run() {
                            try {

                                SocietyHelpDatabaseFactory.getDBInstance().addUserCashPayment(payment);

                                float myDues = SocietyHelpDatabaseFactory.getDBInstance().getMyDue(getFlatId());
                                Intent intentMyDues = new Intent(getApplicationContext(), MyDuesActivity.class);
                                intentMyDues.putExtra("MyDueAmount", myDues);
                                startActivity(intentMyDues);

                            } catch (Exception e) {
                                Log.e("Error", "Login creation failed", e);
                            }
                            progress.dismiss();
                            progress.cancel();
                        }
                    });
                    taskThread.start();

                } catch (Exception e) {
                    Log.d("error", "Expend date has problem", e);
                }

            }
        });
   }

    protected  List<String> getExpenseTypes() throws Exception
    {
        String strList = (String) getIntent().getSerializableExtra(CONST_EXPENSE_TYPES);
        List<String> listTypes = new ArrayList<>();
        for(String flatId : strList.split(","))
        {
            listTypes.add(flatId);
        }
        return  listTypes;
    }


    private boolean validationFailed() {
        boolean validationFailed = false;

        if (amountText.getText().toString().length() == 0) {
            Util.CustomToast(getApplicationContext(), "Amount should not be empty", 1000);
            validationFailed = true;
            return validationFailed;
        } else if (amountText.getText().toString().length() > 0 ) {
            try {
                float v = Float.valueOf(amountText.getText().toString());
                if( v <= 0){
                    Util.CustomToast(getApplicationContext(), "Amount should be greater then zero.", 1000);
                    validationFailed = true;
                }
            }catch (Exception e) {
                Util.CustomToast(getApplicationContext(), "Amount should be proper value.", 1000);
                validationFailed = true;
            }
        } else if (expenseType.getSelectedItemId() == 0) {
            Util.CustomToast(getApplicationContext(), "Expense Type must select.", 1000);
            validationFailed = true;
        } else if (dateText.getText().toString().trim().length() > 0) {

            java.util.Date joinDate;
            try {
                joinDate = Util.dateFormat.parse(dateText.getText().toString());
                if(joinDate.compareTo(new java.util.Date(System.currentTimeMillis())) > 0) {
                    Util.CustomToast(getApplicationContext(), "Expend date should lesser then the current date.", 1000);
                    validationFailed = true;
                }
            } catch (ParseException e) {
                Util.CustomToast(getApplicationContext(), "Expend join date is in incorrect format.", 1000);
                validationFailed = true;
            }
        }
        return validationFailed;
    }

}
