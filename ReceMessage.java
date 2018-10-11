import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by johnsont on 7/10/18.
 */
public class ReceMessage extends Thread {
    int port;

    public ReceMessage(int port){
        this.port = port;
    }

    public void run(){

        while(true) {
            ServerSocket sSock;
            try {
                sSock = new ServerSocket(port);

                System.out.println("open at port:" + sSock.getLocalPort() + " to receive info");
                Socket sock = sSock.accept();
                System.out.println("connect from:" + sock.getRemoteSocketAddress());
                DataInputStream in = new DataInputStream(sock.getInputStream());
                String tmp;
                
                // receive contact info
                if (port == 10004) {
                    tmp = in.readUTF();
                    SendInfoServer.setcon(tmp);
                    sSock.close();
                    sock.close();
                } 
                // receive walk info
                else if (port == 10005) {
                    tmp = in.readUTF();
                    SendInfoServer.setwalk(tmp);
                    sSock.close();
                    sock.close();
                } 
                // receive test info
                else if (port == 10501) {
                    System.out.println("Receive new test: \n");
                    tmp = in.readUTF();
                    SendInfoServer.settest(tmp);
                    sSock.close();
                    sock.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
