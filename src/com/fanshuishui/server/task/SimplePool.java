package com.fanshuishui.server.task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luoqiuyu on 2017/1/18.
 */
public class SimplePool implements Pool {

    private static final int NORMAL_SIZE = 5;

    private static SimplePool simplePool;

    private List<TaskThread> pool;
    private int size;
    private boolean initial;

    private SimplePool() {
        initial = false;
    }

    public static SimplePool getInstance() {
        if (simplePool == null) {
            synchronized (SimplePool.class) {
                if (simplePool == null)
                    simplePool = new SimplePool();
            }
        }
        return simplePool;
    }

    public boolean isInitial(){
        return initial;
    }

    @Override
    public void initial() {
        pool = new ArrayList<>();
        size = NORMAL_SIZE;
        initial = true;
    }

    @Override
    public Task obtain() {
        return new TaskImp();
    }

    @Override
    public void addTask(Task task) {
        boolean add = false;
        if (pool.size() < size) {

            TaskThread thread = new TaskThread();
            pool.add(thread);

            thread.addTask(task);
            thread.start();

        } else {
            for (int i = 0; i < size; i++) {
                if(pool.get(i).canAdd()){
                    pool.get(i).addTask(task);
                    add = true;
                    break;
                }
            }
            if(!add){
                System.out.println("任务已满，丢弃一个任务");
            }
        }
    }
}
