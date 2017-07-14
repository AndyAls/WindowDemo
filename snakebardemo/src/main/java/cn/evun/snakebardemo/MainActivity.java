package cn.evun.snakebardemo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.media.audiofx.BassBoost;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;
    private FloatingActionButton button;
    private WindowManager manager;
    private boolean show = true;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        initView();
        requestPermission();
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 10);
            }
        }
    }

    private void initView() {
        button = (FloatingActionButton) findViewById(R.id.button);
        TouchView touchView = (TouchView) findViewById(R.id.touchView);
        final TextView textView = (TextView) findViewById(R.id.text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (show) {
                    showWindow();
                } else {
                    if (view != null) {
                        manager.removeView(view);
                    }
                }
                show = !show;
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "哈拉", Snackbar.LENGTH_INDEFINITE).setDuration(1000).show();
            }
        });
        touchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void showWindow() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = 200;
        layoutParams.height = 200;
        layoutParams.gravity =  Gravity.CENTER_VERTICAL;
        layoutParams.format = PixelFormat.TRANSPARENT;
        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_BLUR_BEHIND
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        view = View.inflate(MainActivity.this, R.layout.window_view, null);
        TextView textView= (TextView) view;
        textView.setText("自定义弹窗");
        manager.addView(view, layoutParams);
        view.setOnTouchListener(new View.OnTouchListener() {//可以根据TouchView逻辑设置窗体跟着手指移动
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==10){
            Toast.makeText(MainActivity.this,"权限回调",Toast.LENGTH_SHORT).show();
        }
    }
}
