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
import frc.robot.Robot;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;
import java.util.ResourceBundle.Control;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import edu.wpi.first.wpilibj.Joystick;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.I2C.Port;
import com.ctre.phoenix.sensors.PigeonIMU;


// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

/**
 *
 */
public class Drivetrain extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private WPI_TalonSRX leftTalonLead;
    private WPI_TalonSRX rightTalonLead;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    private double kF = 0.04759; //TODO redo all of these values after testing
    private double kP = 0.01461;
    private double kI = 0.0;
    private double kD = 0.0;

    private double Ldeadband = .15;
    private double Rdeadband = .15;

    private final double CLOSED_LOOP_RAMP = 0.5;
    private final double MAX_VELOCITY = 21549;
    private final double VELOCITY_LIMIT_PERCENTAGE = 0.5;
    private final double VELOCITY_SLOWDOWN_MODIFIER = 0.5;

    private final double INVALID_INPUT = -99;
    private AHRS navX;

    public Drivetrain() {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    leftTalonLead = new WPI_TalonSRX(1);       
    rightTalonLead = new WPI_TalonSRX(2);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    
    leftTalonLead.clearStickyFaults();
    rightTalonLead.clearStickyFaults();

    //Set facotry defaults for onboard PID
    leftTalonLead.configFactoryDefault();
    rightTalonLead.configFactoryDefault();
    
    leftTalonLead.setInverted(true); //TODO check this
    rightTalonLead.setInverted(false);

    leftTalonLead.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 4000);
    rightTalonLead.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 4000);

    //Configure PID

    leftTalonLead.config_kF(0,kF,10);
    leftTalonLead.config_kP(0,kP,10);
    leftTalonLead.config_kI(0,kI,10);
    leftTalonLead.config_kD(0,kD,0);
    leftTalonLead.configClosedloopRamp(CLOSED_LOOP_RAMP);

    rightTalonLead.config_kF(0,kF,10);
    rightTalonLead.config_kP(0,kP,10);
    rightTalonLead.config_kI(0,kI,10);
    rightTalonLead.config_kD(0,kD,0);
    rightTalonLead.configClosedloopRamp(CLOSED_LOOP_RAMP);

    rightTalonLead.setNeutralMode(NeutralMode.Coast);
    leftTalonLead.setNeutralMode(NeutralMode.Coast);

    rightTalonLead.configNeutralDeadband(.01);
    leftTalonLead.configNeutralDeadband(.01);

    navX = new AHRS(Port.kMXP);
     
    }

    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.

        // setDefaultCommand(new MySpecialCommand());

        setDefaultCommand(new DumbDrive());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop
        SmartDashboard.putNumber("Left Encoder Velocity", leftTalonLead.getSensorCollection().getQuadratureVelocity());
        SmartDashboard.putNumber("Left Encoder Position", leftTalonLead.getSensorCollection().getQuadraturePosition());
        SmartDashboard.putNumber("Right Encoder Velocity", rightTalonLead.getSensorCollection().getQuadratureVelocity());
        SmartDashboard.putNumber("Right Encoder Position", rightTalonLead.getSensorCollection().getQuadraturePosition());

        SmartDashboard.putNumber("NavX Heading", navX.getAngle());
        SmartDashboard.putNumber("NavX Heading", navX.getFusedHeading());


        //SmartDashboard.putNumber("Gyro Angle",gyro.getAngle());
        //SmartDashboard.putNumber("Gyro Yaw", gyro.getYaw());
        //SmartDashboard.putNumber("Gyro Pitch", gyro.getPitch());
    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    // Converts joystick input adjusted for deadband to current for the motor

    public void dumbDrive(double leftPos, double rightPos) {

        double retval = 0.0;
        // Running at half speed as to not kill people
        retval = calcMotorPower(leftPos, Ldeadband);
        if(INVALID_INPUT == retval) {
            System.out.println("Invalid left motor input " + leftPos);
            stop();
            return;
        } else {
            leftTalonLead.set(ControlMode.PercentOutput,retval * VELOCITY_LIMIT_PERCENTAGE);    
        }

        retval = calcMotorPower(rightPos, Rdeadband);
        if(INVALID_INPUT == retval) {
            System.out.println("Invalid right motor input " + rightPos);
            stop();
            return;
        } else {
            rightTalonLead.set(ControlMode.PercentOutput,retval * VELOCITY_LIMIT_PERCENTAGE);    
        }   
    }

    // Converts joystick input adjusted to a RPM for the Falcon's PIDF loop to aim for

    public void velocityDrive(double leftPos, double rightPos, boolean useSlowModifier, boolean useReverse){

        double retval = 0.0;

        if(useReverse){     
            double temp = leftPos;
            leftPos = -rightPos;
            rightPos = -temp;
        }

        retval = calcMotorPower(leftPos, Ldeadband);
        if(INVALID_INPUT == retval) {
            System.out.println("Invalid left motor input " + leftPos);
            stop();
            return;
        } else {
            if(useSlowModifier){
                leftTalonLead.set(ControlMode.Velocity,(retval * MAX_VELOCITY * VELOCITY_LIMIT_PERCENTAGE * VELOCITY_SLOWDOWN_MODIFIER));    
            } else {
                leftTalonLead.set(ControlMode.Velocity,(retval * MAX_VELOCITY * VELOCITY_LIMIT_PERCENTAGE));
            }
            
        }

        retval = calcMotorPower(rightPos, Rdeadband);
        if(INVALID_INPUT == retval) {
            System.out.println("Invalid right motor input " + rightPos);
            stop();
            return;
        } else {
            if(useSlowModifier){
                rightTalonLead.set(ControlMode.Velocity,(retval * MAX_VELOCITY * VELOCITY_LIMIT_PERCENTAGE * VELOCITY_SLOWDOWN_MODIFIER));    
            } else {
                rightTalonLead.set(ControlMode.Velocity,(retval * MAX_VELOCITY * VELOCITY_LIMIT_PERCENTAGE));
            }
        }
    }

    // Stops motor usually used after the drive command ends to prevent shenanigans
    public void stop() {
        leftTalonLead.set(ControlMode.Current,0);
        rightTalonLead.set(ControlMode.Current,0);
    }

    //Calculates the motor power to use based on a given deadband and 
    //joystick input from -1 to 1
    //Prevents spikes in motor power by calculating the line to use 
    //where 0 is the deadband and 1 is the max
    public double calcMotorPower(double input, double deadband) {
        double retval = 0.0;
        if(Math.abs(input) <= deadband) { //Check if input is inside the deadband
            return 0;
        }

        if((input < -1) || (input > 1)) { //input must be between -1 and 1
            return INVALID_INPUT;
        }
        
        retval = (Math.abs(input) - deadband)/(1 - deadband);

        if(input < 0) {
           return -1 * retval;
        } else {
            return retval;
        }
    }

    //Velocity Drive without Deadband for vision purposes
    public void visionDrive(double left, double right){
        if (left > 1 || left < -1 || right > 1 || right < -1){
            System.out.println("Invalid left motor input " + left);
            System.out.println("Invalid right motor input " + right);
            stop();
            return;
        }

        leftTalonLead.set(ControlMode.Velocity,(left * MAX_VELOCITY));
        rightTalonLead.set(ControlMode.Velocity,(right * MAX_VELOCITY));
    }
}

