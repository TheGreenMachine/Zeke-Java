
package com.edinarobotics.zeke.commands;

import com.edinarobotics.zeke.Components;
import com.edinarobotics.zeke.subsystems.Collector;
import com.edinarobotics.zeke.subsystems.Collector.CollectorWheelState;
import edu.wpi.first.wpilibj.command.Command;

public class SetCollectorCommand extends Command {
    
    private Collector collector;
    private boolean isDeployed;
    private CollectorWheelState collectorWheelState;
    
    public SetCollectorCommand(boolean isDeployed, CollectorWheelState collectorWheelState) {
        this.collector = Components.getInstance().collector;
        this.isDeployed = isDeployed;
        this.collectorWheelState = collectorWheelState;
        requires(collector);
    }
    
    protected void initialize() {
       collector.setDeployed(isDeployed);
       collector.setCollectorWheelState(collectorWheelState);
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
