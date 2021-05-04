import java.util.concurrent.*;

public class Rms extends Thread {
    private static Semaphore first;
    private static Semaphore second;
    private static Semaphore third;
    private static Semaphore fourth; 
    public static boolean done=false;
    private static long startTime=0;
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
                for(int i=0; i<160; i++){
                    one();
                }
            }
        };
        Rms secondThread = new Rms(){
            public void run(){
                for(int i=0; i<80; i++){
                    two();
                }
            }
        };
        Rms thirdThread = new Rms(){
            public void run(){
                for(int i=0; i<40; i++){
                    three();
                }
            }
        };
        Rms fourthThread = new Rms(){
            public void run(){
                for(int i=0; i<10; i++){
                    four();
                }
            }
        };
        scheduler.start();
        firstThread.start();
        secondThread.start();
        thirdThread.start();
        fourthThread.start();
        try{
            firstThread.join();
            secondThread.join();
            thirdThread.join();
            fourthThread.join();
            done=true;
        }
        catch(Exception e){

        }
        System.out.println("# of Times a thread was called");
        System.out.println("First Thread: "+ firstCounter );
        System.out.println("Second Thread: "+ secondCounter );
        System.out.println("Third Thread: "+ thirdCounter );
        System.out.println("Fourth Thread: "+ fourthCounter );
        System.out.println("Number of Overruns");
        System.out.println("First Thread: "+ firstOverrun );
        System.out.println("Second Thread: "+ secondOverrun );
        System.out.println("Third Thread: "+ thirdOverrun );
        System.out.println("Fourth Thread: "+ fourthOverrun );
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
    static boolean oneComplete=false;
    static boolean twoComplete=false;
    static boolean threeComplete=false;
    static boolean fourComplete=false;
    static int firstCounter=0;
    static int secondCounter=0;
    static int thirdCounter=0;
    static int fourthCounter=0;
    static int firstOverrun=0;
    static int secondOverrun=0;
    static int thirdOverrun=0;
    static int fourthOverrun=0;
    public static void schedule(){
        int firstChecker=0;
        int secondChecker=0;
        int thirdChecker=0;
        int fourthChecker=0;
        int genCounter=0;
        //System.out.println("Test");
        startTime=System.currentTimeMillis();
        while(done==false){
            if(oneComplete==false){
                if(firstChecker<1){
                    first.release(); 
                    firstChecker++;
                    firstCounter++;
                }
            }
            else if(twoComplete==false){
                if(secondChecker<1){
                    second.release();
                    secondChecker++; 
                    secondCounter++;
                }
                
            }
            else if(threeComplete==false){
                if(thirdChecker<1){
                    third.release();
                    thirdChecker++;
                    thirdCounter++;
                }
            }
            else if(fourComplete==false){
                if(fourthChecker<1){
                    fourth.release();
                    fourthChecker++;
                    fourthCounter++;
                }
            }
            if(System.currentTimeMillis()-startTime==10){
                genCounter++;
                startTime=System.currentTimeMillis();
                try{
                    first.tryAcquire();
                    firstChecker=0;
                }
                catch (Exception e){
                    
                }
                try{
                    second.tryAcquire();
                    secondChecker=0;
                }
                catch(Exception e){

                }
                try{
                    third.tryAcquire();
                    thirdChecker=0;
                }
                catch (Exception e){
                    
                }
                try{
                    fourth.tryAcquire();
                    fourthChecker=0;
                }
                catch(Exception e){

                }
                if(oneComplete==false){
                    firstOverrun++;
                }
                oneComplete=false;
                if(genCounter%2==0){
                    if(twoComplete==false){
                        secondOverrun++;
                    }
                    twoComplete=false;
                }
                if(genCounter%4==0){
                    if(threeComplete==false){
                        thirdOverrun++;
                    }
                    threeComplete=false;
                }
                if(genCounter%16==0){
                    if(fourComplete==false){
                        fourthOverrun++;
                    }
                    genCounter=0;
                    fourComplete=false;
                }    
            }
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
        oneComplete=true;
        first.tryAcquire();

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
        twoComplete=true;
        second.tryAcquire();
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
        threeComplete=true;
        third.tryAcquire();
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
                }
            }finally {
                if (hasPermit) {
                   fourth.release();
                }
            }
        }
        fourComplete=true;
        fourth.tryAcquire();
    }
}