package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import
import static com.qualcomm.hardware.rev.RevHubOrientationOnRobot.LogoFacingDirection.BACKWARD;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Rotation;.org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp( name = "FieldOrientatedTeleop")
public class FieldOrientatedTeleop extends LinearOpMode {
    @Override
    public void runOpMode() {
        DcMotorSimple leftFront = hardwareMap.get(DcMotorSimple.class, "lf");
        DcMotorSimple leftBack = hardwareMap.get(DcMotorSimple.class, "lb");
        DcMotorSimple rightFront = hardwareMap.get(DcMotorSimple.class, "rf");
        DcMotorSimple rightBack = hardwareMap.get(DcMotorSimple.class, "rb");

        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);

        IMU imu - hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection,BACKWARD
        ));
        imu.initialize(parameters);

        waitForStart();

        while (opModeIsActive()) {
            double lx = gamepad1.left_stick_x;
            double ly = gamepad1.left_stick_y;
            double rx = gamepad1.right_stick_x;

            if (Math.abs(y) < 0.05)

            double max = Math.max(Math.abs(lx) + Math.abs(ly) + Math.abs(rx), 1);

            double power = 0.2 + (0.6 * gamepad1.right_trigger);

            double heading = -imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
            // Cubic response for a smoother low speed control

            ly  = Math.pow(ly,  3);
            lx  = Math.pow(lx,  3);
            rx = Math.pow(rx, 3);

            double adjustedLx = -ly * Math.sin(heading) + lx * Math.cos(heading);
            double adjustedLy = +ly * Math.cos(heading) + lx * Math.sin(heading);


            leftFront.setPower(((adjustedLy + adjustedLx + rx) / max) * power);
            leftBack.setPower(((adjustedLy - adjustedLx + rx) / max) * power);
            rightFront.setPower(((adjustedLy - adjustedLx - rx) / max) * power);
            rightBack.setPower(((adjustedLy + adjustedLx - rx) /max ) * power);


            }


        }


        }
