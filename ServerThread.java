import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by johnsont on 7/10/18.
 */
public class ServerThread extends Thread {
    int port;




    public ServerThread(int port){
        this.port = port;

    }

    // Run as a thread
    public void run(){

        while(true) {
            ServerSocket sSock;
            try {
                sSock = new ServerSocket(port);

                // receive info from different port
                System.out.println("open at port:" + sSock.getLocalPort());
                Socket sock = sSock.accept();
                System.out.println("connect from:" + sock.getRemoteSocketAddress());
                DataOutputStream out = new DataOutputStream(sock.getOutputStream());
                if (port == 10002) {
                    out.writeUTF(SendInfoServer.newCon);
                    sSock.close();
                    sock.close();
                } else if (port == 10003) {
                    out.writeUTF(SendInfoServer.newwalk);
                    sSock.close();
                    sock.close();
                } else if (port == 10500) {
                    out.writeUTF(SendInfoServer.testmes);
                    sSock.close();
                    sock.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


}
