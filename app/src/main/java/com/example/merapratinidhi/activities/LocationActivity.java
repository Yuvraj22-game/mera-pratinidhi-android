package com.example.merapratinidhi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.merapratinidhi.R;
import com.example.merapratinidhi.data.DataRepository;
import com.example.merapratinidhi.models.Ward;
import com.example.merapratinidhi.utils.ThemeHelper;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class LocationActivity extends AppCompatActivity {

    private ProgressBar progressLoading;
    private View cardLocationResult;

    private TextView tvResultWard, tvResultAssembly, tvResultParliament;

    private DataRepository repository;
    private AlertDialog activeDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeHelper.applyTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        repository = DataRepository.getInstance(this);

        ImageButton btnBack = findViewById(R.id.btn_back);
        View cardCurrentLocation = findViewById(R.id.card_option_current_location);
        View cardSearchAddress = findViewById(R.id.card_option_search_address);
        View cardSelectWard = findViewById(R.id.card_option_select_ward);

        progressLoading = findViewById(R.id.progress_loading);
        cardLocationResult = findViewById(R.id.card_location_result);

        tvResultWard = findViewById(R.id.tv_result_ward);
        tvResultAssembly = findViewById(R.id.tv_result_assembly);
        tvResultParliament = findViewById(R.id.tv_result_parliament);
        MaterialButton btnResultViewReps = findViewById(R.id.btn_result_view_representatives);

        btnBack.setOnClickListener(v -> finish());

        // Option 1: Mock current GPS location
        cardCurrentLocation.setOnClickListener(v -> simulateLocationDetection());

        // Option 2: Search address
        cardSearchAddress.setOnClickListener(v -> showAddressSearchDialog());

        // Option 3: Select ward manually
        cardSelectWard.setOnClickListener(v -> showWardSelectionDialog());

        btnResultViewReps.setOnClickListener(v -> {
            Intent intent = new Intent(LocationActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }

    private void simulateLocationDetection() {
        progressLoading.setVisibility(View.VISIBLE);
        cardLocationResult.setVisibility(View.GONE);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            progressLoading.setVisibility(View.GONE);
            showLocationResult("Ward 61 — Kohat Enclave", "Shalimar Bagh", "North West Delhi");
            Toast.makeText(LocationActivity.this, "Mock GPS location acquired: New Delhi", Toast.LENGTH_SHORT).show();
        }, 800);
    }

    private void showAddressSearchDialog() {
        dismissActiveDialog();
        EditText input = new EditText(this);
        input.setHint("e.g. Rohini Sector 8, Pitampura, Laxmi Nagar");
        int padding = (int) (16 * getResources().getDisplayMetrics().density);
        input.setPadding(padding, padding, padding, padding);

        activeDialog = new AlertDialog.Builder(this)
                .setTitle("Search Address")
                .setView(input)
                .setPositiveButton("Search", (dialog, which) -> {
                    String address = input.getText().toString().trim();
                    if (address.isEmpty()) {
                        address = "Rohini, New Delhi";
                    }
                    showLocationResult("Ward 61 — Kohat Enclave", "Shalimar Bagh", "North West Delhi");
                    Toast.makeText(LocationActivity.this, getString(R.string.location_found_msg, address), Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showWardSelectionDialog() {
        dismissActiveDialog();
        List<Ward> wards = repository.getAllWards();
        String[] wardNames = new String[wards.size()];
        for (int i = 0; i < wards.size(); i++) {
            Ward w = wards.get(i);
            wardNames[i] = getString(R.string.ward_title_format, w.getWardNumber(), w.getWardName());
        }

        activeDialog = new AlertDialog.Builder(this)
                .setTitle("Select Delhi Ward")
                .setItems(wardNames, (dialog, which) -> {
                    Ward selectedWard = wards.get(which);
                    showLocationResult(
                            getString(R.string.ward_title_format, selectedWard.getWardNumber(), selectedWard.getWardName()),
                            selectedWard.getAssemblyConstituency(),
                            selectedWard.getParliamentaryConstituency()
                    );
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showLocationResult(String ward, String assembly, String parliament) {
        tvResultWard.setText(ward);
        tvResultAssembly.setText(getString(R.string.assembly_constituency_label, assembly));
        tvResultParliament.setText(getString(R.string.parliamentary_constituency_label, parliament));
        cardLocationResult.setVisibility(View.VISIBLE);
    }

    private void dismissActiveDialog() {
        if (activeDialog != null && activeDialog.isShowing()) {
            activeDialog.dismiss();
            activeDialog = null;
        }
    }

    @Override
    protected void onDestroy() {
        dismissActiveDialog();
        super.onDestroy();
    }
}
