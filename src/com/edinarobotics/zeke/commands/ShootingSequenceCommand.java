package com.edinarobotics.zeke.commands;

import com.edinarobotics.zeke.subsystems.Collector;
import com.edinarobotics.zeke.subsystems.Shooter;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.StartCommand;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class ShootingSequenceCommand extends CommandGroup {
    
    public ShootingSequenceCommand(boolean lowerAfterShoot) {
        this.setInterruptible(false);
        this.addSequential(new SetCollectorCommand(Collector.CollectorState.RETRACTED,
                Collector.CollectorWheelState.STOPPED));
        this.addSequential(new WaitForCollectorUndeployedCommand());
        this.addSequential(new SetShooterCommand(Shooter.WinchState.FREE));
        if(lowerAfterShoot) {
            this.addSequential(new StartCommand(new ShootLowerGroup()));
        }
    }
    
    private static class ShootLowerGroup extends CommandGroup{
        private ShootLowerGroup(){
            this.setInterruptible(false);
            this.addSequential(new WaitCommand(4));
            this.addSequential(new LowerShooterToHeightCommand(Shooter.FIRING_HEIGHT));
        }
    }
}
