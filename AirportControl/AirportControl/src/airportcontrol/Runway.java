 
package airportcontrol;

 
public class Runway {
    private String name;  //runway name
    private int total = 0; //the total number of times each runway is used, initialized to 0
    
    //constructor for Runway class
    public Runway(String name) {
        this.name = name;
    }
    
    //return name of runway
    public String getName() {
        return name;
    }
    
    //set name of runway
    public void setName(String name) {
        this.name = name;
    }
    
    //class method to count number of times runway was used for each runway object
    public void countRunway(){
        total = total + 1;
    }
    
    //class method to return the number of times runway was used for each runway object
    public int getRunwayCount(){
        return total;
    }    
    
}
