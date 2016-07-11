package com.edinarobotics.zeke;

import com.edinarobotics.zeke.subsystems.Collector;
import com.edinarobotics.zeke.subsystems.Drivetrain;
import com.edinarobotics.zeke.subsystems.DrivetrainRotation;
import com.edinarobotics.zeke.subsystems.DrivetrainStrafe;
import com.edinarobotics.zeke.subsystems.Shooter;

import edu.wpi.first.wpilibj.Compressor;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class Components {
    
	private static Components instance;
	public Drivetrain drivetrain;
	public DrivetrainRotation drivetrainRotation;
	public DrivetrainStrafe drivetrainStrafe;
	public Shooter shooter;
	public Collector collector;
	public Compressor compressor;
	
	// CAN Constants
		// Drivetrain Constants
		private static final int FRONT_LEFT_CANTALON = 2;
		private static final int FRONT_RIGHT_CANTALON = 1;
		private static final int BACK_LEFT_CANTALON = 6;
		private static final int BACK_RIGHT_CANTALON = 3;
		// End Drivetrain Constants
		
		//Shooter Constants
		private static final int SHOOTER_WINCH = 5;
		//End Shooter Constants
	
	// End CAN Constants
		
	// DIO Constants
		// Shooter Constants
		private static final int SHOOTER_LIMIT_SWITCH = 0;
		// End Shooter Constants
		
	//End DIO Constants
		
	// Pneumatic Constants
		// Pneumatic Control Module
		private static final int PCM_NODE_ID = 10;
		// End Pneumatic Control Module
		
		// Shooter Constants
		private static final int SHOOTER_FORWARD = 1;
		private static final int SHOOTER_BACKWARD = 3;
		// End Shooter Constants
		
		// Collector Constants
		private static final int COLLECTOR_FORWARD = 0;
		private static final int COLLECTOR_BACKWARD = 1;
		// End Collector Constants
		
	// End Pneumatic Constants
		
	// Relay Constants
		// Collector Constants
		private static final int RELAY_PORT = 1;
		// End Collector Constants
		
	//End Relay Constants
	
	private Components() {
		drivetrain = new Drivetrain(FRONT_LEFT_CANTALON, FRONT_RIGHT_CANTALON, BACK_LEFT_CANTALON, 
				BACK_RIGHT_CANTALON);
		
		drivetrainStrafe = new DrivetrainStrafe(drivetrain);
		drivetrainRotation = new DrivetrainRotation(drivetrain);
		
		shooter = new Shooter(SHOOTER_WINCH, SHOOTER_LIMIT_SWITCH, PCM_NODE_ID, SHOOTER_FORWARD, SHOOTER_BACKWARD);
		
		collector = new Collector(RELAY_PORT, PCM_NODE_ID, COLLECTOR_FORWARD, COLLECTOR_BACKWARD);
		
		compressor = new Compressor(PCM_NODE_ID);
		compressor.start();
	}
	
	public static Components getInstance() {
		if (instance == null) {
			instance = new Components();
		}
		
		return instance;
	}
	
}
