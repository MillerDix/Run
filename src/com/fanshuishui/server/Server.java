package com.fanshuishui.server;

import com.fanshuishui.server.task.SimplePool;
import com.fanshuishui.server.task.Task;
import com.fanshuishui.server.task.TaskImp;
import com.fanshuishui.server.task.TaskThread;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luoqiuyu on 2017/1/18.
 */
public class Server {

    private static Server mainServerProxy;
    private ServerSocket mainServer;
    private List<TaskThread> mainClients;

    private SimplePool simplePool;

    private Server(int port) {
        try {
            mainServer = new ServerSocket(port);
            mainClients = new ArrayList<>();
            simplePool = SimplePool.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Server getInstance(int port) {
        if (mainServerProxy == null) {
            synchronized (Server.class) {
                if (mainServerProxy == null)
                    mainServerProxy = new Server(port);
            }
        }
        return mainServerProxy;
    }

    public void strat() {
        if(!simplePool.isInitial()){
            simplePool.initial();
        }

        while (true) {
            try {
                Socket socket = mainServer.accept();
                if(socket != null) {
                    Task task = simplePool.obtain();
                    ((TaskImp) task).setSocket(socket);
                    simplePool.addTask(task);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
