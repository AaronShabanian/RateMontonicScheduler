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
        Runnable firstRun = new Runnable(){
            public void run(){
                one();
            }
        };
        Thread firstThread= new Thread(firstRun);
        Runnable secondRun = new Runnable(){
            public void run(){
                two();
            }
        };
        Thread secondThread= new Thread(secondRun);
        Runnable thirdRun = new Runnable(){
            public void run(){
                three();
            }
        };
        Thread thirdThread = new Thread(thirdRun);
        Runnable fourthRun = new Runnable(){
            public void run(){
                four();
            }
        };
        Thread fourthThread= new Thread(fourthRun);
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
        fourth.release();
        try{
            Thread.sleep(15);
            fourth.acquire();   
        }
        catch(Exception e){

        }
        System.out.println("Break");
        fourth.release(); 
        try{
            Thread.sleep(5);
            fourth.acquire(); 
        }
        catch(Exception e){

        }
    }

    //Following functions run doWork() the specified number of times and check for semaphore synchronization
    public static void one(){
        boolean hasPermit=false;
        int counter=0;
        while(counter<100){
            try{ 
                hasPermit=first.tryAcquire();
                if(hasPermit){
                    doWork(arr);
                    counter++;
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
        int counter=0;
        while(counter<200){
            try{ 
                hasPermit=second.tryAcquire();
                if(hasPermit){
                    doWork(arr);
                    counter++;
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
        int counter=0;
        while(counter<400){
            try{ 
                hasPermit=third.tryAcquire();
                if(hasPermit){
                    doWork(arr);
                    counter++;
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
        int counter=0;
        while(counter<1600){
            try{ 
                hasPermit=fourth.tryAcquire();
                if(hasPermit){
                    doWork(arr);
                    counter++;
                    System.out.println(counter);
                }
            }finally {
                if (hasPermit) {
                   fourth.release();
                }
            }
        }
    }
}