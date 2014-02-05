package com.edinarobotics.zeke.subsystems;

import com.edinarobotics.utils.log.Level;
import com.edinarobotics.utils.log.LogSystem;
import com.edinarobotics.utils.log.Logger;
import com.edinarobotics.utils.subsystems.Subsystem1816;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Talon;

public class Collector extends Subsystem1816 {
    private DoubleSolenoid collectorRotator;
    private Talon collectorWheelFront;
    private Talon collectorWheelBack;
    
    private boolean isDeployed;
    private CollectorWheelState collectorWheelState;
    
    private static final DoubleSolenoid.Value DEPLOYED = DoubleSolenoid.Value.kForward;
    private static final DoubleSolenoid.Value RETRACTED = DoubleSolenoid.Value.kReverse;
    
    private Logger log = LogSystem.getLogger("zeke.collector");
    
    public Collector(int collectorWheelFrontPort, int collectorWheelBackPort, 
                int doubleSolenoidForward, int doubleSolenoidReverse) {
        collectorWheelFront = new Talon(collectorWheelFrontPort);
        collectorWheelBack = new Talon(collectorWheelBackPort);
        collectorRotator = new DoubleSolenoid(doubleSolenoidForward, doubleSolenoidReverse);
        
        isDeployed = false;
        collectorWheelState = CollectorWheelState.STOPPED;
    }

    public void update() {
        if(isDeployed) {
            collectorRotator.set(DEPLOYED);
            collectorWheelFront.set(collectorWheelState.getFrontWheelSpeed());
            collectorWheelBack.set(collectorWheelState.getBackWheelSpeed());
        } else {
            collectorRotator.set(RETRACTED);
            collectorWheelFront.set(0.0);
            collectorWheelBack.set(0.0);
        }
    }
    
    public void setDeployed(boolean isDeployed) {
        this.isDeployed = isDeployed;
        update();
    }
    
    public boolean getDeployed() {
        return isDeployed;
    }
    
    public void setCollectorWheelState(CollectorWheelState collectorWheelState) {
        if(collectorWheelState != null) {
            this.collectorWheelState = collectorWheelState;
        } else {
            log.log(Level.SEVERE, "Collector.setCollectorWheelState got null");
        }
        update();
    }
    
    public CollectorWheelState getCollectorWheelState() {
        return collectorWheelState;
    }
    
    public static final class CollectorWheelState {
        public static final CollectorWheelState COLLECTING = new 
                CollectorWheelState((byte)0, 1.0, 1.0, "collecting");
        public static final CollectorWheelState HOLDING = new 
                CollectorWheelState((byte)1, 1.0, 0.0, "holding");
        public static final CollectorWheelState STOPPED = new 
                CollectorWheelState((byte)2, 0.0, 0.0, "stopped");
        public static final CollectorWheelState REVERSING = new 
                CollectorWheelState((byte)3, -1.0, -1.0, "reversing");
        
               
        private String stateName;
        private byte collectorState;
        private double frontWheelSpeed;
        private double backWheelSpeed;
        
        private CollectorWheelState(byte collectorState, double frontWheelSpeed, 
                    double backWheelSpeed, String stateName) {
            this.collectorState = collectorState;
            this.frontWheelSpeed = frontWheelSpeed;
            this.backWheelSpeed = backWheelSpeed;
            this.stateName = stateName;
        }
        
        public String getStateName() {
            return stateName;
        }
        
        public byte getCollectorState() {
            return collectorState;
        }
        
        public double getFrontWheelSpeed() {
            return frontWheelSpeed;
        }

        public double getBackWheelSpeed() {
            return backWheelSpeed;
        }
        
        public boolean equals(Object collectorWheelState) {
            if(collectorWheelState instanceof CollectorWheelState) {
                return ((CollectorWheelState)collectorWheelState).getCollectorState() == this.getCollectorState();
            }
            return false;
        }
        
        public String toString() {
            return stateName.toLowerCase();
        }
    }
}
