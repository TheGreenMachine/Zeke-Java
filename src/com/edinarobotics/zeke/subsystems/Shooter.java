package com.edinarobotics.zeke.subsystems;

import com.edinarobotics.utils.log.Level;
import com.edinarobotics.utils.log.LogSystem;
import com.edinarobotics.utils.log.Logger;
import com.edinarobotics.utils.subsystems.Subsystem1816;
import com.edinarobotics.zeke.Components;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Talon;

public class Shooter extends Subsystem1816 {
    
    private Talon winch;
    private DoubleSolenoid solenoidRelease;
    private AnalogPotentiometer shooterPot;
    private DigitalInput lowerLimitSwitch;
    private Logger log = LogSystem.getLogger("zeke.shooter");
   
    private WinchState winchState;
    
    private static final double SCALE = 1;
    private static final double OFFSET = 0;
    private static final DoubleSolenoid.Value ENGAGED = DoubleSolenoid.Value.kForward;
    private static final DoubleSolenoid.Value DISENGAGED = DoubleSolenoid.Value.kReverse;

    public Shooter(int winchPort, int doubleSolenoidForward,
                int doubleSolenoidReverse, int shooterPort,
                DigitalInput lowerLimitSwitch) {
        winch = new Talon(winchPort);
        solenoidRelease = new DoubleSolenoid(doubleSolenoidForward
                        , doubleSolenoidReverse);
        shooterPot = new AnalogPotentiometer(shooterPort, SCALE, OFFSET);
        this.lowerLimitSwitch = lowerLimitSwitch;
        winchState = WinchState.STOPPED;
        
    }
    
    public void setWinchState(WinchState winchState) {
        if(winchState != null) {
            this.winchState = winchState;
        } else {
            log.log(Level.SEVERE, "Shooter.setWinchState got null.");
        }
        update();
    }
    
    public double getStringPot() {
        return shooterPot.get();
    }
    
    public WinchState getWinchState() {
        return winchState;
    }
    
    public void update() {
        // Safety check
        if(getShooterLimitSwitch() && winchState.equals(WinchState.LOWERING)) {
            winchState = WinchState.STOPPED;
        }
        
        winch.set(winchState.getMotorSpeed());
        
        if(winchState.isPistonEngaged() && !Components.getInstance().collector.getDeployed()) {
            solenoidRelease.set(ENGAGED);
        } else {
            solenoidRelease.set(DISENGAGED);
        }
    }
    
    public boolean getShooterLimitSwitch() {
        return lowerLimitSwitch.get();
    }
    
    public static final class WinchState {
        public static final WinchState LOWERING = new WinchState((byte)-1, false, 1.0, "lowering");
        public static final WinchState STOPPED = new WinchState((byte)0, false, 0.0, "stopped");
        public static final WinchState FREE = new WinchState((byte)1, true, 0.0, "free");
        
        private byte winchState;
        private boolean isPistonEngaged;
        private double motorSpeed;
        private String stateName;
        
        private WinchState(byte winchState, boolean isPistonEngaged,
                double motorSpeed, String stateName) {
            this.winchState = winchState;
            this.isPistonEngaged = isPistonEngaged;
            this.motorSpeed = motorSpeed;
            this.stateName = stateName;
        }
        
        private byte getWinchState() {
            return winchState;
        }

        private boolean isPistonEngaged() {
            return isPistonEngaged;
        }

        private double getMotorSpeed() {
            return motorSpeed;
        }
        
        public boolean equals(Object winchState) {
            if(winchState instanceof WinchState) {
                return ((WinchState)winchState).getWinchState() == this.getWinchState();
            }
            return false;
        }
        
        public String toString() {
            return stateName.toLowerCase();
        }
    }
    
}
