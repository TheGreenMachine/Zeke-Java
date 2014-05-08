package com.edinarobotics.zeke.subsystems;

import com.edinarobotics.utils.common.Updatable;
import com.edinarobotics.utils.sensors.UltrasonicSensor;
import edu.wpi.first.wpilibj.RobotDrive;

public class Drivetrain implements Updatable {
    private RobotDrive robotDrive;
    private double magnitude, direction, rotation;
    private UltrasonicSensor ultrasonicSensor;
    private static final boolean IS_PRACTICEBOT = true;
    
    public static final double ULTRASONIC_SCALE = 6.799; //Value is in feet/volt
    
    public Drivetrain(int frontLeft, int rearLeft,
           int frontRight, int rearRight, int ultrasonicSensorChannel) {
        ultrasonicSensor = new UltrasonicSensor(ultrasonicSensorChannel, 
                ULTRASONIC_SCALE);
        this.robotDrive = new RobotDrive(frontLeft, rearLeft,
               frontRight, rearRight);
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
    
    public UltrasonicSensor getUltrasonicSensor() {
        return ultrasonicSensor;
    }
    
    public static double wpilibAngleCorrection(double normalAngle){
        //Converts normal angle format to the weird WPILib measurement
        double convertedAngle = (normalAngle - 90.0) * -1.0;
        if(convertedAngle > 180.0){
            return convertedAngle - 360.0;
        }
        else if(convertedAngle < -180.0){
            return convertedAngle + 360.0;
        }
        return convertedAngle;
    }
    
    public void update() {
        double workingDirection;
        if(IS_PRACTICEBOT) {
            workingDirection = direction;
        }
        else {
            workingDirection = wpilibAngleCorrection(direction);
        }
        robotDrive.mecanumDrive_Polar(magnitude, workingDirection, rotation);
    }
}

