import java.time.LocalDateTime;

public class GiornoSettimana {

    public static String getGiorno(){
        int day = LocalDateTime.now().getDayOfWeek().getValue();
        if (day==7)
            day = 1;
        String[] giorniSettimana = {"Lunedì","Martedì","Mercoledì","Giovedì","Venerdì","Sabato"};
        String oggi =  giorniSettimana[day-1];
        System.out.println(oggi);
        return oggi;
    }

}
