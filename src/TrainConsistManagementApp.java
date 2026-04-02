import java.util.*;
import java.util.regex.*;
import java.util.stream.Collectors;

class InvalidCapacityException extends Exception {
    public InvalidCapacityException(String message) {
        super(message);
    }
}

class CargoSafetyException extends RuntimeException {
    public CargoSafetyException(String message) {
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

class GoodsBogie {
    String type;
    String cargo;

    GoodsBogie(String type, String cargo) {
        this.type = type;
        this.cargo = cargo;
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
        filteredBogies.forEach(System.out::println);

        Map<String, List<Bogie>> groupedBogies = bogieList.stream()
                .collect(Collectors.groupingBy(b -> b.name));

        System.out.println("Grouped Bogies:");
        groupedBogies.forEach((k, v) -> System.out.println(k + " -> " + v));

        int totalCapacity = bogieList.stream()
                .map(b -> b.capacity)
                .reduce(0, Integer::sum);

        System.out.println("Total Seating Capacity: " + totalCapacity);

        Scanner sc = new Scanner(System.in);

        System.out.println("\n=== UC11: Regex Validation ===");
        System.out.print("Enter Train ID: ");
        String trainId = sc.nextLine();
        System.out.print("Enter Cargo Code: ");
        String cargoCode = sc.nextLine();

        Pattern trainPattern = Pattern.compile("TRN-\\d{4}");
        Pattern cargoPattern = Pattern.compile("PET-[A-Z]{2}");

        System.out.println(trainPattern.matcher(trainId).matches() ? "Valid Train ID" : "Invalid Train ID");
        System.out.println(cargoPattern.matcher(cargoCode).matches() ? "Valid Cargo Code" : "Invalid Cargo Code");

        System.out.println("\n=== UC12: Safety Check ===");

        List<GoodsBogie> goodsList = Arrays.asList(
                new GoodsBogie("Cylindrical", "Petroleum"),
                new GoodsBogie("Rectangular", "Coal")
        );

        boolean safe = goodsList.stream()
                .allMatch(g -> !g.type.equals("Cylindrical") || g.cargo.equals("Petroleum"));

        System.out.println(safe ? "Train is Safety Compliant" : "Train is Unsafe");

        System.out.println("\n=== UC13: Performance Comparison ===");

        List<Bogie> bigList = new ArrayList<>();
        try {
            for (int i = 0; i < 50000; i++) {
                bigList.add(new Bogie("Sleeper", i % 100 + 1));
            }
        } catch (InvalidCapacityException e) {}

        long start1 = System.nanoTime();
        List<Bogie> loopResult = new ArrayList<>();
        for (Bogie b : bigList) {
            if (b.capacity > 60) loopResult.add(b);
        }
        long end1 = System.nanoTime();

        long start2 = System.nanoTime();
        List<Bogie> streamResult = bigList.stream()
                .filter(b -> b.capacity > 60)
                .collect(Collectors.toList());
        long end2 = System.nanoTime();

        System.out.println("Loop Time: " + (end1 - start1));
        System.out.println("Stream Time: " + (end2 - start2));

        System.out.println("\n=== UC15: Safe Cargo Assignment ===");

        try {
            String type = "Rectangular";
            String cargo = "Petroleum";

            if (type.equals("Rectangular") && cargo.equals("Petroleum")) {
                throw new CargoSafetyException("Unsafe cargo assignment!");
            }

            System.out.println("Cargo Assigned Successfully");

        } catch (CargoSafetyException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("Cargo assignment attempt completed");
        }

        System.out.println("\n=== UC16: Bubble Sort ===");

        int[] capacities = {72, 56, 24, 70, 60};

        for (int i = 0; i < capacities.length - 1; i++) {
            for (int j = 0; j < capacities.length - i - 1; j++) {
                if (capacities[j] > capacities[j + 1]) {
                    int temp = capacities[j];
                    capacities[j] = capacities[j + 1];
                    capacities[j + 1] = temp;
                }
            }
        }

        System.out.println("Sorted Capacities: " + Arrays.toString(capacities));

        System.out.println("\n=== UC17: Arrays.sort() ===");

        String[] bogieNames = {"Sleeper","AC Chair","First Class","General","Luxury"};
        Arrays.sort(bogieNames);

        System.out.println("Sorted Bogie Names: " + Arrays.toString(bogieNames));

        System.out.println("\n=== UC18: Linear Search ===");

        String[] ids = {"BG101","BG205","BG309","BG412","BG550"};
        System.out.print("Enter Bogie ID to search: ");
        String key = sc.nextLine();

        boolean found = false;
        for (String id : ids) {
            if (id.equals(key)) {
                found = true;
                break;
            }
        }

        System.out.println(found ? "Bogie Found" : "Bogie Not Found");
    }
}