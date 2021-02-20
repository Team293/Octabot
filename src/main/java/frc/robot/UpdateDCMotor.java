package frc.robot;

import edu.wpi.first.wpilibj.system.plant.DCMotor;
import edu.wpi.first.wpilibj.util.Units;


public class UpdateDCMotor extends DCMotor{
    UpdateDCMotor(double nominalVoltageVolts,
    double stallTorqueNewtonMeters,
    double stallCurrentAmps,
    double freeCurrentAmps,
    double freeSpeedRadPerSec,
    int numMotors){
        super(nominalVoltageVolts,
     stallTorqueNewtonMeters,
     stallCurrentAmps,
     freeCurrentAmps,
     freeSpeedRadPerSec,
     numMotors);}

  public static DCMotor getHDHex(int numMotors) {
    return new DCMotor(
        12, //Nominal Voltage Volts
        .105, //stall Torque (Newton Meters)
        8.5, //Stall Current Amps (Weirdly low)
        2.7, //Free Current Amps
        Units.rotationsPerMinuteToRadiansPerSecond(6000), numMotors);
  }
}
