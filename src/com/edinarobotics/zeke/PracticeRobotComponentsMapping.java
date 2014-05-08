package com.edinarobotics.zeke;

public class PracticeRobotComponentsMapping extends RobotComponentsMapping {
    
    public PracticeRobotComponentsMapping(){
    // Analog Inputs
        ULTRASONIC_SENSOR = 4;
    // END Analog Inputs
    
    // Digital IO Constants
        // Limit Switches
        SHOOTER_LOWER_LIMIT = 1;
        
        // Compressor Switch
        COMPRESSOR_PRESSURE_SWITCH = 5;
    // END Digital IO constants
    
    // PWM constants
        // Drivetrain
        FRONT_LEFT_DRIVE  = 4;
        REAR_LEFT_DRIVE   = 3;
        FRONT_RIGHT_DRIVE = 2;
        REAR_RIGHT_DRIVE  = 1;
        WINCH_TALON = 5;
        COLLECTOR_FRONT_TALON = 6;
        COLLECTOR_BACK_TALON = 7;
    // END PWM constants
        
    // Solenoid constants
        // Collector
        COLLECTOR_DOUBLESOLENOID_FORWARD = 3;
        COLLECTOR_DOUBLESOLENOID_REVERSE = 4;
        COLLECTOR_DOUBLESOLENOID_VALVE_OFF = 6;
        COLLECTOR_DOUBLESOLENOID_VALVE_ON = 5;
        // Shooter
        SHOOTER_DOUBLESOLENOID_FORWARD = 2;
        SHOOTER_DOUBLESOLENOID_REVERSE = 1;
        // Pusher
        PUSHER_DOUBLESOLENOID_FORWARD = 7;
        PUSHER_DOUBLESOLENOID_REVERSE = 8;
    // END Solenoid constants
        
    // Relay constats
        // Compressor
        COMPRESSOR_RELAY = 1;
    // END Relay constants
    }
}
