# Lesson 10 — Autonomous I: Park Every Time

A 40-point auto that works 30% of the time loses to a 25-point park that
works 95% — by match three. Points × how often it actually works is the
number that matters.

## The code

`opmodes/ParkAuto.java` — your first complete autonomous, built the
competitive way:

- **A state machine, not a script**: `DRIVE_TO_ZONE → STOP → DONE`. Each
  state = what am I doing; each transition = what makes me stop.
- **Three exit doors on every motion**: goal reached, timeout expired,
  `opModeIsActive()`. The second protects motors; the third is the rules of
  the sport.
- **Encoders, not sleeps**: distance from lesson 09's counts-per-inch, so
  battery voltage doesn't change where you land.
- **Telemetry narrates the state** — you can watch the machine think.

## The reusable skeleton

Every auto you ever write is this shape:

```java
enum State { FIRST_THING, SECOND_THING, DONE }
State state = State.FIRST_THING;
ElapsedTime stateTimer = new ElapsedTime();

while (opModeIsActive() && state != State.DONE) {
    switch (state) {
        case FIRST_THING:
            if (goalReached || stateTimer.seconds() > TIMEOUT) {
                state = State.SECOND_THING;
                stateTimer.reset();
            } else {
                // command the motion
            }
            break;
        // ...
    }
    telemetry.update();
}
```

## The 5/5 drill

Run it five times in a row in the simulator. 5/5 parks or it isn't done —
one run is an anecdote. (On a real field, re-run the drill on carpet:
battery and wheel slip vote too. Generous timeouts and margins absorb it.)

## Next

- **Previous:** `lesson-09-sensors`
- **Next:** `lesson-11-p-control` — precision, with one multiplication.
- **Series index:** [`LESSONS.md`](../../blob/master/LESSONS.md)
