package com.edinarobotics.zeke.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class LowerShooterAfterWaitCommand extends CommandGroup {

    public LowerShooterAfterWaitCommand(double waitTime) {
        this.setInterruptible(true);
        this.addSequential(new WaitCommand(waitTime));
        this.addSequential(new LowerShooterCommand());
    }
}
