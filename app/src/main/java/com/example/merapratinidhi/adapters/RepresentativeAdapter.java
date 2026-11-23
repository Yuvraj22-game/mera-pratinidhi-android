package com.example.merapratinidhi.adapters;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.merapratinidhi.R;
import com.example.merapratinidhi.models.Representative;

import java.util.ArrayList;
import java.util.List;

public class RepresentativeAdapter extends RecyclerView.Adapter<RepresentativeAdapter.ViewHolder> {

    public interface OnRepresentativeClickListener {
        void onItemClick(Representative representative);
        void onBookmarkClick(Representative representative, int position);
    }

    private List<Representative> representativeList;
    private final OnRepresentativeClickListener listener;

    public RepresentativeAdapter(List<Representative> representativeList, OnRepresentativeClickListener listener) {
        this.representativeList = representativeList != null ? new ArrayList<>(representativeList) : new ArrayList<>();
        this.listener = listener;
    }

    public void updateList(List<Representative> newList) {
        List<Representative> safeNewList = newList != null ? newList : new ArrayList<>();
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return representativeList.size();
            }

            @Override
            public int getNewListSize() {
                return safeNewList.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return representativeList.get(oldItemPosition).getId()
                        .equals(safeNewList.get(newItemPosition).getId());
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                Representative oldRep = representativeList.get(oldItemPosition);
                Representative newRep = safeNewList.get(newItemPosition);
                return oldRep.isSaved() == newRep.isSaved()
                        && oldRep.getName().equals(newRep.getName())
                        && oldRep.getParty().equals(newRep.getParty())
                        && oldRep.getWardOrConstituency().equals(newRep.getWardOrConstituency());
            }
        });

        this.representativeList = new ArrayList<>(safeNewList);
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_representative, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Representative rep = representativeList.get(position);

        holder.tvName.setText(rep.getName());
        holder.tvTypeBadge.setText(rep.getType());
        holder.tvPartyBadge.setText(rep.getParty());
        holder.tvLocation.setText(rep.getWardOrConstituency());
        holder.tvInitials.setText(rep.getInitials());

        // Set avatar background color
        int bgColor;
        try {
            bgColor = Color.parseColor(rep.getAvatarBgColor());
        } catch (Exception e) {
            bgColor = Color.parseColor("#1E40AF");
        }
        GradientDrawable avatarDrawable = new GradientDrawable();
        avatarDrawable.setShape(GradientDrawable.OVAL);
        avatarDrawable.setColor(bgColor);
        holder.avatarContainer.setBackground(avatarDrawable);

        // Bookmark icon state
        if (rep.isSaved()) {
            holder.btnSave.setImageResource(R.drawable.ic_bookmark_filled);
            holder.btnSave.setColorFilter(Color.parseColor("#1E40AF"));
        } else {
            holder.btnSave.setImageResource(R.drawable.ic_bookmark_outlined);
            holder.btnSave.clearColorFilter();
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(rep);
            }
        });

        holder.btnSave.setOnClickListener(v -> {
            if (listener != null) {
                listener.onBookmarkClick(rep, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return representativeList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View avatarContainer;
        TextView tvInitials, tvName, tvTypeBadge, tvPartyBadge, tvLocation;
        ImageButton btnSave;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarContainer = itemView.findViewById(R.id.avatar_container);
            tvInitials = itemView.findViewById(R.id.tv_avatar_initials);
            tvName = itemView.findViewById(R.id.tv_rep_name);
            tvTypeBadge = itemView.findViewById(R.id.tv_rep_type_badge);
            tvPartyBadge = itemView.findViewById(R.id.tv_rep_party_badge);
            tvLocation = itemView.findViewById(R.id.tv_rep_location);
            btnSave = itemView.findViewById(R.id.btn_save);
        }
    }
}
