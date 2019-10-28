import java.util.*;
import java.text.*;
 
class DateTimeExample {
   public String Fetch(String...s) {
       DateFormat dateFormate = new SimpleDateFormat("dd-MM-yy   hh:mm:ss");
       Date date = new Date();
       String l=dateFormate.format(date);
       return l;

    }
}