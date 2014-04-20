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
    public static final double MAX_SHOOT_DISTANCE = 18.0; //In feet
    public static final double MIN_SHOOT_DISTANCE = 12.0; //In feet
    private static final double SCALE = 6.317;
    private static final double OFFSET = -1.579;
    private static final double MIN_SAFE_HEIGHT = 0.0;
    
    private Talon winch;
    private DoubleSolenoid winchSolenoidRelease;
    private DoubleSolenoid pusher;
    private AnalogPotentiometer shooterPot;
    private DigitalInput lowerLimitSwitch;
    private WinchState winchState, lastState;
    
    private boolean shouldOverride;
    private boolean isPusherDeployed;
   
    private static final DoubleSolenoid.Value ENGAGED = DoubleSolenoid.Value.kReverse;
    private static final DoubleSolenoid.Value DISENGAGED = DoubleSolenoid.Value.kForward;
    private static final DoubleSolenoid.Value PUSHER_DEPLOY = DoubleSolenoid.Value.kForward;
    private static final DoubleSolenoid.Value PUSHER_RETRACT = DoubleSolenoid.Value.kReverse;
    
    private Logger log = LogSystem.getLogger("zeke.shooter");

    public Shooter(int winchPort, int winchSolenoidForward, int winchSolenoidReverse,
            int pusherSolenoidForward, int pusherSolenoidReverse, int shooterPotPort, int limitSwitchPort) {
        winch = new Talon(winchPort);
        winchSolenoidRelease = new DoubleSolenoid(winchSolenoidForward, winchSolenoidReverse);
        pusher = new DoubleSolenoid(pusherSolenoidForward, pusherSolenoidReverse);
        shooterPot = new AnalogPotentiometer(shooterPotPort, SCALE, OFFSET);
        lowerLimitSwitch = new DigitalInput(1, limitSwitchPort);
        winchState = WinchState.STOPPED;
        lastState = WinchState.STOPPED;
        shouldOverride = false;
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
    
    public double getStringPot() {
        return shooterPot.get();
    }
    
    public WinchState getWinchState() {
        return winchState;
    }
    
    public boolean getShooterLimitSwitch() {
        return lowerLimitSwitch.get();
    }
    
    public void setOverride(boolean shouldOverride) {
        this.shouldOverride = shouldOverride;
    }
     
    public void setPusher(boolean deploy) {
        isPusherDeployed = deploy;
        update();
    }

    public boolean getPusher() {
        return isPusherDeployed;
    }
    
    public void update() {
        // Winch motor
        if((getStringPot() < MIN_SAFE_HEIGHT || getShooterLimitSwitch())
                    && winchState.equals(WinchState.LOWERING)) {
            winchState = WinchState.STOPPED;
        }
        else if(!shouldOverride && (Components.getInstance().collector.getDeployed()
                && lastState.isPistonEngaged() && winchState.equals(WinchState.FREE))){
            winchState = WinchState.STOPPED;
        }
        
        //Pusher
        if(!winchState.equals(WinchState.STOPPED) || !getShooterLimitSwitch()) {
            isPusherDeployed = false;
        }
        pusher.set((isPusherDeployed ? PUSHER_DEPLOY : PUSHER_RETRACT));
        
        winch.set(winchState.getMotorSpeed());
        lastState = winchState;
        DoubleSolenoid.Value pistonState = (winchState.isPistonEngaged() ? ENGAGED : DISENGAGED);
        winchSolenoidRelease.set(pistonState);
    }
    
    public static final class WinchState {
        public static final WinchState LOWERING = new WinchState((byte)0, true, 1.0, "lowering");
        public static final WinchState STOPPED = new WinchState((byte)1, true, 0.0, "stopped");
        public static final WinchState FREE = new WinchState((byte)2, false, 0.0, "free");
        
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

        public boolean isPistonEngaged() {
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
