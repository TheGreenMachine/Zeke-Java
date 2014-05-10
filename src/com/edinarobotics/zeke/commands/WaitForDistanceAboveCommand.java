package com.edinarobotics.zeke.commands;

import com.edinarobotics.zeke.Components;
import com.edinarobotics.zeke.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.command.Command;

public class WaitForDistanceAboveCommand extends Command {
    private Drivetrain drivetrain;
    private double lowerDistance;
    
    public WaitForDistanceAboveCommand(double distLowerThreshold){
        super("WaitForDistanceAbove");
        this.lowerDistance = distLowerThreshold;
        drivetrain = Components.getInstance().drivetrain;
    }

    protected void initialize() {
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return drivetrain.getUltrasonicSensor().getDistance() > lowerDistance;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
