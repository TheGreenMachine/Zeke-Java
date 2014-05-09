package com.edinarobotics.zeke.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class AutonomousCommand extends CommandGroup {

    public AutonomousCommand(boolean twoBallAuto) {
        if (twoBallAuto) {
            CommandGroup driveSubsequence = new CommandGroup();
            driveSubsequence.addSequential(new WaitCommand(0.75));
            driveSubsequence.addSequential(new DriveXTimeCommand(0.5, 0.35));
            
            this.addSequential(new ShootingSequenceCommand(false));
            this.addParallel(new LowerShooterAfterWaitCommand(1.5));
            this.addParallel(driveSubsequence);
            this.addSequential(new RunCollectorCommand(3.5, true));
            this.addSequential(new ShootingSequenceCommand(false));
            this.addParallel(new LowerShooterAfterWaitCommand(1.5));
            this.addSequential(new WaitCommand(0.5));
            this.addSequential(new DriveXTimeCommand(3.5, 0.35));
        } else {
            this.addSequential(new ShootingSequenceCommand(false));
            this.addParallel(new LowerShooterAfterWaitCommand(1.5));
            this.addSequential(new WaitCommand(0.5));
            this.addSequential(new DriveXTimeCommand(3.5, 0.35));
        }
    }
}
