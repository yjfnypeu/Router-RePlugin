package com.lzh.replugindemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lzh.nonview.router.Router;
import com.lzh.nonview.router.RouterConfiguration;
import com.lzh.nonview.router.activityresult.ActivityResultCallback;
import com.lzh.nonview.router.anno.RouterRule;

// 因为指定了baseUrl。 所以这里会使用baseUrl做组合。
@RouterRule("main")
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStackHelper.add(this);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        Router.create(((TextView)v).getText().toString())
                .resultCallback(new ActivityResultCallback() {
                    @Override
                    public void onResult(int resultCode, Intent data) {
                        Toast.makeText(MainActivity.this, "Host MainActivity Receive result" , Toast.LENGTH_SHORT).show();
                    }
                })
                .open(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStackHelper.remove(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        RouterConfiguration.get().dispatchActivityResult(this, requestCode, resultCode, data);
    }
}
