package com.fanshuishui.server.task;

import java.io.*;
import java.net.Socket;

/**
 * Created by luoqiuyu on 2017/1/18.
 */
public class TaskImp implements Task {

    public static final int NO_START = 0;
    public static final int EXECUTING = 1;
    public static final int FINISH = 2;

    private Socket socket;
    private int finish;

    public TaskImp() {
        finish = NO_START;
    }

    public TaskImp(Socket socket) {
        finish = NO_START;
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        finish = EXECUTING;

        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        OutputStream os = null;
        PrintWriter pw = null;
        //读取客户端信息
        try {
            is = socket.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String info = null;
            while ((info = br.readLine()) != null) {
                System.out.println("Client Said: " + info);
            }
            socket.shutdownInput();

            // 获取输出流，发送消息给client
            os = socket.getOutputStream();
            pw = new PrintWriter(os);
            pw.write("Welcome!");
            pw.flush();//调用flush()方法将缓冲输出
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            finish = FINISH;
            System.out.println("finally");
            try {
                if (pw != null)
                    pw.close();
                if (os != null)
                    os.close();
                if (br != null)
                    br.close();
                if (isr != null)
                    isr.close();
                if (is != null)
                    is.close();
                if (socket != null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int finish() {
        return finish;
    }
}
