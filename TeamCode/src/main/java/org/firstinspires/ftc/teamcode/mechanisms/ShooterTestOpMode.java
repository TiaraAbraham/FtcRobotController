package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.shooter.ShooterCommandPower;

@TeleOp
public class ShooterTestOpMode extends OpMode {

    private org.firstinspires.ftc.teamcode.subsystems.ShooterSubsystem shooterSubsystem;
    private org.firstinspires.ftc.teamcode.commands.shooter.ShooterCommandPower shooterCommandPower;

    @Override
    public void init() {
        shooterSubsystem = new org.firstinspires.ftc.teamcode.subsystems.ShooterSubsystem(hardwareMap);
        shooterCommandPower = new ShooterCommandPower();
        shooterCommandPower.shootPower(shooterSubsystem, gamepad1);
    }

    @Override
    public void loop() {
        shooterCommandPower.execute();
    }
}

