/**
 * @author larisa
 * Class for file input parsing and populating TripPlanner object with data.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Scanner;

public class InputFileParser {

    private String fileName;
    private TripPlanner tripPlanner;

    public InputFileParser(String fileName, TripPlanner tripPlanner) {
        this.fileName = fileName;
        this.tripPlanner = tripPlanner;
    }

    /**
     * Find and insert into the TripPlanner's tree the location described by the 3 strings:
     * continent, country, city.
     *
     * @param locations - array of strings with location names
     * @return - the node corresponding to the most specific location,
     * in this case, the city
     */
    public Node<Location> parseLocation(String[] locations) {

        Node<Location> location;

        Node<Location> continent = tripPlanner.findNode(tripPlanner.getRoot(), locations[0]);
        if (continent == null) { // i have to add this continent to my tree

            continent = new Node<>(new Location(locations[0]));
            Node<Location> country = new Node<>(new Location(locations[1]));
            Node<Location> city = new Node<>(new Location(locations[2]));

            location = city;
            country.addChild(city);
            continent.addChild(country);
            tripPlanner.getRoot().addChild(continent);


        } else { // continent already exists, we are looking for the country

            Node<Location> country = tripPlanner.findNode(continent, locations[1]);
            if (country == null) { // i have to add this country to my tree

                country = new Node<>(new Location(locations[1]));
                Node<Location> city = new Node<>(new Location(locations[2]));

                location = city;
                country.addChild(city);
                continent.addChild(country);

            } else { // country already exists, looking for the city

                Node<Location> city = tripPlanner.findNode(country, locations[2]);
                if (city == null) {  // i have to add this city to my tree
                    city = new Node<>(new Location(locations[2]));

                    country.addChild(city);
                }
                location = city;

            }
        }

        return location;
    }

    /**
     * Transform array of strings into enum set of enum elements.
     *
     * @param activities - array of strings with activity names
     * @return - the enum set containing the enum Activity's elements
     * corresponding to the received strings
     */
    public EnumSet<Activity> parseActivities(String[] activities) {

        ArrayList<Activity> tmpActivities = new ArrayList<>();
        for (int i = 0; i < activities.length; i++) {
            if (activities[i].equalsIgnoreCase("sightseeing")) {
                tmpActivities.add(Activity.SIGHTSEEING);
            } else if (activities[i].equalsIgnoreCase("beach")) {
                tmpActivities.add(Activity.BEACH);
            } else if (activities[i].equalsIgnoreCase("hiking")) {
                tmpActivities.add(Activity.HIKING);
            } else if (activities[i].equalsIgnoreCase("swimming")) {
                tmpActivities.add(Activity.SWIMMING);
            }
        }
        EnumSet<Activity> enumSet = EnumSet.copyOf(tmpActivities);
        return enumSet;
    }

    /**
     * Split line to get name of the vacation, location, price,
     * activities and availability of the vacation.
     * Open input files to see how the input is formatted.
     *
     * @param line - line read from file
     */
    public void parseLine(String line) {

        String[] inputs = line.split("\\s*,\\s*", 5);
        Vacation vacation = new Vacation(inputs[0]);

        String[] locations = inputs[1].split("\\s");
        vacation.setLocation(parseLocation(locations));

        vacation.setPricePerDay(Double.parseDouble(inputs[2]));

        String[] activities = inputs[3].split("\\s");
        vacation.setActivities(parseActivities(activities));

        String[] dates = inputs[4].split("\\s*:\\s*", 2);
        vacation.setAvailability(new Availability(dates[0] + "-2018", dates[1] + "-2018"));


        vacation.getLocation().getData().getVacationList().add(vacation);
        tripPlanner.getVacations().add(vacation);
    }

    /**
     * Open file to read data. Read no. of vacations.
     * Then, read no. of vacation lines.
     */
    public void parse() {

        try {
            Scanner scanner = new Scanner(new File(fileName));
            int noVacations = Integer.parseInt(scanner.nextLine());
            tripPlanner.setNoVacations(noVacations);
            for (int i = 0; i < noVacations; i++) {
                parseLine(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
