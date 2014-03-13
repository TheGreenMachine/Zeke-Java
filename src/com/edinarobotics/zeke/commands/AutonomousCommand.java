package com.edinarobotics.zeke.commands;

import com.edinarobotics.zeke.Components;
import com.edinarobotics.zeke.subsystems.Collector;
import com.edinarobotics.zeke.subsystems.Shooter;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonomousCommand extends CommandGroup {
    private static final double DRIVE_DISTANCE_THRESHOLD = 19.8;
    private static final double SECOND_BALL_DISTANCE = 13.0;
    
    public AutonomousCommand() {
        if(true) {
            this.addSequential(new DriveXDistanceCommandNoPID(DRIVE_DISTANCE_THRESHOLD, 0.25, true));
            this.addSequential(new ShootingSequenceCommand(true));
//            this.addSequential(new LowerShooterAfterWaitCommand());
//            this.addParallel(new SetCollectorCommand(Collector.CollectorState.DEPLOYED, Collector.CollectorWheelState.COLLECTING));
//            this.addSequential(new DriveXDistanceCommandNoPID(SECOND_BALL_DISTANCE, 0.25, true));
//            this.addSequential(new SetCollectorCommand(Collector.CollectorWheelState.STOPPED));
//            this.addSequential(new DriveXDistanceCommandNoPID(DRIVE_DISTANCE_THRESHOLD, 0.25, false));
//            this.addSequential(new ShootingSequenceCommand(true));
        } else {
            this.addSequential(new WaitForCollectorUndeployedCommand());
            this.addParallel(new DriveXDistanceCommandNoPID(DRIVE_DISTANCE_THRESHOLD, 0.25, true));
            this.addSequential(new LowerShooterToHeightCommand(Shooter.FIRING_HEIGHT));
        }
    }
}
