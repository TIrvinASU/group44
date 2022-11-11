package group44.pizza.storage;

import java.io.*;
import java.util.ArrayList;

// This is the database class. Everything is currently stored in memory but by abstracting away all the
// "database" logic, we can easily swap it out in the future
public class Database {
    public static ArrayList<Order> orders = new ArrayList<Order>();
    public static ArrayList<String> asuIdList = new ArrayList<String>();

    private static final String orderFilePath = "order.txt";
    private static final String asuIdPath = "asuId.txt";

    // Everything is static. Is it bad practice? Yes. Do I care? No
    public static void addOrder(Order order) {
        Database.orders.add(order);
        writeData();
        displayCurrentStatus();
    }

    // We will remove orders based on their id?
    public static void removeOrderByAsuId(String asuID) {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getAsuID().equals(asuID)) {
                orders.remove(i);
                return;
            }
        }
    }

    private static void displayCurrentStatus() {
        System.out.printf("\nDATABASE STATUS\n");
        System.out.printf("%s\n", orders.toString());
    }

    public static void loadData() {
        orders = (ArrayList<Order>) readObjectFromFile(orderFilePath);
        asuIdList = (ArrayList<String>) readObjectFromFile(asuIdPath);

        if (orders == null) orders = new ArrayList<Order>();
        if (asuIdList == null) asuIdList = new ArrayList<String>();
    }

    // Call this whenever you wanna update the file
    public static void writeData() {
        writeObjectToFile(orders, orderFilePath);
        writeObjectToFile(asuIdList, asuIdPath);
    }

    private static Object readObjectFromFile(String path) {
        try {
            // Read orders from disk
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            Object obj = objectIn.readObject();

            fileIn.close();
            objectIn.close();

            return obj;
        } catch (Exception exception) {
            System.out.println("File does not exist");
        }
        return null;
    }

    private static void writeObjectToFile(Object obj, String path) {
        try {
            FileOutputStream fileOut = new FileOutputStream(new File(path));
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(obj);

            fileOut.close();
            objectOut.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
