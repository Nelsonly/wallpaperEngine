package com.irigel.common.network.interfaces;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 *异步执行任务接口
 * @author zhangqing
 * @date 2/22/21
 */
public interface IOTask {

    /**
     * 得到文件处理文件
     */
    public void doTask();
}
