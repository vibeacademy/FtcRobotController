# Lesson 02 — The Robotics Workstation

Goal: build this repo on your own computer and see **BUILD SUCCESSFUL** —
no robot required. This page is the checklist from the video.

## Setup checklist

1. **Install Android Studio** — https://developer.android.com/studio
   (the free official version; 8GB+ RAM recommended).
2. **Clone this repo** (or your fork of it):
   ```
   git clone https://github.com/vibeacademy/FtcRobotController.git
   ```
3. **Open it in Android Studio** (File → Open → the repo folder) and wait
   out the first Gradle sync. Honest numbers: 3–10 minutes the first time.
   It's downloading the build tools once; later syncs take seconds.
4. **Find your folder.** Everything your team writes lives in
   `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/`. The other ~95%
   of the repo is the FTC SDK — you get to ignore it.
5. **Build**: Build → Make Project (or `./gradlew :TeamCode:assembleDebug`
   in the terminal). You want the green **BUILD SUCCESSFUL**.

## The 3 errors everyone hits

| Error | Fix |
|---|---|
| `SDK location not found` | Android Studio usually creates `local.properties` for you. If building from the terminal, create it with one line: `sdk.dir=/path/to/your/Android/sdk` (on Mac usually `~/Library/Android/sdk`). Never commit this file — it's machine-specific. |
| Gradle sync hangs forever | Check antivirus/VPN isn't scanning the download; plug into real WiFi; let it finish once — it's cached after that. |
| `Unsupported Java` / JDK errors | Use the JDK bundled with Android Studio (Settings → Build Tools → Gradle → Gradle JDK → the "jbr" entry). |

Stuck on something else? Post the **first** error message in your build
output under the video — not the last one. The first error is the real one;
the rest are dominoes.

## Next

- **Previous:** `lesson-01-hello-robot` — what an OpMode is.
- **Next:** `lesson-03-first-opmode` — you write your first program.
- **Series index:** [`LESSONS.md`](../../blob/master/LESSONS.md)
