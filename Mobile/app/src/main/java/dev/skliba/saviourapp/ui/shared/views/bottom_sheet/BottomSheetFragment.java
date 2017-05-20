package dev.skliba.saviourapp.ui.shared.views.bottom_sheet;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.skliba.saviourapp.R;

public class BottomSheetFragment<ItemType extends BottomSheetItem> extends BottomSheetDialogFragment {

    public static final String EXTRA_ITEMS_LIST = "EXTRA_ITEM_LIST";

    public static final String EXTRA_BOTTOM_SHEET_TITLE = "EXTRA_BOTTOM_SHEET_TITLE";

    @BindView(R.id.bottomSheet)
    RecyclerView bottomSheet;

    @BindView(R.id.bottomSheetTitle)
    TextView bottomSheetTitle;

    private OnBottomSheetItemClickListener<ItemType> listener;

    private BottomSheetAdapter<ItemType> adapter;

    public static <ItemType extends BottomSheetItem> BottomSheetFragment<ItemType> newInstance(List<ItemType> items) {
        return newInstance(items, 0);
    }

    public static <ItemType extends BottomSheetItem> BottomSheetFragment<ItemType> newInstance(List<ItemType> items, @StringRes int title) {
        BottomSheetFragment<ItemType> fragment = new BottomSheetFragment<>();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_ITEMS_LIST, (Serializable) items);
        args.putInt(EXTRA_BOTTOM_SHEET_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    public void setListener(OnBottomSheetItemClickListener<ItemType> listener) {
        this.listener = listener;
        if (adapter != null) {
            adapter.setListener(listener);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet, container, false);
        ButterKnife.bind(this, view);
        initUi();
        return view;
    }

    private void initUi() {
        bottomSheet.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (getArguments() != null) {

            //noinspection unchecked
            List<ItemType> bottomSheetItems = (List<ItemType>) getArguments().getSerializable(EXTRA_ITEMS_LIST);

            if (bottomSheetItems != null && !bottomSheetItems.isEmpty()) {
                adapter = new BottomSheetAdapter<>(bottomSheetItems);
                adapter.setListener(listener);
                bottomSheet.setAdapter(adapter);
            }

            initBottomSheetTitle();
        }
    }

    private void initBottomSheetTitle() {
        int bottomSheetTitleStringRes = getArguments().getInt(EXTRA_BOTTOM_SHEET_TITLE);

        if (bottomSheetTitleStringRes != 0) {
            bottomSheetTitle.setVisibility(View.VISIBLE);
            bottomSheetTitle.setText(bottomSheetTitleStringRes);
        } else {
            bottomSheetTitle.setVisibility(View.GONE);
        }
    }
}
