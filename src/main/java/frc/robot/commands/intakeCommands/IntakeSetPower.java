// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.intakeCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class IntakeSetPower extends Command {
  /** Creates a new IntakeSetPower. */
  private Intake m_intake;
  private double m_power;
  public IntakeSetPower(Intake intake, double power) {
    addRequirements(intake);
    m_intake = intake;
    m_power = power;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_intake.setPower(m_power);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
