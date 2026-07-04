#!/usr/bin/env bash
set -euo pipefail

# Codex-seam gate #1: forbidden-pattern scan over a diff.
#
# Reads a unified diff from a file path (arg 1) or from stdin. Scans
# only newly-introduced lines (lines starting with '+' but not '+++').
# Exits 0 if clean; exits 1 with each match on stderr formatted as
# <file>:<lineno>: <pattern>: <text> and a one-line summary on stdout.
#
# Ported verbatim from gembaflow-site/scripts/codex-gates/forbidden-pattern.sh
# with the TypeScript placeholder shapes (`as any`, `@ts-expect-error`,
# `throw new Error("not implemented")`) replaced by Java placeholder
# shapes for this stack (FTC SDK / Java / Gradle). Original Python /
# cross-language entries preserved.
#
# Per-line opt-out: append `# noqa: codex-gate` (script comment) or
# `// noqa: codex-gate` (Java comment) — both are matched as a grep -F
# fixed string, so either form works.
#
# Self-exemption: lines under `scripts/codex-gates/` are skipped so this
# script's own pattern strings don't flag itself.

input_path="${1-}"

current_file=""
current_line=0
matches=0

patterns='^[[:space:]]*if False
^[[:space:]]*if 0:
pass[[:space:]]*#[[:space:]]*placeholder
raise NotImplementedError
#[[:space:]]*(TODO|FIXME|XXX)\b
//[[:space:]]*(TODO|FIXME|XXX)\b
^[[:space:]]*if[[:space:]]*\(false\)
throw new UnsupportedOperationException\("not implemented
throw new RuntimeException\("not implemented
catch[[:space:]]*\([A-Za-z .|]*Exception[[:space:]]+[a-zA-Z_]+\)[[:space:]]*\{[[:space:]]*\}'

process_line() {
    local diff_line="$1"
    local pattern
    local text

    case "$diff_line" in
        '+++ '*)
            current_file="${diff_line#+++ }"
            case "$current_file" in
                b/*)
                    current_file="${current_file#b/}"
                    ;;
                /dev/null)
                    current_file=""
                    ;;
            esac
            return
            ;;
        '@@ '*)
            current_line=$(printf '%s\n' "$diff_line" | sed -n 's/^@@ -[^ ]* +\([0-9][0-9]*\)\(,[0-9][0-9]*\)\{0,1\} @@.*$/\1/p')
            if [ -z "$current_line" ]; then
                current_line=0
            fi
            return
            ;;
        '+'*)
            if [ "${diff_line#+++}" != "$diff_line" ]; then
                return
            fi

            text="${diff_line#+}"
            if printf '%s\n' "$text" | grep -Fq '# noqa: codex-gate'; then
                current_line=$((current_line + 1))
                return
            fi
            if printf '%s\n' "$text" | grep -Fq '// noqa: codex-gate'; then
                current_line=$((current_line + 1))
                return
            fi
            case "$current_file" in
                scripts/codex-gates/*)
                    current_line=$((current_line + 1))
                    return
                    ;;
            esac

            while IFS= read -r pattern; do
                [ -n "$pattern" ] || continue
                if printf '%s\n' "$text" | grep -Eq "$pattern"; then
                    printf '%s:%s: %s: %s\n' "${current_file:-<unknown>}" "$current_line" "$pattern" "$text" >&2
                    matches=$((matches + 1))
                fi
            done <<EOF
$patterns
EOF
            current_line=$((current_line + 1))
            return
            ;;
        ' '*)
            current_line=$((current_line + 1))
            return
            ;;
        '-'*)
            return
            ;;
        *)
            return
            ;;
    esac
}

if [ -n "$input_path" ]; then
    while IFS= read -r line || [ -n "$line" ]; do
        process_line "$line"
    done < "$input_path"
else
    while IFS= read -r line || [ -n "$line" ]; do
        process_line "$line"
    done
fi

if [ "$matches" -gt 0 ]; then
    printf 'forbidden-pattern gate failed: %s match(es)\n' "$matches"
    exit 1
fi

exit 0
