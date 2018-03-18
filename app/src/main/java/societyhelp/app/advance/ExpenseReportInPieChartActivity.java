package societyhelp.app.advance;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import societyhelp.app.R;
import societyhelp.app.util.CustomSerializer;
import societyhelp.dao.mysql.impl.ExpenseType;
import societyhelp.dao.mysql.impl.TransactionOnBalanceSheet;

public class ExpenseReportInPieChartActivity extends DashBoardActivity {

    private static String TAG = "MainActivity";

    private float[] yData = null;
    private String[] xData = null;
    PieChart pieChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_report_in_pie_chart);
        setHeader(getString(R.string.title_apartment_expense_report), true, false);
        TreeMap<ExpenseType.ExpenseTypeConst, List<TransactionOnBalanceSheet>> apartmentExpense = null;
        try {
            byte[] sObjet = (byte[]) getIntent().getSerializableExtra(CONST_APARTMENT_EXPENSE_DATA);
            apartmentExpense = (TreeMap<ExpenseType.ExpenseTypeConst, List<TransactionOnBalanceSheet>>) CustomSerializer.deserializeObject(sObjet);
            yData = new float[apartmentExpense.keySet().size()];
            xData = new String[apartmentExpense.keySet().size()];
            int i=0;
            for(ExpenseType.ExpenseTypeConst eType : apartmentExpense.keySet()){
                xData[i] = eType.toString();
                for(TransactionOnBalanceSheet tran : apartmentExpense.get(eType)) {
                    yData[i] += tran.amount;
                }
                i++;
            }
            /*
             private float[] yData = {25.3f, 10.6f, 66.76f, 44.32f, 46.01f, 16.89f, 23.9f};
             private String[] xData = {"Mitch", "Jessica" , "Mohammad" , "Kelsey", "Sam", "Robert", "Ashley"};
             */
        } catch (Exception e) {
            e.printStackTrace();
        }

        pieChart = (PieChart) findViewById(R.id.idPieChart);

        pieChart.setDescription("Apartment Expense (In Rupees) ");
        pieChart.setRotationEnabled(true);
        pieChart.setUsePercentValues(true);
        //pieChart.setHoleColor(Color.BLUE);
        //pieChart.setCenterTextColor(Color.BLACK);
        pieChart.setHoleRadius(20f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Expense Chart");
        pieChart.setCenterTextSize(10);
        //pieChart.setDrawEntryLabels(true);
        //pieChart.setEntryLabelTextSize(20);
        //More options just check out the documentation!

        addData();

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, int i, Highlight h) {
                Log.d(TAG, "onValueSelected: Value select from chart.");
                Log.d(TAG, "onValueSelected: " + e.toString());
                Log.d(TAG, "onValueSelected: " + h.toString());

                int pos1 = e.toString().indexOf("(sum): ");
                String sales = e.toString().substring(pos1 + 7);

                for (int j = 0; j < yData.length; j++) {
                    if (yData[j] == Float.parseFloat(sales)) {
                        pos1 = j;
                        break;
                    }
                }
                String expenseType = xData[pos1];
                Toast.makeText(ExpenseReportInPieChartActivity.this, "Expense type " + expenseType + "\n" + "Expend: Rs " + sales , Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

    }

    private void addData() {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < yData.length; i++)
            yVals1.add(new Entry(yData[i], i));

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < xData.length; i++)
            xVals.add(xData[i]);

        // create pie data set
        PieDataSet dataSet = new PieDataSet(yVals1, "Market Share");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);
        dataSet.setDrawValues(false);


        // add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        // instantiate pie data object now
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        data.setDrawValues(false);

        pieChart.setData(data);

        // undo all highlights
        pieChart.highlightValues(null);

        // update pie chart
        pieChart.invalidate();
    }
}
