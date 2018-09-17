package com.llong.football.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

/**
 * Message提示框
 * Created by cui-hl on 2018/09/17.
 */
public class MessageDialogFragment extends DialogFragment {

    private static final String BUNDLE_KEY_TITLE = "BUNDLE_KEY_TITLE";
    private static final String BUNDLE_KEY_MESSAGE = "BUNDLE_KEY_MESSAGE";
    private static final String BUNDLE_KEY_POSITIVE_LABEL = "BUNDLE_KEY_POSITIVE_LABEL";
    private static final String BUNDLE_KEY_NEGATIVE_LABEL = "BUNDLE_KEY_NEGATIVE_LABEL";
    private static final String BUNDLE_KEY_CANCELABLE = "BUNDLE_KEY_CANCELABLE";
    private static final String KEY_REQUEST_CODE = "KEY_REQUEST_CODE";

    private Callback callback;

    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dismiss();
            if (callback != null) {
                callback.onSuccess(getRequestCode(), which);
            }
        }
    };

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle arguments = getArguments();

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final String title = arguments.getString(BUNDLE_KEY_TITLE);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }

        final String message = arguments.getString(BUNDLE_KEY_MESSAGE);
        if (!TextUtils.isEmpty(message)) {
            builder.setMessage(message);
        }

        final String positiveLabel = arguments.getString(BUNDLE_KEY_POSITIVE_LABEL);
        if (!TextUtils.isEmpty(positiveLabel)) {
            builder.setPositiveButton(positiveLabel, listener);
        }

        final String negativeLabel = arguments.getString(BUNDLE_KEY_NEGATIVE_LABEL);
        if (!TextUtils.isEmpty(negativeLabel)) {
            builder.setNegativeButton(negativeLabel, listener);
        }

        final boolean cancelable = arguments.getBoolean(BUNDLE_KEY_CANCELABLE);
        this.setCancelable(cancelable);

        return builder.create();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
        listener=null;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (callback != null) {
            callback.onCancel(getRequestCode());
        }
    }

    private int getRequestCode() {
        return getArguments().getInt(KEY_REQUEST_CODE);
    }

    public static final class Builder {
        private String title;
        private String message;
        private String positiveLabel;
        private String negativeLabel;
        private boolean cancelable = true;
        private int requestCode = -1;
        private String tag = "MessageDialog";

        private final AppCompatActivity activity;

        public <T extends AppCompatActivity & Callback> Builder(@NonNull T activity) {
            this.activity = activity;
        }

        public Builder title(@NonNull String title) {
            this.title = title;
            return this;
        }

        public Builder message(@NonNull String message) {
            this.message = message;
            return this;
        }

        public Builder message(@StringRes int res) {
            this.message = getContext().getString(res);
            return this;
        }

        public Builder cancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder positiveLabel(@NonNull String positiveLabel) {
            this.positiveLabel = positiveLabel;
            return this;
        }

        public Builder positiveLabel(@StringRes int res) {
            this.positiveLabel = getContext().getString(res);
            return this;
        }

        public Builder negativeLabel(@NonNull String negativeLabel) {
            this.negativeLabel = negativeLabel;
            return this;
        }

        public Builder negativeLabel(@StringRes int res) {
            this.negativeLabel = getContext().getString(res);
            return this;
        }

        public Builder requestCode(int requestCode) {
            this.requestCode = requestCode;
            return this;
        }

        public MessageDialogFragment build() {
            final Bundle bundle = new Bundle();
            bundle.putString(BUNDLE_KEY_TITLE, title);
            bundle.putString(BUNDLE_KEY_MESSAGE, message);
            bundle.putString(BUNDLE_KEY_POSITIVE_LABEL, positiveLabel);
            bundle.putString(BUNDLE_KEY_NEGATIVE_LABEL, negativeLabel);
            bundle.putBoolean(BUNDLE_KEY_CANCELABLE, cancelable);

            final MessageDialogFragment messageDialogFragment = new MessageDialogFragment();

            bundle.putInt(KEY_REQUEST_CODE, requestCode);

            messageDialogFragment.setArguments(bundle);

            return messageDialogFragment;
        }

        public MessageDialogFragment buildAndShow() {
            MessageDialogFragment messageDialogFragment = build();

            messageDialogFragment.show(activity.getSupportFragmentManager(), tag);

            return messageDialogFragment;
        }

        private Context getContext() {
            return activity.getApplicationContext();
        }


    }

    public interface Callback {
        /**
         *
         * @param requestCode
         * @param resultCode
         */
        void onSuccess(int requestCode, int resultCode);

        /**
         *
         * @param requestCode
         */
        void onCancel(int requestCode);

    }


}
