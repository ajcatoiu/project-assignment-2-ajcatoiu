import java.util.*;

public class ElevatorControlSystem {
    // List to store all elevators managed by the control system
    private final List<Elevator> elevators;

    // Constructor to initialize the ElevatorControlSystem with a list of elevators
    public ElevatorControlSystem(List<Elevator> elevators) {
        this.elevators = elevators;
    }

    // Method to handle button press from a passenger and return the nearest elevator
    public Elevator buttonPressed(Passenger passenger) {
        Elevator nearestElevator = null;
        int minDistance = Integer.MAX_VALUE;
        Iterator<Elevator> elevatorIterator = elevators.iterator();

        // Iterate through each elevator to find the nearest one to the passenger
        while (elevatorIterator.hasNext()) {
            Elevator elevator = elevatorIterator.next();
            int distanceFromClient = Math.abs(elevator.getCurrentElevatorFloor() - passenger.getCurrFloor());

            // Update nearest elevator if the current one is closer
            if (distanceFromClient < minDistance) {
                minDistance = distanceFromClient;
                nearestElevator = elevator;
            }
        }

        return nearestElevator;
    }

    // Method to add a new passenger to the nearest available elevator
    public void addNewPassenger(Passenger passenger) {
        Elevator bestElevator = getNearestElevator(passenger);

        // If a suitable elevator is found, add the passenger to that elevator
        if (bestElevator != null) {
            bestElevator.enterPassenger(passenger);
        }
    }

    // Private helper method to find the nearest available elevator for a passenger
    private Elevator getNearestElevator(Passenger passenger) {
        Elevator nearestElevator = null;
        int minDistance = Integer.MAX_VALUE;

        // Iterate through each elevator to find the nearest one considering capacity and direction
        int elevatorIndex = 0;
        while (elevatorIndex < elevators.size()) {
            Elevator elevator = elevators.get(elevatorIndex);

            // Check if the elevator has available capacity
            if (elevator.getPassengers().size() < elevator.getCapacity()) {
                int distance = Math.abs(elevator.getCurrentElevatorFloor() - passenger.getCurrFloor());

                // Check if the elevator is moving towards the passenger
                boolean travelingToPassenger = (elevator.isMovingUp() && passenger.getCurrFloor() > elevator.getCurrentElevatorFloor()) ||
                        (!elevator.isMovingUp() && passenger.getCurrFloor() < elevator.getCurrentElevatorFloor());

                // Update nearest elevator if it's moving towards the passenger and is closer
                if (travelingToPassenger && distance < minDistance) {
                    minDistance = distance;
                    nearestElevator = elevator;
                }
            }

            elevatorIndex++;
        }

        // If no suitable elevator found, iterate through all elevators to find the nearest one without considering direction
        if (nearestElevator == null) {
            for (Elevator elevator : elevators) {
                if (elevator.getPassengers().size() < elevator.getCapacity()) {
                    int distance = Math.abs(elevator.getCurrentElevatorFloor() - passenger.getCurrFloor());

                    // Update nearest elevator if it's closer
                    if (distance < minDistance) {
                        minDistance = distance;
                        nearestElevator = elevator;
                    }
                }
            }
        }

        return nearestElevator;
    }

    // Method to advance simulation to next step for all elevators
    public void next() {
        for (Elevator elevator : elevators) {
            elevator.update();
        }
    }
}
