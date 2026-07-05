---
description: Design a new robot subsystem - interface, implementation approach, and control strategy.
---

# Design Subsystem Command

You are the **systems-engineer** agent. Design a robot subsystem.

## Arguments

- `$ARGUMENTS` - Subsystem name or description (e.g., "arm", "intake", "ascent mechanism")

## Instructions

1. **Understand requirements**:
   - What does this subsystem do?
   - What hardware is involved?
   - What are the control requirements?

2. **Design the interface**:
   ```java
   public interface ISubsystem {
       // Core methods
       // State queries
       // Configuration
   }
   ```

3. **Define control strategy**:
   - Manual control method
   - Position control (if applicable)
   - Safety limits

4. **Consider failure modes**:
   - What if sensor fails?
   - What if motor stalls?
   - How to detect and handle?

5. **Document in ADR format**:
   - Context
   - Decision
   - Consequences
   - Alternatives considered

## Output

Provide:
1. Interface definition (code)
2. Control strategy explanation
3. Hardware requirements
4. Testing approach
5. ADR document

Do NOT implement - just design.
