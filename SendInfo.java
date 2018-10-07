import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnsont on 10/9/18.
 */

public class SendInfo{


    public static void main(String[] args) throws IOException {
        String host = "35.197.184.151";
        //String host = "localhost";

        int port = 10501;
        Socket sock = new Socket(host, port);
        DataOutputStream in = new DataOutputStream(sock.getOutputStream());
        String msg = "Hello this is a change to test";
        in.writeUTF(msg);

    }

}
