package com.example.merapratinidhi.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.merapratinidhi.R;
import com.example.merapratinidhi.data.DataRepository;
import com.example.merapratinidhi.models.Representative;
import com.example.merapratinidhi.utils.ThemeHelper;
import com.google.android.material.button.MaterialButton;

public class RepresentativeDetailActivity extends AppCompatActivity {

    public static final String EXTRA_REP_ID = "extra_rep_id";

    private DataRepository repository;
    private Representative representative;

    private View avatarContainer;
    private TextView tvInitials, tvName, tvTypeBadge, tvPartyBadge, tvWardConstituency;
    private TextView tvJurisdiction, tvPhone, tvEmail, tvOffice, tvSource, tvVerified;

    private ImageButton btnBack, btnShare, btnSaveTop;
    private MaterialButton btnCall, btnEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeHelper.applyTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_representative_detail);

        repository = DataRepository.getInstance(this);

        String repId = getIntent().getStringExtra(EXTRA_REP_ID);
        if (repId != null) {
            representative = repository.getRepresentativeById(repId);
        }

        if (representative == null) {
            Toast.makeText(this, "Representative not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initViews();
        populateData();
    }

    private void initViews() {
        avatarContainer = findViewById(R.id.detail_avatar_container);
        tvInitials = findViewById(R.id.tv_detail_initials);
        tvName = findViewById(R.id.tv_detail_name);
        tvTypeBadge = findViewById(R.id.tv_detail_type_badge);
        tvPartyBadge = findViewById(R.id.tv_detail_party_badge);
        tvWardConstituency = findViewById(R.id.tv_detail_ward_constituency);

        tvJurisdiction = findViewById(R.id.tv_info_jurisdiction);
        tvPhone = findViewById(R.id.tv_info_phone);
        tvEmail = findViewById(R.id.tv_info_email);
        tvOffice = findViewById(R.id.tv_info_office);
        tvSource = findViewById(R.id.tv_info_source);
        tvVerified = findViewById(R.id.tv_info_verified);

        btnBack = findViewById(R.id.btn_back);
        btnShare = findViewById(R.id.btn_share);
        btnSaveTop = findViewById(R.id.btn_save_top);
        btnCall = findViewById(R.id.btn_action_call);
        btnEmail = findViewById(R.id.btn_action_email);

        btnBack.setOnClickListener(v -> finish());

        btnShare.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, representative.getName());
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Representative details for " + representative.getName() + " (" + representative.getType() + ", " + representative.getParty() + ") - " + representative.getWardOrConstituency());
            startActivity(Intent.createChooser(shareIntent, "Share Representative"));
        });

        btnSaveTop.setOnClickListener(v -> {
            boolean isSavedNow = repository.toggleSaveRepresentative(representative.getId());
            representative.setSaved(isSavedNow);
            updateSaveIcon();
            String message = isSavedNow ? representative.getName() + " saved to bookmarks" : representative.getName() + " removed from bookmarks";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });

        btnCall.setOnClickListener(v -> new AlertDialog.Builder(this)
                .setTitle("Call Representative (Demo)")
                .setMessage("Initiate call to " + representative.getName() + " at " + representative.getPhone() + "?")
                .setPositiveButton("Call", (dialog, which) ->
                        Toast.makeText(this, "Simulating call to " + representative.getPhone(), Toast.LENGTH_SHORT).show())
                .setNegativeButton("Cancel", null)
                .show());

        btnEmail.setOnClickListener(v -> new AlertDialog.Builder(this)
                .setTitle("Email Representative (Demo)")
                .setMessage("Send official email to " + representative.getName() + " at " + representative.getEmail() + "?")
                .setPositiveButton("Open Email", (dialog, which) ->
                        Toast.makeText(this, "Simulating email to " + representative.getEmail(), Toast.LENGTH_SHORT).show())
                .setNegativeButton("Cancel", null)
                .show());
    }

    private void populateData() {
        tvInitials.setText(representative.getInitials());
        tvName.setText(representative.getName());
        tvTypeBadge.setText(representative.getType());
        tvPartyBadge.setText(representative.getParty());
        tvWardConstituency.setText(representative.getWardOrConstituency());

        // Avatar bg
        int bgColor;
        try {
            bgColor = Color.parseColor(representative.getAvatarBgColor());
        } catch (Exception e) {
            bgColor = Color.parseColor("#1E40AF");
        }
        GradientDrawable avatarDrawable = new GradientDrawable();
        avatarDrawable.setShape(GradientDrawable.OVAL);
        avatarDrawable.setColor(bgColor);
        avatarContainer.setBackground(avatarDrawable);

        tvJurisdiction.setText(representative.getWardOrConstituency());
        tvPhone.setText(representative.getPhone());
        tvEmail.setText(representative.getEmail());
        tvOffice.setText(representative.getOfficeAddress());
        tvSource.setText("Source: " + representative.getSource());
        tvVerified.setText("Verified: " + representative.getVerifiedDate());

        updateSaveIcon();
    }

    private void updateSaveIcon() {
        if (representative.isSaved()) {
            btnSaveTop.setImageResource(R.drawable.ic_bookmark_filled);
            btnSaveTop.setColorFilter(Color.parseColor("#1E40AF"));
        } else {
            btnSaveTop.setImageResource(R.drawable.ic_bookmark_outlined);
            btnSaveTop.clearColorFilter();
        }
    }
}
