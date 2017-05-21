package dev.skliba.guardianangel.ui.shared.views.bottom_sheet;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;

public interface BottomSheetItem {

    String getTitle(Context context);

    @Nullable
    Bitmap getIcon();

    boolean isSelected();
}
