package finalproject;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.BevelBorder;

public class ParkingManagementSystemGUI extends JFrame implements ActionListener {
    private ParkingLot parkingLot;

    JButton parkButton, unparkButton, checkAvailabilityButton,parkingFeeButton,showDetailsButton;
    private static final double FEE_PER_HOUR = 100.0; // Fee per hour in Pakistan RS

	private static final int BevelBorder = 0;
    public ParkingManagementSystemGUI(int numSlots) {
        setTitle("Parking Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600); // Adjusted size for a larger window
        setLayout(null); // Switched to null layout
       getContentPane().setBackground(new Color(173, 216, 230));
        parkingLot = new ParkingLot(numSlots);

        // Heading Label
        JLabel headingLabel = new JLabel("Parking Management System");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 30)); // Changed font and size
        headingLabel.setHorizontalAlignment(JLabel.CENTER);
        headingLabel.setPreferredSize(new Dimension(800, 50)); // Adjusted width
        headingLabel.setForeground(new Color(0, 0, 128)); // Dark Blue
        headingLabel.setBounds(20, 20, 800, 50); // Adjusted position
        add(headingLabel);

     
        unparkButton = new JButton("Unpark Vehicle");
        checkAvailabilityButton = new JButton("Check Parking Availability");
        parkingFeeButton = new JButton("Parking Fee");
        showDetailsButton = new JButton("Show Parked Vehicles");
        parkButton = new JButton("Park Vehicle");
        parkingFeeButton.addActionListener(this);
        parkButton.addActionListener(this);
        unparkButton.addActionListener(this);
        checkAvailabilityButton.addActionListener(this);
        showDetailsButton.addActionListener(this);
        // Adjusting button positions and size
        parkButton.setBounds(200, 100, 400, 60);
        unparkButton.setBounds(200, 180, 400, 60);
        checkAvailabilityButton.setBounds(200, 260, 400, 60);
        parkingFeeButton.setBounds(200, 340, 400, 60);
        showDetailsButton.setBounds(200, 420, 400, 60);
        add(parkButton);
        add(unparkButton);
        add(checkAvailabilityButton);
        add(parkingFeeButton);
        add(showDetailsButton);
     // Styling buttons
        parkButton = styleButton(parkButton);
        unparkButton = styleButton(unparkButton);
        checkAvailabilityButton = styleButton(checkAvailabilityButton);
        parkingFeeButton = styleButton(parkingFeeButton);
        showDetailsButton = styleButton(showDetailsButton);
        
