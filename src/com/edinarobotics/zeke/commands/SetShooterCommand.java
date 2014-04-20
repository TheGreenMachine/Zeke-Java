package com.edinarobotics.zeke.commands;

import com.edinarobotics.zeke.Components;
import com.edinarobotics.zeke.subsystems.Shooter;
import com.edinarobotics.zeke.subsystems.Shooter.WinchState;
import edu.wpi.first.wpilibj.command.Command;

public class SetShooterCommand extends Command {
    private Shooter shooter;
    private WinchState winchState;
    
    public SetShooterCommand(WinchState winchState) {
        super("SetShooter");
        this.shooter = Components.getInstance().shooter;
        this.winchState = winchState;
        requires(shooter);
    }
    
    protected void initialize() {
        shooter.setWinchState(winchState);
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
