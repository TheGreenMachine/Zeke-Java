package com.edinarobotics.zeke.commands;

import com.edinarobotics.utils.gamepad.Gamepad;
import com.edinarobotics.zeke.Components;
import com.edinarobotics.zeke.subsystems.DrivetrainRotation;

import edu.wpi.first.wpilibj.command.Command;

public class GamepadDriveRotationCommand extends Command {

	private Gamepad gamepad;
	private DrivetrainRotation drivetrain;
	
	public GamepadDriveRotationCommand(Gamepad gamepad) {
		super("GamepadDriveRotationCommand");
		this.gamepad = gamepad;
		drivetrain = Components.getInstance().drivetrainRotation;
		requires(drivetrain);
	}
	
	
	
	@Override
	protected void initialize() {
		
	}

	@Override
	protected void execute() {
		double rotation = gamepad.getRightX();
		
		drivetrain.setRotation(rotation);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		drivetrain.setRotation(0.0);
		
	}

	@Override
	protected void interrupted() {
		end();
	}
	
	

}
