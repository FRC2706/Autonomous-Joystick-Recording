package org.usfirst.frc.team2706.robot.commands;

import java.io.File;

import org.usfirst.frc.team2706.robot.Robot;
import org.usfirst.frc.team2706.robot.controls.RecordableJoystick;

import edu.wpi.first.wpilibj.GenericHID;

public class DriveWithRecordedJoystick extends ArcadeDriveWithJoystick {
	
	private DriveWithRecordedJoystick(int joystickRecordingNumber) {
		super(getDriverJoystick(joystickRecordingNumber));
	}
	
	private static GenericHID getDriverJoystick(int joystickRecordingNumber) {
		File recordingLocation = new File(RecordArcadeDriveWithJoystick.PATH, joystickRecordingNumber+"");
		File driverDatLocation = new File(recordingLocation, "driver.dat");
		
		GenericHID driverJoystick = Robot.oi.getDriverJoystick();
		
		if(recordingLocation.exists() && recordingLocation.isDirectory() &&
				driverDatLocation.exists() && driverDatLocation.isFile()) {
			 driverJoystick = RecordableJoystick.readFromFile(driverDatLocation);
		}
		return driverJoystick;
	}
	
}
