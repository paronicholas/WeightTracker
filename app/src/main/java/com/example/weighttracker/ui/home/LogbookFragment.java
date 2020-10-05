package com.example.weighttracker.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.weighttracker.Logbook;
import com.example.weighttracker.R;
import com.example.weighttracker.database.helper.WeightTrackerDatabase;
import com.example.weighttracker.database.model.DailyWeight;
import com.example.weighttracker.database.model.TargetWeight;

import java.util.ArrayList;
import java.util.List;

public class LogbookFragment extends Fragment {
    WeightTrackerDatabase db;
    private int accountId;
    private TableLayout tableLayout;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_logbook, container, false);
        tableLayout = root.findViewById(R.id.weight_table);
        db = new WeightTrackerDatabase(getContext());
        Logbook lb = (Logbook) getActivity();
        accountId = lb.getAccountId();
        showWeightTable();

        db.closeDb();
        return root;
    }

    private void showWeightTable() {
        List<DailyWeight> dailyWeightsForAccount = db.getDailyWeightByAccountId(accountId);

        for (int i = dailyWeightsForAccount.size() - 1; i >= 0; i--) {
            createNewTableRow(dailyWeightsForAccount.get(i));
        }
    }

    private void createNewTableRow(DailyWeight dailyWeight) {
        TableRow tableRow = new TableRow(this.getContext());
        tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        tableRow.addView(createTextView(dailyWeight.getDate()));
        tableRow.addView(createTextView(String.valueOf(dailyWeight.getDailyWeight())));
        tableLayout.addView(tableRow);
    }

    private TextView createTextView(String text) {
        TextView textView = new TextView(this.getContext());
        textView.setText(text);
        textView.setTextSize((float)15);
        return textView;
    }
}