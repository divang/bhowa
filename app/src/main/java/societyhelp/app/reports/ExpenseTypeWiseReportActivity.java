package societyhelp.app.reports;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import societyhelp.app.DashBoardActivity;
import societyhelp.app.R;
import societyhelp.app.util.CustomSerializer;
import societyhelp.app.util.SocietyHelpConstant;
import societyhelp.dao.mysql.impl.ExpenseType;
import societyhelp.dao.mysql.impl.TransactionOnBalanceSheet;

public class ExpenseTypeWiseReportActivity extends DashBoardActivity implements SocietyHelpConstant {

    protected HorizontalBarChart mChart;
    private List<TransactionOnBalanceSheet> apartmentTypeWiseExpense;
    private String expenseType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_type_wise_report);
        setHeader(getString(R.string.title_activity_expense_type_wise_report), true, false);

        try {
            byte[] sObjet = (byte[]) getIntent().getSerializableExtra(CONST_EXPENSE_TYPE_WISE_DATA);
            apartmentTypeWiseExpense = (List<TransactionOnBalanceSheet>) CustomSerializer.deserializeObject(sObjet);
            expenseType = getIntent().getStringExtra(CONST_EXPENSE_TYPE_TEXT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mChart = (HorizontalBarChart) findViewById(R.id.barChatExpenseTypeWise);
        if(apartmentTypeWiseExpense == null) return;
        setData();
    }

    private void setData() {

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < apartmentTypeWiseExpense.size(); i++) {
            if(apartmentTypeWiseExpense.get(i).transactionDate != null) {
                BarEntry be = new BarEntry(apartmentTypeWiseExpense.get(i).amount, i);
                yVals1.add(be);
                xVals.add(apartmentTypeWiseExpense.get(i).transactionDate.toString());
            }
        }

        if(expenseType == null) expenseType = "Expense";
        BarDataSet set = new BarDataSet(yVals1, expenseType + " (2011-2017)");


        BarData barData = new BarData(xVals, set);
        mChart.setData(barData);
        mChart.setTouchEnabled(true);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setPinchZoom(false);
        mChart.getAxisLeft().setDrawLabels(false);
        mChart.getAxisRight().setDrawLabels(false);

        XAxis xl = mChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(true);
        xl.setGridLineWidth(0.3f);

        YAxis yl = mChart.getAxisLeft();
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setGridLineWidth(0.3f);
        yl.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        YAxis yr = mChart.getAxisRight();
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
        yr.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setFormSize(8f);
        l.setXEntrySpace(4f);

    }

}
