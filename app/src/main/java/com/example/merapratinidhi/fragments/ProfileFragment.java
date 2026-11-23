package com.example.merapratinidhi.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.merapratinidhi.R;
import com.example.merapratinidhi.activities.LocationActivity;
import com.example.merapratinidhi.utils.ThemeHelper;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class ProfileFragment extends Fragment {

    private SwitchMaterial switchDarkMode;
    private TextView tvCurrentLanguage;
    private AlertDialog activeDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getContext() == null) return;

        SwitchMaterial switchNotifications = view.findViewById(R.id.switch_notifications);
        switchDarkMode = view.findViewById(R.id.switch_dark_mode);
        tvCurrentLanguage = view.findViewById(R.id.tv_current_language);

        // Dark mode setup
        boolean isDark = ThemeHelper.isDarkModeEnabled(getContext());
        switchDarkMode.setChecked(isDark);

        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (getContext() != null) {
                ThemeHelper.setDarkModeEnabled(getContext(), isChecked);
            }
        });

        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String msg = isChecked ? "Notifications enabled" : "Notifications disabled";
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        });

        // Language Selector
        view.findViewById(R.id.row_language).setOnClickListener(v -> showLanguageDialog());

        // Location
        view.findViewById(R.id.row_change_location).setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), LocationActivity.class);
            startActivity(intent);
        });

        // App Info Rows
        view.findViewById(R.id.row_about).setOnClickListener(v -> showInfoDialog(
                getString(R.string.about_mera_pratinidhi),
                "Mera Pratinidhi is a civic-information platform designed to help Delhi residents easily discover their local MCD Ward Councillor, MLA, and Member of Parliament (MP).\n\nVersion: 1.0.0 (Prototype)\nDeveloped for College Project Demonstration."
        ));

        view.findViewById(R.id.row_privacy).setOnClickListener(v -> showInfoDialog(
                getString(R.string.privacy_policy),
                "Mera Pratinidhi respects user privacy. This application operates entirely offline with local mock data and does not collect or transmit any personal data or real GPS location."
        ));

        view.findViewById(R.id.row_help).setOnClickListener(v -> showInfoDialog(
                getString(R.string.help_support),
                "For inquiries, bug reports, or feedback regarding Mera Pratinidhi prototype:\n\nEmail: support@merapratinidhi.delhi\nWebsite: www.merapratinidhi.in"
        ));

        view.findViewById(R.id.row_report).setOnClickListener(v -> showInfoDialog(
                getString(R.string.report_incorrect_info),
                "Notice an inaccuracy in Councillor, MLA, or MP records?\n\nPlease report data updates to update@merapratinidhi.delhi with official state election commission references."
        ));
    }

    private void showLanguageDialog() {
        if (getContext() == null) return;
        dismissActiveDialog();
        String[] languages = {"English", "हिंदी (Hindi)", "ਪੰਜਾਬੀ (Punjabi)"};
        activeDialog = new AlertDialog.Builder(getContext())
                .setTitle("Select Language")
                .setItems(languages, (dialog, which) -> {
                    String selected = languages[which].split(" ")[0];
                    tvCurrentLanguage.setText(selected);
                    Toast.makeText(getContext(), "Language set to " + selected, Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showInfoDialog(String title, String message) {
        if (getContext() == null) return;
        dismissActiveDialog();
        activeDialog = new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

    private void dismissActiveDialog() {
        if (activeDialog != null && activeDialog.isShowing()) {
            activeDialog.dismiss();
            activeDialog = null;
        }
    }

    @Override
    public void onDestroyView() {
        dismissActiveDialog();
        super.onDestroyView();
    }
}
