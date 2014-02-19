package com.edinarobotics.zeke.subsystems;

import com.edinarobotics.utils.common.Updatable;
import com.edinarobotics.utils.sensors.UltrasonicSensor;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;

public class Drivetrain implements Updatable {
    private RobotDrive robotDrive;
    private double magnitude, direction, rotation;
    private Talon frontLeftT, frontRightT, rearLeftT, rearRightT;
    private UltrasonicSensor ultrasonicSensor;
    private DriverStationLCD driverStationLCD;
    
    public static final double ULTRASONIC_SCALE = 6.799; //Value is in feet/volt
    
    public Drivetrain(int frontLeft, int rearLeft,
           int frontRight, int rearRight, int ultrasonicSensorChannel) {
        frontLeftT = new Talon(frontLeft);
        frontRightT = new Talon(frontRight);
        rearLeftT = new Talon(rearLeft);
        rearRightT = new Talon(rearRight);
        ultrasonicSensor = new UltrasonicSensor(ultrasonicSensorChannel, 
                ULTRASONIC_SCALE);
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
    
    public UltrasonicSensor getUltrasonicSensor() {
        return ultrasonicSensor;
    }
    
    public void update() {
        robotDrive.mecanumDrive_Polar(magnitude, direction, rotation);
        driverStationLCD.clear();
        driverStationLCD.println(DriverStationLCD.Line.kUser1, 0, "Ultrasonic (ft): "
                + getUltrasonicSensor().getDistance());
        driverStationLCD.updateLCD();
    }
}

