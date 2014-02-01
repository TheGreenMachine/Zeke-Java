
package com.edinarobotics.zeke.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.edinarobotics.utils.gamepad.Gamepad;
import com.edinarobotics.zeke.Components;
import com.edinarobotics.zeke.subsystems.DrivetrainRotation;

public class GamepadDriveRotationCommand extends Command {
    private Gamepad gamepad;
    private DrivetrainRotation drivetrainRotation;
    
    public GamepadDriveRotationCommand(Gamepad gamepad) {
        super ("GamepadDriveRotation");
        this.gamepad = gamepad;
        this.drivetrainRotation = Components.getInstance().drivetrainRotation;
        requires(drivetrainRotation);
    }

    protected void interrupted() {
        end();
    }

    protected void initialize() {
        drivetrainRotation.setMecanumPolarRotate(0);
    }

    protected void execute() {
        double magnitude = gamepad.getGamepadAxisState().getRightJoystick().getX();
        drivetrainRotation.setMecanumPolarRotate(magnitude);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        drivetrainRotation.setMecanumPolarRotate(0);
    }
    
    
}
