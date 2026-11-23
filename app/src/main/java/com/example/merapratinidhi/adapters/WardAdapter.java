package com.example.merapratinidhi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.merapratinidhi.R;
import com.example.merapratinidhi.models.Ward;

import java.util.ArrayList;
import java.util.List;

public class WardAdapter extends RecyclerView.Adapter<WardAdapter.ViewHolder> {

    public interface OnWardClickListener {
        void onWardClick(Ward ward);
    }

    private List<Ward> wardList;
    private final OnWardClickListener listener;

    public WardAdapter(List<Ward> wardList, OnWardClickListener listener) {
        this.wardList = wardList != null ? new ArrayList<>(wardList) : new ArrayList<>();
        this.listener = listener;
    }

    public void updateList(List<Ward> newList) {
        List<Ward> safeNewList = newList != null ? newList : new ArrayList<>();
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return wardList.size();
            }

            @Override
            public int getNewListSize() {
                return safeNewList.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return wardList.get(oldItemPosition).getId()
                        .equals(safeNewList.get(newItemPosition).getId());
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                Ward oldW = wardList.get(oldItemPosition);
                Ward newW = safeNewList.get(newItemPosition);
                return oldW.getWardName().equals(newW.getWardName())
                        && oldW.getCouncillorName().equals(newW.getCouncillorName());
            }
        });

        this.wardList = new ArrayList<>(safeNewList);
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ward, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ward ward = wardList.get(position);
        Context context = holder.itemView.getContext();

        holder.tvTitle.setText(context.getString(R.string.ward_title_format, ward.getWardNumber(), ward.getWardName()));
        holder.tvCouncillor.setText(context.getString(R.string.councillor_format, ward.getCouncillorName()));
        holder.tvDetails.setText(context.getString(R.string.ward_details_format, ward.getAssemblyConstituency(), ward.getParliamentaryConstituency()));

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onWardClick(ward);
            }
        });
    }

    @Override
    public int getItemCount() {
        return wardList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvCouncillor, tvDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_ward_title);
            tvCouncillor = itemView.findViewById(R.id.tv_ward_councillor);
            tvDetails = itemView.findViewById(R.id.tv_ward_details);
        }
    }
}
