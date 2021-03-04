// RobotBuilder Version: 3.1
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package frc.robot;

import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public class Constants {

    public static final class DrivetrainConstants{
        public final static double VELOCITY_LIMIT_PERCENTAGE = 0.5;
        public final static double CLOSED_LOOP_RAMP = 0.5;
        public final static double VELOCITY_SLOWDOWN_MODIFIER = 0.5;
        public final static double MAX_VELOCITY = 21549;
        public final static double INVALID_INPUT = -99;
        public final static double L_DEADBAND = .15;
        public final static double R_DEADBAND = .15;

        //PID configuration constants
        public final static int PID_SLOT_ID = 0;
        public final static int ENCODER_CONFIG_TIMEOUT_MS = 4000;
        public final static int PID_CONFIG_TIMEOUT_MS = 10;
        public final static double KF = 0.04759;
        public final static double KP = 0.0181;
        public final static double KI = 0.0;
        public final static double KD = 0.0;
        public final static double KA = 0.575;
        public final static double KV = 9.9;
        public final static double KS = 0.679;

        // MISC Constants
        public final static double WHEEL_CIRCUMFERENCE_METERS = 0.24;
        public static final SimpleMotorFeedforward FEED_FORWARD = new SimpleMotorFeedforward(KS, KV, KA);
        public static final int ENCODER_EPR = 112; // Edges per Rotation
        public final static int ENCODER_EDGES_PER_STEP =  4; 
        public final static int GEARBOX_RATIO_TO_ONE = 20;
        // Baseline values for a RAMSETE follower in units of meters and seconds
        public static final double RAMSETE_B = 2;
        public static final double RAMSETE_ZETA = 0.7;

        public static final double TRACK_WIDTH_METERS = 0.6563335663292353; // TODO Check
        public static final DifferentialDriveKinematics DRIVE_KINEMATICS = new DifferentialDriveKinematics(TRACK_WIDTH_METERS);
        
        
    }

    public static final class TrajectoryConstants{
        
        // Max speed in meters per second
        public static final double MAX_SPEED_AUTO = 1;

        // Max acceleration in meters per second per second
        public static final double MAX_ACCELERATION_AUTO = 0.5; //TODO find actual numbers for us

        // Max voltage
        public static final double MAX_VOLTAGE_AUTO = 11;
        public static final DifferentialDriveVoltageConstraint VOLTAGE_CONSTRAINT= 
            new DifferentialDriveVoltageConstraint(DrivetrainConstants.FEED_FORWARD, DrivetrainConstants.DRIVE_KINEMATICS, MAX_VOLTAGE_AUTO);

        // Baseline values for a RAMSETE follower in units of meters and seconds
        public static final double RAMSETE_B = 2;
        public static final double RAMSETE_ZETA = 0.7;
    }

}

