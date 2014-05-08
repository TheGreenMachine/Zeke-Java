package com.edinarobotics.zeke;

public class RobotComponentsMapping {
    
    public RobotComponentsMapping(){
        
    }
    
    // Analog Inputs
        protected int ULTRASONIC_SENSOR = 4;
    // END Analog Inputs
    
    // Digital IO Constants
        // Limit Switches
        protected int SHOOTER_LOWER_LIMIT = 6;
        
        // Compressor Switch
        protected int COMPRESSOR_PRESSURE_SWITCH = 5;
    // END Digital IO constants
    
    // PWM constants
        // Drivetrain
        protected int FRONT_LEFT_DRIVE  = 4;
        protected int REAR_LEFT_DRIVE   = 3;
        protected int FRONT_RIGHT_DRIVE = 2;
        protected int REAR_RIGHT_DRIVE  = 1;
        protected int WINCH_TALON = 5;
        protected int COLLECTOR_FRONT_TALON = 6;
        protected int COLLECTOR_BACK_TALON = 7;
    // END PWM constants
        
    // Solenoid constants
        // Collector
        protected int COLLECTOR_DOUBLESOLENOID_FORWARD = 2;
        protected int COLLECTOR_DOUBLESOLENOID_REVERSE = 1;
        protected int COLLECTOR_DOUBLESOLENOID_VALVE_OFF = 5;
        protected int COLLECTOR_DOUBLESOLENOID_VALVE_ON = 6;
        // Shooter
        protected int SHOOTER_DOUBLESOLENOID_FORWARD = 3;
        protected int SHOOTER_DOUBLESOLENOID_REVERSE = 4;
        // Pusher
        protected int PUSHER_DOUBLESOLENOID_FORWARD = 7;
        protected int PUSHER_DOUBLESOLENOID_REVERSE = 8;
    // END Solenoid constants
        
    // Relay constats
        // Compressor
        protected int COMPRESSOR_RELAY = 1;
    // END Relay constants
        
    // Vision Server Port
        protected int VISION_SERVER_PORT = 1180;
}
