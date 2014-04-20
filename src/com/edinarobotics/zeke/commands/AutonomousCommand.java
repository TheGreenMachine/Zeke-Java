package com.edinarobotics.zeke.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonomousCommand extends CommandGroup {
    private static final double DRIVE_DISTANCE_THRESHOLD = 12;
    private static final double SECOND_BALL_DISTANCE = 13.0;
    
    public AutonomousCommand(boolean doVision) {
        if(doVision) {
        }
        else {
            this.addSequential(new DriveXDistanceCommandNoPID(DRIVE_DISTANCE_THRESHOLD, 0.25, true));
            this.addSequential(new ShootingSequenceCommand(true));
        }
    }
}
