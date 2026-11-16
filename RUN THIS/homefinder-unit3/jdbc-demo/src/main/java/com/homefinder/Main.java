package com.homefinder;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/** Console menu to try every JDBC operation. */
public class Main {

    public static void main(String[] args) {
        PropertyDAO dao = new PropertyDAO();
        Scanner in = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n===== HomeFinder (JDBC) =====");
            System.out.println("1. List all properties");
            System.out.println("2. Find property by id");
            System.out.println("3. Search by status and max price");
            System.out.println("4. Add a property");
            System.out.println("5. Update a price");
            System.out.println("6. Delete a property");
            System.out.println("7. Count properties");
            System.out.println("8. List properties with agent names (JOIN)");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            try {
                String choice = in.nextLine().trim();

                switch (choice) {
                    case "1" -> {
                        for (Property p : dao.findAll()) {
                            System.out.println(p);
                        }
                    }
                    case "2" -> {
                        System.out.print("Id: ");
                        Property p = dao.findById(Integer.parseInt(in.nextLine().trim()));
                        System.out.println(p == null ? "Not found." : p);
                    }
                    case "3" -> {
                        System.out.print("Status (For Sale / For Rent): ");
                        String status = in.nextLine().trim();
                        System.out.print("Max price: ");
                        BigDecimal max = new BigDecimal(in.nextLine().trim());
                        List<Property> result = dao.findByStatusAndMaxPrice(status, max);
                        if (result.isEmpty()) System.out.println("No matches.");
                        for (Property p : result) System.out.println(p);
                    }
                    case "4" -> {
                        Property p = new Property();
                        System.out.print("Title: ");      p.setTitle(in.nextLine().trim());
                        System.out.print("Price: ");      p.setPrice(new BigDecimal(in.nextLine().trim()));
                        System.out.print("Location: ");   p.setLocation(in.nextLine().trim());
                        System.out.print("Bedrooms: ");   p.setBedrooms(Integer.parseInt(in.nextLine().trim()));
                        System.out.print("Bathrooms: ");  p.setBathrooms(Integer.parseInt(in.nextLine().trim()));
                        System.out.print("Area (sqft): "); p.setArea(Integer.parseInt(in.nextLine().trim()));
                        System.out.print("Type (Apartment/House/Villa): "); p.setType(in.nextLine().trim());
                        System.out.print("Status (For Sale / For Rent): "); p.setStatus(in.nextLine().trim());
                        p.setImageUrl("https://picsum.photos/id/1040/600/400");
                        p.setDescription("Added from the Java console.");
                        p.setAgentId(1);
                        dao.insert(p);
                        System.out.println("Added with id " + p.getId());
                    }
                    case "5" -> {
                        System.out.print("Id: ");
                        int id = Integer.parseInt(in.nextLine().trim());
                        System.out.print("New price: ");
                        BigDecimal price = new BigDecimal(in.nextLine().trim());
                        int rows = dao.updatePrice(id, price);
                        System.out.println(rows + " row(s) updated.");
                    }
                    case "6" -> {
                        System.out.print("Id: ");
                        int rows = dao.delete(Integer.parseInt(in.nextLine().trim()));
                        System.out.println(rows + " row(s) deleted.");
                    }
                    case "7" -> System.out.println("Total properties: " + dao.count());
                    case "8" -> dao.printPropertiesWithAgents();
                    case "0" -> running = false;
                    default -> System.out.println("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            }
        }
        System.out.println("Bye!");
    }
}
