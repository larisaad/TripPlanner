/**
 * @author larisa
 * Location representation.
 */

import java.util.ArrayList;

public class Location {

    private String name;
    private ArrayList<Vacation> vacationList;

    public Location(String name) {
        this.name = name;
        vacationList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Vacation> getVacationList() {
        return vacationList;
    }

    @Override
    public String toString() {
        return name;
    }
}
