
public class Rms extends Thread {
    public Rms()
    {

    }
    public static void main(String[] args){
        //creation of 2d array for doWork function
        int[][] arr = new int[10][10];
        for(int i=0; i<arr.length; i++){
            for(int j=0; j<arr.length; j++){
                arr[i][j]=1;
            }
        }
        doWork(arr);
    }
    public static void doWork(int [][] arr){
        //Execution of doWork Function
        int counter=0;
        int place=0;
        for(int i=0; i<arr.length; i++){
            for(int j=0; j<arr.length; j++){
                for(int k=0; k<arr.length; k++){
                    while(counter<10){
                        arr[i][j]*=arr[k][place];
                        place+=5;
                        counter++;
                        arr[i][j]*=arr[k][place];
                        place-=4;
                        counter++;
                    }
                }
            }
        }
    }
}