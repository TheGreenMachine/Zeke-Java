package com.edinarobotics.zeke.commands;

import com.edinarobotics.zeke.Components;
import com.edinarobotics.zeke.subsystems.Drivetrain;
import com.edinarobotics.zeke.subsystems.DrivetrainRotation;
import com.edinarobotics.zeke.subsystems.DrivetrainStrafe;
import edu.wpi.first.wpilibj.command.Command;

public class DriveXDistanceCommand extends Command {
    private Drivetrain drivetrain;
    private DrivetrainStrafe drivetrainStrafe;
    private DrivetrainRotation drivetrainRotation;
    private double distanceThreshold;
    
    private final double FORWARD_SPEED = 0.5;
    
    public DriveXDistanceCommand(double distanceThreshold) {
        super("DriveXDistanceCommand");
        this.drivetrain = Components.getInstance().drivetrain;
        this.drivetrainStrafe = Components.getInstance().drivetrainStrafe;
        this.drivetrainRotation = Components.getInstance().drivetrainRotation;
        this.distanceThreshold = distanceThreshold;
        requires(drivetrainStrafe);
        requires(drivetrainRotation);
    }
    
    protected void initialize() {
        drivetrainStrafe.setMecanumPolarStrafe(FORWARD_SPEED, 0.0);
        drivetrainRotation.setMecanumPolarRotate(0.0);
    }

    protected void execute() {
        drivetrainStrafe.setMecanumPolarStrafe(FORWARD_SPEED, 0.0);
        drivetrainRotation.setMecanumPolarRotate(0.0);
    }

    protected boolean isFinished() {
        return drivetrain.getUltrasonicSensor().getDistance() < distanceThreshold;
    }

    protected void end() {
        drivetrainStrafe.setMecanumPolarStrafe(0, 0);
        drivetrainRotation.setMecanumPolarRotate(0);
    }

    protected void interrupted() {
        end();
    }
}
