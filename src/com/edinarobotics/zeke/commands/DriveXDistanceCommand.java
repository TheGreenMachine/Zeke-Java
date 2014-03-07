package com.edinarobotics.zeke.commands;

import com.edinarobotics.utils.pid.PIDConfig;
import com.edinarobotics.utils.pid.PIDTuningManager;
import com.edinarobotics.zeke.Components;
import com.edinarobotics.zeke.subsystems.Drivetrain;
import com.edinarobotics.zeke.subsystems.DrivetrainRotation;
import com.edinarobotics.zeke.subsystems.DrivetrainStrafe;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;

public class DriveXDistanceCommand extends Command {
    private Drivetrain drivetrain;
    private DrivetrainStrafe drivetrainStrafe;
    private DrivetrainRotation drivetrainRotation;
    private double distanceThreshold;
    private double speed;
    private boolean isForward;
    private final double P = 0.5, I = 0.0, D = 0.0, F = 0.0;
    PIDConfig pidConfig;
    PIDController pidController;
    
    public DriveXDistanceCommand(double distanceThreshold, double speed, boolean isForward) {
        super("DriveXDistanceCommand");
        this.drivetrain = Components.getInstance().drivetrain;
        this.drivetrainStrafe = Components.getInstance().drivetrainStrafe;
        this.drivetrainRotation = Components.getInstance().drivetrainRotation;
        this.distanceThreshold = distanceThreshold;
        this.speed = speed;
        this.isForward = isForward;
        pidConfig = PIDTuningManager.getInstance().getPIDConfig("DriveXDistance");
        pidController = new PIDController(P, I, D, F, drivetrainRotation.getGyro(), drivetrainRotation, 0.025);
        pidController.setAbsoluteTolerance(5.0);
        requires(drivetrainStrafe);
        requires(drivetrainRotation);
    }
    
    protected void initialize() {
        drivetrainStrafe.setMecanumPolarStrafe(speed, isForward ? 90.0 : 270.0);
        drivetrainRotation.setMecanumPolarRotate(0.0);
        drivetrainRotation.resetGyro();
        pidController.reset();
        pidController.enable();
    }

    protected void execute() {
        drivetrainStrafe.setMecanumPolarStrafe(speed, isForward ? 90.0 : 270.0);
        pidConfig.setSetpoint(0.0);
        pidConfig.setValue(drivetrainRotation.getGyroAngle());
        pidController.setPID(pidConfig.getP(P), pidConfig.getI(I), pidConfig.getD(D), pidConfig.getF(F));
        pidController.setSetpoint(pidConfig.getSetpoint());
        System.out.println(drivetrainRotation.getGyroAngle() + "  " + drivetrainRotation.getRotation());
    }

    protected boolean isFinished() {
        if(isForward) {
            return drivetrain.getUltrasonicSensor().getDistance() < distanceThreshold;
        } else {
            return drivetrain.getUltrasonicSensor().getDistance() > distanceThreshold;
        }
    }

    protected void end() {
        pidController.disable();
        drivetrainStrafe.setMecanumPolarStrafe(0, 0);
        drivetrainRotation.setMecanumPolarRotate(0);
    }

    protected void interrupted() {
        end();
    }
}
