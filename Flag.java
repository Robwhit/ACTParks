/**
 * Created by 23932 on 2018/8/13.
 */
public class Flag {
    private int[] flag;
    public Flag(int size){
        this.flag = new int[size];
    }

    public void setFlag(int[] value){
        flag = value;
    }

    public String getflag(){
        String tmp = "";
        for (int bit: flag){
            tmp += bit + "";
        }
        return tmp;
    }
}
