package finalproject;

public class ParkingSlot 
{
    private boolean available;
    private Vehicle parkedVehicle;

    public ParkingSlot() {
        this.available = true;
        this.parkedVehicle = null;
    }

    public boolean isAvailable() {
        return available;
    }

    public void parkVehicle(Vehicle vehicle) {
        this.parkedVehicle = vehicle;
        this.available = false;
    }

    public Vehicle unparkVehicle() {
        if (!isAvailable()) {
            Vehicle removedVehicle = this.parkedVehicle;
            this.parkedVehicle = null;
            this.available = true;
            return removedVehicle;
        }
        return null; // No vehicle parked or already available
    }


    public Vehicle getParkedVehicle() {
        return parkedVehicle;
    }
}