package com.example.weighttracker.ui.profile;

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
import com.example.weighttracker.database.model.TargetWeight;

import java.util.List;

public class ProfileFragment extends Fragment {
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
            String weight = ProfileFragment.this.weight.getText().toString().trim();
            logWeight.setEnabled(!weight.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };
    private View.OnClickListener onSetTargetWeightButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            boolean isUpdateTargetWeight = false;
            TargetWeight targetWeight = new TargetWeight();
            System.out.println("The Log Target Weight button was clicked");
            System.out.println("Current Account: " + accountId);
            List<TargetWeight> targetWeights = db.getTargetWeights();

            for (TargetWeight tg : targetWeights) {
                System.out.println("target weight id: " + tg.getId());
                System.out.println("target weight weight: " + tg.getTargetWeight());
                System.out.println("target weight account: " + tg.getAccountId());
                if (tg.getAccountId() == accountId) {
                    isUpdateTargetWeight = true;
                    targetWeight = tg;
                    System.out.println("Current target weight: " + tg.getTargetWeight());
                }
            }

            System.out.println("isUpdateTargetWeight: " + isUpdateTargetWeight);

            targetWeight.setTargetWeight(Integer.parseInt(weight.getText().toString()));
            System.out.println("new target weight: " + targetWeight.getTargetWeight());
            if (isUpdateTargetWeight) {
                db.updateTargetWeight(targetWeight);
            } else {
                targetWeight.setAccountId(accountId);
                System.out.println("Account id for new target weight entry: " + targetWeight.getAccountId());
                db.createTargetWeight(targetWeight);
            }
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        weight = root.findViewById(R.id.target_weight_number);
        logWeight = root.findViewById(R.id.button_target_weight);
        db = new WeightTrackerDatabase(getContext());
        setButtonState();
        Logbook lb = (Logbook) getActivity();
        accountId = lb.getAccountId();
        logWeight.setOnClickListener(onSetTargetWeightButtonClick);

        db.closeDb();
        return root;
    }

    private void setButtonState() {
        weight.addTextChangedListener(buttonWatcher);
    }
}