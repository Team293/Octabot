// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package frc.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.Constants.DrivetrainConstants.MAX_ACCELERATION_AUTO;
import frc.robot.Constants.DrivetrainConstants.MAX_SPEED_AUTO;
import frc.robot.Constants.DrivetrainConstants.DRIVE_KINEMATICS;
import frc.robot.Constants.TargetingConstants;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import frc.robot.subsystems.Drivetrain;
/**
 *
 */
public class AutonomousCommand extends Command {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public AutonomousCommand() {
        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES

        var autoVoltageConstraint =
        new DifferentialDriveVoltageConstraint(
            new SimpleMotorFeedforward(DriveConstants.ksVolts,
                                       DriveConstants.kvVoltSecondsPerMeter,
                                       DriveConstants.kaVoltSecondsSquaredPerMeter),
            DriveConstants.kDriveKinematics,
            10); //Adapt and overcome


        TrajectoryConfig config = 
        new TrajectoryConfig(
            MAX_SPEED_AUTO,
            MAX_ACCELERATION_AUTO)
        .setKinematics(DRIVE_KINEMATICS)
        .addConstraint(autoVoltageConstraint); //Doesn't work :)

        Trajectory octoAuto = TrajectoryGenerator.generateTrajectory( //Import this
            new Pose2d(0,0, Rotation2d(0)); // Start at the origin facing forward
            List.of(
                new Translation2d(1,1), //Go through these two points
                new Translation2d(2,-1)
                ),  
            new Pose2d(3,1,new Rotation2d(0)), //End at the point (3,1) in meters
            config
         );

         m_robotDrive.resetOdometry(exampleTrajectory.getInitialPose());

         RamseteCommand ramseteCommand = new RamseteCommand(Drivetrain.createRamseteController(octoAuto));
    }
    
    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
            
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}
