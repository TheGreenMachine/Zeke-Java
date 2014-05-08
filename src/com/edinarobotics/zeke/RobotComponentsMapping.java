package com.edinarobotics.zeke;

public class RobotComponentsMapping {
    
    public RobotComponentsMapping(){
        
    }
    
    // Analog Inputs
        protected final int ULTRASONIC_SENSOR = 4;
    // END Analog Inputs
    
    // Digital IO Constants
        // Limit Switches
        protected final int SHOOTER_LOWER_LIMIT = 6;
        
        // Compressor Switch
        protected final int COMPRESSOR_PRESSURE_SWITCH = 5;
    // END Digital IO constants
    
    // PWM constants
        // Drivetrain
        protected final int FRONT_LEFT_DRIVE  = 4;
        protected final int REAR_LEFT_DRIVE   = 3;
        protected final int FRONT_RIGHT_DRIVE = 2;
        protected final int REAR_RIGHT_DRIVE  = 1;
        protected final int WINCH_TALON = 5;
        protected final int COLLECTOR_FRONT_TALON = 6;
        protected final int COLLECTOR_BACK_TALON = 7;
    // END PWM constants
        
    // Solenoid constants
        // Collector
        protected final int COLLECTOR_DOUBLESOLENOID_FORWARD = 2;
        protected final int COLLECTOR_DOUBLESOLENOID_REVERSE = 1;
        protected final int COLLECTOR_DOUBLESOLENOID_VALVE_OFF = 5;
        protected final int COLLECTOR_DOUBLESOLENOID_VALVE_ON = 6;
        // Shooter
        protected final int SHOOTER_DOUBLESOLENOID_FORWARD = 3;
        protected final int SHOOTER_DOUBLESOLENOID_REVERSE = 4;
        // Pusher
        protected final int PUSHER_DOUBLESOLENOID_FORWARD = 7;
        protected final int PUSHER_DOUBLESOLENOID_REVERSE = 8;
    // END Solenoid constants
        
    // Relay constats
        // Compressor
        protected final int COMPRESSOR_RELAY = 1;
    // END Relay constants
}
