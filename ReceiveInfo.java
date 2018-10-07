import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by johnsont on 10/9/18.
 */
public class ReceiveInfo {

    public class Contact{

        int contactId;

        String contactName;

        String contactNumber;

        String contactEmail;

        String contactLink;

    }


    public class Route {
        int id;

        int walkId;

        String x;

        String y;

        String elevation;

        int order;
    }

    public static void main(String[] args) throws IOException {
        String host = "35.197.184.151";
        //String host = "localhost";


        int port1 = 10002;

        Socket sock1 = new Socket(host, port1);

        int port2 = 10003;
        Socket sock2 = new Socket(host, port2);

        DataInputStream in1 = new DataInputStream(sock1.getInputStream());
        DataInputStream in2 = new DataInputStream(sock2.getInputStream());



        int port3 = 10500;
        Socket sock3 = new Socket(host, port3);
        DataInputStream in3 = new DataInputStream(sock3.getInputStream());



        String msg_contact = in1.readUTF();
        String msg_route = in2.readUTF();
        String msg_test= in3.readUTF();


        System.out.println(msg_test);
       // System.out.println(msg_contact);
       // System.out.println(msg_route);

        //Contact con = gson.fromJson(msg_contact, Contact.class);
        //System.out.println(con.contactEmail);
    }
}
