package com.edinarobotics.zeke.commands;

import com.edinarobotics.zeke.Components;
import com.edinarobotics.zeke.subsystems.DrivetrainRotation;
import com.edinarobotics.zeke.subsystems.DrivetrainStrafe;
import edu.wpi.first.wpilibj.command.Command;

public class DriveXTimeCommand extends Command {
    private DrivetrainStrafe drivetrainStrafe;
    private DrivetrainRotation drivetrainRotation;
    private double speed;
    
    public DriveXTimeCommand(double time, double speed) {
        super("DriveXTimeCommand");
        this.drivetrainStrafe = Components.getInstance().drivetrainStrafe;
        this.drivetrainRotation = Components.getInstance().drivetrainRotation;
        this.speed = speed;
        requires(drivetrainStrafe);
        requires(drivetrainRotation);
        setTimeout(time);
    }
    
    protected void initialize() {
        drivetrainStrafe.setMecanumPolarStrafe(speed, 0.0);
        drivetrainRotation.setMecanumPolarRotate(0.0);
    }

    protected void execute() {
        drivetrainStrafe.setMecanumPolarStrafe(speed, 0.0);
        drivetrainRotation.setMecanumPolarRotate(0.0);
    }

    protected boolean isFinished() {
        return isTimedOut();
    }

    protected void end() {
        drivetrainStrafe.setMecanumPolarStrafe(0, 0);
        drivetrainRotation.setMecanumPolarRotate(0);
    }

    protected void interrupted() {
        end();
    }
}
