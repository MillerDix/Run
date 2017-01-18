package com.fanshuishui.server.task;

/**
 * Created by luoqiuyu on 2017/1/18.
 */
public interface Pool {
    void initial();
    Task obtain();
    void addTask(Task task);
}
