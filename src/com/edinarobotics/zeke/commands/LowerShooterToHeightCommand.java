package com.edinarobotics.zeke.commands;

import com.edinarobotics.zeke.Components;
import com.edinarobotics.zeke.subsystems.Shooter;
import edu.wpi.first.wpilibj.command.Command;

public class LowerShooterToHeightCommand extends Command {
    
    private double targetHeight;
    private double currentHeight;
    private Shooter shooter;

    public LowerShooterToHeightCommand(double targetHeight) {
        shooter = Components.getInstance().shooter;
        this.targetHeight = targetHeight;
        this.currentHeight = shooter.getStringPot();
        requires(shooter);
    }

    protected void initialize() {
        
    }

    protected void execute() {
        this.currentHeight = shooter.getStringPot();
        if (targetHeight < currentHeight) {
            end();
        } else {
            shooter.setWinchState(Shooter.WinchState.LOWERING);
        }
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        shooter.setWinchState(Shooter.WinchState.STOPPED);
    }

    protected void interrupted() {
        shooter.setWinchState(Shooter.WinchState.STOPPED);
    }
    
}
