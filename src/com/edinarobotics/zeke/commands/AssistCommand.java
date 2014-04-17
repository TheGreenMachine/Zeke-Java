package com.edinarobotics.zeke.commands;

import com.edinarobotics.zeke.subsystems.Collector;
import com.edinarobotics.zeke.subsystems.Collector.CollectorWheelState;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author GreenMachine
 */
public class AssistCommand extends CommandGroup {
    
    public AssistCommand(boolean runAssist){
        if(runAssist){
            addSequential(new SetCollectorCommand(CollectorWheelState.REVERSING));
            addSequential(new SetPusherCommand(true));
        }
        else {
            addSequential(new SetCollectorCommand(CollectorWheelState.STOPPED));
            addSequential(new SetPusherCommand(false));
        }
    }
    
}
