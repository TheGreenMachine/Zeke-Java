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
import com.edinarobotics.zeke.commands.GamepadDriveRotationCommand;
import com.edinarobotics.zeke.commands.GamepadDriveStrafeCommand;
import com.edinarobotics.zeke.subsystems.DrivetrainRotation;
import com.edinarobotics.zeke.subsystems.DrivetrainStrafe;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Zeke extends IterativeRobot {
    private Logger zekeLogger;
    Command autonomousCommand;

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
        zekeLogger.log(Level.INFO, "Zeke is alive.");
    }
    
    public void disabledInit() {
    }
    
    public void disabledPeriodic() {
        stop();
    }

    public void autonomousInit() {
        // schedule the autonomous command (example)
        autonomousCommand.start();
        DrivetrainRotation drivetrainRotation = Components.getInstance().drivetrainRotation;
        drivetrainRotation.setDefaultCommand(new MaintainStateCommand(drivetrainRotation));
        DrivetrainStrafe drivetrainStrafe = Components.getInstance().drivetrainStrafe;
        drivetrainStrafe.setDefaultCommand(new MaintainStateCommand(drivetrainStrafe));
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
        Gamepad gamepad1 = Controls.getInstance().gamepad1;
        Components.getInstance().drivetrainRotation
                .setDefaultCommand(new GamepadDriveRotationCommand(gamepad1));
        Components.getInstance().drivetrainStrafe
                .setDefaultCommand(new GamepadDriveStrafeCommand(gamepad1));
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
    
    public void stop() {
        Components.getInstance().drivetrainRotation.setMecanumPolarRotate(0);
        Components.getInstance().drivetrainStrafe.setMecanumPolarStrafe(0, 0);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
