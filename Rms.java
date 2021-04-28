import java.util.Arrays;
public class Rms extends Thread {
    public Rms()
    {

    }
    public static void main(String[] args){
        doWork();
    }
    public static void doWork(){
        int[][] arr = new int[10][10];
        for(int i=0; i<arr.length; i++){
            for(int j=0; j<arr.length; j++){
                arr[i][j]=1;
            }
        }
        System.out.println(arr[3][4]);
    }
}