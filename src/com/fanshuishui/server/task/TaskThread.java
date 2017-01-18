package com.fanshuishui.server.task;

import java.util.ArrayList;
import java.util.List;

import static com.fanshuishui.server.task.TaskImp.FINISH;
import static com.fanshuishui.server.task.TaskImp.NO_START;

/**
 * Created by luoqiuyu on 2017/1/18.
 */
public class TaskThread extends Thread {

    private boolean cancel;
    private List<Task> tasks;

    public TaskThread(){
        cancel = false;
        tasks = new ArrayList<>();
    }

    public void addTask(Task task, boolean notify){
        tasks.add(task);
        if(notify) {
            synchronized (this) {
                this.notify();
            }
        }
    }

    public void addTask(Task task){
        addTask(task, false);
    }

    public void cancel(){
        cancel = true;
    }

    public boolean canAdd(){
        return tasks.size() < 1;
    }

    private void stopThread(){
        this.interrupt();
    }

    @Override
    public void run() {
        while (true){
            if(cancel){
                stopThread();
            }

            if(tasks.size() > 0){
                if(tasks.get(0).finish() == NO_START) {
                    tasks.get(0).run();
                }else if(tasks.get(0).finish() == FINISH){
                    tasks.remove(0);
                    System.out.println();
                }
            } else if(tasks.size() == 0){
                synchronized (this){
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }
}
