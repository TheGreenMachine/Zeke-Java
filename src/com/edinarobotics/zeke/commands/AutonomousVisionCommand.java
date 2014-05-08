package com.edinarobotics.zeke.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class AutonomousVisionCommand extends CommandGroup {

    public AutonomousVisionCommand() {
        this.addSequential(new WaitForVisionCommand(20, 6.0)); // 20 = 1 second
        this.addSequential(new ShootingSequenceCommand(false));
        this.addParallel(new LowerShooterAfterWaitCommand(1.5));
        this.addSequential(new WaitCommand(0.5));
        this.addSequential(new DriveXTimeCommand(3.5, 0.35));
    }
}
