# Lesson 04 — Drive a Robot That Doesn't Exist

Arc 1 payoff: drive a simulated robot with a real gamepad, using code from
this repo. Robot time is scarce; sim time is unlimited.

## Setup

1. **Get the simulator** (community project — credit where due):
   ```
   git clone https://github.com/Beta8397/virtual_robot.git
   ```
   Open it in IntelliJ IDEA (free Community edition) and run it once.
2. **Sync this repo's TeamCode into it:**
   ```
   cp -r TeamCode/src/main/java/org/firstinspires/ftc/teamcode/* \
       <virtual_robot>/TeamCode/src/org/firstinspires/ftc/teamcode/
   ```
3. Run the simulator, pick **Sim TeleOp (Lesson 04)** from its menu (same
   menu idea as the Driver Station), plug in a gamepad, drive.

## The code

`opmodes/SimTeleOp.java` — two-stick tank drive. Read the comments: the
hardware names (`front_left_motor`, …) are the contract that makes the same
code work in the sim and on a real robot.

## Honest print: where the sim lies

No battery sag, no wheel slip, perfect sensors. The simulator proves your
**logic**; only the field proves **physics**. Both matter; the sim is where
you get to be wrong cheaply.

## Known SDK ↔ simulator deltas

- Not every SDK class exists in the sim; stick to motors/servos/IMU/gamepad
  for these lessons.
- If an OpMode compiles here but not in the sim, delete the sim copy and
  re-sync — stale files are the usual cause.

## Next

- **Previous:** `lesson-03-first-opmode`
- **Next:** `lesson-05-mecanum` — the math that makes it strafe.
- **Series index:** [`LESSONS.md`](../../blob/master/LESSONS.md)