        setVisible(true);
    }
    private JButton styleButton(JButton button) {
        button.setBorder(BorderFactory.createBevelBorder(BevelBorder)); // White border
        button.setBackground(Color.WHITE); // White background
        button.setForeground(new Color(0, 0, 128)); // Dark Blue text
        button.setFont(new Font("Arial", Font.BOLD, 18)); // Adjusted font size
        button.setFocusPainted(false);
        return button;
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == parkButton) {
            parkVehicle();
        } else if (e.getSource() == unparkButton) {
            unparkVehicle();
        } else if (e.getSource() == checkAvailabilityButton) {
            checkParkingAvailability();
        }else if (e.getSource() == parkingFeeButton) {
            showParkingFeeInfoFrame();
        }
        else if (e.getSource() == showDetailsButton) {
            showParkedVehiclesDetails();}
    }

  
    private void parkVehicle() {
        JFrame park = new JFrame("Parking Information");
        park.setSize(700, 700);
        park.setLayout(null);
     
        // Creating and positioning components as before
        JLabel np = new JLabel("Vehicle Plate Number");
        np.setBounds(50, 50, 200, 40); // Increased width and height
        np.setFont(new Font("Arial", Font.BOLD, 16)); // Increased font size
        JTextField p = new JTextField();
        p.setBounds(250, 50, 200, 40); // Increased width and height

        JLabel vt = new JLabel("Vehicle Type");
        vt.setBounds(50, 100, 200, 40); // Increased width and height
        vt.setFont(new Font("Arial", Font.BOLD, 16)); // Increased font size
        JTextField v = new JTextField();
        v.setBounds(250, 100, 200, 40); // Increased width and height

        park.add(np);
        park.add(p);
        park.add(vt);
        park.add(v);

        // Adding JComboBox for selecting parking slots
        JLabel slotLabel = new JLabel("Select Parking Slot:");
        slotLabel.setBounds(50, 150, 200, 40); // Increased width and height
        slotLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Increased font size
        park.add(slotLabel);

        String[] slotOptions = {"Slot 1", "Slot 2", "Slot 3", "Slot 4", "Slot 5", "Slot 6", "Slot 7", "Slot 8", "Slot 9", "Slot 10"};
        JComboBox<String> slotComboBox = new JComboBox<>(slotOptions);
        slotComboBox.setBounds(250, 150, 200, 40); // Increased width and height
        park.add(slotComboBox);

        JButton btn1 = new JButton("Submit");
        btn1.setBounds(50, 200, 150, 40); // Increased width and height
        btn1.setFont(new Font("Arial", Font.PLAIN, 18)); // Increased font size
        park.add(btn1);
        btn1.addActionListener(new ActionListener() {
    	    public void actionPerformed(ActionEvent e) {
    	        Vehicle newVehicle = new Vehicle(p.getText(), v.getText());

    	        int selectedSlotIndex = slotComboBox.getSelectedIndex();

    	        if (selectedSlotIndex != -1) {
    	           int selectedSlot = selectedSlotIndex+1 ; // Adjusting index to slot number

    	            boolean parked = parkingLot.parkVehicle(newVehicle, selectedSlot);
    	            if (parked) {
    	                Date entryTime = new Date();
    	                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    	                String receipt = "Vehicle Plate Number: " + p.getText() +
    	                        "\nVehicle Type: " + v.getText() +
    	                        "\nParking Slot: " + selectedSlot +
    	                        "\nEntry Time: " + dateFormat.format(entryTime);

    	                JOptionPane.showMessageDialog(park, "Vehicle parked successfully!\n\nReceipt:\n" + receipt);
    	            park.dispose();
    	            }
    	            else {
    	                JOptionPane.showMessageDialog(park, "Invalid or occupied slot!");
    	            }
    	        } else {
    	            JOptionPane.showMessageDialog(park, "Please select a parking slot!");
    	        }
    	        try {
    	        	Class.forName("com.mysql.cj.jdbc.Driver");
    	        	Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/parking","root","BSF21#F@ll23");
    	        	Statement  s = con.createStatement();

    	          	
    	            // Inserting values into the table
    	            String plateNumber = p.getText();
    	            String vehicleType = v.getText();
    	            int selectedSlot = selectedSlotIndex + 1;
					String insertQuery = "INSERT INTO parkingsystem (Vehicle_Plate_Number, Vehicle_Type,Parking_Slot) VALUES ('" + plateNumber + "', '" + vehicleType + "','" + selectedSlot+"')";
                    s.executeUpdate(insertQuery);
                    s.close();
    	            con.close();
    	        }
    	        catch(Exception ex)
    	        {
    	        	ex.printStackTrace();
    	        }
    	    }
    	});
        // Set background color for the park frame
        park.getContentPane().setBackground(new Color(170, 230, 200));

        // Change label text color to dark blue
        np.setForeground(new Color(0, 0, 128));
        vt.setForeground(new Color(0, 0, 128));
        slotLabel.setForeground(new Color(0, 0, 128));

        // Change button text color to white
        btn1.setForeground(Color.BLACK);
        park.setVisible(true);
    }

   

    private void showParkingFeeInfoFrame() {
        JFrame feeInfoFrame = new JFrame("Parking Fee Info");
        feeInfoFrame.setSize(400, 300);
        feeInfoFrame.setLayout(null);
        feeInfoFrame.getContentPane().setBackground(new Color(170, 230, 200)); // Updated background color
        feeInfoFrame.getContentPane().setBackground(new Color(173, 216, 230));
        JLabel plateNumberLabel = new JLabel("Vehicle PlateNo:");
        plateNumberLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JTextField plateNumberField = new JTextField(15);

        JLabel vehicleTypeLabel = new JLabel("Vehicle Type:");
        vehicleTypeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JTextField vehicleTypeField = new JTextField(15);

        JLabel durationLabel = new JLabel("Enter Duration(Minutes)");
        JTextField durationField = new JTextField(15);
        durationLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JButton calculateButton = new JButton("Calculate Fee");
        calculateButton.setFont(new Font("Arial", Font.BOLD, 14)); // Increased font size
        calculateButton.setBounds(50, 200, 200, 30);
        JLabel payFeeLabel = new JLabel("Pay Fee (Rs):");
        payFeeLabel.setFont(new Font("Arial", Font.BOLD, 14)); 
        JTextField payFeeField = new JTextField(15);
        payFeeField.setEditable(false); // Disable editing in this field

        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.BOLD, 14)); // Increased font size
        submitButton.setBounds(50, 250, 150, 40);
        plateNumberLabel.setBounds(50, 50, 150, 30);
        plateNumberField.setBounds(200, 50, 150, 30);

        vehicleTypeLabel.setBounds(50, 100, 150, 30);
        vehicleTypeField.setBounds(200, 100, 150, 30);

        durationLabel.setBounds(50, 150, 150, 30);
        durationField.setBounds(200, 150, 150, 30);
        calculateButton.setBounds(50, 200, 150, 30);

        payFeeLabel.setBounds(50, 250, 150, 30);
        payFeeField.setBounds(200, 250, 150, 30);

        submitButton.setBounds(50, 300, 100, 30);
     // Set background color for the feeInfoFrame
        feeInfoFrame.getContentPane().setBackground(new Color(173, 216, 230));

        // ... (existing code remains unchanged)

        // Change label text color to dark blue
        plateNumberLabel.setForeground(new Color(0, 0, 128));
        vehicleTypeLabel.setForeground(new Color(0, 0, 128));
        durationLabel.setForeground(new Color(0, 0, 128));
        payFeeLabel.setForeground(new Color(0, 0, 128));

        // Change button text color to white
        calculateButton.setForeground(Color.BLACK);
        submitButton.setForeground(Color.BLACK);
        feeInfoFrame.add(plateNumberLabel);
        feeInfoFrame.add(plateNumberField);
        feeInfoFrame.add(vehicleTypeLabel);
        feeInfoFrame.add(vehicleTypeField);
        feeInfoFrame.add(durationLabel);
        feeInfoFrame.add(durationField);
        feeInfoFrame.add(calculateButton);
        feeInfoFrame.add(payFeeLabel);
        feeInfoFrame.add(payFeeField);
        feeInfoFrame.add(submitButton);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String plateNumber = plateNumberField.getText();

                // Check if the vehicle is parked
                if (parkingLot.isVehicleParked(plateNumber)) {
                    try {
                        int duration = Integer.parseInt(durationField.getText());
                        double parkingFee = parkingLot.calculateFee(duration); // Use parkingLot to call the method
                        payFeeField.setText("Rs " + parkingFee);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(feeInfoFrame, "Invalid duration input. Please enter a valid number.");
                    }
                } else {
                    JOptionPane.showMessageDialog(feeInfoFrame, "Vehicle with plate number " + plateNumber + " is not parked.");
                }
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String plateNumber = plateNumberField.getText();
                String payFee = payFeeField.getText();
                if (!payFee.isEmpty()) {
                	try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/parking","root","BSF21#F@ll23");
                        Statement  s = con.createStatement();

                        // Update fee status to 'Paid'
                        String updateQuery = "UPDATE parkingsystem SET Fee_Status = 'Paid' WHERE Vehicle_Plate_Number = '" + plateNumber + "'";
                        s.executeUpdate(updateQuery);
                        con.close();

                        JOptionPane.showMessageDialog(feeInfoFrame, "Parking fee received successfully! Fee Status updated to Paid.");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(feeInfoFrame, "Parking fee received successfully!");
                } else {
                    JOptionPane.showMessageDialog(feeInfoFrame, "Please calculate the fee first.");
                }
            }
        });

        feeInfoFrame.setVisible(true);
    }

   private int selectParkingSlot() {
        StringBuilder availableSlotsMsg = new StringBuilder("Available parking slots: ");
        boolean slotsAvailable = false;

        for (int i = 0; i < parkingLot.getNumSlots(); i++) {
            if (parkingLot.getSlotAvailability(i)) {
                availableSlotsMsg.append((i+1 )).append(" ");
                slotsAvailable = true;
            }
        }

        if (slotsAvailable) {
            String input = JOptionPane.showInputDialog(this, availableSlotsMsg.toString() + "\nEnter the preferred parking slot number (1-" + parkingLot.getNumSlots() + "):");
            try {
                int selectedSlot = Integer.parseInt(input) -1;
                if (selectedSlot >= 0 && selectedSlot < parkingLot.getNumSlots() && parkingLot.getSlotAvailability(selectedSlot)) {
                    return selectedSlot;
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid or occupied slot!");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid input! Please enter a valid slot number.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "No available parking slots!");
        }
        return -1;
    }


    private void unparkVehicle() {
        String plateNumber = JOptionPane.showInputDialog(this, "Enter Vehicle Plate Number:");

        Vehicle removedVehicle = parkingLot.unparkVehicle(plateNumber);
        if (removedVehicle != null) {
            JOptionPane.showMessageDialog(this, "Vehicle with plate number " + plateNumber + " unparked successfully");
            try {
                // Establish the database connection
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/parking","root","BSF21#F@ll23");
                Statement s = con.createStatement();

                // Delete data from the table based on plate number
                String deleteQuery = "DELETE FROM parkingsystem WHERE Vehicle_Plate_Number = '" + plateNumber + "'";
                s.executeUpdate(deleteQuery);
                s.close();
                con.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vehicle not found or not parked in this parking lot!");
        }
    }

    private void checkParkingAvailability() {
        int availableSlots = parkingLot.getNumAvailableSlots();

        if (availableSlots > 0) {
            StringBuilder slotsMessage = new StringBuilder("Available parking slots: ");
            boolean isFirst = true;

            for (int i = 0; i <parkingLot.getNumSlots(); i++) {
                if (parkingLot.getSlotAvailability(i)) {
                    if (!isFirst) {
                        slotsMessage.append(", ");
                    }
                    slotsMessage.append("Slot ").append(i+1);
                    isFirst = false;
                }
            }
            JOptionPane.showMessageDialog(this, slotsMessage.toString());
        } else {
            JOptionPane.showMessageDialog(this, "No available parking slots!");
        }
    }
    private void showParkedVehiclesDetails() {
        StringBuilder details = new StringBuilder("Parked Vehicles:\n");

        for (int i = 0; i < parkingLot.getNumSlots(); i++) {
            ParkingSlot slot = parkingLot.getParkingSlots().get(i);
            if (!slot.isAvailable()) {
                Vehicle parkedVehicle = slot.getParkedVehicle();
                details.append("Slot ").append(i+1).append(": ")
                        .append("Plate Number: ").append(parkedVehicle.getPlateNumber()).append("\n ")
                        .append("\t\tVehicle Type: ").append(parkedVehicle.getVehicleType()).append("\n");
            }
        }

        if (details.length() == 17) { // If no vehicles are parked (length of "Parked Vehicles:\n")
            details.append("No vehicles parked.");
        }

        JOptionPane.showMessageDialog(this, details.toString());
    }

}