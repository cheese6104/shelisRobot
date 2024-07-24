// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.ArmCommands.ArmSetState;
import frc.robot.commands.ArmCommands.ArmState;
import frc.robot.commands.ArmCommands.HomingArm;
import frc.robot.commands.ArmCommands.ResetPositionArm;
import frc.robot.commands.ElevatorCommands.ElevatorSetPower;
import frc.robot.commands.ElevatorCommands.ElevatorSetState;
import frc.robot.commands.ElevatorCommands.ElevatorState;
import frc.robot.commands.ElevatorCommands.HomingElevator;
import frc.robot.commands.ElevatorCommands.ResetPositionElevator;
import frc.robot.commands.shlongCommands.DisableShlong;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shlong;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandPS5Controller m_driverController =
      new CommandPS5Controller(OperatorConstants.kDriverControllerPort);

  private Intake m_intake = new Intake();
  private Shlong m_shlongLeft = new Shlong(Constants.shlong.leftMotorId, true);
  private Shlong m_shlongRight = new Shlong(Constants.shlong.rightMotorId, false);
  private Elevator m_elevator;
  private Arm m_arm;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer(Elevator elevator, Arm arm) {
    m_elevator = elevator;
    m_arm = arm;
    m_driverController.square().onTrue(new ElevatorSetPower(m_elevator, 0.1));
    m_driverController.R2().onTrue(new ElevatorSetState(elevator, ElevatorState.def));
    m_driverController.L2().onTrue(new ElevatorSetState(elevator, ElevatorState.floor));
    m_driverController.cross().onTrue(new ArmSetState(m_arm, ArmState.in));
    m_driverController.triangle().onTrue(new ArmSetState(m_arm, ArmState.floor));
    m_driverController.circle().onTrue(new HomingElevator(m_elevator));
    // m_driverController.cross().onTrue(new ResetPositionElevator(elevator, 0));
    SmartDashboard.putData("Reset Elevator Position", new ResetPositionElevator(elevator, 0).ignoringDisable(true));
    SmartDashboard.putData("Reset Arm Position", new ResetPositionArm(m_arm, 127).ignoringDisable(true));
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return null;
  }
}
