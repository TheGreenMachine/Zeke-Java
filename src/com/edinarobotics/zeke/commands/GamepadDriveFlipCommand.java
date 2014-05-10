package com.edinarobotics.zeke.commands;

import com.edinarobotics.zeke.Components;
import com.edinarobotics.zeke.subsystems.DrivetrainStrafe;
import edu.wpi.first.wpilibj.command.Command;

public class GamepadDriveFlipCommand extends Command {
    private boolean flipped;
    private DrivetrainStrafe strafeSubsystem;
    
    public GamepadDriveFlipCommand(boolean setFlipped){
        this.flipped = setFlipped;
        strafeSubsystem = Components.getInstance().drivetrainStrafe;
    }

    protected void initialize() {
        Command currentStrafeCmd = strafeSubsystem.getCurrentCommand();
        if(currentStrafeCmd instanceof GamepadDriveStrafeCommand){
            ((GamepadDriveStrafeCommand)currentStrafeCmd).setFlipped(flipped);
        }
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
