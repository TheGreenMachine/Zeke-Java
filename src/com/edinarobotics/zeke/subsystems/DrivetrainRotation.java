package com.edinarobotics.zeke.subsystems;

import com.edinarobotics.utils.subsystems.Subsystem1816;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Command;

public class DrivetrainRotation extends Subsystem1816 implements PIDOutput {
    private double rotation;
    private Drivetrain drivetrain;
    private Gyro gyro;
    
    public DrivetrainRotation(Drivetrain drivetrain, int channel) {
        super("DrivetrainRotation");
        this.drivetrain = drivetrain;
        gyro = new Gyro(channel);
    }
    
    public void setMecanumPolarRotate(double rotation) {
        this.rotation = rotation;
        update();
    }
    
    public void pidWrite(double rotation){
        setMecanumPolarRotate(rotation);
    }
    
    public double getRotation() {
        return rotation;
    }
    
    public Gyro getGyro(){
        return gyro;
    }
    
    public double getGyroAngle() {
        return gyro.getAngle();
    }
    
    public void resetGyro() {
        gyro.reset();
    }
    
    public void update() {
        drivetrain.mecanumPolarRotation(rotation);
    }
    
    public void setDefaultCommand(Command command){
        if(getDefaultCommand() != null){
            super.getDefaultCommand().cancel();
        }
        super.setDefaultCommand(command);
    }
    
}
