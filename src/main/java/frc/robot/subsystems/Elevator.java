// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import javax.swing.text.Position;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Elevator extends SubsystemBase {
  private TalonFX m_motorMaster; 
  private TalonFX m_motorFollower;
  private TalonFXConfiguration configuration;
  private DutyCycleOut m_DutyCycleOut;

  public Elevator() {
    m_motorMaster = new TalonFX(Constants.Elevator.MasterID);
    m_motorFollower = new TalonFX(Constants.Elevator.FollowerID);

    m_DutyCycleOut = new DutyCycleOut(0);
    configuration = new TalonFXConfiguration();

    configuration.withCurrentLimits(new CurrentLimitsConfigs()
    .withSupplyCurrentLimit(Constants.Elevator.CurrentLimits)
    .withSupplyCurrentLimitEnable(Constants.Elevator.currentLimitEnbale) 
    .withSupplyCurrentThreshold(Constants.Elevator.CurrentLimitsThreshold)
    .withSupplyTimeThreshold(Constants.Elevator.TimeThreshold));

    configuration.Slot0
    .withKP(Constants.Elevator.P)
    .withKI(Constants.Elevator.I)
    .withKD(Constants.Elevator.D)
    .withKS(Constants.Elevator.S)
    .withKV(Constants.Elevator.V)
    .withKG(Constants.Elevator.G)
    .withGravityType(null);

    configuration.MotorOutput.withInverted(InvertedValue.CounterClockwise_Positive);

    configuration.withSoftwareLimitSwitch(new SoftwareLimitSwitchConfigs()
    .withForwardSoftLimitEnable(Constants.Elevator.ForwardSoftLimitEnable)
    .withForwardSoftLimitThreshold(Constants.Elevator.TimeThreshold)
    .withReverseSoftLimitEnable(Constants.Elevator.ReverseSoftLimitEnable)
    .withReverseSoftLimitThreshold(Constants.Elevator.TimeThreshold));

    m_motorFollower.setControl(new Follower(m_motorMaster.getDeviceID(), Constants.Elevator.opposeMaster)); 

    m_motorMaster.getConfigurator().apply(configuration);
    m_motorFollower.getConfigurator().apply(configuration);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setPower(double power){
    m_motorMaster.setControl(m_DutyCycleOut.withOutput(power));
    }

  public void disableMotors(){
    m_motorMaster.disable();
    m_motorFollower.disable();
  }

  public void setPosition(double position){
     m_motorMaster.setControl(new PositionVoltage(position));
  }



}
