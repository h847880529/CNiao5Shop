package com.example.hwg.cniao5shop.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hwg.cniao5shop.R;

/**
 * Created by hwg on 2017/2/12.
 */

public class CnToolbar extends Toolbar {

    private LayoutInflater mInflater ;
    private TextView mTextTitle ;
    private EditText mSearchView ;
    private ImageButton mRightImageButton ;

    public CnToolbar(Context context) {
        this(context , null) ;
    }

    public CnToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs , 0);
    }

    public CnToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }


    private void initView() {

        View view = mInflater.inflate(R.layout.toolbar , null) ;

        mTextTitle = (TextView) view.findViewById(R.id.toolbar_title) ;
        mSearchView = (EditText) view.findViewById(R.id.toolbar_searchview) ;
        mRightImageButton = (ImageButton) view.findViewById(R.id.toolbar_rightButton) ;

        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT , Gravity.CENTER_HORIZONTAL) ;

        addView(view , lp);

    }
}
