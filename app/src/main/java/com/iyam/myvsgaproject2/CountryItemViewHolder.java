package com.iyam.myvsgaproject2;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.iyam.myvsgaproject2.databinding.CountryItemLayoutBinding;

/*
 *    Hi, Code Enthusiast!
 *    https://github.com/yudiatmoko
 */

public class CountryItemViewHolder extends RecyclerView.ViewHolder implements ViewHolderBinder<Country> {
    private final CountryItemLayoutBinding binding;
    private final CountryAdapter adapter;
    private final ItemClickListener onClick;

    public interface ItemClickListener {
        void onItemClick(Country country);
    }

    public CountryItemViewHolder(@NonNull CountryItemLayoutBinding binding, ItemClickListener onClick, CountryAdapter adapter) {
        super(binding.getRoot());
        this.binding = binding;
        this.onClick = onClick;
        this.adapter = adapter;
    }

    @Override
    public void bind(Country item) {
        binding.tvCountryName.setText(item.getName());
        binding.getRoot().setOnClickListener(v -> onClick.onItemClick(item));
        setSelectedItem(getAdapterPosition() == adapter.getSelectedPosition());
    }

    private void setSelectedItem(boolean isSelected) {
        itemView.setActivated(isSelected);
        int textColor = isSelected ? ContextCompat.getColor(itemView.getContext(), R.color.blue) : ContextCompat.getColor(itemView.getContext(), R.color.black);
        binding.tvCountryName.setTextColor(textColor);
    }
}

