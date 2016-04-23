package org.usfirst.frc.team2706.robot.commands;

import java.io.File;

import org.usfirst.frc.team2706.robot.Robot;
import org.usfirst.frc.team2706.robot.controls.RecordableJoystick;

import edu.wpi.first.wpilibj.GenericHID;

public class DriveWithRecordedJoystick extends ArcadeDriveWithJoystick {
	
	TeleopPneumaticControl operator = new TeleopPneumaticControl();
	
	private DriveWithRecordedJoystick(int joystickRecordingNumber) {
		super(getJoystick(joystickRecordingNumber, true));
		operator.setJoystick(getJoystick(joystickRecordingNumber, false));
		
	}
	
	private static GenericHID getJoystick(int joystickRecordingNumber, boolean driver) {
		File recordingLocation = new File(RecordArcadeDriveWithJoystick.PATH, joystickRecordingNumber+"");
		File driverDatLocation = new File(recordingLocation, "driver.dat");
		File operatorDatLocation = new File(recordingLocation, "operator.dat");
		
		GenericHID recordedJoystick = driver ? Robot.oi.getDriverJoystick() : Robot.oi.getOperatorJoystick();
		
		if(recordingLocation.exists() && recordingLocation.isDirectory()) {
			if(driverDatLocation.exists() && driverDatLocation.isFile() && driver)
				recordedJoystick = RecordableJoystick.readFromFile(driverDatLocation);
			if(operatorDatLocation.exists() && operatorDatLocation.isFile() && !driver)
				recordedJoystick = RecordableJoystick.readFromFile(operatorDatLocation);
		}
		return recordedJoystick;
	}
	
	@Override
	public void start() {
		super.start();
		operator.start();
	}
	
	@Override
	public void end() {
		super.end();
		operator.cancel();
	}
}
