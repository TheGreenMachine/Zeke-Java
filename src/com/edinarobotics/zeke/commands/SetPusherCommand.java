package com.edinarobotics.zeke.commands;

import com.edinarobotics.zeke.Components;
import com.edinarobotics.zeke.subsystems.Shooter;
import edu.wpi.first.wpilibj.command.Command;

public class SetPusherCommand extends Command {

    private Shooter shooter;
    private boolean shouldDeploy;
    
    public SetPusherCommand(boolean shouldDeploy) {
        super("SetPusher");
        this.shooter = Components.getInstance().shooter;
        this.shouldDeploy = shouldDeploy;
        requires(shooter);
    }
    
    protected void initialize() {
        shooter.setPusher(shouldDeploy);
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