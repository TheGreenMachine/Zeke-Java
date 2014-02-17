package com.edinarobotics.zeke.commands;

import com.edinarobotics.zeke.Components;
import com.edinarobotics.zeke.subsystems.Collector;
import com.edinarobotics.zeke.subsystems.Shooter;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class ShootingSequenceCommand extends CommandGroup {
    private Shooter shooter;
    private Collector collector;
    
    public ShootingSequenceCommand() {
        shooter = Components.getInstance().shooter;
        collector = Components.getInstance().collector;
        
        this.addSequential(new SetCollectorCommand(false, Collector.CollectorWheelState.STOPPED));
        this.addSequential(new WaitCommand(4));
        this.addSequential(new SetShooterCommand(Shooter.WinchState.FREE));
        this.addSequential(new WaitCommand(6));
        this.addSequential(new LowerShooterToHeightCommand(Shooter.FIRING_HEIGHT));
    }
}
