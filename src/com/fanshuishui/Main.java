package com.fanshuishui;

import com.fanshuishui.server.Server;
import com.fanshuishui.server.ServerThread;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        Server server = Server.getInstance(8888);
        server.strat();
//        try {
//            //create server, set port
//            ServerSocket serverSocket = new ServerSocket(8888);
//
//            Socket clientSocket = null;
//
//            // client count
//            int clientCount = 0;
//            System.out.println("***服务器即将启动，等待客户端的连接***");
//
//            while (true) {
//                //wait for client
//                clientSocket = serverSocket.accept();
//                // create thread for client
//                ServerThread serverThread = new ServerThread(clientSocket);
//                serverThread.start();
//                clientCount++;
//
//                System.out.println("客户端数量: " + clientCount);
//                InetAddress address = clientSocket.getInetAddress();
//                System.out.println("客户端地址: " + address.getHostAddress());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
