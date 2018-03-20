/**
 * @author larisa
 * Class for parsing the introduced commands and executing them, calling TripPlanner's functions.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class CommandParser {

    private String command;
    private TripPlanner tripPlanner;

    public CommandParser(String command, TripPlanner tripPlanner) {

        this.command = command;
        this.tripPlanner = tripPlanner;
    }

    /**
     * Parsing find vacations by a place or name command.
     *
     * @param st - string tokenizer object containing the command
     * @return - command status
     */
    public Command parseFilterCmd(StringTokenizer st) {

        if (st.hasMoreTokens()) {

            String vacationName = st.nextToken(); // get vacation from stdin

            // if a location is entered, then get all vacations from that location
            Node<Location> locationNode = tripPlanner.findNode(vacationName);
            ArrayList<Vacation> vacations = null;
            if (locationNode != null) {
                vacations = tripPlanner.getVacationsByLocation(locationNode);
            }
            // if the name of a place is entered, then get the vacation if there is one
            Vacation v = tripPlanner.getVacationByName(vacationName);

            if ((vacations == null || vacations.isEmpty())
                    && v == null) return Command.RESULT_NOT_FOUND;
            else {
                if (vacations != null && !vacations.isEmpty()) {
                    System.out.println(vacations);
                } else if (v != null) {
                    System.out.println(v);
                }
                return Command.SUCCESS;
            }

        } else return Command.INVALID;

    }

    /**
     * Parsing top vacations form a location and within a given period command.
     *
     * @param st - string tokenizer object with de command
     * @return - command status
     */
    public Command parseTopCmd(StringTokenizer st) {

        if (st.hasMoreTokens()) {

            String place = st.nextToken(); // get place from stdin

            // looking for the location in my tree
            Node<Location> locationNode = tripPlanner.findNode(place);
            ArrayList<Vacation> vacations = null;

            if (st.hasMoreTokens()) { // if availability is given, parse it

                String interval = st.nextToken();
                String[] dates = interval.split("\\s*:\\s*", 2); // splitting input to get dates
                if (dates.length < 2) return Command.INVALID;

                // creating availability object with the corresponding dates
                Availability av = new Availability(dates[0] + "-2018", dates[1] + "-2018");
                if (av != null && av.getEnding() != null && av.getBeginning() != null
                        && locationNode != null) { // if location exists
                    vacations = tripPlanner.getVacationsByAvailability(locationNode, av);
                } else if (av == null || av.getBeginning() == null || av.getEnding() == null)
                    return Command.SUCCESS;

            } else if (locationNode != null) {
                // if a period is not given from user, i am ignoring availabilities
                vacations = tripPlanner.getVacationsByLocation(locationNode);
            }

            if (vacations == null || vacations.isEmpty()) return Command.RESULT_NOT_FOUND;
            // sorting result and choosing best 5
            Collections.sort(vacations);
            List<Vacation> result = vacations.size() < 5
                    ? vacations.subList(0, vacations.size()) : vacations.subList(0, 5);
            System.out.println(result);

        } else return Command.INVALID;

        return Command.SUCCESS;
    }

    /**
     * Parsing "Best place to practice activity" command.
     *
     * @param st - StringTokenizer object with the command
     * @return - command status
     */
    public Command parseBestCmd(StringTokenizer st) {

        if (st.hasMoreTokens()) {
            // get activity from stdin
            Activity activity = Activity.valueOf(st.nextToken().toUpperCase());

            double minPrice = 1000000;
            Vacation cheaperVacation = null;

            /* going through all vacations and selecting those which have this activity
            * and have a cheaper price than the previous ones*/
            for (Vacation v : tripPlanner.getVacations()) {
                if (v.getActivities().contains(activity) && v.getPricePerDay() < minPrice) {
                    cheaperVacation = v;
                    minPrice = v.getPricePerDay();
                }
            }
            if (cheaperVacation == null) return Command.RESULT_NOT_FOUND;
            else {
                System.out.println(cheaperVacation);
                return Command.SUCCESS;
            }

        } else return Command.INVALID;

    }

    /**
     * Parse command to see which type of command it is.
     *
     * @return - command status to be processed in main
     */
    public Command parse() {

        StringTokenizer st = new StringTokenizer(this.command);
        if (st.hasMoreTokens()) {

            String cmdType = st.nextToken();
            if (cmdType.equalsIgnoreCase("exit")) {
                return Command.EXIT;
            } else if (cmdType.equalsIgnoreCase("vacation")) {
                return parseFilterCmd(st);
            } else if (cmdType.equalsIgnoreCase("top")) {
                return parseTopCmd(st);
            } else if (cmdType.equalsIgnoreCase("cheapest")) {
                return parseBestCmd(st);
            } else if (cmdType.equalsIgnoreCase("menu")) {
                Main.printMenu();
                return Command.SUCCESS;
            }

        }
        return Command.INVALID;

    }

}
