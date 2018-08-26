/**
 * Created by 23932 on 2018/8/13.
 * GENERAL APP CONNECTION PROTOCOL
 */


public class GACP {
    /**
     * SF:  State flag
     * CSF: Connection State flag
     * QoS: Quality of service
     * timestamp: The timestamp when the message is sent
     * payload: The message
     */
    private Flag SF = new Flag(4);
    private Flag CSF = new Flag(4);
    private Flag QoS = new Flag(4);
    private String timestamp;
    private String payload;

    public GACP(Flag sf, Flag csf, Flag qos, String payload){
        this.SF  = sf;
        this.CSF = csf;
        this.QoS = qos;
        this.payload = payload;
    }



    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getGACP(){
        return SF.getflag() + timestamp + CSF.getflag() + QoS.getflag() + payload;
    }
}
