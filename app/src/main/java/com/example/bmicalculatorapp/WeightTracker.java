package com.example.bmicalculatorapp;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class WeightTracker {

    private static final String PREFS_NAME = "WeightHistory";
    private SharedPreferences sharedPreferences;

    public WeightTracker(Context context) {
        // Initialize SharedPreferences for storing weight history
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveWeightEntry(float weight) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Shift previous entries to make room for the new entry
        for (int i = 6; i > 0; i--) {
            editor.putFloat("weight_day_" + i, sharedPreferences.getFloat("weight_day_" + (i - 1), 0));
        }
        editor.putFloat("weight_day_0", weight); // Save the new entry at day 0
        editor.apply();
    }

    public List<Float> getWeightHistory() {
        List<Float> weightHistory = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            weightHistory.add(sharedPreferences.getFloat("weight_day_" + i, 0));
        }
        return weightHistory;
    }
}
