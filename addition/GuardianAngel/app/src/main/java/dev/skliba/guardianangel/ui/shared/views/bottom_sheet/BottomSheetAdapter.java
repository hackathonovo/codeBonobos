package dev.skliba.guardianangel.ui.shared.views.bottom_sheet;


import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import dev.skliba.guardianangel.R;

public class BottomSheetAdapter <ItemType extends BottomSheetItem> extends RecyclerView.Adapter<BottomSheetAdapter.ViewHolder<ItemType>> {

    private List<ItemType> items;

    private OnBottomSheetItemClickListener<ItemType> listener;

    BottomSheetAdapter(List<ItemType> items) {
        this.items = items;
    }

    public void setListener(OnBottomSheetItemClickListener<ItemType> listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder<ItemType> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder<>(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bottom_sheet, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder<ItemType> holder, int position) {
        holder.bindItem(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder<ItemType extends BottomSheetItem> extends RecyclerView.ViewHolder {

        private final OnBottomSheetItemClickListener<ItemType> listener;

        @BindView(R.id.itemIcon)
        CircleImageView itemIcon;

        @BindView(R.id.itemTitle)
        CheckedTextView itemTitle;

        ViewHolder(View itemView, OnBottomSheetItemClickListener<ItemType> listener) {
            super(itemView);
            this.listener = listener;
            ButterKnife.bind(this, itemView);
        }

        void bindItem(ItemType item) {
            Context context = itemView.getContext();

            if (item.getIcon() != null) {
                Drawable drawable = new BitmapDrawable(context.getResources(), item.getIcon());
                itemIcon.setImageDrawable(drawable);
            }

            itemTitle.setText(item.getTitle(itemView.getContext()));
            boolean isSelected = item.isSelected();
            itemTitle.setChecked(isSelected);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.bottomSheetItemClicked(item);
                }
            });
        }
    }
}
