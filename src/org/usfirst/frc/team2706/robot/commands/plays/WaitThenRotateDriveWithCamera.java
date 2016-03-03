package org.usfirst.frc.team2706.robot.commands.plays;

import org.usfirst.frc.team2706.robot.commands.RotateDriveWithCamera;
import org.usfirst.frc.team2706.robot.commands.StraightDriveWithTime;

import edu.wpi.first.wpilibj.command.Command;

public class WaitThenRotateDriveWithCamera extends Command {
	
	double speed;
	long time;
	
	Command first;
	Command second;
	
	public WaitThenRotateDriveWithCamera(double speed, long time) {
		this.speed = speed;
		this.time = time;
	}

	@Override
	protected void initialize() {
		first = new StraightDriveWithTime(0.0, time);
		second = new RotateDriveWithCamera(speed, 100);
		
		first.start();
		
		//System.out.println(first.isRunning());
	}

	boolean second1;
	
	@Override
	protected void execute() {
		if(((StraightDriveWithTime)first).done) {
			if(!second1) {
				second.start(); 
				System.out.println("TEst: Camera Start Called");
				second1 = !second1;
			}
			
			//System.out.println("Second!");		
		}
		else;
		//	System.out.println("First!");
		
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}
	
	
}
