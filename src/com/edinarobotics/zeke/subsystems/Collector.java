package com.edinarobotics.zeke.subsystems;

import com.edinarobotics.utils.subsystems.Subsystem1816;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Collector extends Subsystem1816 {

	private CANTalon talon;
	private DoubleSolenoid collectorStateSolenoid;
	
	private CollectorState collectorState;
	
	private boolean active = false;
	private boolean forwards = false;
	
	public Collector(int relay, int pcModule, int collectorStateForward, int collectorStateReverse) {
		
		//Relay
		
		collectorStateSolenoid = new DoubleSolenoid(pcModule, collectorStateForward, collectorStateReverse);
		
		setCollectorState(CollectorState.RETRACTED);
	}
	
	@Override
	public void update() {
		
		collectorStateSolenoid.set(getCollectorState().getValue());
	}
	
	public enum CollectorState {
		DEPLOYED(Value.kForward),
		RETRACTED(Value.kReverse);
		
		private Value value;
		
		CollectorState(Value value) {
			this.value = value;
		}
		
		public Value getValue() {
			return value;
		}
	}
	
	public enum CollectorWheelState {
		
	}
	
	public void setActive(boolean active) {
		this.active = active;
		update();
	}
	
	public void setForwards(boolean forwards) {
		this.forwards = forwards;
		update();
	}
	
	public boolean isActive() {
		return active;
	}
	
	public boolean isForwards() {
		return forwards;
	}
	
	public CollectorState getCollectorState() {
		return collectorState;
	}
	
	public void setCollectorState(CollectorState collectorState) {
		this.collectorState = collectorState;
		update();
	}
}
