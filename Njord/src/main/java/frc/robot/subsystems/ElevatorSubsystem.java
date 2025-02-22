
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import static edu.wpi.first.units.Units.MetersPerSecond;

import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.units.measure.MutLinearVelocity;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.Constants;
import frc.robot.subsystems.ElevatorSubsystem.ElevatorState;

public class ElevatorSubsystem extends SubsystemBase {
  private TalonFX elevatorLeft;
  private TalonFX elevatorRight;
  public static int TargetLevel;
  private int ElevatorLevel;
  private DigitalInput MagLimit = new DigitalInput(0);
  public ElevatorState mElevatorState;
  private final double GearRatio = 9/1;
  private final double kSproketCircumfrence = 1.887829 * Math.PI;
  private final double kFloorToCarriage = 19.876;

  public double rotationsToInches(double angle){
    return angle * kSproketCircumfrence;
  }

  public double getPosition(){
    return rotationsToInches(elevatorLeft.getPosition().getValueAsDouble() / GearRatio );
  }

  public void resetPosition(){
    elevatorLeft.setPosition(0);
    
  }

  public void setPosition(double position){
    PositionVoltage pos = new PositionVoltage(position).withSlot(0);
    elevatorLeft.setControl(pos);
  }

  public enum ElevatorState {
    S_GoingUp, S_GoingDown, S_Stopped
    }


private final SysIdRoutine mSysIdRoutine = 
   new SysIdRoutine(
     new SysIdRoutine.Config(),
     new SysIdRoutine.Mechanism(
      voltage -> {
        elevatorLeft.setVoltage(voltage.magnitude());
      },
        log -> {
        log.motor("elevator")
        .voltage(
           elevatorLeft.getMotorVoltage().getValue())
          .angularPosition(elevatorLeft.getPosition().getValue())
          .angularVelocity(elevatorLeft.getVelocity().getValue());        
        },
         this,
        "elevatorSysId"));

public Command sysIdQuasistatic(SysIdRoutine.Direction direction) {
  return mSysIdRoutine.quasistatic(direction);
  }
          
public Command sysIdDynamic(SysIdRoutine.Direction direction) {
  return mSysIdRoutine.dynamic(direction);
  }
    
      

  /** Creates a new ElevatorSubsystem. */
  public ElevatorSubsystem() {
    elevatorLeft = new TalonFX(Constants.ElevatorConstants.kElevatorLeft, Constants.kCarnivoreName);
    elevatorRight = new TalonFX(Constants.ElevatorConstants.kElevatorRight, Constants.kCarnivoreName);
    elevatorRight.setControl(new Follower(Constants.ElevatorConstants.kElevatorLeft, true));
    

    ElevatorLevel = 0;
    TargetLevel = 0;
  
    mElevatorState = ElevatorState.S_Stopped;

    var fx_cfg = new MotorOutputConfigs();

    fx_cfg.NeutralMode = NeutralModeValue.Brake;
    
    elevatorLeft.getConfigurator().apply(fx_cfg);
    elevatorRight.getConfigurator().apply(fx_cfg);


     var Slot0Configs = new Slot0Configs();
    Slot0Configs.GravityType = GravityTypeValue.Elevator_Static;
    Slot0Configs.kS = 0;
    Slot0Configs.kG = 0;
    Slot0Configs.kP = 1;
    Slot0Configs.kI = 0;
    Slot0Configs.kD = 0;

    elevatorLeft.getConfigurator().apply(Slot0Configs, 0.05);

     elevatorRight.setControl(new Follower(23, true));
  }

  public void runElevatorState(){
    switch (mElevatorState){
      case S_GoingUp:
      GoingUp();
      break;
      case S_GoingDown:
      GoingDown();
      break;
      case S_Stopped:
      Stopped();
      break;
    }
  }

  private void GoingUp(){
   if(getLimitState()){
    ElevatorLevel++;
    mElevatorState = ElevatorState.S_Stopped;
   } else{
    elevatorLeft.setVoltage(4);
   }
  }

  private void GoingDown(){
    if(getLimitState()){
      ElevatorLevel--;
      mElevatorState = ElevatorState.S_Stopped;
     } else{
      elevatorLeft.setVoltage(-4);
     }
  }

  private void Stopped(){
    if(TargetLevel > ElevatorLevel){
      elevatorLeft.setVoltage(4);
      if(!getLimitState()){
        mElevatorState = ElevatorState.S_GoingUp;
      }
    } else if (TargetLevel < ElevatorLevel){
      elevatorLeft.setVoltage(-4);
      if(!getLimitState()){
        mElevatorState = ElevatorState.S_GoingDown;
      }
    } else{
      elevatorLeft.setVoltage(0);
    }
  }

  public void spinMotorNeg(){
    elevatorLeft.setVoltage(-4);
  }

  public void spinMotorPos(){
    elevatorLeft.setVoltage(4);
  }

  public void stop(){
    elevatorLeft.setVoltage(0);
  }

  private boolean getLimitState(){
    return MagLimit.get();
  }

  
  @Override
  public void periodic() {
    SmartDashboard.putNumber("getRaw", elevatorLeft.getRotorPosition().getValueAsDouble());

    SmartDashboard.putNumber("isConfig", elevatorLeft.getDeviceID());
    setPosition(SmartDashboard.getNumber("DesiredHeight", 0));
    SmartDashboard.putNumber("Height", getPosition());
    // This method will be called once per scheduler run
  }
}
