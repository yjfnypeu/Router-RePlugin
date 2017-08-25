# Router-Plugin
[![](https://jitpack.io/v/yjfnypeu/Router-RePlugin.svg)](https://jitpack.io/#yjfnypeu/Router-RePlugin)


Router-Plugin为一款专用于对360的RePlugin框架使用Router作为兼容适配的框架。使更容易接入。

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

*此用法是基于[Router](https://github.com/yjfnypeu/Router)框架进行使用的。所以请未接触过的先移步到[Router](https://github.com/yjfnypeu/Router)框架进行配置了解*

1. 在每个插件或者宿主中。添加注册自身的编译时生成的路由表类：

```
// 添加路由规则。
RouterConfiguration.get().addRouteCreator(new RouterRuleCreator());
```

2. 在宿主中进行初始化：

```
HostRouterConfiguration.init(hostPackage, context);
```

3. 在插件中进行初始化：

```
PluginRouterConfiguration.init(hostPackage, alias, context);
```

- hostPackage代表宿主包名。用于启动远程路由服务。
- alias为RePlugin框架所提供的插件别名。

具体使用配置请参考：**[RePluginDemo](https://github.com/JumeiRdGroup/Router/tree/master/demos/RePluginDemo)**

### Advance

框架提供了一些额外接口。便于更好的进行使用,所有的配置项均在宿主-插件对应的配置入口类中进行配置：

1. [IUriConverter](https://github.com/yjfnypeu/Router-RePlugin/blob/master/core/src/main/java/com/lzh/router/replugin/core/IUriConverter.java)

- 作用：当用于启动路由的uri在路由表中未找到时，使用此装换器通过uri获取到对应的插件别名，默认为使用路由uri的scheme部分作为对应的插件别名。
- 配置：[HostRouterConfiguration/PluginRouterConfiguration].setConverter(converter)
- 接口说明：[点击查看说明文档](https://github.com/yjfnypeu/Router-RePlugin/blob/master/core/src/main/java/com/lzh/router/replugin/core/IUriConverter.java)

2. [IPluginCallback](https://github.com/yjfnypeu/Router-RePlugin/blob/master/core/src/main/java/com/lzh/router/replugin/core/IPluginCallback.java)

- 作用：用于在对插件路由的接入过程中。出现的问题进行通知。
- 配置：[HostRouterConfiguration/PluginRouterConfiguration].setCallback(callback)
- 接口说明：[点击查看说明文档](https://github.com/yjfnypeu/Router-RePlugin/blob/master/core/src/main/java/com/lzh/router/replugin/core/IPluginCallback.java)

3. [IUpdateCombine](https://github.com/yjfnypeu/Router-RePlugin/blob/master/host/src/main/java/com/lzh/router/replugin/update/IUpdateCombine.java)

- 作用：

    当使用框架提供的[UpdateRePluginCallbacks](https://github.com/yjfnypeu/Router-RePlugin/blob/master/host/src/main/java/com/lzh/router/replugin/update/UpdateRePluginCallbacks.java) 作为远程插件下载更新的入口时。
    此接口将被用于配合[UpdatePlugin](https://github.com/yjfnypeu/UpdatePlugin)框架使用, 用于根据插件别名组装真正的对应的更新接口url地址。
- 接口说明：[点击查看说明文档](https://github.com/yjfnypeu/Router-RePlugin/blob/master/host/src/main/java/com/lzh/router/replugin/update/IUpdateCombine.java)

4. 兼容Router全局路由回调：

- 作用：

    由于Router本身只提供了一个独立的全局路由回调。而Router-RePlugin框架本身内部是基于此全局回调来做的兼容配置。所以对于需要使用全局回调的需求。可以使用下方配置进行接入

- 配置：[HostRouterConfiguration/PluginRouterConfiguration].setRouteCallback(routeCallback)

### 联系作者
email: 470368500@qq.com

<a target="_blank" href="http://shang.qq.com/wpa/qunwpa?idkey=99e758d20823a18049a06131b6d1b2722878720a437b4690e238bce43aceb5e1"><img border="0" src="http://pub.idqqimg.com/wpa/images/group.png" alt="安卓交流会所" title="安卓交流会所"></a>

或者手动加入QQ群: 108895031

## License

[Apache v2.0](https://github.com/yjfnypeu/Router-RePlugin/blob/master/LICENSE)

