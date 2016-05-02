package org.usfirst.frc.team2706.robot.commands;

import java.io.File;

import org.usfirst.frc.team2706.robot.controls.RecordableJoystick;

import edu.wpi.first.wpilibj.GenericHID;

public class RecordArcadeDriveWithJoystick extends ArcadeDriveWithJoystick {
	
	public static final File PATH = new File("/home/lvuser/joystick-recordings");
	
	private final RecordableJoystick recordingDriverJoystick;
	private final RecordableJoystick recordingOperatorJoystick;
	
	public RecordArcadeDriveWithJoystick(GenericHID driverJoystick, GenericHID operatorJoystick) {
		super(driverJoystick);
		
		recordingDriverJoystick = new RecordableJoystick(driverJoystick);
		recordingOperatorJoystick = new RecordableJoystick(operatorJoystick);	
	}
	
	@Override
	public void execute() {
		recordingDriverJoystick.update();
		recordingOperatorJoystick.update();
	}
	
	@Override
	public void end() {
		super.end();
		recordingDriverJoystick.cleanup();
		recordingOperatorJoystick.cleanup();
		
		if(!PATH.isDirectory()) {
			PATH.delete();
			PATH.mkdir();
		}
		
		File[] files = PATH.listFiles();
		
		int biggest = 0;
		for(File file : files)
			if(file.isDirectory()) {
				int temp = Integer.parseInt(file.getName());
				if(temp > biggest)
					biggest = temp;
			}
		
		File recordingFolder = new File(PATH, (biggest+1)+"");
		if(recordingFolder.exists())
			recordingFolder.delete();
		
		recordingFolder.mkdir();
		
		RecordableJoystick.writeToFile(recordingDriverJoystick, new File(recordingFolder, "driver.dat"));
		RecordableJoystick.writeToFile(recordingOperatorJoystick, new File(recordingFolder, "operator.dat"));
	}
}
