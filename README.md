# Router-Plugin
[![](https://jitpack.io/v/yjfnypeu/Router-RePlugin.svg)](https://jitpack.io/#yjfnypeu/Router-RePlugin)


Router-Plugin是一款针对360的[RePlugin](https://github.com/Qihoo360/RePlugin)框架使用[Router](https://github.com/yjfnypeu/Router)作为路由跳转的兼容框架。

### Dependencies

RePlugin分为host与plugin包。所以针对RePlugin所做的配置。也分为了host与plugin包：

LastestVersion=[![](https://jitpack.io/v/yjfnypeu/Router-RePlugin.svg)](https://jitpack.io/#yjfnypeu/Router-RePlugin)


- 针对host：

```groovy
compile "com.github.yjfnypeu.Router-RePlugin:host:${LastestVersion}"
```
 **若使用框架所提供的UpdateRePluginCallbacks类做远程插件下载管理。则需要同时引入[UpdatePlugin](https://github.com/yjfnypeu/UpdatePlugin)框架使用。推荐使用！**
 
- 针对plugin:

```groovy
compile "com.github.yjfnypeu.Router-RePlugin:plugin:${LastestVersion}"
```

### Usage

[请参考Wiki使用说明](https://github.com/yjfnypeu/Router-RePlugin/wiki)

### ProGuard

```
-keep class com.lzh.nonview.router.**{*;}
-keep class com.lzh.router.replugin.**{*;}
-keep class * extends com.lzh.nonview.router.route.ActionSupport{*;}
-keep class * implements com.lzh.nonview.router.interceptors.RouteInterceptor{*;}
-keep class * implements com.lzh.nonview.router.route.RouteCallback{*;}
```

### 联系作者
email: 470368500@qq.com

<a target="_blank" href="http://shang.qq.com/wpa/qunwpa?idkey=99e758d20823a18049a06131b6d1b2722878720a437b4690e238bce43aceb5e1"><img border="0" src="http://pub.idqqimg.com/wpa/images/group.png" alt="安卓交流会所" title="安卓交流会所"></a>

或者手动加入QQ群: 108895031

## License

[Apache v2.0](https://github.com/yjfnypeu/Router-RePlugin/blob/master/LICENSE)

