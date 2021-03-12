// RobotBuilder Version: 3.1
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.DrivetrainConstants.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.classes.Position2D;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

/**
 *
 */
public class Drivetrain extends SubsystemBase 
{
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private WPI_TalonSRX leftTalonLead;
    private WPI_TalonSRX rightTalonLead;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private boolean encodersAvailable;
    
    private AHRS navX;
    private final DifferentialDriveOdometry m_odometry;

    public Drivetrain() 
    {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        leftTalonLead = new WPI_TalonSRX(1);
        rightTalonLead = new WPI_TalonSRX(2);
        
        //Reset and calibrate the gyro
        navX = new AHRS(SPI.Port.kMXP);

        setupGyro(navX);
        zeroDriveTrainEncoders();

        //Instantiating Drivetrain Odometry as NavX heading
        m_odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(getGyroHeading())); 

        //Clearing sticky faults
        leftTalonLead.clearStickyFaults();
        leftTalonLead.clearStickyFaults();

        //Setting factory default settings to motors
        leftTalonLead.configFactoryDefault();
        rightTalonLead.configFactoryDefault();
    
        //Set motors to inverted if needed
        leftTalonLead.setInverted(false);
        rightTalonLead.setInverted(true);

        //Set encoder
        leftTalonLead.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PID_SLOT_ID, ENCODER_CONFIG_TIMEOUT_MS);
        rightTalonLead.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PID_SLOT_ID, ENCODER_CONFIG_TIMEOUT_MS);
        leftTalonLead.setSensorPhase(true);
        rightTalonLead.setSensorPhase(true);
        
        //Configure PID
        leftTalonLead.config_kF(PID_SLOT_ID, KF, PID_CONFIG_TIMEOUT_MS);
        leftTalonLead.config_kP(PID_SLOT_ID, KP, PID_CONFIG_TIMEOUT_MS);
        leftTalonLead.config_kI(PID_SLOT_ID, KI, PID_CONFIG_TIMEOUT_MS);
        leftTalonLead.config_kD(PID_SLOT_ID, KD, PID_CONFIG_TIMEOUT_MS);
        leftTalonLead.configClosedloopRamp(CLOSED_LOOP_RAMP);
        leftTalonLead.configNeutralDeadband(L_DEADBAND);
        leftTalonLead.setNeutralMode(NeutralMode.Coast);

        rightTalonLead.config_kF(PID_SLOT_ID, KF, PID_CONFIG_TIMEOUT_MS);
        rightTalonLead.config_kP(PID_SLOT_ID, KP, PID_CONFIG_TIMEOUT_MS);
        rightTalonLead.config_kI(PID_SLOT_ID, KI, PID_CONFIG_TIMEOUT_MS);
        rightTalonLead.config_kD(PID_SLOT_ID, KD, PID_CONFIG_TIMEOUT_MS);
        rightTalonLead.configClosedloopRamp(CLOSED_LOOP_RAMP);
        rightTalonLead.configNeutralDeadband(R_DEADBAND);
        rightTalonLead.setNeutralMode(NeutralMode.Coast);
        
        enableEncoders();
        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }

    @Override
    public void periodic() 
    {
        m_odometry.update(
            Rotation2d.fromDegrees(getGyroHeading()),
                edgesToFeet(getLeftEncoderPosition()),
                edgesToFeet(getRightEncoderPosition())
        );

        SmartDashboard.putNumber("Gyro heading", navX.getAngle());
        SmartDashboard.putNumber("Yaw val", navX.getYaw());
        SmartDashboard.putNumber("TranslationX", m_odometry.getPoseMeters().getTranslation().getX());
        SmartDashboard.putNumber("TranslationY", m_odometry.getPoseMeters().getTranslation().getY());
        
        SmartDashboard.putNumber("Left Encoder Position", getLeftEncoderPosition());
        SmartDashboard.putNumber("Right Encoder Position", getRightEncoderPosition());
    }

    @Override
    public void simulationPeriodic() 
    {
        // This method will be called once per scheduler run when in simulation
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public Position2D getCurrentPose()
    {
        double x = (m_odometry.getPoseMeters().getTranslation().getX()) * FEETPERMETERS;
        double y = (m_odometry.getPoseMeters().getTranslation().getY()) * FEETPERMETERS;
        double heading = getGyroHeading();
        
        return (new Position2D(x, y, heading));
    }

    public double getCurrentVelocity()
    {
        double leftFeetPerSecond = edgesPerDecisecToFeetPerSec(leftTalonLead.getSelectedSensorVelocity(0)) * FEETPERMETERS;
        double rightFeetPerSecond = edgesPerDecisecToFeetPerSec(rightTalonLead.getSelectedSensorVelocity(0)) * FEETPERMETERS;
        
        return((leftFeetPerSecond + rightFeetPerSecond) / 2.0); 
    }


    public void velocityDrive(double vL, double vR)
    {
        //Convert vL and vR to percentages
        leftTalonLead.set(ControlMode.Velocity, vL);
        rightTalonLead.set(ControlMode.Velocity, vR);
    }

    // Converts joystick input adjusted to a RPM for the Falcon's PIDF loop to aim for
    public void powerDrive(double leftPower, double rightPower)
    {
        double leftMotorPower = leftPower;
        double rightMotorPower = rightPower;

        //Prevent invalid input
        if( leftPower > 1.0)
        {
            leftMotorPower = 1.0;
        }

        if( leftPower < -1.0)
        {
            leftMotorPower = -1.0;
        }

        if( rightPower > 1.0)
        {
            rightMotorPower = 1.0;
        }

        if( rightPower < -1.0)
        {
            rightMotorPower = -1.0;
        }
        
        //Both left and right motor power is valid, set motor power
        leftMotorPower = leftMotorPower * MAX_VELOCITY * VELOCITY_LIMIT_PERCENTAGE;
        rightMotorPower = rightMotorPower * MAX_VELOCITY * VELOCITY_LIMIT_PERCENTAGE;

        //Set motor power
        leftTalonLead.set(ControlMode.Velocity, leftMotorPower);
        rightTalonLead.set(ControlMode.Velocity, rightMotorPower);
    }

    // Stops motor usually used after the drive command ends to prevent shenanigans
    public void stop()
    {
        leftTalonLead.set(ControlMode.Current, 0);
        rightTalonLead.set(ControlMode.Current, 0);
    }


    //Returns encoder clicks to meters used for getting distance travelled
    public static double edgesToFeet(double clicks)
    {
        return (WHEEL_CIRCUMFERENCE_FEET / ENCODER_EPR) * clicks; 
    }
    // Converts from encoder edges per 100 milliseconds to meters per second.
    public static double edgesPerDecisecToFeetPerSec(double stepsPerDecisec) 
    {
        return edgesToFeet(stepsPerDecisec * 10);
    }
    //Converts from meters per second to encoder edges per 100 milliseconds.
    public static double feetPerSecToEdgesPerDecisec(double metersPerSec) 
    {
        return feetToEdges(metersPerSec) * 0.1d;
    }

    //Converts from meters to encoder edges
    public static double feetToEdges(double meters)
    {
        return (meters/ WHEEL_CIRCUMFERENCE_FEET) * 0.1d;
    }
    //
   

    /**
   * Attempts to enable the drivetrain encoders.
   */
    private void enableEncoders() 
    {
        encodersAvailable = 
            ((leftTalonLead.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10) == ErrorCode.OK) &&
            (rightTalonLead.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10) == ErrorCode.OK));

        if (!encodersAvailable) 
        {
            DriverStation.reportError("Failed to configure Drivetrain encoders!!", false);
        }
    }
    public boolean isEncodersAvailable() 
    {
        return encodersAvailable;
    }
    
      /**   
       * returns left encoder position
       * 
       * @return left encoder position
       */
    public double getLeftEncoderPosition() 
    {
        // Returns the number of steps, multiply by edges per step to get EPR, divided by the gearbox ratio
        return (leftTalonLead.getSelectedSensorPosition(0) * ENCODER_EDGES_PER_STEP) / GEARBOX_RATIO_TO_ONE;
    }
    
      /**
       * returns right encoder position
       * 
       * @return right encoder position
       */
    public double getRightEncoderPosition() 
    {
        // Returns the number of steps, multiply by edges per step to get EPR, divided by the gearbox ratio
        return (rightTalonLead.getSelectedSensorPosition(0) * ENCODER_EDGES_PER_STEP) / GEARBOX_RATIO_TO_ONE;
    }
    
    private void zeroDriveTrainEncoders() 
    {
        leftTalonLead.setSelectedSensorPosition(0);
        rightTalonLead.setSelectedSensorPosition(0);
    }
      
    public double getGyroHeading() 
    {
        return (navX.getYaw() * -1.0d); // makes 0-360 fused heading from navx to -180->180
    }

    public void resetOdometry() 
    {   
        Pose2d pose = new Pose2d(); 
        zeroDriveTrainEncoders();
        m_odometry.resetPosition(pose, new Rotation2d(getGyroHeading()));
      }
    
    public void setupGyro(AHRS gyro)
    {
        System.out.println("Calibrating gyroscope.");
        gyro.enableBoardlevelYawReset(true);
        gyro.reset();
        gyro.calibrate();

        //Wait for gyro to calibrate ~1 - 10 seconds
        while (gyro.isCalibrating())
        {
        }
        
        gyro.zeroYaw();
        
        System.out.println("Calibrating gyroscope done.");
    }
}
