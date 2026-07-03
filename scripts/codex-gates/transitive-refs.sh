#!/usr/bin/env bash
set -euo pipefail

# Codex-seam gate #2: transitive-references scan for deletion tickets.
#
# Takes a list of file paths (one per arg) that the ticket plans to
# delete. For each, derive the basename and grep the repo for
# references. Emit the union of referencing files (deduped, excluding
# the targets themselves) to stdout, one path per line. Exits 0 always.
#
# Ported verbatim from gembaflow-site/scripts/codex-gates/transitive-refs.sh
# with the grep glob list adjusted for this stack (FTC SDK / Java /
# Gradle). Original Next.js/TypeScript globs (.ts / .tsx / .mdx / .css,
# app/ components/ lib/) replaced with Java/Gradle extensions and this
# repo's source layout. One behavioral adaptation for Java: the search
# term is the basename with its extension stripped, because Java code
# references classes by bare name (`RobotHardware robot = ...`), never
# by filename — searching for `RobotHardware.java` would miss every
# real reference.

if [ "$#" -eq 0 ]; then
    exit 0
fi

tmp_refs="$(mktemp "${TMPDIR:-/tmp}/codex-transitive-refs.XXXXXX")"
trap 'rm -f "$tmp_refs"' EXIT

for target in "$@"; do
    base="$(basename "$target")"
    base="${base%.*}"

    grep -rln "$base" \
        --include='*.java' \
        --include='*.gradle' \
        --include='*.xml' \
        --include='*.md' \
        --include='*.sh' \
        --include='*.yml' \
        --include='*.yaml' \
        TeamCode/ docs/ scripts/ .claude/ 2>/dev/null >> "$tmp_refs" || true
done

if [ -s "$tmp_refs" ]; then
    sort -u "$tmp_refs" | while IFS= read -r ref_path; do
        skip=0
        for target in "$@"; do
            if [ "$ref_path" = "$target" ]; then
                skip=1
                break
            fi
        done
        if [ "$skip" -eq 0 ]; then
            printf '%s\n' "$ref_path"
        fi
    done
fi

exit 0
