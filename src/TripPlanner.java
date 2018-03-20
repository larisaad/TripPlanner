/**
 * @author larisa
 * Singleton class representing this application.
 */

import java.util.ArrayList;

public class TripPlanner {


    private static TripPlanner instance = null;
    private ArrayList<Vacation> vacations;
    private int noVacations;
    private Node<Location> root;


    private TripPlanner() {
        vacations = new ArrayList<>();
        root = new Node<>(new Location("globe"));
    }

    /**
     * Method to instantiate this class only if instance does not
     * already exist.
     *
     * @return - instance of this object
     */
    public static TripPlanner getInstance() {
        if (instance == null) {
            instance = new TripPlanner(); //lazy instantiation of Singleton Pattern
        }
        return instance;
    }

    /**
     * Search for vacation with given name.
     *
     * @param vacationName
     * @return - object Vacation with requested name
     */
    public Vacation getVacationByName(String vacationName) {

        for (Vacation v : vacations) {
            if (v.getName().equalsIgnoreCase(vacationName)) {
                return v;
            }
        }
        return null;
    }


    /**
     * Goes through the node's subtree to get all vacations.
     *
     * @param node - node in the tree of locations
     * @return - an arraylist of vacations
     */
    public ArrayList<Vacation> getVacationsByLocation(Node<Location> node) {

        if (node.isLeaf()) {
            return node.getData().getVacationList();
        } else {
            ArrayList<Vacation> vacations = new ArrayList<>();
            for (Node<Location> n : node.getChildren()) {
                vacations.addAll(getVacationsByLocation(n));
            }
            return vacations;
        }

    }

    /**
     * Search in the node's subtree, for vacations with the availability given.
     *
     * @param node - node in the tree of locations
     * @param a    - availability
     * @return - an arraylist with vacations
     */
    public ArrayList<Vacation> getVacationsByAvailability(Node<Location> node, Availability a) {

        if (node.isLeaf()) {
            ArrayList<Vacation> vacations = new ArrayList<>();
            for (Vacation v : node.getData().getVacationList()) {
                if (!a.getBeginning().before(v.getAvailability().getBeginning())
                        && !a.getEnding().after(v.getAvailability().getEnding()))
                    vacations.add(v);
            }
            return vacations;
        } else {
            ArrayList<Vacation> vacations = new ArrayList<>();
            for (Node<Location> n : node.getChildren()) {
                vacations.addAll(getVacationsByAvailability(n, a));

            }
            return vacations;
        }

    }

    /**
     * Searches for a location starting with root.
     *
     * @param s - name of location to be searched
     * @return the subtree corresponding with the location name given
     */
    public Node<Location> findNode(String s) {
        return findNode(this.root, s);
    }

    /**
     * Searches for a location starting with a given node.
     *
     * @param n - the given node
     * @param s - name of the location to be searched
     * @return - node corresponding to the searched location
     */
    public Node<Location> findNode(Node<Location> n, String s) {
        if (n.getData().getName().equalsIgnoreCase(s)) {
            return n;
        } else {
            for (Node<Location> child : n.getChildren()) {
                Node<Location> result = findNode(child, s);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    public void printTree(Node<Location> tree) {
        System.out.println(tree);
        for (Node<Location> node : tree.getChildren()) {
            printTree(node);

        }
    }

    public ArrayList<Vacation> getVacations() {
        return vacations;
    }

    public void setNoVacations(int noVacations) {
        this.noVacations = noVacations;
    }

    public Node<Location> getRoot() {
        return root;
    }
}
