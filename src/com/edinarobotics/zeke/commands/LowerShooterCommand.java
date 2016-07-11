package com.edinarobotics.zeke.commands;

import com.edinarobotics.zeke.Components;
import com.edinarobotics.zeke.subsystems.Shooter;
import com.edinarobotics.zeke.subsystems.Shooter.ShifterState;
import com.edinarobotics.zeke.subsystems.Shooter.WinchState;

import edu.wpi.first.wpilibj.command.Command;

public class LowerShooterCommand extends Command {

	private Shooter shooter;
	
	public LowerShooterCommand() {
		super("lowershootercommand");
		shooter = Components.getInstance().shooter;
		requires(shooter);
	}
	
	@Override
	protected void initialize() {
		shooter.setShifterState(ShifterState.EXTENDED);
	}

	@Override
	protected void execute() {
		if (!shooter.getLimitSwitch()) {
			shooter.setWinchState(WinchState.LOWERING);
		}		
	}

	@Override
	protected boolean isFinished() {
		return shooter.getLimitSwitch();
	}

	@Override
	protected void end() {
		shooter.setWinchState(WinchState.STOPPED);
	}

	@Override
	protected void interrupted() {
		end();
	}

}
