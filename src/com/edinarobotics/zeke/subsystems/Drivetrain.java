package com.edinarobotics.zeke.subsystems;

import com.edinarobotics.utils.subsystems.Subsystem1816;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.RobotDrive;

public class Drivetrain extends Subsystem1816 {

	private RobotDrive robotDrive;
	private CANTalon frontLeft, frontRight, backLeft, backRight;

	private double magnitude, direction, rotation;
	
	public Drivetrain(int frontLeft, int frontRight, int backLeft, int backRight) {
		this.frontLeft = new CANTalon(frontLeft);
		this.frontRight = new CANTalon(frontRight);
		this.backLeft = new CANTalon(backLeft);
		this.backRight = new CANTalon(backRight);
		
		this.frontRight.setInverted(true);
		this.backRight.setInverted(true);
		
		robotDrive = new RobotDrive(this.frontLeft, this.backLeft, this.frontRight, this.backRight);
	}
	
	@Override
	public void update() {
		robotDrive.mecanumDrive_Polar(magnitude, direction, rotation);
	}
	
	public void setDrivetrainStrafe(double magnitude, double direction) {
		this.magnitude = magnitude;
		this.direction = direction;
		update();
	}
	
	public void setRotation(double rotation) {
		this.rotation = rotation;
		update();
	}

}
