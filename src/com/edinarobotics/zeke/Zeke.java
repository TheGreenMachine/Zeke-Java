
package com.edinarobotics.zeke;

import com.edinarobotics.utils.gamepad.Gamepad;
import com.edinarobotics.zeke.commands.GamepadDriveRotationCommand;
import com.edinarobotics.zeke.commands.GamepadDriveStrafeCommand;
import com.edinarobotics.zeke.subsystems.Drivetrain;
import com.edinarobotics.zeke.subsystems.DrivetrainRotation;
import com.edinarobotics.zeke.subsystems.DrivetrainStrafe;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Zeke extends IterativeRobot {
	
	private Gamepad gamepad;
	private Drivetrain drivetrain;
	private DrivetrainStrafe drivetrainStrafe;
	private DrivetrainRotation drivetrainRotation;
	
    public void robotInit() {
		Controls.getInstance();
		Components.getInstance();
		
		gamepad = Controls.getInstance().gamepad;
		drivetrain = Components.getInstance().drivetrain;
		drivetrainRotation = Components.getInstance().drivetrainRotation;
		drivetrainStrafe = Components.getInstance().drivetrainStrafe;
    }

    public void autonomousInit() {

    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
    	drivetrainStrafe.setDefaultCommand(new GamepadDriveStrafeCommand(gamepad));
    	drivetrainRotation.setDefaultCommand(new GamepadDriveRotationCommand(gamepad));
    }

    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
    
	
    public void disabledInit(){
    	stop();
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}
    
    public void testPeriodic() {

    }
    
    public void stop() {
    	drivetrain.setRotation(0.0);
    	drivetrain.setDrivetrainStrafe(0.0,0.0);
    	
    }
}
