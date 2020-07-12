package com.owlling.cookbook.community.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.owlling.cookbook.R;

public class ConfirmDialog extends DialogFragment {

    private static final String TITLE = "title";
    private static final String TAG = "ConfirmDialog";
    TextView tvTitle;
    TextView btnYes;
    TextView btnNo;

    public interface ConfirmListener {
        void onClick();
    }

    private ConfirmListener confirmListener;

    public void setConfirmListener(ConfirmListener listener) {
        this.confirmListener = listener;
    }

    public ConfirmDialog() {
    }

    public static ConfirmDialog newInst(String title) {
        final ConfirmDialog f = new ConfirmDialog();
        final Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_MinWidth);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_confirm, container, false);
        tvTitle = view.findViewById(R.id.tvTitle);
        btnNo = view.findViewById(R.id.btnNo);
        btnYes = view.findViewById(R.id.btnYes);

        tvTitle.setText(getArguments().getString(TITLE));
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmListener.onClick();
                dismiss();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();//重写实现；消失对话框
            }
        });
        return view;
    }

}
