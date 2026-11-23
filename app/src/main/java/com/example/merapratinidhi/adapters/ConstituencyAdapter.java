package com.example.merapratinidhi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.merapratinidhi.R;
import com.example.merapratinidhi.models.AssemblyConstituency;
import com.example.merapratinidhi.models.ParliamentaryConstituency;

import java.util.ArrayList;
import java.util.List;

public class ConstituencyAdapter extends RecyclerView.Adapter<ConstituencyAdapter.ViewHolder> {

    public interface OnConstituencyClickListener {
        void onAssemblyClick(AssemblyConstituency ac);
        void onParliamentaryClick(ParliamentaryConstituency pc);
    }

    private boolean isAssemblyMode = true;
    private final List<AssemblyConstituency> assemblyList;
    private final List<ParliamentaryConstituency> parliamentaryList;
    private final OnConstituencyClickListener listener;

    public ConstituencyAdapter(List<AssemblyConstituency> assemblyList,
                               List<ParliamentaryConstituency> parliamentaryList,
                               OnConstituencyClickListener listener) {
        this.assemblyList = assemblyList != null ? new ArrayList<>(assemblyList) : new ArrayList<>();
        this.parliamentaryList = parliamentaryList != null ? new ArrayList<>(parliamentaryList) : new ArrayList<>();
        this.listener = listener;
    }

    public void setMode(boolean assemblyMode) {
        if (this.isAssemblyMode != assemblyMode) {
            this.isAssemblyMode = assemblyMode;
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_constituency, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();

        if (isAssemblyMode) {
            AssemblyConstituency ac = assemblyList.get(position);
            holder.ivIcon.setImageResource(R.drawable.ic_assembly);
            holder.tvTitle.setText(context.getString(R.string.assembly_title_format, ac.getName()));
            holder.tvLeader.setText(context.getString(R.string.mla_format, ac.getMlaName()));
            holder.tvSub.setText(context.getString(R.string.assembly_sub_format, ac.getParliamentaryConstituency(), ac.getWardCount()));

            holder.itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onAssemblyClick(ac);
                }
            });
        } else {
            ParliamentaryConstituency pc = parliamentaryList.get(position);
            holder.ivIcon.setImageResource(R.drawable.ic_parliament);
            holder.tvTitle.setText(context.getString(R.string.parliament_title_format, pc.getName()));
            holder.tvLeader.setText(context.getString(R.string.mp_format, pc.getMpName()));
            holder.tvSub.setText(context.getString(R.string.parliament_sub_format, pc.getAssemblyCount()));

            holder.itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onParliamentaryClick(pc);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return isAssemblyMode ? assemblyList.size() : parliamentaryList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView tvTitle, tvLeader, tvSub;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.iv_constituency_icon);
            tvTitle = itemView.findViewById(R.id.tv_constituency_title);
            tvLeader = itemView.findViewById(R.id.tv_constituency_leader);
            tvSub = itemView.findViewById(R.id.tv_constituency_sub);
        }
    }
}
