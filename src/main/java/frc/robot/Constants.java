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
public class Constants 
{
    public static final class DrivetrainConstants
    {
        public static final double VELOCITY_LIMIT_PERCENTAGE = 0.5;
        public static final double CLOSED_LOOP_RAMP = 0.5;
        public static final double VELOCITY_SLOWDOWN_MODIFIER = 0.5;
        public static final double MAX_VELOCITY = 21549;
        public static final double INVALID_INPUT = -99;
        public static final double L_DEADBAND = .15;
        public static final double R_DEADBAND = .15;

        //PID configuration constants
        public static final int PID_SLOT_ID = 0;
        public static final int ENCODER_CONFIG_TIMEOUT_MS = 4000;
        public static final int PID_CONFIG_TIMEOUT_MS = 10;
        public static final double KF = 0.04759;
        public static final double KP = 0.0181;
        public static final double KI = 0.0;
        public static final double KD = 0.0;
        public static final double KA = 0.575;
        public static final double KV = 9.9;
        public static final double KS = 0.679;

        // MISC Constants
        public static final  double WHEEL_CIRCUMFERENCE_METERS = 0.24;
        public static final SimpleMotorFeedforward FEED_FORWARD = new SimpleMotorFeedforward(KS, KV, KA);
        public static final int ENCODER_EPR = 112; // Edges per Rotation
        public static final  int ENCODER_EDGES_PER_STEP =  4; 
        public static final  int GEARBOX_RATIO_TO_ONE = 20;

        public static final double TRACK_WIDTH_METERS = 0.6563335663292353; // TODO Check
        public static final DifferentialDriveKinematics DRIVE_KINEMATICS = new DifferentialDriveKinematics(TRACK_WIDTH_METERS);
        public static final double FEETPERMETERS = 3.281;
    }

    public static final class SmoothControlConstants
    {
        public static final double K1 = 1.0;
        public static final double K2 = 3.0;
    }
}

