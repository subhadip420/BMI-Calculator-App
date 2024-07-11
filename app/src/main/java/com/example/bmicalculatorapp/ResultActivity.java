package com.example.bmicalculatorapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {


    private LineChart lineChart;
    private WeightTracker weightTracker;

    private ImageView emoji;
    private TextView bmiDisplay, preText, bmiCategory, userGender;

    String bmi;
    float bmiResult;
    String height, weight;
    float heightResult, weightResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Initialize views from layout
        RelativeLayout relativeLayout = findViewById(R.id.resultBG);

        emoji = (ImageView) findViewById(R.id.emojiIcon);
        bmiDisplay = (TextView) findViewById(R.id.bmiResult);
        preText = (TextView) findViewById(R.id.preText);
        bmiCategory = (TextView) findViewById(R.id.bmiCategory);
        userGender = (TextView) findViewById(R.id.viewGender);

        // Retrieve data passed from HomeActivity
        height = getIntent().getStringExtra("height");
        weight = getIntent().getStringExtra("weight");

        // Convert height and weight to float values
        heightResult = Float.parseFloat(height);
        weightResult = Float.parseFloat(weight);

        // convert height to meters
        heightResult = heightResult / 100;

        // Calculate BMI
        bmiResult = weightResult / (heightResult * heightResult);

        // format BMI result
        bmi = Float.toString(bmiResult);

        // Display BMI result and user gender
        bmiDisplay.setText(bmi);
        userGender.setText(getIntent().getStringExtra("gender"));

        // Determine BMI category and update UI accordingly
        if (bmiResult <= 18.4) {
            preText.setText("Oh No!");
            bmiCategory.setText("Underweight");
            bmiCategory.setTextColor(Color.parseColor("#303F9F"));
            emoji.setImageResource(R.drawable.emoji_under);
            relativeLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.lightblue));
        } else if (bmiResult >= 18.5 && bmiResult <= 24.9) {
            preText.setText("No Worry!");
            bmiCategory.setText("Normal");
            bmiCategory.setTextColor(Color.parseColor("#089508"));
            emoji.setImageResource(R.drawable.emoji_normal);
            relativeLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.lightgreen));
        } else if (bmiResult >= 25 && bmiResult <= 29.9) {
            preText.setText("Oh No!");
            bmiCategory.setText("Overweight");
            bmiCategory.setTextColor(Color.parseColor("#F42525"));
            emoji.setImageResource(R.drawable.emoji_over);
            relativeLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.lightred));
        } else if (bmiResult >= 30) {
            preText.setText("Oh No!");
            bmiCategory.setText("Obese");
            bmiCategory.setTextColor(Color.parseColor("#F42525"));
            emoji.setImageResource(R.drawable.emoji_obese);
            relativeLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.lightred));
        }

        // Initialize and configure the line chart
        lineChart = findViewById(R.id.lineChart);
        weightTracker = new WeightTracker(this);

        // Example: Save a new weight entry
        weightTracker.saveWeightEntry(weightResult);

        // Retrieve weight history and plot the graph
        List<Float> weightHistory = weightTracker.getWeightHistory();
        plotWeightGraph(weightHistory);

    }//end on create method


    // Method to plot weight history graph using MPAndroidChart library
    private void plotWeightGraph(List<Float> weightHistory) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < weightHistory.size(); i++) {
            entries.add(new Entry(i, weightHistory.get(i)));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Weight History");
        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineChart.invalidate(); // refresh

        // Set description
        Description description = new Description();
        description.setText("Weight History Graph");
        description.setTextSize(12f);
        lineChart.setDescription(description);

        // Customize X-axis
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(15f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawAxisLine(true);

        // Customize Y-axis
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setTextSize(15f);
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false); // disable right Y-axis

        // Enable scaling and dragging
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
    }

}//end public classs