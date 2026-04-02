import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.LinkedList;
import java.util.LinkedHashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

class InvalidCapacityException extends Exception {
    InvalidCapacityException(String message) {
        super(message);
    }
}

class Bogie {
    String name;
    int capacity;

    Bogie(String name, int capacity) throws InvalidCapacityException {
        if (capacity <= 0) {
            throw new InvalidCapacityException("Capacity must be greater than zero");
        }
        this.name = name;
        this.capacity = capacity;
    }

    public String toString() {
        return name + " - " + capacity;
    }
}

public class TrainConsistManagementApp {
    public static void main(String[] args) {

        System.out.println("=== Train Consist Management App ===");

        List<String> bogies = new ArrayList<>();
        System.out.println("Train consist initialized.");
        System.out.println("Initial bogie count: " + bogies.size());

        List<String> passengerBogies = new ArrayList<>();
        passengerBogies.add("Sleeper");
        passengerBogies.add("AC Chair");
        passengerBogies.add("First Class");

        System.out.println("Passenger Bogies: " + passengerBogies);

        passengerBogies.remove("AC Chair");
        System.out.println("After Removal: " + passengerBogies);
        System.out.println("Sleeper exists: " + passengerBogies.contains("Sleeper"));

        Set<String> bogieIds = new HashSet<>();
        bogieIds.add("BG101");
        bogieIds.add("BG102");
        bogieIds.add("BG101");
        bogieIds.add("BG103");
        bogieIds.add("BG102");

        System.out.println("Unique Bogie IDs: " + bogieIds);

        LinkedList<String> trainConsist = new LinkedList<>();
        trainConsist.add("Engine");
        trainConsist.add("Sleeper");
        trainConsist.add("AC");
        trainConsist.add("Cargo");
        trainConsist.add("Guard");

        trainConsist.add(2, "Pantry Car");
        trainConsist.removeFirst();
        trainConsist.removeLast();

        System.out.println("Final Train Consist: " + trainConsist);

        LinkedHashSet<String> formation = new LinkedHashSet<>();
        formation.add("Engine");
        formation.add("Sleeper");
        formation.add("Cargo");
        formation.add("Guard");
        formation.add("Sleeper");

        System.out.println("Ordered Unique Formation: " + formation);

        HashMap<String, Integer> bogieCapacity = new HashMap<>();
        bogieCapacity.put("Sleeper", 72);
        bogieCapacity.put("AC Chair", 60);
        bogieCapacity.put("First Class", 24);

        for (Map.Entry<String, Integer> entry : bogieCapacity.entrySet()) {
            System.out.println(entry.getKey() + " Capacity: " + entry.getValue());
        }

        List<Bogie> bogieList = new ArrayList<>();

        try {
            bogieList.add(new Bogie("Sleeper", 72));
            bogieList.add(new Bogie("AC Chair", 60));
            bogieList.add(new Bogie("First Class", 24));
            bogieList.add(new Bogie("Sleeper", 72));
        } catch (InvalidCapacityException e) {
            System.out.println(e.getMessage());
        }

        bogieList.sort(Comparator.comparingInt(b -> b.capacity));

        System.out.println("Bogies sorted by capacity:");
        for (Bogie b : bogieList) {
            System.out.println(b);
        }

        List<Bogie> filteredBogies = bogieList.stream()
                .filter(b -> b.capacity > 60)
                .collect(Collectors.toList());

        System.out.println("Filtered Bogies (capacity > 60):");
        for (Bogie b : filteredBogies) {
            System.out.println(b);
        }

        Map<String, List<Bogie>> groupedBogies = bogieList.stream()
                .collect(Collectors.groupingBy(b -> b.name));

        System.out.println("Grouped Bogies:");
        for (Map.Entry<String, List<Bogie>> entry : groupedBogies.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        int totalCapacity = bogieList.stream()
                .map(b -> b.capacity)
                .reduce(0, Integer::sum);

        System.out.println("Total Seating Capacity: " + totalCapacity);

        System.out.println("\n=== UC11: Regex Validation ===");

        String trainId = "TRN-1234";
        String cargoCode = "PET-AB";

        Pattern trainPattern = Pattern.compile("TRN-\\d{4}");
        Pattern cargoPattern = Pattern.compile("PET-[A-Z]{2}");

        Matcher trainMatcher = trainPattern.matcher(trainId);
        Matcher cargoMatcher = cargoPattern.matcher(cargoCode);

        System.out.println(trainMatcher.matches() ? "Valid Train ID" : "Invalid Train ID");
        System.out.println(cargoMatcher.matches() ? "Valid Cargo Code" : "Invalid Cargo Code");

        System.out.println("\n=== UC12: Safety Compliance Check ===");

        class GoodsBogie {
            String type;
            String cargo;

            GoodsBogie(String type, String cargo) {
                this.type = type;
                this.cargo = cargo;
            }
        }

        List<GoodsBogie> goodsList = new ArrayList<>();
        goodsList.add(new GoodsBogie("Cylindrical", "Petroleum"));
        goodsList.add(new GoodsBogie("Rectangular", "Coal"));

        boolean isSafe = goodsList.stream()
                .allMatch(g -> !g.type.equals("Cylindrical") || g.cargo.equals("Petroleum"));

        System.out.println(isSafe ? "Train is Safety Compliant" : "Train is NOT Safe");

        System.out.println("\n=== UC13: Performance Comparison ===");

        List<Bogie> bigList = new ArrayList<>();
        try {
            for (int i = 0; i < 50000; i++) {
                bigList.add(new Bogie("Sleeper", 72));
                bigList.add(new Bogie("AC", 60));
            }
        } catch (InvalidCapacityException e) {
            System.out.println(e.getMessage());
        }

        long startLoop = System.nanoTime();
        List<Bogie> loopResult = new ArrayList<>();
        for (Bogie b : bigList) {
            if (b.capacity > 60) {
                loopResult.add(b);
            }
        }
        long endLoop = System.nanoTime();

        long startStream = System.nanoTime();
        List<Bogie> streamResult = bigList.stream()
                .filter(b -> b.capacity > 60)
                .collect(Collectors.toList());
        long endStream = System.nanoTime();

        System.out.println("Loop Time: " + (endLoop - startLoop) + " ns");
        System.out.println("Stream Time: " + (endStream - startStream) + " ns");
        System.out.println("Loop Result Size: " + loopResult.size());
        System.out.println("Stream Result Size: " + streamResult.size());

        System.out.println("\n=== UC14: Custom Exception Validation ===");

        try {
            Bogie validBogie = new Bogie("AC Chair", 50);
            System.out.println("Created: " + validBogie);
        } catch (InvalidCapacityException e) {
            System.out.println(e.getMessage());
        }

        try {
            Bogie invalidBogie = new Bogie("Faulty", 0);
            System.out.println("Created: " + invalidBogie);
        } catch (InvalidCapacityException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\n=== UC15: Safe Cargo Assignment ===");

        class CargoSafetyException extends RuntimeException {
            CargoSafetyException(String message) {
                super(message);
            }
        }

        class GoodsBogieUC15 {
            String type;
            String cargo;

            GoodsBogieUC15(String type) {
                this.type = type;
            }

            void assignCargo(String cargo) {
                try {
                    if (type.equals("Rectangular") && cargo.equals("Petroleum")) {
                        throw new CargoSafetyException("Unsafe cargo assignment: Petroleum not allowed in Rectangular bogie");
                    }
                    this.cargo = cargo;
                    System.out.println("Cargo assigned successfully: " + type + " -> " + cargo);
                } catch (CargoSafetyException e) {
                    System.out.println("Error: " + e.getMessage());
                } finally {
                    System.out.println("Assignment attempt completed for " + type);
                }
            }
        }

        GoodsBogieUC15 g1 = new GoodsBogieUC15("Cylindrical");
        g1.assignCargo("Petroleum");

        GoodsBogieUC15 g2 = new GoodsBogieUC15("Rectangular");
        g2.assignCargo("Petroleum");

        GoodsBogieUC15 g3 = new GoodsBogieUC15("Rectangular");
        g3.assignCargo("Coal");
    }
}