package com.edinarobotics.zeke.commands;

import com.edinarobotics.zeke.Components;
import com.edinarobotics.zeke.subsystems.Drivetrain;
import com.edinarobotics.zeke.subsystems.DrivetrainRotation;
import com.edinarobotics.zeke.subsystems.DrivetrainStrafe;
import edu.wpi.first.wpilibj.command.Command;

public class AutonomousMobilityOnlyCommand extends Command {

    private double maxDistanceToTrigger;
    private double driveSpeed;
    private double forwardTime;
    private DrivetrainStrafe drivetrainStrafe;
    private DrivetrainRotation drivetrainRotation;
    private Drivetrain drivetrain;
    private boolean shouldContinue;

    public AutonomousMobilityOnlyCommand(double maxDistanceToTrigger, double driveSpeed, double forwardTimeSecs) {
        super("AutonomousMobilityOnly");
        this.driveSpeed = driveSpeed;
        this.forwardTime = forwardTimeSecs;
        drivetrainStrafe = Components.getInstance().drivetrainStrafe;
        drivetrainRotation = Components.getInstance().drivetrainRotation;
        drivetrain = Components.getInstance().drivetrain;
        this.maxDistanceToTrigger = maxDistanceToTrigger;
        shouldContinue = false;
    }

    protected void initialize() {
        drivetrainStrafe.setMecanumPolarStrafe(0, 0);
        drivetrainRotation.setMecanumPolarRotate(0);
        double currentDistance = drivetrain.getUltrasonicSensor().getDistance();
        shouldContinue = currentDistance <= maxDistanceToTrigger;
        System.out.println("Autonomous allowed to continue: "+!shouldContinue);
    }

    protected void execute() {
        if (shouldContinue) {
            if (timeSinceInitialized() > this.forwardTime) {
                // Drive time is over. Stop robot.
                drivetrainStrafe.setMecanumPolarStrafe(0, 0);
                drivetrainRotation.setMecanumPolarRotate(0);
            } else {
                drivetrainStrafe.setMecanumPolarStrafe(driveSpeed, 90.0);
                drivetrainRotation.setMecanumPolarRotate(0);
            }
        }
    }

    protected boolean isFinished() {
        return !shouldContinue;
    }

    protected void end() {
        drivetrainStrafe.setMecanumPolarStrafe(0, 0);
        drivetrainRotation.setMecanumPolarRotate(0);
        shouldContinue = false;
    }

    protected void interrupted() {
        end();
    }
}
