package org.cgspine.smdialog.lib;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.cgspine.smdialog.R;

/**
 * Created by cgine on 16/7/27.
 */
public abstract class SMDialogBuilder {
    protected Context mContext;
    protected SMDialog mDialog;
    protected LayoutInflater mInflater;
    protected String mTitle;
    protected CharSequence mPositiveButtonText; // 正向按钮
    protected CharSequence mNegativeButtonText; // 反向按钮
    protected DialogInterface.OnClickListener mOnPositiveButtonClickListener;
    protected DialogInterface.OnClickListener mOnNegativeButtonClickListener;

    private LinearLayout mRootView;
    private LinearLayout  mDialogLayout;
    private View mAnchorView;
    private ViewGroup mTitleContainer;
    private ScrollView mScrollerView;
    private ViewGroup  mActionContainer;

    LinearLayout.LayoutParams mScrollViewLp;
    LinearLayout.LayoutParams mAnchorViewLp;

    private int mAnchorHeight = 0;
    private int mScreenHeight = 0;
    private int mScrollHeight = 0;
    private int mDialogMarginVertical = 0;

    public SMDialogBuilder(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        mDialogMarginVertical = Util.dp2px(context, 20);
    }
    public SMDialogBuilder setPositiveButton(CharSequence text,
                                           final DialogInterface.OnClickListener listener) {
        this.mPositiveButtonText = text;
        this.mOnPositiveButtonClickListener = listener;
        return this;
    }

    public SMDialogBuilder setNegativeButton(CharSequence text,
                                           final DialogInterface.OnClickListener listener) {
        this.mNegativeButtonText = text;
        this.mOnNegativeButtonClickListener = listener;
        return this;
    }


    public SMDialogBuilder setTitle(String title) {
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

    public void buildAction(){
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
        mAnchorViewLp = (LinearLayout.LayoutParams) mAnchorView.getLayoutParams();

        // title
        buildTitle();

        //content
        mScrollerView = new ScrollView(mContext);
        mScrollViewLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, onGetScrollHeight());
        mScrollerView.setLayoutParams(mScrollViewLp);
        mScrollerView.addView(onBuildContent(mDialog));
        mDialogLayout.addView(mScrollerView);

        // 操作
        buildAction();

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

                // 1. 记录上一次屏幕高度，获取新的屏幕高度
                int oldScreenHeight = mScreenHeight;
                mScreenHeight = Util.getScreenHeight(mContext);

                // 2. 获取内容区域最大允许高度
                int usefulMaxHeight = mScreenHeight - 2 * mDialogMarginVertical
                        - mScrollViewLp.bottomMargin - mScrollViewLp.topMargin;

                if(mTitleContainer != null){
                    usefulMaxHeight -= mTitleContainer.getMeasuredHeight();
                }
                if(mActionContainer != null){
                    //开始时未必能拿到它的高度，因为按钮可能还未被添加上去
                    usefulMaxHeight -= Math.max(mActionContainer.getMeasuredHeight(), Util.dp2px(mContext, 80));
                }

                if(onGetScrollHeight()>0 && usefulMaxHeight > onGetScrollHeight()) {
                    usefulMaxHeight = onGetScrollHeight();
                }

                int contentHeight = mScrollerView.getChildAt(0).getHeight();
                // 3. 屏幕高度发生变化的处理
                if(oldScreenHeight != mScreenHeight){
                    //屏幕高度发生变化, 一般是横竖屏发生改变
                    if(usefulMaxHeight < contentHeight){
                        mScrollViewLp.height = usefulMaxHeight;
                    }else{
                        mScrollViewLp.height = contentHeight;
                    }
                    mScrollerView.setLayoutParams(mScrollViewLp);
                }


                // 4. 键盘升降的处理
                View mDecor = mDialog.getWindow().getDecorView();
                final Rect r = new Rect();
                mDecor.getWindowVisibleDisplayFrame(r);
                int anchorShouldHeight = mScreenHeight - r.bottom;
                //部分手机这个计算会偏差一两像素，允许其有10像素的误差
                if (anchorShouldHeight >=mAnchorHeight+5 || anchorShouldHeight <= mAnchorHeight-5) {
                    mAnchorHeight = anchorShouldHeight;
                    if (anchorShouldHeight > 0 ) {//键盘升起
                        mAnchorViewLp.height = mAnchorHeight;
                        mAnchorView.setLayoutParams(mAnchorViewLp);

                        mScrollViewLp.height = mScrollerView.getHeight() - mAnchorHeight;
                        mScrollerView.setLayoutParams(mScrollViewLp);

                    } else if(anchorShouldHeight ==0) {//键盘降落
                        mScrollViewLp.height = Math.min(contentHeight, usefulMaxHeight);
                        mScrollerView.setLayoutParams(mScrollViewLp);
                        mAnchorViewLp.height = mAnchorHeight;
                        mAnchorView.setLayoutParams(mAnchorViewLp);
                    }

                }
            }
        });
    }
}
