package com.edinarobotics.zeke.commands;

import com.edinarobotics.zeke.Components;
import com.edinarobotics.zeke.subsystems.Shooter;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonomousCommand extends CommandGroup {
    private static final double DRIVE_DISTANCE_THRESHOLD = 12.0;
    
    public AutonomousCommand() {
        if(Components.getInstance().shooter.getStringPot() < (Shooter.FIRING_HEIGHT + 1.5)) {
            this.addSequential(new ShootingSequenceCommand(false));
            this.addSequential(new DriveXDistanceCommand(DRIVE_DISTANCE_THRESHOLD));
            this.addParallel(new LowerShooterAfterWaitCommand());
        } else {
            this.addSequential(new WaitForCollectorUndeployedCommand());
            this.addParallel(new DriveXDistanceCommand(DRIVE_DISTANCE_THRESHOLD));
            this.addSequential(new LowerShooterToHeightCommand(Shooter.FIRING_HEIGHT));
        }
    }
}
