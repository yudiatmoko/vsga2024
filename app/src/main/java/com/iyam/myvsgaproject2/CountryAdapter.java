package com.iyam.myvsgaproject2;
/*
 *    Hi, Code Enthusiast!
 *    https://github.com/yudiatmoko
 */

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.iyam.myvsgaproject2.databinding.CountryItemLayoutBinding;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryItemViewHolder> {

    private final CountryItemViewHolder.ItemClickListener itemClick;
    private final AsyncListDiffer<Country> differ;
    private int selectedPosition = RecyclerView.NO_POSITION;

    public CountryAdapter(CountryItemViewHolder.ItemClickListener itemClick) {
        this.itemClick = itemClick;
        this.differ = new AsyncListDiffer<>(this, new DiffUtil.ItemCallback<Country>() {
            @Override
            public boolean areItemsTheSame(@NonNull Country oldItem, @NonNull Country newItem) {
                return oldItem.getId().equals(newItem.getId());
            }

            @Override
            public boolean areContentsTheSame(@NonNull Country oldItem, @NonNull Country newItem) {
                return oldItem.getName().equals(newItem.getName());
            }
        });
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    @NonNull
    @Override
    public CountryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CountryItemLayoutBinding binding = CountryItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CountryItemViewHolder(binding, itemClick, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryItemViewHolder holder, int position) {
        Country country = differ.getCurrentList().get(position);
        holder.bind(country);

        holder.itemView.setOnClickListener(v -> {
            selectedPosition = holder.getAdapterPosition();
            notifyDataSetChanged();
            itemClick.onItemClick(country);
        });
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public void setData(List<Country> items) {
        differ.submitList(items);
    }
}