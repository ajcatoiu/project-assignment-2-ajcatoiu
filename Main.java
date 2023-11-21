import java.util.*;
import java.io.*;

public class Main {
    // Default configuration values
    private static final String structure = "linked";
    private static final int floors = 32;
    private static final double passengers = 0.03;
    private static final int elevators = 1;
    private static final int capacity = 10;
    private static final int duration = 500;

    public static void main(String[] args) {
        String propsFile = "";

        // Check if a configuration file is provided as a command-line argument
        if (args.length > 0) {
            propsFile = args[0];
        } else if (args.length == 0) {
            // Use default configuration file if not provided
            propsFile = "test.properties";
        }

        // Create and run simulation
        Sim sim = new Sim(propsFile);
        sim.simulate();
    }

    // Simulation class
    private static class Sim {
        private Properties props;
        private ElevatorControlSystem controlSystem;
        private int ticks;
        private Random random;
        private List<Passenger> passengerList;

        // Constructor
        public Sim(String propsFile) {
            this.props = getProperties(propsFile);
            passengerList = new ArrayList<>();

            // Initialize ElevatorControlSystem and other simulation parameters
            ArrayList<Elevator> elevators = new ArrayList<>();
            for (int i = 0; i < getElevators(); i++) {
                elevators.add(new Elevator(getCapacity()));
            }
            controlSystem = new ElevatorControlSystem(elevators);
            ticks = getDuration();
            random = new Random();
        }

        // Get number of elevators from properties
        public int getElevators() {
            return Integer.parseInt(props.getProperty("elevators", String.valueOf(elevators)));
        }

        // Get number of floors from properties
        public int getFloors() {
            return Integer.parseInt(props.getProperty("floors", String.valueOf(floors)));
        }

        // Get elevator capacity from properties
        public int getCapacity() {
            return Integer.parseInt(props.getProperty("elevatorCapacity", String.valueOf(capacity)));
        }

        // Get passenger probability from properties
        public double getProbability() {
            return Double.parseDouble(props.getProperty("passengers", String.valueOf(passengers)));
        }

        // Get simulation duration from properties
        public int getDuration() {
            return Integer.parseInt(props.getProperty("duration", String.valueOf(duration)));
        }

        // Load properties from a file or use default values if file not found
        private Properties getProperties(String filename) {
            Properties properties = new Properties();

            try {
                properties.load(new FileInputStream(filename));
            } catch (IOException e) {
                // Set default values if file not found
                properties.setProperty("structures", structure);
                properties.setProperty("floors", Integer.toString(floors));
                properties.setProperty("passengers", Double.toString(passengers));
                properties.setProperty("elevators", Integer.toString(elevators));
                properties.setProperty("elevatorCapacity", Integer.toString(capacity));
                properties.setProperty("duration", Integer.toString(duration));
            }

            return properties;
        }

        // Run the simulation
        public void simulate() {
            for (int tick = 0; tick < ticks; tick++) {
                int floors = getFloors();
                double prob = getProbability();

                // Generate passengers based on probability
                for (int floor = 1; floor <= floors; floor++) {
                    if (random.nextDouble() < prob) {
                        int dest;
                        do {
                            dest = random.nextInt(floors) + 1;
                        } while (dest == floor);
                        Passenger passenger = new Passenger(floor, dest);
                        controlSystem.addNewPassenger(passenger);
                        passengerList.add(passenger);
                    }
                }

                // Move simulation to the next time step
                controlSystem.next();

                // Update wait times for passengers not yet in the elevator
                for (Passenger passenger : passengerList) {
                    if (!passenger.isInElevator()) {
                        passenger.inc();
                    }
                }
            }

            // Display simulation results
            displayResults();
        }

        // Compute and display average wait time
        private void computeAverageTime() {
            int sum = 0;
            double avg = 0;
            for (Passenger passenger : passengerList) {
                int waitTime = passenger.getWaitTime();
                sum += waitTime;
            }

            if (!passengerList.isEmpty()) {
                avg = (double) sum / passengerList.size();
            } else {
                avg = 0;
            }

            System.out.println("Average time: " + avg);
        }

        // Compute and display the longest wait time
        private void computeLongestTime() {
            int sum = 0;
            int max = 0;
            for (Passenger passenger : passengerList) {
                int waitTime = passenger.getWaitTime();
                sum += waitTime;
                max = Math.max(max, waitTime);
            }

            System.out.println("Longest time: " + max);
        }

        // Compute and display the shortest wait time
        private void computeShortestTime() {
            int sum = 0;
            int min = Integer.MAX_VALUE;
            for (Passenger passenger : passengerList) {
                int waitTime = passenger.getWaitTime();
                sum += waitTime;
                min = Math.min(min, waitTime);
            }

            System.out.print("Shortest time: ");

            if (min == Integer.MAX_VALUE) {
                System.out.println(0);
            } else {
                System.out.println(min);
            }
        }

        // Display overall simulation results
        private void displayResults() {
            computeAverageTime();
            computeLongestTime();
            computeShortestTime();
        }
    }
}