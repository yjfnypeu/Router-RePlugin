package com.lzh.replugindemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lzh.compiler.parceler.Parceler;
import com.lzh.nonview.router.Router;
import com.lzh.nonview.router.anno.RouterRule;
import com.lzh.nonview.router.route.IActivityRoute;
import com.lzh.nonview.router.route.IBaseRoute;
import com.lzh.replugindemo.pojo.User;

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
        IBaseRoute baseRoute = Router.create(((TextView)v).getText().toString()).getBaseRoute();
        if (baseRoute instanceof IActivityRoute) {
            ((IActivityRoute) baseRoute).setAnim(R.anim.anim_fade_in, R.anim.anim_fade_out);
        }
        baseRoute.open(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStackHelper.remove(this);
    }
}
