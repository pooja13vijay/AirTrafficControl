 
package airportcontrol;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

 
public class AirCraft implements Runnable{
    private String name;    //variable to store aircraft name
    private Runway run1, run2, run3;    //runway objects 
    private static int run1Avail = 0; //initalize 0 for variable run1Avail 
    private static int run2Avail  = 0; //initalize 0 for variable run2Avail 
    private ClockClass cc;
    //landing queue to store name of all aircrafts that are going to land
    BlockingQueue<String> landingQueue = new LinkedBlockingDeque<>(); 
    //departing queue to store name of all aircraft that is going to fly
    BlockingQueue<String> departingQueue = new LinkedBlockingDeque<>();

    //constructor to initialize each aircraft object
    public AirCraft(String name, ClockClass cc, Runway run1, Runway run2, Runway run3) {
        this.name = name;
        this.cc = cc;
        this.run1 = run1;
        this.run2 = run2;
        this.run3 = run3;
    }
    
    //method to return name of aircraft
    public String getName() {
        return name;
    }
    
    //method to set name of aircraft
    public void setName(String name) {
        this.name = name;
    }
    
    //method that accepts name of aircraft and put the aircraft name into landing queue
    public BlockingQueue<String> setLandingqueue(String nameAir){
        try{
            landingQueue.put(nameAir);
        }catch(InterruptedException ie ){}
        return landingQueue;
    }
    
    //method that accepts name of aircraft and put the aircaft name into departing queue.
    public void setDepartingqueue(String nameAir){
        try{
            departingQueue.put(nameAir);
        }catch(InterruptedException ie ){}
    }
    
    //method that accepts the total number of aircrafts and divide the total aircrafts equally between 3 runways
    public static void divideAirCraft(int num){
        if((num % 3) == 2 ){ // if total aircrafts divided by 3 runways has 2 as remainder
            run1Avail = num/3 +1; // run1Avail will be updated with division result added by 1
            run2Avail = num/3 + 1; // run2Avail will be updated with division result added by 1
        }else{ //if the total aircrafts divided by 3 runways has 0 or 1 as remainder
        run1Avail = num / 3;  //run1Avail will be updated with division result
        run2Avail = num / 3;  //run2Avail will be updated with division result  
        }
    }
    
    /*method flying that accepts option, aircraft name and runway name and allow aircraft thread
    to sleep for specified sleep duration depending on the option */
    public void flying(String option, String name, String runName){
        //print statement to specifiy time aicraft start flying and the runway name 
         System.out.println('\n' + cc.getTheTime() + " " + name + " is flying from " + runName);
        if(option == "None"){   //if there is no need to take longer time for aircraft to fly
         try{
               Thread.sleep(5000); //the aircraft thread sleeps for 5 seconds to fly
               //print statement to specify time aicraft finish flying and runway name
               System.out.println('\n' + cc.getTheTime() + " " + name + " has flown from " + runName);
            }catch(InterruptedException ie ){}
        }
        else{ // if the aicraft may take longer time to fly
            // generate random number between 5 to 10 and store in flyTime variable
            int flyTime = new Random().nextInt(6)  + 5 ; 
            try{
                //the time to sleep converted to seconds and thread sleeps for specified time to fly
                Thread.sleep(flyTime*1000); 
                //print statement to specify time aircraft finish flying and runway name
                System.out.println('\n' + cc.getTheTime() + " " + name + " has flown from " + runName);
            }catch(InterruptedException ie){}
        } 
      }
    
    /*method landing that accepts aircraft name and runway name and allow aircraft thread
    to sleep for 10 seconds to land */
    public void landing(String name, String runName){
        //print statement to specifiy time aicraft start landing and the runway name 
       System.out.println('\n' + cc.getTheTime() + " " + name + " is landing at " + runName);
        try{
                Thread.sleep(10000); //aircraft sleeps for 10 seconds to land
                //print statement to specify time aircraft finish landing and runway name
                System.out.println('\n' + cc.getTheTime() + " " + name + " has landed at " + runName);
            }catch(InterruptedException ie ){}
    }
    
    //method for runway1, similar to hasrunway2 and has runway3
    public void hasrunway1(){
            //get the current thread name and store it in waitingAiCraft
            String waitingAirCraft = Thread.currentThread().getName();
            synchronized(run1){ //synchronized block to synchronize run1 object
                //checks if the aircraft is going to land
                if (landingQueue.contains(this.getName())){
                   //call the landing method and pass current thread name and runway name
                 landing(Thread.currentThread().getName(), run1.getName());                    
                }
                else{// if aircraft is not going to land, its going to fly
                    /*if the value in waitingAirCraft is the name of current thread in synchronized block or if 
                    it is an aircraft that is going to land */
                    if(waitingAirCraft == Thread.currentThread().getName()|| (landingQueue.contains(waitingAirCraft))){
                        /*aircraft may take longer time to fly, call flying method and pass the option,
                        aircraft name and runway name*/
                         flying("longer", Thread.currentThread().getName(),run1.getName());
                     }
                    else{
                        /*aircraft will not take longer time to fly, call flying method and pass the option,
                        aircraft name and runway name*/
                         flying("None", Thread.currentThread().getName(),run1.getName());
                     }
                }
            }
    }
    
    //method for runway2, similar to hasrunway1
    public void hasrunway2(){
         String waitingAirCraft = Thread.currentThread().getName();
            synchronized(run2){ //synchronized block to synchronize run2 object
                if (landingQueue.contains(this.getName())){
                    landing(Thread.currentThread().getName(), run2.getName()); 
                }
                else{
                    if(waitingAirCraft == Thread.currentThread().getName()|| (landingQueue.contains(waitingAirCraft))){
                         flying("longer", Thread.currentThread().getName(),run2.getName());
                     }
                     else{
                         flying("None", Thread.currentThread().getName(),run2.getName());
                     }
                }
            }
    }
    
    //method for runway3, similar to hasrunway1
    public void hasrunway3(){
        String waitingAirCraft = Thread.currentThread().getName();
        synchronized(run3){ //synchronized block to synchronize run3 object
                if (landingQueue.contains(this.getName())){
                    landing(Thread.currentThread().getName(), run3.getName());
                }
                else{
                    if(waitingAirCraft == Thread.currentThread().getName()|| (landingQueue.contains(waitingAirCraft))){
                         flying("longer", Thread.currentThread().getName(),run3.getName());
                     }
                     else{
                         flying("None", Thread.currentThread().getName(),run3.getName());
                     }
                }
            }
        
    }
    
    //run method for threads
    public void run(){
        //check if the value in run1Avail is greater than 0
        if(run1Avail > 0){
            //decrease the run1Avail value by 1 by each thread
            run1Avail = run1Avail - 1;
            //call runway1 method
            hasrunway1();
            //increase the total runway1 usage by 1 
            run1.countRunway();
        }
        else
            //check if the value in run2Avail is greater than 0
            if(run2Avail > 0){
                //decrease the run2Avail value by 1 by each thread
                run2Avail = run2Avail -1;
                //call runway2 method
                hasrunway2();
                //increase total runway2 usage by 1
                run2.countRunway();
            }
            else{ // if the run1Avail and run2Avail is not greater than 0
                //call runway3 method
                hasrunway3();
                //increase the total runway3 usage by 1
                run3.countRunway();
            } 
   }
    
}
