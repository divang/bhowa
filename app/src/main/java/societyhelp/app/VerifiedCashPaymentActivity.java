package societyhelp.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import societyhelp.app.util.CustomSerializer;
import societyhelp.app.util.Util;
import societyhelp.dao.SocietyHelpDatabaseFactory;
import societyhelp.dao.mysql.impl.UserPaid;

public class VerifiedCashPaymentActivity extends DashBoardActivity {

    private ProgressDialog progress;
    private List<Integer> verifiedPaymentIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verified_cash_payment);
        setHeader(getString(R.string.title_activity_verify_user_cash_deposit), false, true);


        List<UserPaid> userPaids = null;
        try {
            byte[] sObjet = (byte[]) getIntent().getSerializableExtra(CONST_UN_VERIFIED_PAYMENT);
            userPaids = (List<UserPaid>) CustomSerializer.deserializeObject(sObjet);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            TableRow.LayoutParams wrapWrapTableRowParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //User_ID,Payment_ID,Flat_ID,Amount,Paid_Date,Type,User_Comment,Admin_Comment
            int[] fixedColumnWidths = new int[]{20, 10, 5, 20, 20, 20, 30, 40, 40};
            int[] scrollableColumnWidths = new int[]{20, 10, 5, 20, 20, 20, 30, 40, 40};

            int fixedRowHeight = 50;
            int fixedHeaderHeight = 60;

            TableLayout fixedColumn = (TableLayout) findViewById(R.id.fixed_column);
            TextView fixedViewUserIdH = makeTableRowWithText("User ID", scrollableColumnWidths[0], fixedHeaderHeight);
            fixedViewUserIdH.setBackgroundColor(ContextCompat.getColor(this, R.color.tableStaticHeaderColor));
            fixedViewUserIdH.setLayoutParams(wrapWrapTableRowParams);
            fixedColumn.addView(fixedViewUserIdH);
            fixedColumn.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow1Color));

            TableLayout fixedVerificationColumn = (TableLayout) findViewById(R.id.fixed_verification_column);
            TextView fixedVerificationH = makeTableRowWithText("Verified", scrollableColumnWidths[1], fixedHeaderHeight);
            fixedVerificationH.setBackgroundColor(ContextCompat.getColor(this, R.color.tableStaticHeaderColor));
            fixedVerificationH.setLayoutParams(wrapWrapTableRowParams);
            fixedVerificationColumn.addView(fixedVerificationH);
            fixedVerificationColumn.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow1Color));

            //rest of the table (within a scroll view)
            TableLayout scrollablePart = (TableLayout) findViewById(R.id.reportTableLayout);
            TableRow row = new TableRow(this);
            //header (fixed vertically)
            row.setLayoutParams(wrapWrapTableRowParams);
            row.setGravity(Gravity.CENTER);
            row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableStaticHeaderColor));
            row.addView(makeTableRowWithText("Payment ID", fixedColumnWidths[2], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Flat ID", fixedColumnWidths[3], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Amount", fixedColumnWidths[4], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Paid Date", fixedColumnWidths[5], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Expense Type", fixedColumnWidths[6], fixedHeaderHeight));
            row.addView(makeTableRowWithText("User Comment", fixedColumnWidths[7], fixedHeaderHeight));
            row.addView(makeTableRowWithText("Admin Comment", fixedColumnWidths[8], fixedHeaderHeight));
            scrollablePart.addView(row);

            boolean rowColorFlip = true;

            for (UserPaid paid : userPaids) {

                rowColorFlip = !rowColorFlip;
                //Fixed Columns
                TextView fixedViewUserId = makeTableRowWithText(paid.userId, scrollableColumnWidths[0], fixedRowHeight);
                //fixedViewUserId.setBackgroundColor(Color.BLUE);
                fixedColumn.addView(fixedViewUserId);
                //Fixed Columns

                //Scrollable columns
                row = new TableRow(this);
                row.setLayoutParams(wrapWrapTableRowParams);
                row.setGravity(Gravity.CENTER);
                if(rowColorFlip) row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow1Color));
                else row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableRow2Color));
                //User_ID,Payment_ID,Flat_ID,Amount,Paid_Date,Type,User_Comment,Admin_Comment

                CheckBox verified = makeTableRowWithCheckBox(String.valueOf(paid.paymentId), scrollableColumnWidths[1], fixedRowHeight);
                verified.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("info","msg....... payment id-" + v.getTag());
                        if(verifiedPaymentIds.contains(Integer.valueOf((String) v.getTag()))) {
                            Log.d("info","deselect ....... payment id-" + v.getTag());
                            verifiedPaymentIds.remove(Integer.valueOf((String) v.getTag()));
                        }
                        else {
                            Log.d("info","Select ....... payment id-" + v.getTag());
                            verifiedPaymentIds.add(Integer.valueOf((String) v.getTag()));
                        }
                    }
                });
                fixedVerificationColumn.addView(verified);

                row.addView(makeTableRowWithText(String.valueOf(paid.paymentId), scrollableColumnWidths[2], fixedRowHeight));
                row.addView(makeTableRowWithText(String.valueOf(paid.flatId), scrollableColumnWidths[3], fixedRowHeight));
                row.addView(makeTableRowWithText(String.valueOf(paid.amount), scrollableColumnWidths[4], fixedRowHeight));
                row.addView(makeTableRowWithText(String.valueOf(paid.expendDate), scrollableColumnWidths[5], fixedRowHeight));
                row.addView(makeTableRowWithText(String.valueOf(paid.expenseType), scrollableColumnWidths[6], fixedRowHeight));
                row.addView(makeTableRowWithText(String.valueOf(paid.userComment), scrollableColumnWidths[7], fixedRowHeight));
                row.addView(makeTableRowWithText(String.valueOf(paid.adminComment), scrollableColumnWidths[8], fixedRowHeight));

                scrollablePart.addView(row);
            }

            Button saveVerifiedBtn = (Button) findViewById(R.id.save_verified_payment);
            progress = ProgressDialog.show(this, null, "Saving verified cash payments...", true, true);
            progress.setCancelable(true);
            progress.hide();

            saveVerifiedBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String paymentIds = getStrPaymentIds();
                    if(paymentIds.length() == 0) {
                        Util.CustomToast(getApplicationContext(), "You have not verified any payment.", 1000);
                        return;
                    }
                    progress.show();
                    try {

                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    SocietyHelpDatabaseFactory.getDBInstance().saveVerifiedCashPayment(getLoginId(), getStrPaymentIds());
                                    List<UserPaid> payments = SocietyHelpDatabaseFactory.getDBInstance().getUnVerifiedCashPayment();
                                    Intent intentInner = new Intent(getApplicationContext(), VerifiedCashPaymentActivity.class);
                                    byte[] sObj = CustomSerializer.serializeObject(payments);
                                    intentInner.putExtra(CONST_UN_VERIFIED_PAYMENT, sObj);
                                    startActivity(intentInner);

                                } catch (Exception e)
                                {
                                    Log.e("Error", "Fetching My dues verified data has problem", e);
                                }
                                progress.dismiss();
                                progress.cancel();
                            }
                        }).start();
                    } catch (Exception e) {
                        Log.d("Error", "Verified transactions have some problem.");
                    }
                }
            });

        } catch (Exception e) {
            Log.e("Error", "Transaction Raw data activity has problem", e);
        }
    }

    private String getStrPaymentIds()
    {
        StringBuilder ids = new StringBuilder();
        boolean first = true;
        for(Integer id : verifiedPaymentIds) {
            if(first){
                first = false;
            }
            else ids.append(",");
            ids.append(id);
        }
        return ids.toString();
    }
}
