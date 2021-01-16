package com.uds.bakingtime.adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uds.bakingtime.R;
import com.uds.bakingtime.Utils.ItemClickListener;
import com.uds.bakingtime.model.Steps;

import java.util.List;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.ViewHolder> {
    private final List<Steps> steps;
    private final ItemClickListener itemClickListener;

    public RecipeStepsAdapter(List<Steps> steps, ItemClickListener itemClickListener) {
        this.steps = steps;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.steps_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String index = "";
        if (position > 0)
            index += position + ". ";

        holder.description.setText(TextUtils.concat(index, steps.get(position).getShortDescription()));
    }

    @Override
    public int getItemCount() {
        return Math.max(0, steps.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.tv_steps_description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(getAdapterPosition());
        }
    }
}
