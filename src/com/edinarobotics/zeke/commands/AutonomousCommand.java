package com.edinarobotics.zeke.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class AutonomousCommand extends CommandGroup {

    public AutonomousCommand(boolean twoBallAuto) {
        if (twoBallAuto) {
            this.addSequential(new ShootingSequenceCommand(true));
            this.addSequential(new RunCollectorCommand(2.0, true));
            this.addSequential(new ShootingSequenceCommand(false));
            this.addParallel(new LowerShooterAfterWaitCommand(1.5));
            this.addSequential(new WaitCommand(0.5));
            this.addSequential(new DriveXTimeCommand(2.0, 0.35));
        } else {
            this.addSequential(new DriveXTimeCommand(2.0, 0.35));
            this.addSequential(new ShootingSequenceCommand(true));
        }
    }
}
