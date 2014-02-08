package com.edinarobotics.zeke.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.edinarobotics.utils.gamepad.Gamepad;
import com.edinarobotics.zeke.Components;
import com.edinarobotics.zeke.subsystems.DrivetrainRotation;

public class GamepadDriveRotationCommand extends Command {
    private Gamepad gamepad;
    private DrivetrainRotation drivetrainRotation;
    
    public GamepadDriveRotationCommand(Gamepad gamepad) {
        super("GamepadDriveRotation");
        this.gamepad = gamepad;
        this.drivetrainRotation = Components.getInstance().drivetrainRotation;
        requires(drivetrainRotation);
    }

    protected void initialize() {
    }

    protected void execute() {
        double rotation = gamepad.getGamepadAxisState().getRightJoystick().getX();
        drivetrainRotation.setMecanumPolarRotate(rotation);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }
    
    protected void interrupted() {
        end();
    }

}
