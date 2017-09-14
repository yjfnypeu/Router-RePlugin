package com.lzh.plugina;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lzh.baselib.SerialData;
import com.lzh.nonview.router.Router;
import com.lzh.nonview.router.anno.RouterRule;
import com.lzh.nonview.router.route.IActionRoute;
import com.lzh.nonview.router.route.IActivityRoute;
import com.lzh.nonview.router.route.IBaseRoute;

import butterknife.ButterKnife;
import butterknife.OnClick;

@RouterRule("main")
public class PluginA extends Activity {

    SerialData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin_a);
        data = (SerialData) getIntent().getExtras().getSerializable("serialData");
        System.out.println("data = " + data);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.toHost, R.id.toLogin, R.id.triggerActionHost, R.id.triggerActionPluginA, R.id.triggerActionUserCenter})
    void click(Button v) {
        String url = v.getText().toString();

        IBaseRoute baseRoute = Router.create(url).getBaseRoute();
        if (baseRoute instanceof IActivityRoute) {
            ((IActivityRoute) baseRoute).setAnim(R.anim.anim_fade_in, R.anim.anim_fade_out);
        }
        baseRoute.open(this);
    }

}
