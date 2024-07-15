package finalproject;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
	 private static final double FEE_PER_Minute = 5.0;
	private List<ParkingSlot> parkingSlots;

    public ParkingLot(int numSlots) {
        parkingSlots = new ArrayList<>();
        for (int i = 0; i < numSlots; i++) {
            parkingSlots.add(new ParkingSlot());
        }
    }

    public String getAvailableSlots() {
        StringBuilder availableSlots = new StringBuilder();
        boolean isFirst = true;

        for (int i = 0; i < parkingSlots.size(); i++) {
            if (parkingSlots.get(i).isAvailable()) {
                if (!isFirst) {
                    availableSlots.append(", ");
                }
                availableSlots.append(i + 1);
                isFirst = false;
            }
        }
        return availableSlots.toString();
    }


    public boolean parkVehicle(Vehicle vehicle, int selectedSlot) {
    int adjustedSlot = selectedSlot - 1; // Adjusting index to match internal representation

    if (adjustedSlot >= 0 && adjustedSlot < parkingSlots.size() && parkingSlots.get(adjustedSlot).isAvailable()) {
        parkingSlots.get(adjustedSlot).parkVehicle(vehicle);
        return true;
    }
    return false; // Invalid or occupied slot
}
    public Vehicle unparkVehicle(String plateNumber) {
        for (ParkingSlot slot : parkingSlots) {
            if (!slot.isAvailable() && slot.getParkedVehicle().getPlateNumber().equals(plateNumber)) {
                return slot.unparkVehicle();
            }
        }
        return null; // Vehicle not found or not parked
    }

    public boolean checkAvailability() {
        for (ParkingSlot slot : parkingSlots) {
            if (slot.isAvailable()) {
                return true; // At least one slot is available
            }
        }
        return false; // No available slot
    }

	public int getNumSlots() {
		// TODO Auto-generated method stub
		 return parkingSlots.size();
	}

	
	public boolean getSlotAvailability(int i) {
	    if (i >= 0 && i <parkingSlots.size()) {
	        return parkingSlots.get(i).isAvailable();
	    }
	    return true; // or throw an exception indicating an invalid slot index
	}
	public List<ParkingSlot> getParkingSlots() {
	    return parkingSlots;
	}
	public boolean isVehicleParked(String plateNumber) {
        for (ParkingSlot slot : parkingSlots) {
            if (!slot.isAvailable() && slot.getParkedVehicle().getPlateNumber().equals(plateNumber)) {
                return true; // Vehicle is parked
            }
        }
        return false; // Vehicle is not parked
    }

    public double calculateFee(int duration) {
        // Fee calculation formula: Duration * Fee per Hour
        return duration * FEE_PER_Minute;
    }

    public int getNumAvailableSlots() {
        int count = 0;
        for (ParkingSlot slot : parkingSlots) {
            if (slot.isAvailable()) {
                count++;
            }
        }
        return count;
    }

    
}