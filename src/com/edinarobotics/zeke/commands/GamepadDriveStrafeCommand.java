package com.edinarobotics.zeke.commands;

import com.edinarobotics.utils.gamepad.Gamepad;
import com.edinarobotics.zeke.Components;
import com.edinarobotics.zeke.subsystems.DrivetrainStrafe;

import edu.wpi.first.wpilibj.command.Command;

public class GamepadDriveStrafeCommand extends Command {

	private Gamepad gamepad;
	private DrivetrainStrafe drivetrain;
	
	public GamepadDriveStrafeCommand(Gamepad gamepad) {
		super("gamepaddrivecommand");
		this.gamepad = gamepad;
		drivetrain = Components.getInstance().drivetrainStrafe;
		requires(drivetrain);
	}
	
	@Override
	protected void initialize() {
		
	}

	@Override
	protected void execute() {
		double magnitude = gamepad.getGamepadAxisState().getLeftMagnitude();
		double direction = gamepad.getGamepadAxisState().getLeftDirection();
		
		drivetrain.setStrafe(magnitude, direction);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		drivetrain.setStrafe(0.0,0.0);
		
	}

	@Override
	protected void interrupted() {
		end();
	}

}
