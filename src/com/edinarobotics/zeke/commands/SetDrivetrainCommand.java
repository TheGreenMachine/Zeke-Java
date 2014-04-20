package com.edinarobotics.zeke.commands;

import edu.wpi.first.wpilibj.command.Command;

public class SetDrivetrainCommand extends Command {
    private SetDrivetrainStrafeCommand drivetrainStrafeCommand;
    private SetDrivetrainRotationCommand drivetrainRotationCommand;
    
    public SetDrivetrainCommand(double magnitude, double direction, double rotation){
        super("SetDrivetrain");
        drivetrainStrafeCommand = new SetDrivetrainStrafeCommand(magnitude, direction);
        drivetrainRotationCommand = new SetDrivetrainRotationCommand(rotation);
    }

    protected void initialize() {
        drivetrainStrafeCommand.start();
        drivetrainRotationCommand.start();
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
