package com.lzh.plugina;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.lzh.nonview.router.Router;
import com.lzh.nonview.router.RouterConfiguration;
import com.lzh.nonview.router.activityresult.ActivityResultCallback;
import com.lzh.nonview.router.anno.RouterRule;

import butterknife.ButterKnife;
import butterknife.OnClick;

@RouterRule("main")
public class PluginA extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin_a);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.toHost, R.id.toLogin, R.id.triggerActionHost, R.id.triggerActionPluginA, R.id.triggerActionUserCenter})
    void click(Button v) {
        String url = v.getText().toString();
        Router.create(url)
                .resultCallback(new ActivityResultCallback() {
                    @Override
                    public void onResult(int resultCode, Intent data) {
                        Toast.makeText(PluginA.this, "Plugin A Receive result" , Toast.LENGTH_SHORT).show();
                    }
                })
                .open(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        RouterConfiguration.get().dispatchActivityResult(this, requestCode, resultCode, data);
    }
}
