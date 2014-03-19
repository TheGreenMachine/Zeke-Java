package com.edinarobotics.zeke.commands;

import com.edinarobotics.zeke.subsystems.Collector;
import com.edinarobotics.zeke.subsystems.Shooter;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.StartCommand;

public class ShootingSequenceCommand extends CommandGroup {
    
    public ShootingSequenceCommand(boolean lowerAfterShoot) {
        this.setInterruptible(false);
        this.addSequential(new SetCollectorCommand(Collector.CollectorState.RETRACT,
                Collector.CollectorWheelState.STOPPED));
        this.addSequential(new WaitForCollectorUndeployedCommand());
        this.addSequential(new SetShooterCommand(Shooter.WinchState.FREE));
        if(lowerAfterShoot) {
            this.addSequential(new StartCommand(new LowerShooterAfterWaitCommand(3)));
        }
    }
}
