package com.edinarobotics.zeke.commands;

import com.edinarobotics.zeke.Components;
import com.edinarobotics.zeke.subsystems.Collector;

import edu.wpi.first.wpilibj.command.Command;

public class RunCollectorCommand extends Command {

	private Collector collector;
	private boolean active, forwards;
	
	public RunCollectorCommand(boolean active, boolean forwards) {
		super("runcollectorcommand");
		collector = Components.getInstance().collector;
		this.active = active;
		this.forwards = forwards;
		requires(collector);
	}
	
	@Override
	protected void initialize() {
		collector.setActive(active);
		collector.setForwards(forwards);	
		System.out.println("Initialized...");
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
