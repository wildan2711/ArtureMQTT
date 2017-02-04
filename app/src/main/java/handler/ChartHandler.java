package handler;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

/**
 * Created by wildan on 8/5/2016.
 */
public class ChartHandler implements OnChartValueSelectedListener{

    private LineChart mChart;

    public ChartHandler(LineChart chart, boolean ph) {
        mChart = chart;
        mChart.setOnChartValueSelectedListener(this);

        // no description text
        mChart.setDescription("");
        mChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // set an alternative background color
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setBorderColor(Color.rgb(67,164,34));


        LineData data = new LineData();
        data.setValueTextColor(Color.WHITE);

        // add empty data
        mChart.setData(data);

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.LINE);
        l.setTypeface(Typeface.MONOSPACE);
        l.setTextColor(Color.rgb(67, 164, 34));

        XAxis xl = mChart.getXAxis();
        xl.setTypeface(Typeface.MONOSPACE);
        xl.setTextColor(Color.rgb(67, 164, 34));
        xl.setDrawGridLines(false);
        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(Typeface.MONOSPACE);
        leftAxis.setTextColor(Color.rgb(67, 164, 34));
        if(ph){
            leftAxis.setAxisMaxValue(10f);
            leftAxis.setAxisMinValue(4f);
        }else{
            leftAxis.setAxisMaxValue(1300f);
            leftAxis.setAxisMinValue(1100f);
        }

        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);

    }

    public void setChart(LineChart chart){ this.mChart = chart; }

    public void addEntry(float value) {

        LineData data = mChart.getData();

        if (data != null){

            ILineDataSet set = data.getDataSetByIndex(0);
            // set.addEntry(...); // can be called as well

            if (set == null) {
                set = createSet();
                data.addDataSet(set);
            }

            data.addEntry(new Entry(set.getEntryCount(),value),0);
            Log.w("anjing", set.getEntryForIndex(set.getEntryCount()-1).toString());

            data.notifyDataChanged();

            // let the chart know it's data has changed
            mChart.notifyDataSetChanged();

            // limit the number of visible entries
            mChart.setVisibleXRangeMaximum(10);
            // mChart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            mChart.moveViewTo(set.getEntryCount()-1, data.getYMax(), AxisDependency.LEFT);

            // this automatically refreshes the chart (calls invalidate())
            // mChart.moveViewTo(data.getXValCount()-7, 55f,
            // AxisDependency.LEFT);
        }
    }

    private LineDataSet createSet() {
        LineDataSet set = new LineDataSet(null, "Data");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(Color.rgb(67, 164, 34));
        //set.setCircleColor(Color.WHITE);
        set.setLineWidth(2f);
        //set.setCircleRadius(4f);
        set.setFillAlpha(65);
        set.setFillColor(Color.rgb(67, 164, 34));
        set.setHighLightColor(Color.rgb(67, 164, 34));
        set.setValueTextColor(Color.rgb(67, 164, 34));
        set.setValueTextSize(9f);
        set.setDrawValues(false);
        return set;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Entry selected", e.toString());
    }

    @Override
    public void onNothingSelected(){
        Log.i("Nothing selected", "Nothing selected.");
    }

}