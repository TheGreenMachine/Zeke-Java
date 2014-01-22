package com.edinarobotics.zeke.subsystems;

import edu.wpi.first.wpilibj.RobotDrive;

public class Drivetrain {
    private RobotDrive robotDrive;
    private double magnitude, direction, rotation;
    
    public Drivetrain(int frontLeft, int rearLeft,
           int frontRight, int rearRight) {
        this.robotDrive = new RobotDrive(frontLeft, rearLeft,
               frontRight, rearRight);
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
    
    public void update() {
        robotDrive.mecanumDrive_Polar(magnitude, direction, rotation);
    }
    
    public double getMagnitude() {
        return magnitude;
    }
    
    public double getDirection() {
        return direction;
    }
    
}
