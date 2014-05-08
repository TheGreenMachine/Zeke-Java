package com.edinarobotics.zeke.commands;

import com.edinarobotics.zeke.Components;
import com.edinarobotics.zeke.subsystems.Shooter;
import edu.wpi.first.wpilibj.command.Command;

public class LowerShooterCommand extends Command {

    private Shooter shooter;

    public LowerShooterCommand() {
        shooter = Components.getInstance().shooter;
        requires(shooter);
    }

    protected void initialize() {

    }

    protected void execute() {
        if (!shooter.getShooterLimitSwitch()) {
            shooter.setWinchState(Shooter.WinchState.LOWERING);
        }
    }

    protected boolean isFinished() {
        return shooter.getShooterLimitSwitch();
    }

    protected void end() {
        shooter.setWinchState(Shooter.WinchState.STOPPED);
    }

    protected void interrupted() {
        end();
    }
}
