package com.edinarobotics.zeke.commands;

import com.edinarobotics.zeke.subsystems.Collector;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class AutonomousCommand extends CommandGroup {

    public AutonomousCommand(boolean twoBallAuto) {
        if (twoBallAuto) {
            CommandGroup lowerAndDeploy = new CommandGroup();
            lowerAndDeploy.addParallel(new LowerShooterCommand());
            lowerAndDeploy.addParallel(new SetCollectorCommand(Collector.CollectorState.DEPLOY));
            
            CommandGroup driveAndCollect = new CommandGroup();
            driveAndCollect.addParallel(new SetCollectorCommand(Collector.CollectorWheelState.COLLECTING));
            driveAndCollect.addParallel(new DriveXTimeCommand(0.5, 0.35));
            driveAndCollect.addSequential(new WaitCommand(1.5));
            
            CommandGroup retractWhileCollecting = new CommandGroup();
            retractWhileCollecting.addParallel(new WaitCommand(2.0));
            retractWhileCollecting.addParallel(new SetCollectorCommand(Collector.CollectorState.RETRACT,
                    Collector.CollectorWheelState.COLLECTING));
            
            this.addSequential(new ShootingSequenceCommand(false));
            this.addSequential(new WaitCommand(1.5));
            this.addSequential(lowerAndDeploy);
            this.addSequential(driveAndCollect);
            this.addSequential(retractWhileCollecting);
            this.addSequential(new ShootingSequenceCommand(false));
            this.addParallel(new DriveXTimeCommand(3.5, 0.35));
            this.addSequential(new WaitCommand(1.5));
            this.addSequential(new LowerShooterCommand());
        } else {
            this.addSequential(new ShootingSequenceCommand(false));
            this.addParallel(new LowerShooterAfterWaitCommand(1.5));
            this.addSequential(new WaitCommand(0.5));
            this.addSequential(new DriveXTimeCommand(3.5, 0.35));
        }
    }
}
