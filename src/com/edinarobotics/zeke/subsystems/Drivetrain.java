package com.edinarobotics.zeke.subsystems;

import com.edinarobotics.utils.common.Updatable;
import com.edinarobotics.utils.sensors.UltrasonicSensor;
import com.edinarobotics.utils.wheel.Wheel;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;

public class Drivetrain implements Updatable {
    private RobotDrive robotDrive;
    private double magnitude, direction, rotation;
    private Wheel frontLeft, frontRight, rearLeft, rearRight;
    private UltrasonicSensor ultrasonicSensor;
    
    private static final double ULTRASONIC_SCALE = 6.799;
    
    public Drivetrain(Wheel frontLeft, Wheel rearLeft,
           Wheel frontRight, Wheel rearRight, int ultrasonicSensorChannel) {
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.rearLeft = rearLeft;
        this.rearRight = rearRight;
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
    
    public void update() {
        robotDrive.mecanumDrive_Polar(magnitude, direction, rotation);  
    }
}

