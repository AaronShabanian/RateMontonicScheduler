import java.util.concurrent.*;

public class Rms extends Thread {
    private static Semaphore first;
    private static Semaphore second;
    private static Semaphore third;
    private static Semaphore fourth; 
    public Rms()
    {
        first = new Semaphore(0);
        second = new Semaphore(0);
        third = new Semaphore(0);
        fourth = new Semaphore(0);
    }
    //arr for doWork()
   
    public static double[][] arr = new double[10][10];
    public static void main(String[] args){
        //creation of 2d array for doWork function
        for(int i=0; i<arr.length; i++){
            for(int j=0; j<arr.length; j++){
                arr[i][j]=1.0;
            }
        }
        Rms scheduler =new Rms(){
            public void run(){
                schedule();
            }
        };
        Rms firstThread = new Rms(){
            public void run(){
                one();
            }
        };
        Rms secondThread = new Rms(){
            public void run(){
                two();
            }
        };
        Rms thirdThread = new Rms(){
            public void run(){
                three();
            }
        };
        Rms fourthThread = new Rms(){
            public void run(){
                four();
            }
        };
        scheduler.start();
        firstThread.start();
        secondThread.start();
        thirdThread.start();
        fourthThread.start();
        //Create 5 threads
    }
    //Scheduler method (Triggers the other 4 threads in time (uses semaphores))
    public static void doWork(double [][] arr){
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
    public static void schedule(){
        second.release();
    }

    //Following functions run doWork() the specified number of times and check for semaphore synchronization
    public static void one(){
        boolean hasPermit=false;
        for(int i=0; i<100; i++){
            try{ 
                hasPermit=first.tryAcquire();
                if(hasPermit){
                    doWork(arr);
                }
            }finally {
                if (hasPermit) {
                   first.release();
                }
            }
        }

    }
    public static void two(){
        boolean hasPermit=false;
        for(int i=0; i<200; i++){
            try{ 
                hasPermit=second.tryAcquire();
                if(hasPermit){
                    doWork(arr);
                }
            }finally {
                if (hasPermit) {
                   second.release();
                }
            }
        }
    }
    public static void three(){
        boolean hasPermit=false;
        for(int i=0; i<400; i++){
            try{ 
                hasPermit=third.tryAcquire();
                if(hasPermit){
                    doWork(arr);
                }
            }finally {
                if (hasPermit) {
                   third.release();
                }
            }
        }
    }
    public static void four(){
        boolean hasPermit=false;
        for(int i=0; i<1600; i++){
            try{ 
                hasPermit=fourth.tryAcquire();
                if(hasPermit){
                    doWork(arr);
                }
            }finally {
                if (hasPermit) {
                   fourth.release();
                }
            }
        }
    }
}