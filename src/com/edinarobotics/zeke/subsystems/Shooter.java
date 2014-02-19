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
    
    public static final double FIRING_HEIGHT = Shooter.MIN_SAFE_HEIGHT;
    
    private Logger log = LogSystem.getLogger("zeke.shooter");
    
    private Talon winch;
    private DoubleSolenoid winchSolenoidRelease;
    private DoubleSolenoid pusherSolenoid;
    private AnalogPotentiometer shooterPot;
    private DigitalInput lowerLimitSwitch;
   
    private WinchState winchState;
    private boolean isPusherDeployed;
    
    private static final double SCALE = 6.317;
    private static final double OFFSET = -1.579;
    private static final double MIN_SAFE_HEIGHT = 2.24;
    private static final double MAX_PUSH_HEIGHT = 3.0;
    
    private static final DoubleSolenoid.Value PUSHER_OUT = DoubleSolenoid.Value.kForward;
    private static final DoubleSolenoid.Value PUSHER_IN = DoubleSolenoid.Value.kReverse;
    
    private static final DoubleSolenoid.Value ENGAGED = DoubleSolenoid.Value.kForward;
    private static final DoubleSolenoid.Value DISENGAGED = DoubleSolenoid.Value.kReverse;

    public Shooter(int winchPort, int winchSolenoidForward, int winchSolenoidReverse,
            int pusherSolenoidForward, int pusherSolenoidReverse,
            int shooterPotPort, int limitSwitchPort) {
        winch = new Talon(winchPort);
        winchSolenoidRelease = new DoubleSolenoid(winchSolenoidForward, winchSolenoidReverse);
        pusherSolenoid = new DoubleSolenoid(pusherSolenoidForward, pusherSolenoidReverse);
        shooterPot = new AnalogPotentiometer(shooterPotPort, SCALE, OFFSET);
        lowerLimitSwitch = new DigitalInput(1, 6);
        winchState = WinchState.STOPPED;
        isPusherDeployed = false;
    }
    
    public void setWinchState(WinchState winchState) {
        if(winchState != null) {
            this.winchState = winchState;
        } else {
            log.log(Level.SEVERE, "Shooter.setWinchState got null.");
        }
        update();
    }
    
    public void setPusherDeployed(boolean deploy) {
        isPusherDeployed = deploy;
        update();
    }
    
    public double getStringPot() {
        return shooterPot.get();
    }
    
    public WinchState getWinchState() {
        return winchState;
    }
    
    public boolean getPusherDeployed() {
        return isPusherDeployed;
    }
    
    public boolean getShooterLimitSwitch() {
        return lowerLimitSwitch.get();
    }
    
    public void update() {
        // Winch motor
        if((getStringPot() < MIN_SAFE_HEIGHT || getShooterLimitSwitch())
                    && winchState.equals(WinchState.LOWERING)) {
            winchState = WinchState.STOPPED;
        }
        winch.set(winchState.getMotorSpeed());
        
        // Pusher
        if(getStringPot() > MAX_PUSH_HEIGHT && !winchState.equals(WinchState.STOPPED)) {
            isPusherDeployed = false;
        }
        pusherSolenoid.set(isPusherDeployed ? PUSHER_OUT : PUSHER_IN);
        
        // Solenoid release
        if(Components.getInstance().collector.getDeployed() && winchState.isPistonEngaged()) {
            winchSolenoidRelease.set(ENGAGED);
        } else {
            winchSolenoidRelease.set(DISENGAGED);
        }
    }
    
    public static final class WinchState {
        public static final WinchState LOWERING = new WinchState((byte)0, false, 1.0, "lowering");
        public static final WinchState STOPPED = new WinchState((byte)1, false, 0.0, "stopped");
        public static final WinchState FREE = new WinchState((byte)2, true, 0.0, "free");
        
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
