/**
 * Created by 23932 on 2018/8/13.
 */
import java.io.*;
import java.net.*;
import java.sql.Timestamp;

public class Network_Util {
    public boolean app_start(){
        start_con();
        return false;
    }

    public String connres(DataOutputStream out, DataInputStream in) throws IOException {
        // create 100KB payload
        String payload = String.format("%020480d",1);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String ts = (String.format("%64s", Long.toBinaryString(timestamp.getTime()))).replace(" ", "0");
        String msg = "0001" + ts + "00000000" + payload;
        out.writeUTF(msg);
        return in.readUTF();
    }

    public boolean start_con(){
        String host = "35.197.184.151";
        int port = 10002;
        /**
         * Set qs flag  := 0000
         * Set csf flag := 0000 (doesn't matter)
         * Set qos flag := 0000 (doesn't matter)
         */
        Flag sf  = new Flag(4);
        Flag csf = new Flag(4);
        Flag qos = new Flag(4);
        sf.setFlag(new int[]{0,0,0,0});
        csf.setFlag(new int[]{0,0,0,0});
        qos.setFlag(new int[]{0,0,0,0});

        // The payload for "connreq" is the MAC address in this case, can be set to password and username
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);

            byte[] mac = network.getHardwareAddress();
            StringBuffer buffer = new StringBuffer();

            // Change the byte format MAC address into string
            for (byte a : mac) {
                buffer.append(String.format("%02X", a));
            }

            Socket sock = new Socket(host, port);
            DataOutputStream out = new DataOutputStream(sock.getOutputStream());
            DataInputStream in = new DataInputStream(sock.getInputStream());

            // An example of gacp class. This can be used for GACP control
            GACP gacp = new GACP(sf, csf, qos, buffer.toString());

            // Setup the timestamp
            Timestamp tsendreq = new Timestamp(System.currentTimeMillis());
            gacp.setTimestamp((String.format("%64s", Long.toBinaryString(tsendreq.getTime()))).replace(" ", "0") + "");

            // 1. Send connreq
            // 2. Receive connack
            String connack = connreq(out, in,gacp.getGACP());
            // The time when server sent out the connack
            String time_ser_send_ack = connack.substring(4,68);
            long connack_timestamp = Long.valueOf(time_ser_send_ack, 2);


            System.out.println("CONNACK received!\n");


            // 3.Send connres
            // 4.Receive initend
            String initinfo = connres(out,in);

            // The time when server receives the connres
            String time_ser_send_iniend = initinfo.substring(4,68);
            long initend_timestamp = Long.valueOf(time_ser_send_iniend, 2);
            double dur = (initend_timestamp-connack_timestamp)/1000.0;

            System.out.println("It takes " + dur + "sec to send 40KB\n");
            System.out.println("The network speed is:" + String.format("%.2f",40/dur) + "KB/S\n");

            String initend_payload = initinfo.substring(76);
            System.out.println("The app state info is " + initend_payload);
            sock.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return false;

    }

    public static void main(String[] args) {
        Network_Util network_util = new Network_Util();
        network_util.app_start();
    }

    public boolean connctr(){
        return false;
    }

    public String connreq(DataOutputStream out, DataInputStream in, String payload) {
        try {
            out.writeUTF(payload);
            System.out.println("CONNREQ Sent!\n");
            String msg = in.readUTF();
            return msg;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
