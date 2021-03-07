package frc.robot.classes;

public class Position2D 
{
  private double m_x;
  private double m_y;
  private double m_heading;

  public Position2D(double x, double y, double heading)
  {
    m_x = x;
    m_y = y;
    m_heading = heading;
  }

  public double getX()
  {
    return m_x;
  }

  public double getY()
  {
    return m_y;
  }

  public double getHeading()
  {
    return m_heading;
  }
}
