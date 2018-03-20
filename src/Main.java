/**
 * Main class for running the app.
 */

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Main {

    private static Scanner mySc;
    protected static Locale locale;
    protected static ResourceBundle messages;

    static {
        mySc = new Scanner(System.in);
    }

    public static void selectLanguage() {

        System.out.println("Type ro for Romanian and en for English");

        String language;
        if (mySc.nextLine().equalsIgnoreCase("ro")) {
            language = "ro";
        } else {
            language = "en";
        }

        locale = new Locale.Builder().setLanguage(language).build();
        messages = ResourceBundle.getBundle("resources/messages_" + language, locale);
    }

    public static void printWelcomeMessage() {
        System.out.println("\t\t\t\t" + messages.getString("welcome"));
        System.out.println(messages.getString("welcome_more") + "\n");

    }

    public static void printMenu() {

        System.out.println("\n\t\t\t\t\t\t" + messages.getString("options"));
        System.out.println(messages.getString("show_by_name/location"));
        System.out.println("\t----> " + messages.getString("example1"));
        System.out.println(messages.getString("show_top"));
        System.out.println("\t----> " + messages.getString("example2"));
        System.out.println(messages.getString("show_best_price"));
        System.out.println("\t----> " + messages.getString("example3"));
        System.out.println(messages.getString("menu"));
        System.out.println(messages.getString("exit") + "\n");
    }

    /**
     * Creating CommandParser object for each line read.
     * Calling parse() to parse command.
     *
     * @param tripPlanner
     * @return - true when the user entered exit
     */
    public static boolean readCommand(TripPlanner tripPlanner) {

        CommandParser parser = new CommandParser(mySc.nextLine(), tripPlanner);
        switch (parser.parse()) {

            case EXIT:
                return false;
            case INVALID:
                System.out.println(messages.getString("invalid_command"));
                break;
            case RESULT_NOT_FOUND:
                System.out.println(messages.getString("not_found"));
                break;
            case SUCCESS:
                break;

        }
        return true;

    }

    public static void main(String[] args) {

        /* First, we have to get the input from the input file. */
        TripPlanner tripPlanner = TripPlanner.getInstance();
        InputFileParser fileParser = new InputFileParser("input1.in", tripPlanner);
        fileParser.parse();

        /* Print messages for user. */
        selectLanguage();
        printWelcomeMessage();
        printMenu();

        /* Read input from user. */
        while (true) {
            if (!readCommand(tripPlanner)) return;

        }

    }

}
