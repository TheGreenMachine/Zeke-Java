package com.edinarobotics.zeke.subsystems;

import com.edinarobotics.utils.subsystems.Subsystem1816;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Talon;

public class Shooter extends Subsystem1816 {
    
    private Talon winch;
    private WinchState winchState;
    
    private DoubleSolenoid solenoidRelease;
    private static DoubleSolenoid.Value value;
    private AnalogPotentiometer shooterPot;
    private final double SCALE = 1;
    private final double OFFSET = 0;

    public Shooter(int winchPort, int doubleSolenoidForward,
                int doubleSolenoidReverse, int shooterPort) {
        winch = new Talon(winchPort);
        solenoidRelease = new DoubleSolenoid(doubleSolenoidForward
                        , doubleSolenoidReverse);
        shooterPot = new AnalogPotentiometer(shooterPort, SCALE, OFFSET);
    }
    
    public void setSolenoid(DoubleSolenoid.Value value) {
        this.value = value;
        update();
    }
    
    public void update() {
        solenoidRelease.set(value);
    }
    
    public static final class WinchState {
        public static final WinchState LOWERING = new WinchState((byte)-1, false, true, "lowering");
        public static final WinchState STOPPED = new WinchState((byte)0, false, false, "stopped");
        public static final WinchState FREE = new WinchState((byte)1, true, false, "free");
        
        private byte winchState;
        private boolean isPistonEngaged;
        private boolean isMotorOn;
        private String stateName;
        
        private WinchState(byte winchState, boolean isPistonEngaged,
                boolean isMotorOn, String stateName) {
            this.winchState = winchState;
            this.isPistonEngaged = isPistonEngaged;
            this.isMotorOn = isMotorOn;
            this.stateName = stateName;
        }
        
        private byte getWinchState() {
            return winchState;
        }

        private boolean isIsPistonEngaged() {
            return isPistonEngaged;
        }

        private boolean isIsMotorOn() {
            return isMotorOn;
        }
        
        public boolean equals(Object winchState) {
            if (winchState instanceof WinchState) {
                return ((WinchState)winchState).getWinchState() == this.getWinchState();
            }
            return false;
        }
        
        public String toString() {
            return stateName.toLowerCase();
        }
    }
    
}
