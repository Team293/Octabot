// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package frc.robot.subsystems;

import frc.robot.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.controller.PIDController;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

/**
 *
 */
public class Targeting extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private NetworkTable limeData;
    // t stands for target
    private NetworkTableEntry tAcquired;
    // x and y values of the target
    private NetworkTableEntry tx;
    private NetworkTableEntry ty;

    private double vF = 0.0;
    private double vP = 0.6;
    private double vI = 0.0;
    private double vD = 4.0;

    private double TARGET_RIGHT_THRESHOLD = 1.0;
    private double TARGET_LEFT_THRESHOLD = -1.0;
    PIDController visionPID;
    private double integral;
    private double lastError = 0.0;
    private double STATIC_POWER = 0.05;
    private double currentOutput = 0.0;
    private double INTEGRAL_LIMIT = 1000000000; // TODO Check math and get an actual number
    private double VISION_RANGE = 0.5;
    private PigeonIMU gyro;

    public Targeting() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        limeData = NetworkTableInstance.getDefault().getTable("limelight");
        tAcquired = limeData.getEntry("tv");
        tx = limeData.getEntry("tx");
        ty = limeData.getEntry("ty");

        limeData.getEntry("camMode").setNumber(0);
        
        visionPID = new PIDController(vP,vI,vD);
        visionPID.disableContinuousInput();
        visionPID.setSetpoint(0.0);
        visionPID.setTolerance(1.0);    

        SmartDashboard.putNumber("P Gain", vP);
        SmartDashboard.putNumber("I Gain", vI);
        SmartDashboard.putNumber("D Gain", vD);
        
        gyro = new PigeonIMU(5);
        gyro.clearStickyFaults();
    }

    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop
        double p = SmartDashboard.getNumber("P Gain", 0);
        double i = SmartDashboard.getNumber("I Gain", 0);
        double d = SmartDashboard.getNumber("D Gain", 0);

        if((p != vP)) {vP = p;}
        if((i != vI)) {vI = i; }
        if((d != vD)) {vD = d; }

        SmartDashboard.putNumber("Pigeon Compass", gyro.getCompassHeading());
        SmartDashboard.putNumber("Pigeon Tempurature",gyro.getTemp());
        SmartDashboard.putNumber("Pigeon Fused Heading", gyro.getFusedHeading());
        

    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    // This method just returns whether it sees the target and it's x and y values
    public void controlLight(boolean enabled){
        if(enabled){
            limeData.getEntry("ledMode").setNumber(3);
        }
        else{
            limeData.getEntry("ledMode").setNumber(1);
        }
    }

    public double[] navToTarget(){
        limeData.getEntry("ledMode").setNumber(3);
        double[] velCmds = {0.0,0.0}; //Left motor velocity, Right motor velocity (retunrns -1 to 1)
        if (tAcquired.getDouble(0.0) == 1.0){
            double headingError = tx.getDouble(0.0)/29.5; // 29.5 is the range of the limelight which goes from -29.5 to 29.5
            double change = headingError - lastError;
            if(Math.abs(integral) < INTEGRAL_LIMIT){
                integral += headingError * .2;
            }
            double percentOutput = (vP * headingError) + (vI * integral) + (vD * change);
            /*if(percentOutput > 0.0){
                percentOutput += STATIC_POWER;
            }
            else if(percentOutput < 0.0){
                percentOutput -= STATIC_POWER;
            }*/

            if(percentOutput > .5){
                percentOutput = .5;
            }
            else if(percentOutput < -.5){
                percentOutput = -.5;
            }
            
            velCmds[0] = percentOutput; //Left Motor
            velCmds[1] = -percentOutput; //Right Motor
            SmartDashboard.putNumber("LeftOutput",velCmds[0]);
            SmartDashboard.putNumber("RightOutput",velCmds[1]);
            currentOutput = percentOutput;
            lastError = headingError;

        }

        /*else{
            velCmds[0] = -currentOutput; //Left Motor
            velCmds[1] = currentOutput; //Right Motor
            SmartDashboard.putNumber("LeftOutput",velCmds[0]);
            SmartDashboard.putNumber("RightOutput",velCmds[1]);
        }*/
        
        return velCmds;
    }


    // me attempting to use wpilib's integrated pid encoder

    public double[] wpilibNavToTarget(){
        limeData.getEntry("ledMode").setNumber(3);
        double[] velCmds = {0.0,0.0}; //Left motor velocity, Right motor velocity (retunrns -1 to 1)
        if (tAcquired.getDouble(0.0) == 1.0){
                double error = tx.getDouble(0.0)/29.5;
                double velPwr = visionPID.calculate(error);
                velCmds[0] = velPwr;
                velCmds[1] = -velPwr;
                SmartDashboard.putNumber("Output",velPwr);
        }
        return velCmds;
    }

    public void resetPID(){
        integral = 0.0;
        lastError = 0.0;
    }
    


    

}

