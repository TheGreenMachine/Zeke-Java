package com.edinarobotics.zeke.subsystems;

import com.edinarobotics.utils.common.Updatable;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;

public class Drivetrain implements Updatable {
    private RobotDrive robotDrive;
    private double magnitude, direction, rotation;
    private Talon frontLeftT, frontRightT, rearLeftT, rearRightT;
    
    public Drivetrain(int frontLeft, int rearLeft,
           int frontRight, int rearRight) {
        frontLeftT = new Talon(frontLeft);
        frontRightT = new Talon(frontRight);
        rearLeftT = new Talon(rearLeft);
        rearRightT = new Talon(rearRight);
        this.robotDrive = new RobotDrive(frontLeftT, rearLeftT,
               frontRightT, rearRightT);
        robotDrive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, false);
        robotDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, false);
        robotDrive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
        robotDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
    }
    
    public void mecanumPolarStrafe(double magnitude, double direction) {
        this.magnitude = magnitude;
        this.direction = direction;
        update();
    }
    
    public void mecanumPolarRotation(double rotation) {
        this.rotation = rotation;
        update();
    }
    
    public double getMagnitude() {
        return magnitude;
    }
    
    public double getDirection() {
        return direction;
    }
    
    public void update() {
        robotDrive.mecanumDrive_Polar(magnitude, direction, rotation);
    }
}

