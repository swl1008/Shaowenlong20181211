package com.bwie.shaowenlong.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class RunHorse extends TextView {
    public RunHorse(Context context) {
        super(context);
    }

    public RunHorse(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RunHorse(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public boolean isFocused(){
        return true;
    }
}
