package com.example.mystartmodetest;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "测试启动模式 " + MainActivity.class.getSimpleName();
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.startSecond);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                // 显式跳转
//                Intent intent = new Intent();
//                intent.setClassName(MainActivity.this, "com.example.mystartmodetest.SecondActivity");

                // 隐式跳转
//                Intent intent = new Intent(Intent.ACTION_DIAL); // 拨号页面
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("num", 1);
                intent.putExtra("name", "haojli");
                intent.putExtras(bundle);
                intent.setAction("haojli");
                Uri uri = Uri.parse("haojli://1111");
                intent.setData(uri);
//                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                startActivity(intent);
            }
        });

        Log.d(TAG, "onCreate: " + getTaskId());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent: " + getTaskId());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: " + getTaskId());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: " + getTaskId());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: " + getTaskId());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: " + getTaskId());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: " + getTaskId());
    }

    // 退出时保证onDestroy执行
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        finish();
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: " + getTaskId());
    }
}

/**
 * Android 中一个APP的主页面为mainActivity，启动这个mainActivity之后返回键返回桌面，只调用了onpause和onstop，
 * 并没有调用ondestroy，杀死APP也没有调用ondestroy，那ondestroy什么时候回调用？
 */
/**
 * 在Android中，`onDestroy()` 方法一般在以下两种情况下会被调用：
 *
 * 1. 当系统由于资源不足，需要杀死正在后台运行的App时，系统首先会调用 `onStop()`，然后调用 `onDestroy()`。
 * 然而，这种情况不常发生，因为当系统准备杀死进程之前，它会尝试通过调用 `onPause()` 或 `onStop()` 来保存尽可能多的状态。
 *
 * 2. 当你的Activity使用了 `finish()` 方法时。该方法用于显式关闭当前Activity，结束后，系统会立即调用 `onDestroy()`。
 *
 * 请注意，当你按返回键使Activity进入后台时，也就是从前台转移到后台，系统并不会立刻销毁这个Activity，
 * 而是调用`onPause()`和`onStop()`，不存在 `onDestroy()`。这是因为系统可能会将其保留在内存中以便快速重启，
 * 除非系统由于资源缺乏或者是程序调用了finish()方法才会去销毁这个Activity。
 *
 * 但是，为什么有时你要杀死App时，`onDestroy()`并不被调用呢？这是因为系统不保证调用 `onDestroy()`。
 * 在某些极端情况下，如程序崩溃或系统杀死进程等，`onDestroy()` 可能不会得到执行。
 * 在这种情况下，系统并不会提前通知用户，因此，有些清理工作可能并不会按照你的想法进行。
 *
 * 因此，对于需要保存的数据和状态，通常推荐在 `onPause()` 或 `onStop()` 方法中保存，而不是依赖 `onDestroy()`。
 *
 * 其实，是否在onPause()或者onStop()保存数据，取决于你的应用以及数据的性质。
 * 如果你要保存的数据很关键，并且你希望即使在最差的情况下也可以保存（例如：在Activity被系统杀死之前），那么在onPause()中保存数据可能是正确的选择。
 *
 * 然而，如果数据的保存可能需要耗费一些时间，并且可以在Activity完全暂停后进行，那么使用onStop()可能是更好的选择。
 * 在onPause()中进行资源密集型的操作可能会影响到新Activity的启动速度，因为新的Activity在旧Activity的onPause()返回后才能开始启动。
 *
 * 总的来说，具体应选择哪个方法保存数据，需要根据你的应用场景以及要保存数据的大小和重要性来判断。
 */