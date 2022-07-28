package com.tencent.world;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class ScreenCheckBox extends CheckBox {

    public ScreenCheckBox(Context context) {
        super(context);
        setOnCheckedChangeListener();
        initStyle();
    }

    //这个方法一定要写 否则会报错
    public ScreenCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnCheckedChangeListener();
        initStyle();

    }

    public ScreenCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnCheckedChangeListener();
        initStyle();
    }

    //这个方法是检测按钮初始化时是否是选中状态，如果是选中状态就要进行相应设置
    @SuppressLint("SetTextI18n")
    public void initStyle(){
        if (this.isChecked()){
            setText("这个方法是检测按钮初始化时是否是选中状态，如果是选中状态就要进行相应设置"+getText());
        }
    }

    //这个方法监控按钮选中状态的变化，并进行相应操作
    public void setOnCheckedChangeListener() {
        this.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        buttonView.setText("√" + getText());
                        setTextColor(Color.parseColor("#d52c34"));
                    } else {
                        buttonView.setText(getText().subSequence(1, getText().length()));
                        setTextColor(Color.parseColor("#000000"));
                    }
                }
            });
    }

}
