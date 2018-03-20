/**
 * @author larisa
 * Vacation representation.
 * Implements comparable for sorting vacation by medium price per day.
 */

import java.util.EnumSet;

public class Vacation implements Comparable<Vacation> {

    private String name;
    private Node<Location> location;
    private double pricePerDay;
    private EnumSet<Activity> activities;
    private Availability availability;

    public Vacation(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Node<Location> getLocation() {
        return location;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public EnumSet<Activity> getActivities() {
        return activities;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setLocation(Node<Location> location) {
        this.location = location;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public void setActivities(EnumSet<Activity> activities) {
        this.activities = activities;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "\nVacation to " + name +
                "\n\t -you would stay in " + location.getData().getName() +
                "\n\t -the medium price per day would be " + pricePerDay + "€" +
                "\n\t -activities there are " + activities +
                "\n\t -and you can go there " + availability;
    }

    @Override
    public int compareTo(Vacation vacation) {
        return (int) (this.pricePerDay - vacation.pricePerDay);
    }
}
