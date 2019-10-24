 
package airportcontrol;

import java.util.Calendar;

public class ClockClass {
    Calendar calendar;
    String time;
    
    // method to get the current time in hh:mm:ss
    public String getTheTime() {
        calendar = Calendar.getInstance(); 
        // get string of current time in form of hh:mm:ss
        time = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + 
                calendar.get(Calendar.SECOND);  
        return time;
    }
}
