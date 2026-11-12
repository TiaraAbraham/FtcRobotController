package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ShooterSubsystem extends SubsystemBase {

    private final DcMotorEx shooterMotor1;
    // private final DcMotorEx shooterMotor2;

    private static final double TICKS_PER_REV = 28.0;
    private static final double GEAR_RATIO = 1.0 / 1.0;


    private final PDController controller;

    public static double kP = 0.003;
    public static double kD = 0.1;
    public static double kF = 0.2;   // feedforward guess

    public ShooterSubsystem(HardwareMap hardwareMap) {
        shooterMotor1 = hardwareMap.get(DcMotorEx.class, "shooterMotor1");
        // shooterMotor2 = hardwareMap.get(DcMotorEx.class, "shooterMotor2");


        shooterMotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        // shooterMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        shooterMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // shooterMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        shooterMotor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        // shooterMotor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        controller = new PDController(kP, kD);
    }

    public double getCurrentRPM() {
        double ticksPerSec = (shooterMotor1.getVelocity());
        double motorRPM = (ticksPerSec * 60.0) / TICKS_PER_REV;
        return motorRPM * GEAR_RATIO;
    }

    public void stop() {
        shooterMotor1.setPower(0);
       //  shooterMotor2.setPower(0);
    }

    public void setPower(double power) {
        shooterMotor1.setPower(power);
        // shooterMotor2.setPower(power);
    }

    public void runToRPM(double targetRPM) {
        double currentRPM = getCurrentRPM();

        controller.setP(kP);
        controller.setD(kD);

        double correction = controller.calculate(targetRPM, currentRPM);

        double ff = kF * (targetRPM / 6000.0);

        double power = ff + correction;

        power = Math.max(-1.0, Math.min(1.0, power));

        shooterMotor1.setPower(-power);
        // shooterMotor2.setPower(-power);
    }

    public double calculateRPM(double distance) {
        return 9.01121e-8 * Math.pow(distance, 6)
                - 3.92579e-5 * Math.pow(distance, 5)
                + 0.0069767 * Math.pow(distance, 4)
                - 0.642693 * Math.pow(distance, 3)
                + 32.13839 * Math.pow(distance, 2)
                - 815.39232 * distance
                + 10747.4643;
    }

    public void runLimelightShot(double distance) {
        runToRPM(calculateRPM(distance));
    }
}