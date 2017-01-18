/**
 * Created by MillerD on 1/16/17.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {
    Socket socket = null;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        OutputStream os = null;
        PrintWriter pw = null;
        try {
            //读取客户端信息
            is = socket.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String info = null;
            while((info=br.readLine()) != null) {
                System.out.println("Client Said: " + info);
            }
//            info = br.readLine();
//            System.out.println("Client Said: " + info);
            socket.shutdownInput();

            // 获取输出流，发送消息给client
            os = socket.getOutputStream();
            pw = new PrintWriter(os);
            pw.write("Welcome!");
            pw.flush();//调用flush()方法将缓冲输出
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("finally");
            try {
                if(pw!=null)
                    pw.close();
                if(os!=null)
                    os.close();
                if(br!=null)
                    br.close();
                if(isr!=null)
                    isr.close();
                if(is!=null)
                    is.close();
                if(socket!=null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
