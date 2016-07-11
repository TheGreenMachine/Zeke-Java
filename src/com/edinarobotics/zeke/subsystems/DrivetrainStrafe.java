package com.edinarobotics.zeke.subsystems;

import com.edinarobotics.utils.subsystems.Subsystem1816;

import edu.wpi.first.wpilibj.command.Command;

public class DrivetrainStrafe extends Subsystem1816 {

	private Drivetrain drivetrain;
	private double magnitude;
	private double direction;
	
	public DrivetrainStrafe(Drivetrain drivetrain) {
		this.drivetrain = drivetrain;
	}
	
	@Override
	public void update() {
		drivetrain.setDrivetrainStrafe(magnitude, direction);
	}
	
	public void setStrafe(double magnitude, double direction) {
		this.magnitude = magnitude;
		this.direction = direction;
		update();
	}
	
	public void setDefaultCommand(Command command) {
		if (getDefaultCommand() != null) {
			super.getDefaultCommand().cancel();
		}
		super.setDefaultCommand(command);
	}
}
