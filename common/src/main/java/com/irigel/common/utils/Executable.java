package com.irigel.common.utils;

/**
 * 将异常记录为log的Runnable类
 */
public abstract class Executable implements Runnable {

    @Override
    public void run() {
        try {
            execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected abstract void execute();
}