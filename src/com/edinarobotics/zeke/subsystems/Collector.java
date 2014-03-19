package com.edinarobotics.zeke.subsystems;

import com.edinarobotics.utils.log.Level;
import com.edinarobotics.utils.log.LogSystem;
import com.edinarobotics.utils.log.Logger;
import com.edinarobotics.utils.subsystems.Subsystem1816;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Talon;

public class Collector extends Subsystem1816 {
    private DoubleSolenoid collectorPiston;
    private DoubleSolenoid collectorValve;
    private Talon collectorWheelFront;
    private Talon collectorWheelBack;
    private DigitalInput upperLimitSwitch;
    
    private CollectorState collectorState;
    private CollectorWheelState collectorWheelState;
    
    private Logger log = LogSystem.getLogger("zeke.collector");
    
    public Collector(int collectorWheelFrontPort, int collectorWheelBackPort, 
                int doubleSolenoidForward, int doubleSolenoidReverse,
                int doubleSolenoidValveOn, int doubleSolenoidValveOff, int upperLimitSwitchPort) {
        collectorWheelFront = new Talon(collectorWheelFrontPort);
        collectorWheelBack = new Talon(collectorWheelBackPort);
        collectorPiston = new DoubleSolenoid(doubleSolenoidForward, doubleSolenoidReverse);
        collectorValve = new DoubleSolenoid(doubleSolenoidValveOn, doubleSolenoidValveOff);
        
        upperLimitSwitch = new DigitalInput(upperLimitSwitchPort);
        
        collectorState = CollectorState.RETRACTED;
        collectorWheelState = CollectorWheelState.STOPPED;
    }

    public void update() {
        collectorPiston.set(collectorState.getPistonState());
        collectorValve.set(collectorState.getValveState());
        collectorWheelFront.set(collectorWheelState.getFrontWheelSpeed());
        collectorWheelBack.set(collectorWheelState.getBackWheelSpeed());
    }
    
    public void setDeployed(CollectorState collectorState) {
        if(collectorState != null) {
            this.collectorState = collectorState;
        } else {
            log.log(Level.SEVERE, "Collector.setCollectorState got null");
        }
        update();
    }
    
    public CollectorState getState(){
        return collectorState;
    }
    
    public boolean getDeployed() {
        return !getUndeployed();
    }
    
    public boolean getUndeployed(){
        return !collectorState.getDeployed() && getUpperLimitSwitch();
    }
    
    public boolean getUpperLimitSwitch(){
        return upperLimitSwitch.get();
    }
    
    public void setWheelState(CollectorWheelState collectorWheelState) {
        if(collectorWheelState != null) {
            this.collectorWheelState = collectorWheelState;
        } else {
            log.log(Level.SEVERE, "Collector.setCollectorWheelState got null");
        }
        update();
    }
    
    public CollectorWheelState getWheelState() {
        return collectorWheelState;
    }
    
    public static final class CollectorState {
        public static final CollectorState DEPLOYING =
                new CollectorState((byte)0, DoubleSolenoid.Value.kReverse, DoubleSolenoid.Value.kReverse, "deploying");
        public static final CollectorState DEPLOYED =
                new CollectorState((byte)1, DoubleSolenoid.Value.kReverse, DoubleSolenoid.Value.kOff, "deployed");
        public static final CollectorState RETRACTED =
                new CollectorState((byte)2, DoubleSolenoid.Value.kForward, DoubleSolenoid.Value.kReverse, "retracted");
        
        private String stateName;
        private byte collectorState;
        private DoubleSolenoid.Value pistonState;
        private DoubleSolenoid.Value valveState;
        
        private CollectorState(byte collectorState, DoubleSolenoid.Value pistonState,
                DoubleSolenoid.Value valveState, String stateName) {
            this.collectorState = collectorState;
            this.pistonState = pistonState;
            this.valveState = valveState;
            this.stateName = stateName;
        }
        
        public String getStateName() {
            return stateName;
        }
        
        public byte getCollectorState() {
            return collectorState;
        }
        
        private DoubleSolenoid.Value getPistonState() {
            return this.pistonState;
        }
        
        private DoubleSolenoid.Value getValveState() {
            return this.valveState;
        }
        
        public boolean getDeployed() {
            return this.equals(DEPLOYED);
        }
        
        public boolean equals(Object collectorState) {
            if(collectorState instanceof CollectorState) {
                return ((CollectorState)collectorState).getCollectorState() == this.getCollectorState();
            }
            return false;
        }
        
        public String toString() {
            return stateName.toLowerCase();
        }
    }
    
    public static final class CollectorWheelState {
        public static final CollectorWheelState COLLECTING = new 
                CollectorWheelState((byte)0, -0.75, 1, "collecting");
        public static final CollectorWheelState HOLDING = new 
                CollectorWheelState((byte)1, -0.75, 0.0, "holding");
        public static final CollectorWheelState STOPPED = new 
                CollectorWheelState((byte)2, 0.0, 0.0, "stopped");
        public static final CollectorWheelState REVERSING = new 
                CollectorWheelState((byte)3, 0.75, -0.75, "reversing");
        
               
        private String stateName;
        private byte collectorWheelState;
        private double frontWheelSpeed;
        private double backWheelSpeed;
        
        private CollectorWheelState(byte collectorWheelState, double frontWheelSpeed, 
                    double backWheelSpeed, String stateName) {
            this.collectorWheelState = collectorWheelState;
            this.frontWheelSpeed = frontWheelSpeed;
            this.backWheelSpeed = backWheelSpeed;
            this.stateName = stateName;
        }
        
        public String getStateName() {
            return stateName;
        }
        
        public byte getCollectorWheelState() {
            return collectorWheelState;
        }
        
        public double getFrontWheelSpeed() {
            return frontWheelSpeed;
        }

        public double getBackWheelSpeed() {
            return backWheelSpeed;
        }
        
        public boolean equals(Object collectorWheelState) {
            if(collectorWheelState instanceof CollectorWheelState) {
                return ((CollectorWheelState)collectorWheelState).getCollectorWheelState() == this.getCollectorWheelState();
            }
            return false;
        }
        
        public String toString() {
            return stateName.toLowerCase();
        }
    }
}
