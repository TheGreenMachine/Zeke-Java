package com.edinarobotics.zeke.commands;

import com.edinarobotics.zeke.subsystems.Shooter;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class LowerShooterAfterWaitCommand extends CommandGroup {

    public LowerShooterAfterWaitCommand() {
        this.setInterruptible(false);
        this.addSequential(new WaitCommand(4));
        this.addSequential(new LowerShooterToHeightCommand(Shooter.FIRING_HEIGHT));
    }
}
