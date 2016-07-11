package com.edinarobotics.zeke.subsystems;

import com.edinarobotics.utils.subsystems.Subsystem1816;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Shooter extends Subsystem1816 {

	private CANTalon winch;
	private DigitalInput limitSwitch;

	private DoubleSolenoid shifter;
	private ShifterState shifterState;
	private boolean shifterActive = false;
	
	private WinchState winchState;
	private double winchRate = 0.0;
	private static final double WINCH_LOWERING = -0.6;
	private static final double WINCH_FIRING = 0.5;

	public Shooter(int winch, int limitSwitch, int pcModule,
			int shifterForward, int shifterReverse) {
		this.winch = new CANTalon(winch);
		this.limitSwitch = new DigitalInput(limitSwitch);
		
		shifter = new DoubleSolenoid(pcModule, shifterForward, shifterReverse);
		shifterState = ShifterState.RETRACTED;
		shifter.set(Value.kReverse);
	}

	@Override
	public void update() {
		if (getLimitSwitch()) {
			if (winchState == WinchState.LOWERING) {
				winchState = WinchState.STOPPED;
				winchRate = 0.0;
			}
		}
		
		winch.set(winchRate);
				
		if (isShifterActive()) {
			if (shifterState == ShifterState.EXTENDED) {
				shifter.set(Value.kReverse);
			} else if (shifterState == ShifterState.RETRACTED) {
				shifter.set(Value.kForward);
			}

			shifterActive = false;
		}		
	}
	
	public void fireShooter() {
		setWinchState(WinchState.FIRING);
		System.out.println("Winch firing...");
		
		setShifterState(ShifterState.RETRACTED);
		System.out.println("Shifter extended...");
		
		Timer.delay(0.1);
		
		setWinchState(WinchState.FREE);
	}

	public enum WinchState {
		LOWERING, STOPPED, FIRING, FREE;
	}
	
	public enum ShifterState {
		EXTENDED, RETRACTED, VENT;
	}

	public void setWinchState(WinchState winchState) {
		this.winchState = winchState;

		if (winchState == WinchState.LOWERING) {
			winchRate = WINCH_LOWERING;
		} else if (winchState == WinchState.FIRING) {
			winchRate = WINCH_FIRING;
		} else {
			winchRate = 0;
		}
		
		update();		
	}

	public double getWinchRate() {
		return winchRate;
	}
	
	public void setShifterState(ShifterState shifterState) {
		this.shifterState = shifterState;
		shifterActive = true;
		update();
	}
	
	public boolean isShifterActive() {
		return shifterActive;
	}
	
	public boolean isShifterExtended() {
		return shifter.get() == Value.kForward;
	}

	public boolean getLimitSwitch() {
		return limitSwitch.get();
	}

}
