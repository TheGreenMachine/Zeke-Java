/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.edinarobotics.zeke;

import com.edinarobotics.utils.commands.MaintainStateCommand;
import com.edinarobotics.utils.gamepad.Gamepad;
import com.edinarobotics.utils.log.Level;
import com.edinarobotics.utils.log.LogSystem;
import com.edinarobotics.utils.log.Logger;
import com.edinarobotics.utils.log.filters.MinimumLevelFilter;
import com.edinarobotics.utils.log.handlers.PrintHandler;
import com.edinarobotics.zeke.commands.AutonomousCommand;
import com.edinarobotics.zeke.commands.GamepadDriveRotationCommand;
import com.edinarobotics.zeke.commands.GamepadDriveStrafeCommand;
import com.edinarobotics.zeke.commands.LowerShooterAfterWaitCommand;
import com.edinarobotics.zeke.commands.LowerShooterToHeightCommand;
import com.edinarobotics.zeke.subsystems.Collector;
import com.edinarobotics.zeke.subsystems.Drivetrain;
import com.edinarobotics.zeke.subsystems.DrivetrainRotation;
import com.edinarobotics.zeke.subsystems.DrivetrainStrafe;
import com.edinarobotics.zeke.subsystems.Shooter;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Zeke extends IterativeRobot {
    private Logger zekeLogger;
    private Command autonomousCommand;
    
    private NetworkTable statusTable;
    
    private Shooter shooter;
    private Drivetrain drivetrain;
    private Collector collector;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        zekeLogger = LogSystem.getLogger("zeke");
        LogSystem.getRootLogger().setHandler(new PrintHandler(System.out));
        LogSystem.getRootLogger().setFilter(new MinimumLevelFilter(Level.INFO));
        
        Controls.getInstance(); //Create all robot controls.
        Components.getInstance(); //Create all robot subsystems.
        autonomousCommand = new AutonomousCommand();
        statusTable = NetworkTable.getTable("status");
        shooter = Components.getInstance().shooter;
        drivetrain = Components.getInstance().drivetrain;
        collector = Components.getInstance().collector;
        zekeLogger.log(Level.INFO, "Zeke is alive.");
    }
    
    public void disabledInit() {
        stop();
        zekeLogger.log(Level.INFO, "Disabled the robot.");
    }
    
    public void disabledPeriodic() {
        stop();
        outputData();
    }

    public void autonomousInit() {
        // schedule the autonomous command (example)
        DrivetrainRotation drivetrainRotation = Components.getInstance().drivetrainRotation;
        drivetrainRotation.setDefaultCommand(new MaintainStateCommand(drivetrainRotation));
        DrivetrainStrafe drivetrainStrafe = Components.getInstance().drivetrainStrafe;
        drivetrainStrafe.setDefaultCommand(new MaintainStateCommand(drivetrainStrafe));
        
        autonomousCommand.start();
        zekeLogger.log(Level.INFO, "Initialized autonomous.");
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        outputData();
    }

    public void teleopInit() {
        autonomousCommand.cancel();
        
        Gamepad gamepad1 = Controls.getInstance().gamepad1;
        Components.getInstance().drivetrainRotation
                .setDefaultCommand(new GamepadDriveRotationCommand(gamepad1));
        Components.getInstance().drivetrainStrafe
                .setDefaultCommand(new GamepadDriveStrafeCommand(gamepad1));
        
        Command teleopLowerShooterCommand = new LowerShooterAfterWaitCommand(1.5);
        teleopLowerShooterCommand.start();
        zekeLogger.log(Level.INFO, "Initialized teleop.");
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        outputData();
    }
    
    public void stop() {
        Components.getInstance().drivetrainRotation.setMecanumPolarRotate(0);
        Components.getInstance().drivetrainStrafe.setMecanumPolarStrafe(0, 0);
    }
    
    public void outputData() {
        statusTable.putBoolean("collectorout", collector.getState().getDeployed());
        statusTable.putBoolean("shooterholding", shooter.getWinchState().isPistonEngaged());
        statusTable.putNumber("shooterheight", shooter.getStringPot());
        statusTable.putNumber("maxshootheight", Shooter.FIRING_HEIGHT);
        statusTable.putNumber("ultrasounddistance", drivetrain.getUltrasonicSensor().getDistance());
        statusTable.putNumber("maxdistance", Shooter.MAX_SHOOT_DISTANCE);
        statusTable.putNumber("mindistance", Shooter.MIN_SHOOT_DISTANCE);
    }
    
    public void testInit(){
        LiveWindow.setEnabled(false);
        teleopInit();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        Scheduler.getInstance().run();
    }
}
