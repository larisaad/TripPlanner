/**
 * @author larisa
 * Class for representing an interval of dates a vacation is available.
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Availability {

    private Date beginning;
    private Date ending;
    private static SimpleDateFormat format, printFormat;

    static {
        format = new SimpleDateFormat("dd-MMMM-yyyy");
        printFormat = new SimpleDateFormat("dd MMMM");
    }

    public Availability(String from, String to) {

        try {
            this.beginning = format.parse(from);
            this.ending = format.parse(to);
        } catch (ParseException e) {
            System.out.println(Main.messages.getString("date_incorrect"));
        }

    }

    public Date getBeginning() {
        return beginning;
    }

    public Date getEnding() {
        return ending;
    }

    @Override
    public String toString() {
        return "<" +
                " " + printFormat.format(beginning) +
                ", " + printFormat.format(ending) +
                '>';
    }
}
