package com.edinarobotics.zeke.subsystems;

import com.edinarobotics.utils.subsystems.Subsystem1816;

import edu.wpi.first.wpilibj.command.Command;

public class DrivetrainRotation extends Subsystem1816 {

	private Drivetrain drivetrain;
	private double rotation;
	
	public DrivetrainRotation(Drivetrain drivetrain) {
		this.drivetrain = drivetrain;
	}
	
	@Override
	public void update() {
		drivetrain.setRotation(rotation);
	}
	
	public void setRotation(double rotation) {
		this.rotation = rotation;
		update();
	}
	
	public void setDefaultCommand(Command command) {
		if (getDefaultCommand() != null) {
			super.getDefaultCommand().cancel();
		}
		super.setDefaultCommand(command);
	}

}
