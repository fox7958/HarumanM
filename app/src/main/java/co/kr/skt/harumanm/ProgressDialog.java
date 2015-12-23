package co.kr.skt.harumanm;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.widget.ProgressBar;

/**
 * Created by T on 2015-12-21.
 */
public class ProgressDialog extends Dialog {


    public static ProgressDialog show(Context context, CharSequence title,
                                        CharSequence message) {
        return show(context, title, message, false);
    }

    public static ProgressDialog show(Context context, CharSequence title,
                                        CharSequence message, boolean indeterminate) {
        return show(context, title, message, indeterminate, false, null);
    }

    public static ProgressDialog show(Context context, CharSequence title,
                                        CharSequence message, boolean indeterminate, boolean cancelable) {
        return show(context, title, message, indeterminate, cancelable, null);
    }


    public static ProgressDialog show(Context context, CharSequence title,
                                        CharSequence message, boolean indeterminate,
                                        boolean cancelable, OnCancelListener cancelListener) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle(title);
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(cancelListener);
/* The next line will add the ProgressBar to the dialog. */
        dialog.addContentView(new ProgressBar(context), new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
        dialog.show();

        return dialog;
    }

    public ProgressDialog(Context context) {
        super(context, R.style.NewDialog);
    }
}
