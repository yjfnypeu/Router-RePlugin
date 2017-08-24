package com.lzh.router.replugin.update;

import org.lzh.framework.updatepluginlib.model.CheckEntity;

/**
 * 根据插件别名组装出远程插件地址。
 * Created by haoge on 2017/8/24.
 */
public interface IUpdateCombine {

    /**
     * 根据插件别名组装合成使用的插件地址。
     * @param alias 插件别名
     * @return 组装后的更新接口实体类。
     */
    CheckEntity combine(String alias);
}
