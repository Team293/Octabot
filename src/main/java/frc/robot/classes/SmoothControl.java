package frc.robot.classes;

import static frc.robot.Constants.SmoothControlConstants.*;

public class SmoothControl 
{
  private boolean m_targetSet;          //True if the target is set!
  private Position2D m_targetPose;
  private double m_range;               //Feet
  private double m_omegaDesired;        //Radians per second
  
  public SmoothControl()
  {
    m_targetSet = false;
  }
  
  public void setNextTarget(Position2D newTarget)
  {
    m_targetPose = newTarget;
    m_targetSet = true;
  }

  //Called when the target has been reached
  public void clearTarget()
  {
    m_targetSet = false;
  }

  public void computeTurnRate(Position2D currentPose, double currentVelocity)
  {
    double vv = currentVelocity;
    double poseHeading = Math.toRadians(currentPose.getHeading()); //Convert heading to radians

    //Check if the target pose is set
    if (false == m_targetSet)
    {
      //ERROR! m_targetPose has not been set!
      System.out.println("ERROR! m_targetPose has not been set!");        
    }
    else
    {
      //m_targetPose has been set, OK to continue!
      //Check if direction of velocity is in reverse
      if (currentVelocity < 0) 
      {
        //Since current velocity is negative, we must flip the sign to be positive for the algorithm to work!
        vv = -currentVelocity; 
        //Set the pose to be 180 from "front" to show backwards direction
        poseHeading = poseHeading + Math.PI;
      }

      //Limit pose heading to be within -Pi and Pi
      poseHeading = limitRadians( poseHeading ); // poseHeading is now radians

      // With the velocity and robot heading set appropriately, get the range
      // and the vector orientation that runs from the robot to the target
      double dx = m_targetPose.getX() - currentPose.getX();
      double dy = m_targetPose.getY() - currentPose.getY();
      m_range = Math.sqrt( dx * dx + dy * dy ); // distance in feet
      double r_angle = Math.atan2( dy, dx );    // vector heading in radians

      // Compute the angle between this vector and the desired orientation at the target
      double thetaT = Math.toRadians(m_targetPose.getHeading()) - r_angle;
      thetaT = limitRadians( thetaT ); // bound this between -PI to PI

      // Compute the angle between current robot heading and the vector from
      // the robot to the target
      double del_r = poseHeading - r_angle;
      del_r = limitRadians(del_r); // bound this between -PI to PI
      
      // All set, now the equation for the angular rate!
      m_omegaDesired = -(vv / m_range) * (K2 * ( del_r - Math.atan( -K1 * thetaT ) ) + 
        Math.sin( del_r ) * ( 1.0 + ( K1 / ( 1.0 + (K1 * thetaT)*(K1 * thetaT))))); 
    }
  }

  
  public double limitRadians(double radians)
  {
    double retval = radians;
    
    while(retval > Math.PI)
    {
      retval -= 2 * Math.PI;
    }

    while(retval < -Math.PI)
    {
      retval += 2 * Math.PI;
    }

    return retval;
  }

  public double getRange()
  {
      return m_range;
  }
  
  public double getTurnRate()
  {
      return m_omegaDesired;
  }
}
