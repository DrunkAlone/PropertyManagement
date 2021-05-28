package com.pro.propertymanagepro.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.pro.propertymanagepro.R;

/**
 * 功能:
 *
 * @author
 * @time 2018/11/13
 * @email
 */
public class VoteView extends LinearLayout {
    private Context context;
    private LinearLayout llContent;
    private TextView tvApproveOfTv, tvApproveOf, tvOppose, tvOpposeTv;

    public VoteView(Context context) {
        super(context);
    }

    public VoteView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public VoteView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public VoteView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        initView();
    }

    private void initView() {
        if (isInEditMode())
            return;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.item_vote, this);
        llContent = (LinearLayout) v.findViewById(R.id.llContent);
        tvApproveOf = (TextView) v.findViewById(R.id.tvApproveOf);
        tvOppose = (TextView) v.findViewById(R.id.tvOppose);
        tvApproveOfTv = (TextView) v.findViewById(R.id.tvApproveOfTv);
        tvOpposeTv = (TextView) v.findViewById(R.id.tvOpposeTv);
    }

    public void setWeightForView(float scaleApproveOf) {
        tvApproveOf.setLayoutParams(new LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, scaleApproveOf));//设置赞成的权重
        tvOppose.setLayoutParams(new LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1 - scaleApproveOf));//设置反对的权重
        if (scaleApproveOf == 0) {
            tvApproveOfTv.setBackgroundResource(R.drawable.left_blue_radius_shape);
            tvOpposeTv.setBackgroundResource(R.drawable.right_radius_shape);
            return;
        }
        if (scaleApproveOf == 1) {
            tvApproveOfTv.setBackgroundResource(R.drawable.left_radius_shape);
            tvOpposeTv.setBackgroundResource(R.drawable.right_red_radius_shape);
            return;
        }
        tvApproveOfTv.setBackgroundResource(R.drawable.left_radius_shape);
        tvOpposeTv.setBackgroundResource(R.drawable.right_radius_shape);
    }

    public void setApproveOf(String approveOfText) {
        if (TextUtils.isEmpty(approveOfText)) return;
        tvApproveOfTv.setText(approveOfText);
    }

    public void setOppose(String opposeText) {
        if (TextUtils.isEmpty(opposeText)) return;
        tvOpposeTv.setText(opposeText);
    }
}
