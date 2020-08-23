package com.example.weighttracker.ui.addWeight;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.weighttracker.Logbook;
import com.example.weighttracker.R;
import com.example.weighttracker.database.helper.WeightTrackerDatabase;
import com.example.weighttracker.database.model.DailyWeight;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddWeightFragment extends Fragment {
    WeightTrackerDatabase db;
    private int accountId;
    private EditText weight;
    private Button logWeight;
    private TextWatcher buttonWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String weight = AddWeightFragment.this.weight.getText().toString().trim();
            logWeight.setEnabled(!weight.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };
    private View.OnClickListener onLogWeightButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int weightToBeAdded = Integer.parseInt(weight.getText().toString());
            DailyWeight dailyWeight = new DailyWeight();

            dailyWeight.setDailyWeight(weightToBeAdded);
            dailyWeight.setDate(setDate());
            dailyWeight.setAccountId(accountId);

            db.createDailyWeight(dailyWeight);
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_weight, container, false);
        weight = root.findViewById(R.id.weight_number);
        logWeight = root.findViewById(R.id.button_log_weight);
        db = new WeightTrackerDatabase(getContext());
        setButtonState();
        Logbook lb = (Logbook) getActivity();
        accountId = lb.getAccountId();
        logWeight.setOnClickListener(onLogWeightButtonClick);

        return root;
    }

    private void setButtonState() {
        weight.addTextChangedListener(buttonWatcher);
    }

    private String setDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        return formatter.format(new Date());
    }
}
