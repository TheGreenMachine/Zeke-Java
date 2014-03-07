package com.edinarobotics.zeke.commands;

import com.edinarobotics.zeke.Components;
import com.edinarobotics.zeke.subsystems.Drivetrain;
import com.edinarobotics.zeke.subsystems.DrivetrainRotation;
import com.edinarobotics.zeke.subsystems.DrivetrainStrafe;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

public class DriveXDistanceCommandNoPID extends Command {
    private Drivetrain drivetrain;
    private DrivetrainStrafe drivetrainStrafe;
    private DrivetrainRotation drivetrainRotation;
    private double distanceThreshold;
    private double speed;
    private boolean isForward;
    
    public DriveXDistanceCommandNoPID(double distanceThreshold, double speed, boolean isForward) {
        super("DriveXDistanceCommand");
        this.drivetrain = Components.getInstance().drivetrain;
        this.drivetrainStrafe = Components.getInstance().drivetrainStrafe;
        this.drivetrainRotation = Components.getInstance().drivetrainRotation;
        this.distanceThreshold = distanceThreshold;
        this.speed = speed;
        this.isForward = isForward;
        requires(drivetrainStrafe);
        requires(drivetrainRotation);
    }
    
    protected void initialize() {
        drivetrainStrafe.setMecanumPolarStrafe(speed, isForward ? 90.0 : 270.0);
        drivetrainRotation.setMecanumPolarRotate(0.0);
    }

    protected void execute() {
        speed = DriverStation.getInstance().getAnalogIn(1);
        drivetrainStrafe.setMecanumPolarStrafe(speed, isForward ? 90.0 : 270.0);
        drivetrainRotation.setMecanumPolarRotate(0.0);
    }

    protected boolean isFinished() {
        if(isForward) {
            return drivetrain.getUltrasonicSensor().getDistance() < distanceThreshold;
        } else {
            return drivetrain.getUltrasonicSensor().getDistance() > distanceThreshold;
        }
    }

    protected void end() {
        drivetrainStrafe.setMecanumPolarStrafe(0, 0);
        drivetrainRotation.setMecanumPolarRotate(0);
    }

    protected void interrupted() {
        end();
    }
}
