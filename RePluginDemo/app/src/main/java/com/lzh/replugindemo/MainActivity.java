package com.lzh.replugindemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lzh.baselib.RePluginCallback;
import com.lzh.baselib.SerialData;
import com.lzh.compiler.parceler.Parceler;
import com.lzh.nonview.router.Router;
import com.lzh.nonview.router.anno.RouterRule;
import com.lzh.nonview.router.route.IActionRoute;
import com.lzh.nonview.router.route.IActivityRoute;
import com.lzh.nonview.router.route.IBaseRoute;
import com.lzh.replugindemo.pojo.User;
import com.qihoo360.replugin.RePlugin;

import java.io.Serializable;

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
        baseRoute.addExtras(Parceler.createFactory(null)
                .put("serialData", new SerialData().setName("haoge"))
                .getBundle());
        if (baseRoute instanceof IActivityRoute) {
            ((IActivityRoute) baseRoute).setAnim(R.anim.anim_fade_in, R.anim.anim_fade_out);
        } else if (baseRoute instanceof IActionRoute) {
            ((IActionRoute) baseRoute).addExtras(Parceler.createFactory(null)
                    .setForceConvert(true)
                    .put("user", new User("haoge", 18))
                    .getBundle());
        }
        baseRoute.open(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStackHelper.remove(this);
    }
}
