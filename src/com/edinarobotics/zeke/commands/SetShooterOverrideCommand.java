package com.edinarobotics.zeke.commands;

import com.edinarobotics.zeke.Components;
import com.edinarobotics.zeke.subsystems.Shooter;
import edu.wpi.first.wpilibj.command.Command;

public class SetShooterOverrideCommand extends Command {

    private boolean shouldOverride;
    private Shooter shooter;
    
    public SetShooterOverrideCommand(boolean shouldOverride) {
        super("SetShooterOverrideCommand");
        this.shouldOverride = shouldOverride;
        shooter = Components.getInstance().shooter;
    }
    
    protected void initialize() {
        shooter.setOverride(shouldOverride);
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
