package edu.sjsu.mithai.apps.parkinglot;

import edu.sjsu.mithai.util.StoppableRunnableTask;

import java.util.Scanner;

public class ParkingClient extends StoppableRunnableTask {

    @Override
    public void run() {

        while (true) {
            System.out.println("===============PARKING APPLICATION===============");
            System.out.println("1. Park");
            System.out.println("2. Status");
            System.out.println("3. Exit");

            Scanner in = new Scanner(System.in);
            int i = in.nextInt();
            in.nextLine();
            switch (i) {
                case 1:
                    System.out.println("Enter location to park:");
                    String location = in.next();
                    if (!ParkingApplication.parkingSensorMap.containsKey(location)) {
                        System.out.println("ERROR: Parking spot not present.");
                        continue;
                    } else if (ParkingApplication.parkingSensorMap.get(location).isParked()) {
                        System.out.println("Parking spot already taken!");
                        continue;
                    } else {
                        ParkingApplication.parkingSensorMap.get(location).setParked(true);
                    }
                    break;
                case 2:
                    for (String id : ParkingApplication.parkingSensorMap.keySet()) {
                        System.out.println(id + "=>" + ParkingApplication.parkingSensorMap.get(id).isParked());
                    }
                    break;
                case 3:
                    System.exit(0);
                default:
                    System.out.println("Please enter valid choice.");
            }
        }
    }

}
