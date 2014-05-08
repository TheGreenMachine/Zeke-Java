
package com.edinarobotics.zeke.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.edinarobotics.utils.gamepad.Gamepad;
import com.edinarobotics.zeke.subsystems.DrivetrainStrafe;
import com.edinarobotics.zeke.Components;

public class GamepadDriveStrafeCommand extends Command {
    private Gamepad gamepad;
    private DrivetrainStrafe drivetrainStrafe;
    private boolean flipped;
    
    public GamepadDriveStrafeCommand(Gamepad gamepad) {
        super("GamepadDriveStrafe");
        this.gamepad = gamepad;
        this.drivetrainStrafe = Components.getInstance().drivetrainStrafe;
        flipped = false;
        requires(drivetrainStrafe);
    }

    protected void initialize() {
    }

    protected void execute() {
        double magnitude = gamepad.getGamepadAxisState().getLeftMagnitude();
        double direction = gamepad.getGamepadAxisState().getLeftDirection();
        double flipValue = (flipped ? 180.0 : 0.0);
        drivetrainStrafe.setMecanumPolarStrafe(magnitude, direction + flipValue);
    }
    
    public void setFlipped(boolean flip){
        this.flipped = flip;
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
