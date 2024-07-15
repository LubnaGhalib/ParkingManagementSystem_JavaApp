package finalproject;

public class Main {
    public static void main(String[] args) {
        int numSlots = 10; // Number of parking slots

        ParkingLot parkingLot = new ParkingLot(numSlots);
        ParkingManagementSystemGUI gui = new ParkingManagementSystemGUI(numSlots);
        gui.setVisible(true);
    }
}
