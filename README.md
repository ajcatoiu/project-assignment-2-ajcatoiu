ElevatorControlSystem.java: 
-   Class represents control system for managing multiple elevators
-   Includes methods for handling passenger requests, finding the nearest elevator, and advancing the simulation to next step

Elevator.java:
-   It keeps track of elevator's current floor, direction, passenger list, and destination floors
-   Includes methods for moving, boarding and removing passengers, and updating the state

Passenger.java:
-   Class represents a passenger within the simulation
-   It has attributes such as current floor, destination floor, and whether the passenger is inside the elevator
-   Includes methods for checking status and performing actions like exiting the elevator

Main.java:
-   Contains main method
-   Initializes the control system, sets simulation parameters, and executes simulation based on property while specifications

Run the code by:
-   Moving all of the files into the same directory
-   Compiling: javac Main.java
-   And run: java Main 
                OR
             java Main test.properties