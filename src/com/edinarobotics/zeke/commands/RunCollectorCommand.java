package com.edinarobotics.zeke.commands;

import com.edinarobotics.zeke.subsystems.Collector;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class RunCollectorCommand extends CommandGroup {
    
    public RunCollectorCommand(double time, boolean raiseWhenFinished) {
        CommandGroup lowerAndRun = new CommandGroup();
        lowerAndRun.addParallel(new SetCollectorCommand(Collector.CollectorState.DEPLOY,
                Collector.CollectorWheelState.COLLECTING));
        lowerAndRun.addParallel(new WaitCommand(time));
        
        this.addSequential(lowerAndRun);
        this.addSequential(new SetCollectorCommand(Collector.CollectorWheelState.STOPPED));
        if(raiseWhenFinished) {
            this.addParallel(new SetCollectorCommand(Collector.CollectorState.RETRACT,
                    Collector.CollectorWheelState.COLLECTING));
            this.addSequential(new WaitCommand(2.0));
        }
    }
}
