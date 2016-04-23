package org.usfirst.frc.team2706.robot.controls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;

public class RecordableJoystick extends GenericHID {
	
	public static void writeToFile(RecordableJoystick s, File file) {
		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(s);
			oos.flush();
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static RecordableJoystick readFromFile(File file) {
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Object temp = ois.readObject();
			
			if(!(temp instanceof RecordableJoystick)) {
				ois.close();
				fis.close();
				throw new ClassNotFoundException("Object read was not an instance of RecordableJoystick");
			}
			
			ois.close();
			fis.close();
			return (RecordableJoystick)temp;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	private GenericHID joystick;
	
	private List<GenericHID> joystickStates = new ArrayList<>();
	
	private int count = 0;
	
	private boolean write = true;
	
	public RecordableJoystick(GenericHID joystick) {
		this.joystick = joystick;
	}
	
	public GenericHID update() {
		if(write) {
			GenericHID state = new JoystickState(joystick);
			joystickStates.add(state);
			return state;
		}
		else {
			return joystickStates.get(count++);
		}
	}
	
	public void cleanup() {
		// Make sure this doesn't get deserialized
		joystick = null;
		
		write = false;
	}
	
	@Override
	public double getX(Hand hand) {
		return joystick.getX(hand);
	}

	@Override
	public double getY(Hand hand) {
		return joystick.getY(hand);
	}

	@Override
	public double getZ(Hand hand) {
		return joystick.getZ(hand);
	}

	@Override
	public double getTwist() {
		return joystick.getTwist();
	}

	@Override
	public double getThrottle() {
		return joystick.getThrottle();
	}

	@Override
	public double getRawAxis(int which) {
		return joystick.getRawAxis(which);
	}

	@Override
	public boolean getTrigger(Hand hand) {
		return joystick.getTrigger();
	}

	@Override
	public boolean getTop(Hand hand) {
		return joystick.getTop(hand);
	}

	@Override
	public boolean getBumper(Hand hand) {
		return joystick.getBumper(hand);
	}

	@Override
	public boolean getRawButton(int button) {
		return joystick.getRawButton(button);
	}

	@Override
	public int getPOV(int pov) {
		return joystick.getPOV(pov);
	}

	private class JoystickState extends GenericHID {
		
		private final double x, y, z;
		
		private final double twist;
		private final double throttle;
		
		private final boolean trigger;
		private final boolean top;
		private final boolean bumper;
		
		private final double[] rawAxis;
		private final boolean[] rawButton;
		private final int[] pov;
		
		public JoystickState(GenericHID current) {
			x = current.getX();
			y = current.getY();
			z = current.getZ();
			
			twist = current.getTwist();
			throttle = current.getThrottle();
			
			trigger = current.getTrigger();
			top = current.getTop();
			bumper = current.getBumper();
			
			int axisMax = 128;
			int buttonMax = 128;
			int povMax = 128;
			if(current instanceof Joystick) {
				axisMax = ((Joystick)current).getAxisCount();
				buttonMax = ((Joystick)current).getButtonCount();
				povMax = ((Joystick)current).getPOVCount();
			}
			
			rawAxis = new double[axisMax];
			for(int i = 0; i < axisMax; i++) {
				rawAxis[i] = current.getRawAxis(i);
			}
			
			rawButton = new boolean[buttonMax];
			for(int i = 0; i < buttonMax; i++) {
				rawButton[i] = current.getRawButton(i);
			}
			
			pov = new int[povMax];
			for(int i = 0; i < povMax; i++) {
				pov[i] = current.getPOV(i);
			}
		}

		@Override
		public double getX(Hand hand) {
			return x;
		}

		@Override
		public double getY(Hand hand) {
			return y;
		}

		@Override
		public double getZ(Hand hand) {
			return z;
		}

		@Override
		public double getTwist() {
			return twist;
		}

		@Override
		public double getThrottle() {
			return throttle;
		}

		@Override
		public double getRawAxis(int which) {
			return rawAxis[which];
		}

		@Override
		public boolean getTrigger(Hand hand) {
			return trigger;
		}

		@Override
		public boolean getTop(Hand hand) {
			return top;
		}

		@Override
		public boolean getBumper(Hand hand) {
			return bumper;
		}

		@Override
		public boolean getRawButton(int button) {
			return rawButton[button];
		}

		@Override
		public int getPOV(int pov) {
			return this.pov[pov];
		}
	}
	
}
