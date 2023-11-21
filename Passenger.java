public class Passenger {
    // Passenger attributes
    private int currFloor; // Current floor of the passenger
    private int destination; // Destination floor of the passenger
    private int waitTime; // Total time the passenger has spent waiting
    private boolean inside; // Flag indicating whether the passenger is inside an elevator
    private Elevator currElevator; // Reference to the current elevator the passenger is in

    // Constructor to initialize passenger with current and destination floors
    public Passenger(int currFloor, int destination) {
        this.currFloor = currFloor;
        this.destination = destination;
        this.inside = false; // Passenger starts outside the elevator
        this.waitTime = 0; // Initialize wait time
    }

    // Check if the passenger is currently inside an elevator
    public boolean isInElevator() {
        return inside;
    }

    // Get the destination floor of the passenger
    public int getDestination() {
        return destination;
    }

    // Get the current floor of the passenger
    public int getCurrFloor() {
        return currFloor;
    }

    // Get the total wait time of the passenger
    public int getWaitTime() {
        return waitTime;
    }

    // Increment the wait time of the passenger during each simulation step
    public void inc() {
        this.waitTime++;
    }

    // Method called when the passenger exits the elevator
    public void exit() {
        // Check if the passenger is inside an elevator and has reached the destination floor
        if (inside && currElevator.getCurrentElevatorFloor() == destination) {
            inside = false; // Passenger is no longer inside the elevator
            currElevator.removePassenger(this); // Remove the passenger from the elevator
            currElevator = null; // Set the current elevator reference to null
        }
    }
}
