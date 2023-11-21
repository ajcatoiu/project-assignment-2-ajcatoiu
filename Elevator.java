import java.util.*;

public class Elevator {
    // Current floor of the elevator
    private int currFloor;

    // Flag indicating whether the elevator is moving up or down
    private boolean isGoingUp;

    // Maximum capacity of the elevator
    private final int capacity;

    // List to store passengers currently inside the elevator
    private final List<Passenger> passengers;

    // List to store destination floors requested by passengers
    private final List<Integer> destinationFloors;

    // Constructor to initialize the elevator with a given capacity
    public Elevator(int capacity) {
        this.currFloor = 1;
        this.isGoingUp = true;
        this.capacity = capacity;

        // Initialize the lists to store passengers and destination floors
        this.passengers = new ArrayList<>();
        this.destinationFloors = new ArrayList<>();
    }

    // Getter method to retrieve the current floor of elevator
    public int getCurrentElevatorFloor() {
        return currFloor;
    }

    // Method to move elevator based on destination floors
    public void move() {
        if (!destinationFloors.isEmpty()) {
            // Update the current floor based on elevator's direction
            if (isGoingUp) {
                currFloor++;
            } else {
                currFloor--;
            }

            // Check and handle destination floors
            checkDestinationFloor();
        }
    }

    // Method to add passenger to the elevator
    public void enterPassenger(Passenger p) {
        // Check if elevator is full before allowing passenger to enter
        if (this.passengers.size() >= capacity) {
            System.out.println("Elevator is full");
        }

        // Adds passenger to list and register their destination floor
        this.passengers.add(p);
        addDestination(p.getDestination());
    }

    // Private helper method to add destination floor to list
    private void addDestination(int destinationFloor) {
        if (!destinationFloors.contains(destinationFloor)) {
            destinationFloors.add(destinationFloor);
        }
    }

    // Method to remove passenger from elevator
    public void removePassenger(Passenger p) {
        this.passengers.remove(p);
    }

    // Private helper method to check and handle destination floors
    private void checkDestinationFloor() {
        Iterator<Integer> iterator = destinationFloors.iterator();
        while (iterator.hasNext()) {
            int floor = iterator.next();
            if (floor == currFloor) {
                // Unload passengers at the current floor
                unload();
                iterator.remove();
            }
        }
    }

    // Private helper method to unload passengers at the current floor
    private void unload() {
        List<Passenger> passengersToUnload = new ArrayList<>(this.passengers);
        Iterator<Passenger> iterator = passengersToUnload.iterator();
        while (iterator.hasNext()) {
            Passenger passenger = iterator.next();
            if (passenger.getDestination() == currFloor) {
                // Exit the passenger from the elevator
                passenger.exit();
                iterator.remove();
                removePassenger(passenger);
            }
        }
    }

    // Getter method to check if the elevator is moving up
    public boolean isMovingUp() {
        return isGoingUp;
    }

    // Getter method to retrieve the maximum capacity of elevator
    public int getCapacity() {
        return capacity;
    }

    // Getter method to retrieve the list of passengers in elevator
    public List<Passenger> getPassengers() {
        return passengers;
    }

    // Update elevator state
    public void update() {
        move();
        checkDestinationFloor();
    }
}
