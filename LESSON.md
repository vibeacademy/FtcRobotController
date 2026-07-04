# Lesson 03 — Your First OpMode

You write a program from an empty file: menu registration, the init/loop
lifecycle, live telemetry, and gamepad input. Still no robot required.

## Challenge first (really)

Before opening my file: create
`TeamCode/src/main/java/org/firstinspires/ftc/teamcode/opmodes/` →
`MyFirstOpMode.java` and type it yourself from the video. Typos and red
squiggles are the actual learning. Then compare with:

```
TeamCode/src/main/java/org/firstinspires/ftc/teamcode/opmodes/MyFirstOpMode.java
```

(The comments in that file match the video's beats.)

## The 4 mistakes everyone makes

1. Forgetting `telemetry.update()` — dashboard stays blank forever.
2. Expecting code *below* the loop to run during the match — it doesn't.
3. Stick up = **negative** value (aviation convention).
4. Heavy work above `waitForStart()` — keep init fast and light.

## Prove it worked

Run it (simulator comes next lesson — for now, Build → Make Project must go
green, and if you have any gamepad, the sim in Lesson 04 will show your
stick values live). Your win: a program with your name on the menu.

## Next

- **Previous:** `lesson-02-workstation`
- **Next:** `lesson-04-simulator` — drive a robot that doesn't exist.
- **Series index:** [`LESSONS.md`](../../blob/master/LESSONS.md)
