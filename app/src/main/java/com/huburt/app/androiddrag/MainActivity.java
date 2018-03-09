package com.huburt.app.androiddrag;

import android.content.ClipData;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private static final String BLUE = "BLUE";
    private static final String RED = "RED";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        final FrameLayout fl_blue = (FrameLayout) findViewById(R.id.fl_blue);
        final LinearLayout ll_red = (LinearLayout) findViewById(R.id.ll_red);
        final ImageView iv = (ImageView) findViewById(R.id.iv);

        iv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent();
                ClipData clipData = ClipData.newIntent("label", intent);
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                v.startDrag(null, shadowBuilder, iv, 0);
                //震动反馈
                v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                return true;
            }
        });

        fl_blue.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                //v 永远是设置该监听的view，这里即fl_blue
                String simpleName = v.getClass().getSimpleName();
//                Log.w(BLUE, "view name:" + simpleName);

                //获取事件
                int action = event.getAction();
                switch (action) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.i(BLUE, "开始拖拽");
                        iv.setVisibility(View.INVISIBLE);
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        Log.i(BLUE, "结束拖拽");
                        iv.setVisibility(View.VISIBLE);
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.i(BLUE, "拖拽的view进入监听的view时");
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        Log.i(BLUE, "拖拽的view离开监听的view时");
                        break;
                    case DragEvent.ACTION_DRAG_LOCATION:
                        float x = event.getX();
                        float y = event.getY();
                        long l = SystemClock.currentThreadTimeMillis();
                        Log.i(BLUE, "拖拽的view在BLUE中的位置:x =" + x + ",y=" + y);
                        break;
                    case DragEvent.ACTION_DROP:
                        Log.i(BLUE, "释放拖拽的view");
                        ImageView localState = (ImageView) event.getLocalState();
                        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.topMargin = (int) event.getY() - localState.getWidth() / 2;
                        layoutParams.leftMargin = (int) event.getX() - localState.getHeight() / 2;
                        ((ViewGroup) localState.getParent()).removeView(localState);
                        fl_blue.addView(localState, layoutParams);
                        break;
                }
                //是否响应拖拽事件，true响应，返回false只能接受到ACTION_DRAG_STARTED事件，后续事件不会收到
                return true;
            }
        });

        ll_red.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                String simpleName = v.getClass().getSimpleName();
//                Log.w(RED, "view name:" + simpleName);

                int action = event.getAction();
                switch (action) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.i(RED, "开始拖拽");
                        iv.setVisibility(View.INVISIBLE);
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        Log.i(RED, "结束拖拽");
                        iv.setVisibility(View.VISIBLE);
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.i(RED, "拖拽的view进入监听的view时");
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        Log.i(RED, "拖拽的view离开监听的view时");
                        break;
                    case DragEvent.ACTION_DRAG_LOCATION:
                        float x = event.getX();
                        float y = event.getY();
                        Log.i(RED, "拖拽的view在RED中的位置:x =" + x + ",y=" + y);
                        break;
                    case DragEvent.ACTION_DROP:
                        Log.i(RED, "释放拖拽的view");
                        ImageView localState = (ImageView) event.getLocalState();
                        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.topMargin = (int) event.getY() - localState.getWidth() / 2;
                        layoutParams.leftMargin = (int) event.getX() - localState.getHeight() / 2;
                        ((ViewGroup) localState.getParent()).removeView(localState);
                        ll_red.addView(localState, layoutParams);
                        break;
                }
                return true;
            }
        });

    }
}
