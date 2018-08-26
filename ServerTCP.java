import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.sql.Timestamp;

public class ServerTCP {
	static ArrayList<String> mac_addresses = new ArrayList<>();
    static int port = 10002;


    public static void connack(DataOutputStream out) throws IOException {
    	// create 10KB payload
		String payload = String.format("%020480d",1);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String msg = "0001" + (String.format("%64s", Long.toBinaryString(timestamp.getTime()))).replace(" ", "0") + "00000000" + payload;
		out.writeUTF(msg);
		System.out.println("Connack sent!");
	}

	public static void initend(DataOutputStream out, String time) throws IOException {
		// create 10KB payload
		String payload = "App version: 1.0.0 \n";
		String msg = "0011" + (String.format("%64s", time + "00000000" + payload));
		out.writeUTF(msg);
		System.out.println("InitEnd sent!");
	}
    public static void main (String[] args) throws IOException {

        ServerSocket sSock = new ServerSocket(port);
        System.out.println("open at port:" + sSock.getLocalPort());
        Socket sock = sSock.accept();
        System.out.println("connect from:" + sock.getRemoteSocketAddress());

        DataInputStream in = new DataInputStream(sock.getInputStream());
        DataOutputStream out = new DataOutputStream(sock.getOutputStream());
        String buf;

	while (true) {
	    try {
		buf = in.readUTF();
	    } catch (EOFException oef) {
		break;
	    }
	    String sfheader = buf.substring(0,4);
	    switch (sfheader){
			case "0000":
				// 1. Receive CONNREQ
				System.out.println("connection REQ");
				String mac = buf.substring(76);
				mac_addresses.add(mac);
				System.out.println("connection from: " + mac);
				// 2. Send CONNACK
				connack(out);
				// 3. Receive CONNRES
				String connres = in.readUTF();
				Timestamp trecres = new Timestamp(System.currentTimeMillis());
				String tmptime = (String.format("%64s", Long.toBinaryString(trecres.getTime()))).replace(" ", "0");
				// 4. Send InitEnd
				initend(out, tmptime);
				break;

			case "1111":
				System.out.println("connection control");
				break;
		}
	}
        sock.close();
        sSock.close();
    }
}
