package com.edinarobotics.zeke.commands;

import com.edinarobotics.zeke.Components;
import com.edinarobotics.zeke.subsystems.Collector;
import edu.wpi.first.wpilibj.command.Command;


public class ValveWaitCommand extends Command {
    private Collector collector;

    protected void initialize() {
        this.collector = Components.getInstance().collector;
        requires(collector);
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
