package org.cgspine.smdialog.lib;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import org.cgspine.smdialog.R;

/**
 * Created by cgspine on 15/11/22.
 */
public class SMDialog extends Dialog {
    public SMDialog(Context context) {
        super(context, R.style.dialog_theme_base);
        init();
    }

    public SMDialog(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    private void init() {
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }

    private void initDialogWidth() {
        Window window = getWindow();
        WindowManager.LayoutParams wmlp = window.getAttributes();
        wmlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        window.setAttributes(wmlp);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDialogWidth();
    }
}
