package com.edinarobotics.zeke.commands;

import com.edinarobotics.zeke.Components;
import com.edinarobotics.zeke.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.Command;

public class FireShooterCommand extends Command {

	private Shooter shooter;
	
	public FireShooterCommand() {
		super("fireshootercommand");
		shooter = Components.getInstance().shooter;
		requires(shooter);
	}
	
	@Override
	protected void initialize() {
		shooter.fireShooter();
	}

	@Override
	protected void execute() {
		
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
		
	}

	@Override
	protected void interrupted() {
		
	}

}
