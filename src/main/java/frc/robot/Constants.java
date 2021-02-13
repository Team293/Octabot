package frc.robot;

import static frc.robot.Constants.DriveTrainConstants.DRIVE_KINEMATICS;
import static frc.robot.Constants.DriveTrainConstants.FEED_FORWARD;

import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.util.Units;

public final class Constants{

    public static final class DrivetrainConstants{
        private final double CLOSED_LOOP_RAMP = 0.5;
        private final double MAX_VELOCITY = 21549;
        private final double VELOCITY_LIMIT_PERCENTAGE = 0.5;
        private final double VELOCITY_SLOWDOWN_MODIFIER = 0.5;
        private final double INVALID_INPUT = -99;
        private final double L_DEADBAND = .15;
        private final double R_DEADBAND = .15;

        //PID configuration constants
        private final int PID_SLOT_ID = 0;
        private final int ENCODER_CONFIG_TIMEOUT_MS = 4000;
        private final int PID_CONFIG_TIMEOUT_MS = 10;
        private final double KF = 0.04759;
        private final double KP = 0.0181;
        private final double KI = 0.0;
        private final double KD = 0.0;
        private final double KA = 0.575;
        private final double KV = 9.9;
        private final double KS = 0.679;

        //MISC Constants
        private final double WHEEL_CIRCUMFERENCE_METERS = 0.24;
        private final int ENCODER_EPR = 112; // Edges per Rotation
        private static final SimpleMotorFeedforward FEED_FORWARD = new SimpleMotorFeedforward(kS,kV,kA);
        // Baseline values for a RAMSETE follower in units of meters and seconds
        public static final double RAMSETE_B = 2;
        public static final double RAMSETE_ZETA = 0.7;

        public static final double TRACK_WIDTH_METERS = 1.6563335663292353; // TODO Check
        public static final DifferentialDriveKinematics DRIVE_KINEMATICS = new DifferentialDriveKinematics(TRACK_WIDTH);
        
        
    }

    public static final class TargetingConstants{
        
        private final int LIMELIGHT_LED_ON = 3;
        private final int LIMELIGHT_LED_OFF = 1;
        private final int LEFT_MOTOR_IND = 0;
        private final int RIGHT_MOTOR_IND = 1;
        private final double TARGET_ACQUIRED = 1.0;
        private final double TARGET_NO_TARGET = 0.0;
        private final double INTEGRAL_WEIGHT = .2;
        private final double CONFIRMED_THRESHOLD = 1.0;
        private final double CONFIRMED_TIME = .25;        // Amount of seconds before it considers a target confirmed
        private final double INTEGRAL_LIMIT = 1000000000; // TODO Check math and get an actual number
        private final double LIMELIGHT_ERROR_MAX = 29.5;
        private final double PERCENT_OUTPUT_LIMIT = .5;
        private final double TIMER_NOT_STARTED_VALUE = 0.0;
        private final double DEFAULT_LAUNCHER_RPM = 1200.0;
        private final double ERROR_INTEGRAL_DEFAULT = 0.0;
        private final double LAST_ERROR_DEFAULT = 0.0;

        // Max speed in meters per second
        public static final double MAX_SPEED_AUTO = 3;

        // Max acceleration in meters per second per second
        public static final double MAX_ACCELERATION_AUTO = 2; //TODO find actual numbers for us

        // Max voltage
        public static final double MAX_VOLTAGE_AUTO = 11;
        public static final DifferentialDriveVoltageConstraint VOLTAGE_CONSTRAINT= 
            new DifferentialDriveVoltageConstraint(FEED_FORWARD, DRIVE_KINEMATICS, MAX_VOLTAGE_AUTO);

        // Baseline values for a RAMSETE follower in units of meters and seconds
        public static final double RAMSETE_B = 2;
        public static final double RAMSETE_ZETA = 0.7;
    }

}