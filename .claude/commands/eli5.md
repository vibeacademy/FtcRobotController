---
description: Explain code, a file, or a concept in simple, beginner-friendly terms (Explain Like I'm 5).
---

# ELI5 Command

Explain the topic the user asked about as if teaching a smart beginner with no
programming or robotics background — a new FTC team member seeing this for the
first time.

## Input

The thing to explain: $ARGUMENTS

- If it's a file path or class name, read the relevant code first, then explain it.
- If it's a concept (e.g. "PID control", "mecanum drive", "hardware abstraction"),
  explain the concept, using this project's code as the example where possible.
- If no argument was given, ask what they'd like explained.

## How to Explain

1. **Start with a one-sentence answer** — what is it, in plain words.
2. **Use a real-world analogy** a teenager would recognize (driving a car,
   video games, sports — not other programming concepts).
3. **Walk through the pieces** step by step, in the order they matter, not the
   order they appear in the file.
4. **Avoid jargon.** When a technical term is unavoidable, define it right there
   in one phrase.
5. **Connect it back to the robot**: what would a driver or spectator actually
   see happen because of this code?
6. **End with "why it matters"** — one or two sentences on what would go wrong
   without it.

## Style Rules

- Short paragraphs and plain sentences. No walls of text.
- Show tiny code snippets (3-8 lines) only when pointing at something specific.
- Don't dumb down the facts — simplify the language, not the truth.
- Offer to go one level deeper at the end ("Want the more technical version?").
