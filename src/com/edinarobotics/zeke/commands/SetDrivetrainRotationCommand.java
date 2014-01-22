package com.edinarobotics.zeke.commands;

import com.edinarobotics.zeke.Components;
import com.edinarobotics.zeke.subsystems.DrivetrainRotation;
import edu.wpi.first.wpilibj.command.Command;

public class SetDrivetrainRotationCommand extends Command{
    private double rotation;
    private DrivetrainRotation drivetrainRotation;
    
    public SetDrivetrainRotationCommand(double rotation){
        super("SetDrivetrainRotation");
        this.rotation = rotation;
        drivetrainRotation = Components.getInstance().drivetrainRotation;
        requires(drivetrainRotation);
    }

    protected void initialize() {
        drivetrainRotation.setMecanumPolarRotate(rotation);
    }

    protected void execute() {
        
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
        
    }

    protected void interrupted() {
        
    }
    
}
