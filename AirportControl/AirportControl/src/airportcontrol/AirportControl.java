 
package airportcontrol;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

 
public class AirportControl {

     
    public static void main(String[] args) {
          //Create 3 runway objects, r1, r2, r3 with names runway1, runway2 and runway3
        Runway r1 = new Runway("runway1");
        Runway r2 = new Runway("runway2");
        Runway r3 = new Runway("runway3");
        ClockClass cc = new ClockClass(); //Create object cc to get the time
        Random r = new Random();
        int totalAirCraft = r.nextInt(20); //total aircraft number, generated randomly from 0-19
        AirCraft.divideAirCraft(totalAirCraft); //pass the total aircraft number to method divideAirCraft in class AirCraft
        List<Thread> threadList = new ArrayList<Thread>(); //thread list to store aircraft threads to be joined
 
        System.out.println("Total aircrafts : " + totalAirCraft);
         
        Thread aircraft;
        AirCraft ac;
         
        for(int x = 1; x <= totalAirCraft; x++){
            int y = r.nextInt(2); //random number 0 or 1 generated for each loop
            if(y == 0){ //if y value is 0, then aircraft is going to land
                ac = new AirCraft("CP-" + x, cc, r1, r2, r3); //create aircraft object
                ac.setLandingqueue("CP-" + x); //pass the name of aircraft to landingQueue
                aircraft = new Thread(ac); //create instance of aircraft thread aicraft
                aircraft.setName("CP-" + x); //set the name for aircraft thread aircraft
                System.out.println(cc.getTheTime() + " Generated : " + aircraft.getName() + " going to land ");
                threadList.add(aircraft); //add the thread to threadList to be joined
                aircraft.start(); //start the thread
            }
            else{// if y value is 1 then aircraft is going to fly
                ac = new AirCraft("CP-" + x, cc, r1, r2, r3);
                ac.setDepartingqueue("CP-" + x); //pass the aircraft name to departingQueue
                aircraft = new Thread(ac);
                aircraft.setName("CP-" + x);
                System.out.println(cc.getTheTime() + " Generated : " + aircraft.getName() + " going to fly ");
                threadList.add(aircraft);
                aircraft.start();
            
                }
        }
        
        for (Thread th : threadList) { //iterate through the threadList
            try {
                //join all threads in threadList
                th.join();
            } catch (InterruptedException ex){}
        }
        
        //print the number of times each runway1, runway2 and runway3 was used
        System.out.println();
        System.out.println("Runway1 used: " + r1.getRunwayCount() + " times");
        System.out.println("Runway2 used: " + r2.getRunwayCount() + " times");
        System.out.println("Runway3 used: " + r3.getRunwayCount() + " times");
                 
                 
    }
    
    
}
