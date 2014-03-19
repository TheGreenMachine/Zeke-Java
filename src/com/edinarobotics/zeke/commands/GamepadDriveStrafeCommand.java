
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
        double direction = wpilibAngleCorrection(gamepad.getGamepadAxisState().getLeftDirection());
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
    
    public static double wpilibAngleCorrection(double normalAngle){
        //Converts normal angle format to the weird WPILib measurement
        double convertedAngle = (normalAngle - 90.0) * -1.0;
        if(convertedAngle > 180.0){
            return convertedAngle - 360.0;
        }
        else if(convertedAngle < -180.0){
            return convertedAngle + 360.0;
        }
        return convertedAngle;
    }
    
}
