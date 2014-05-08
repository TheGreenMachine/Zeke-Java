package com.edinarobotics.zeke.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonomousVisionCommand extends CommandGroup{
    public AutonomousVisionCommand(){
        this.addSequential(new WaitForVisionCommand(20, 6.0)); // 20 = 1 second
        this.addSequential(new ShootingSequenceCommand(true));
    }
}
