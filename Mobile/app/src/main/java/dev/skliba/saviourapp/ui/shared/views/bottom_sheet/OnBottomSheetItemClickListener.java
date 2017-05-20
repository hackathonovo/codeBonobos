package dev.skliba.saviourapp.ui.shared.views.bottom_sheet;


public interface OnBottomSheetItemClickListener<ItemType extends BottomSheetItem> {

    void bottomSheetItemClicked(ItemType item);
}
