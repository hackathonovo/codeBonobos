package dev.skliba.saviourapp.util;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

public class ViewUtils {

    private ViewUtils() {
        throw new UnsupportedOperationException("Cannot create instance of this class");
    }

    public static int getTopMargin(View view) {
        ViewGroup.MarginLayoutParams params = getMarginLayoutParams(view);
        if (params != null) {
            return params.topMargin;
        } else {
            return 0;
        }
    }

    public static void setMargins(View view, int left, int top, int right, int bottom) {
        ViewGroup.MarginLayoutParams params = createMarginLayoutParams(view);
        params.leftMargin = left;
        params.rightMargin = right;
        params.topMargin = top;
        params.bottomMargin = bottom;
        view.setLayoutParams(params);
    }

    public static void setHorizontalMargins(View view, int left, int right) {
        ViewGroup.MarginLayoutParams params = createMarginLayoutParams(view);
        params.leftMargin = left;
        params.rightMargin = right;
        view.setLayoutParams(params);
    }

    public static void setVerticalMargins(View view, int top, int bottom) {
        ViewGroup.MarginLayoutParams params = createMarginLayoutParams(view);
        params.topMargin = top;
        params.bottomMargin = bottom;
        view.setLayoutParams(params);
    }

    public static void setLeftMargin(View view, int left) {
        ViewGroup.MarginLayoutParams params = createMarginLayoutParams(view);
        params.leftMargin = left;
        view.setLayoutParams(params);
    }

    public static void setTopMargin(View view, int top) {
        ViewGroup.MarginLayoutParams params = createMarginLayoutParams(view);
        params.topMargin = top;
        view.setLayoutParams(params);
    }

    public static void setRightMargin(View view, int right) {
        ViewGroup.MarginLayoutParams params = createMarginLayoutParams(view);
        params.rightMargin = right;
        view.setLayoutParams(params);
    }

    public static void setBottomMargin(View view, int bottom) {
        ViewGroup.MarginLayoutParams params = createMarginLayoutParams(view);
        params.bottomMargin = bottom;
        view.setLayoutParams(params);
    }

    public static int getTransparentColor(int color, float alpha) {
        final int maxColorValue = 255;
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        float c = 1 - alpha;
        return Color.argb(maxColorValue,
                Math.round(red * alpha + maxColorValue * c),
                Math.round(green * alpha + maxColorValue * c),
                Math.round(blue * alpha + maxColorValue * c));
    }

    public static int getDarkerColor(int color, float opacity) {
        //CHECKSTYLE:OFF
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= opacity;
        //CHECKSTYLE:ON
        return Color.HSVToColor(hsv);
    }

    public static void setBackgroundColorFilter(View view, @ColorInt int color) {
        Drawable drawable = view.getBackground().mutate();
        drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        view.setBackground(drawable);
    }

    public static void showMessage(View view, CharSequence message) {
        if (view != null) {
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
        }
    }

    public static void showMessage(View view, @StringRes int stringRes) {
        showMessage(view, view.getContext().getString(stringRes));
    }

    public static void setStatusBarColor(Window window, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }

    private static ViewGroup.MarginLayoutParams createMarginLayoutParams(View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (!(params instanceof ViewGroup.MarginLayoutParams)) {
            if (params != null) {
                params = new ViewGroup.MarginLayoutParams(params);
            } else {
                //noinspection ResourceType
                params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        }
        return (ViewGroup.MarginLayoutParams) params;
    }

    private static ViewGroup.MarginLayoutParams getMarginLayoutParams(View view) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp instanceof ViewGroup.MarginLayoutParams) {
            return (ViewGroup.MarginLayoutParams) lp;
        }
        return null;
    }

    public static void hideKeyboard(Activity activity) {
        if (activity != null) {
            hideKeyboard(activity.getCurrentFocus());
        }
    }

    public static void hideKeyboard(View view) {
        if (view != null && view.getContext() != null) {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void showKeyboard(View view) {
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    public static float dpToPx(Context context, float valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }

    public static Drawable getTintedDrawable(@NonNull Context context, @NonNull Bitmap inputBitmap, @ColorRes int colorResId) {
        return getTintedDrawable(context, new BitmapDrawable(context.getResources(), inputBitmap), colorResId);
    }

    public static Drawable getTintedDrawable(@NonNull Context context, @DrawableRes int drawableResId, @ColorRes int colorResId) {
        return getTintedDrawable(context, ContextCompat.getDrawable(context, drawableResId), colorResId);
    }

    public static Drawable getTintedDrawable(@NonNull Context context, @NonNull Drawable inputDrawable, @ColorRes int colorResId) {
        int color = ContextCompat.getColor(context, colorResId);
        Drawable wrapDrawable = DrawableCompat.wrap(inputDrawable).mutate();
        DrawableCompat.setTint(wrapDrawable, color);
        DrawableCompat.setTintMode(wrapDrawable, PorterDuff.Mode.SRC_IN);
        return wrapDrawable;
    }
}
