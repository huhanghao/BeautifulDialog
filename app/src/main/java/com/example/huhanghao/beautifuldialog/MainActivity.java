package com.example.huhanghao.beautifuldialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huhanghao.beautifuldialog.floatwindow.FloatWindowManager;
import com.example.huhanghao.beautifuldialog.floatwindow.FloatWindowService;
import com.example.huhanghao.beautifuldialog.floatwindow.FloatWindowSmallView;


/**
 * 示例
 *
 * @author zhaokaiqiang
 * @ClassName: com.qust.demo.MainActivity
 * @Description:
 * @date 2014-10-23 下午11:30:13
 */
public class MainActivity extends Activity {

    private FloatWindowManager floatWindowManager;

    private Context context;
    private MyPopWindow myPopWindow;
    private View v;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_float);

        context = this;
        floatWindowManager = FloatWindowManager.getInstance(context);
    }

    /**
     * 显示小窗口
     *
     * @param view
     */
    public void show(View view) {
        // 需要传递小悬浮窗布局，以及根布局的id，启动后台服务
        Intent intent = new Intent(context, FloatWindowService.class);
        intent.putExtra(FloatWindowService.LAYOUT_RES_ID,
                R.layout.float_window_small);
        intent.putExtra(FloatWindowService.ROOT_LAYOUT_ID,
                R.id.small_window_layout);
        startService(intent);
    }

    /**
     * 显示二级悬浮窗
     *
     * @param view
     */
    public void showBig(View view) {
        floatWindowManager.createBigWindow(context);

        // 设置小悬浮窗的单击事件
        floatWindowManager.setOnClickListener(new FloatWindowSmallView.OnClickListener() {
            @Override
            public void click() {
                floatWindowManager.createBigWindow(context);
            }
        });

    }

    /**
     * 移除所有的悬浮窗
     *
     * @param view
     */
    public void remove(View view) {
        // 删除圆点和big框
        floatWindowManager.removeAll();

        // 移除我的window
        Window window = this.getWindow();
        if (v != null) {
//            window.get
            window.getWindowManager().removeView(v);
        }

    }


    public void showMy(View view) {
        LayoutInflater inflate = (LayoutInflater)
                this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflate.inflate(R.layout.test, null);
        TextView tv = (TextView) v.findViewById(R.id.message);
        tv.setText("heheheheh");
        View viewById = findViewById(R.id.third);

        myPopWindow = new MyPopWindow(this, 300, 0);
        myPopWindow.addView(v);
//		myPopWindow.showAsDropDown(viewById);

    }

    public void showShadow(View view) {
        final WindowManager windowManager = getWindowManager();
        Window window = getWindow();

        // 动态初始化图层
        img = new ImageView(this);
        img.setLayoutParams(new WindowManager.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT));
        img.setScaleType(ImageView.ScaleType.FIT_XY);
        img.setImageResource(R.drawable.guide);

        // 设置LayoutParams参数
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        // 设置显示的类型，TYPE_PHONE指的是来电话的时候会被覆盖，其他时候会在最前端，显示位置在stateBar下面，其他更多的值请查阅文档
//        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.type = WindowManager.LayoutParams.TYPE_APPLICATION;
        // 设置显示格式
        params.format = PixelFormat.RGBA_8888;
        // 设置对齐方式
        params.gravity = Gravity.LEFT | Gravity.TOP;
        // 设置宽高
        params.width = ScreenUtils.getScreenWidth(this);
        params.height = ScreenUtils.getScreenHeight(this);

        // 添加到当前的窗口上
        windowManager.addView(img, params);
//        window.addContentView(img,params);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                windowManager.removeView(img);
            }
        });

    }




    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        // 返回键移除二级悬浮窗
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            floatWindowManager.removeBigWindow();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
