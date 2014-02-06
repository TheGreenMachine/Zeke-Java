
package com.edinarobotics.zeke.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.edinarobotics.utils.gamepad.Gamepad;
import com.edinarobotics.zeke.subsystems.DrivetrainStrafe;
import com.edinarobotics.zeke.Components;
import com.edinarobotics.utils.gamepad.GamepadAxisState;

public class GamepadDriveStrafeCommand extends Command {
    private Gamepad gamepad;
    private GamepadAxisState gamepadAxisState;
    private DrivetrainStrafe drivetrainStrafe;
    
    public GamepadDriveStrafeCommand(Gamepad gamepad) {
        super("GamepadDriveStrafe");
        this.gamepad = gamepad;
        this.drivetrainStrafe = Components.getInstance().drivetrainStrafe;
        requires(drivetrainStrafe);
    }

    protected void initialize() {
    }

    protected void execute() {
        double magnitude = gamepad.getGamepadAxisState().getLeftMagnitude();
        double direction = gamepad.getGamepadAxisState().getLeftDirection();
        drivetrainStrafe.setMecanumPolarStrafe(magnitude, direction);
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
