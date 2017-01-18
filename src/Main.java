import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class Main {

    public static void main(String[] args) {
        try {
            //create server, set port
            ServerSocket serverSocket = new ServerSocket(8888);

            Socket clientSocket = null;

            // client count
            int clientCount = 0;
            System.out.println("***服务器即将启动，等待客户端的连接***");

            while (true) {
                //wait for client
                clientSocket = serverSocket.accept();
                // create thread for client
                ServerThread serverThread = new ServerThread(clientSocket);
                serverThread.start();
                clientCount++;

                System.out.println("客户端数量: " + clientCount);
                InetAddress address = clientSocket.getInetAddress();
                System.out.println("客户端地址: " + address.getHostAddress());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
