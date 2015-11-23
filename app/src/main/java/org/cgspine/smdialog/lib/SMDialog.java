package org.cgspine.smdialog.lib;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

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

    public abstract static class DialogBuilder{
        protected Context mContext;
        protected SMDialog mDialog;
        protected LayoutInflater mInflater;
        protected String mTitle;
        protected CharSequence mPositiveButtonText; // 正向按钮
        protected CharSequence mNegativeButtonText; // 反向按钮
        protected DialogInterface.OnClickListener mOnPositiveButtonClickListener;
        protected DialogInterface.OnClickListener mOnNegativeButtonClickListener;

        private LinearLayout  mRootView;
        private LinearLayout  mDialogLayout;
        private View          mAnchorView;
        private ViewGroup     mTitleContainer;
        private ScrollView    mScrollerView;
        private ViewGroup     mActionContainer;

        private int mAnchorHeight = 0;
        private int mScreenHeight = 0;
        private int mScrollHeight = 0;

        public DialogBuilder(Context context) {
            this.mContext = context;
            mInflater = LayoutInflater.from(context);
        }
        public DialogBuilder setPositiveButton(CharSequence text,
                                               final DialogInterface.OnClickListener listener) {
            this.mPositiveButtonText = text;
            this.mOnPositiveButtonClickListener = listener;
            return this;
        }

        public DialogBuilder setNegativeButton(CharSequence text,
                                               final DialogInterface.OnClickListener listener) {
            this.mNegativeButtonText = text;
            this.mOnNegativeButtonClickListener = listener;
            return this;
        }


        public DialogBuilder setTitle(String title) {
            if (title != null && title.length() > 0) {
                this.mTitle = title;
            }
            return this;
        }

        public void buildTitle(){
            if (mTitle != null && mTitle.length() != 0) {
                mTitleContainer = (ViewGroup) mInflater.inflate(
                        R.layout.dialog_title_layout, mRootView, false);
                TextView titleView = (TextView) mTitleContainer
                        .findViewById(R.id.dialog_title);
                titleView.setText(mTitle);

                mDialogLayout.addView(mTitleContainer);
            }
        }

        public abstract View onBuildContent(SMDialog dialog);

        public abstract int onGetScrollHeight();

        public void buildeAction(){
            boolean hasPositiveAction = (mPositiveButtonText != null && mPositiveButtonText
                    .length() != 0);
            boolean hasNegativeAction = (mNegativeButtonText != null && mNegativeButtonText
                    .length() != 0);
            if (hasPositiveAction || hasNegativeAction) {
                mActionContainer = (ViewGroup) mInflater.inflate(
                        R.layout.dialog_action_layout, mRootView, false);
                if (hasPositiveAction) {
                    Button positiveButton = (Button) mActionContainer
                            .findViewById(R.id.dialog_action_positive);
                    positiveButton.setVisibility(View.VISIBLE);
                    positiveButton.setText(mPositiveButtonText);
                    if (mOnPositiveButtonClickListener != null) {
                        positiveButton
                                .setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mOnPositiveButtonClickListener.onClick(
                                                mDialog, Dialog.BUTTON_POSITIVE);
                                    }
                                });
                    }
                }
                if (hasNegativeAction) {
                    Button negativeButton = (Button) mActionContainer
                            .findViewById(R.id.dialog_action_negative);
                    negativeButton.setVisibility(View.VISIBLE);
                    negativeButton.setText(mNegativeButtonText);
                    if (mOnNegativeButtonClickListener != null) {
                        negativeButton
                                .setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mOnNegativeButtonClickListener.onClick(
                                                mDialog, Dialog.BUTTON_NEGATIVE);
                                    }
                                });
                    }
                }
                mDialogLayout.addView(mActionContainer);
            }
        }

        public SMDialog build() {
            mDialog = new SMDialog(mContext);


            mRootView = (LinearLayout) mInflater.inflate(
                    R.layout.dialog_layout, null);
            mDialogLayout = (LinearLayout) mRootView.findViewById(R.id.dialog);
            mAnchorView = mRootView.findViewById(R.id.anchor);

            // title
            buildTitle();

            //content
            mScrollerView = new ScrollView(mContext);
            mScrollerView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, onGetScrollHeight()));
            mScrollerView.addView(onBuildContent(mDialog));
            mDialogLayout.addView(mScrollerView);

            // 操作
            buildeAction();

            //event
            bindEvent();

            mDialog.addContentView(mRootView, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));


            return mDialog;
        }

        private void bindEvent(){
            mAnchorView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                }
            });
            mRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                public void onGlobalLayout() {
//                    View mDecor = mDialog.getWindow().getDecorView();
//                    Rect r = new Rect();
//                    Log.d("mScrollHeight",""+onGetScrollHeight()); //收集设置的scrollViewHeight
//                    mDecor.getWindowVisibleDisplayFrame(r);
//                    Log.d("r.bottom",""+r.bottom); //收集getWindowVisibleDisplayFrame得到的r.bottom的值
//                    int mDecorHeight = mDecor.getHeight();
//                    Log.d("mDecorHeight", ""+mDecorHeight);//收集DecorView的高度
                    View mDecor = mDialog.getWindow().getDecorView();
                    Rect r = new Rect();
                    mDecor.getWindowVisibleDisplayFrame(r);
                    mScreenHeight = Math.max(mScreenHeight,r.bottom);
                    int anchorSholdHeight = mScreenHeight-r.bottom;
                    if(anchorSholdHeight != mAnchorHeight){
                        mAnchorHeight = anchorSholdHeight;
                        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mAnchorView.getLayoutParams();
                        lp.height = mAnchorHeight;
                        mAnchorView.setLayoutParams(lp);
                        LinearLayout.LayoutParams slp = (LinearLayout.LayoutParams) mScrollerView.getLayoutParams();
                        if(onGetScrollHeight() == ViewGroup.LayoutParams.WRAP_CONTENT){

                            mScrollHeight = Math.max(mScrollHeight,mScrollerView.getMeasuredHeight());
                        }else{
                            mScrollHeight =onGetScrollHeight();
                        }
                        if(mAnchorHeight == 0){
                            slp.height = mScrollHeight;
                        }else {
                            mScrollerView.getChildAt(0).requestFocus();
                            slp.height = mScrollHeight - mAnchorHeight;
                        }
                        mScrollerView.setLayoutParams(slp);
                    }else{
                        //如果内容过高,anchorSholdHeight=0,但实际下半部分会被截断,因此需要保护
                        //由于高度超过后,actionContainer并不会去测量和布局,所以这里拿不到action的高度,因此用比例估算一个值
                        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mDialogLayout.getLayoutParams();
                        int dialogLayoutMaxHeight = mScreenHeight - lp.bottomMargin - lp.topMargin - r.top;
                        int scrollLayoutHeight = mScrollerView.getMeasuredHeight();
                        if(scrollLayoutHeight>dialogLayoutMaxHeight * 0.8){
                            mScrollHeight = (int) (dialogLayoutMaxHeight * 0.8);
                            LinearLayout.LayoutParams slp = (LinearLayout.LayoutParams) mScrollerView.getLayoutParams();
                            slp.height = mScrollHeight;
                            mScrollerView.setLayoutParams(slp);
                        }
                    }
                }
            });
        }
    }

}
