package com.owlling.cookbook.community.dialog;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.owlling.cookbook.R;

public class CommentDialog extends DialogFragment {

    private static final String AUTHOR = "author";
    private static final String TAG = "CommentDialog";
    TextView tvCommentTitle;
    EditText etComment;
    TextView btnComment;
    TextView btnCommentCancel;
    public interface ConfirmListener{
        void onClick(String content);
    }

    private ConfirmListener confirmListener;

    public void setConfirmListener(ConfirmListener listener){
        this.confirmListener = listener;
    }

    public CommentDialog() {
    }

    public static CommentDialog newInst(String author) {
        final CommentDialog f = new CommentDialog();
        final Bundle bundle = new Bundle();
        bundle.putString(AUTHOR, author);
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

//    评论模块
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_comment, container, false);
        tvCommentTitle = view.findViewById(R.id.tvCommentTitle);
        etComment = view.findViewById(R.id.etComment);
        btnCommentCancel = view.findViewById(R.id.btnCommentCancel);
        btnComment = view.findViewById(R.id.btnComment);

        tvCommentTitle.setText("回复@ " + getArguments().getString(AUTHOR));
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmListener.onClick(etComment.getText().toString());
                dismiss();
            }
        });
        btnCommentCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (etComment == null) return;
                etComment.setFocusable(true);
                etComment.setFocusableInTouchMode(true);
                etComment.requestFocus();
                InputMethodManager inputManager = (InputMethodManager) etComment.getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(etComment, 0);
            }
        }, 100);
    }

}
