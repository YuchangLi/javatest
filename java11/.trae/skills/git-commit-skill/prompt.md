# Git Commit Skill Instructions

## Task
Analyze git changes and generate commit messages following these guidelines:

1. Check git status and git diff --staged
2. Analyze what files were modified/added/deleted
3. Generate a concise and descriptive commit message starting with `feat:`
4. Execute git commit with the generated message

## Commit Message Guidelines
- Start with `feat:` (feature), `fix:`, `docs:`, `style:`, `refactor:`, `test:`, or `chore:` as appropriate
- Be descriptive about what was changed
- Keep it concise but meaningful
- Use English or Chinese based on the project's existing commit history

## Example Commit Messages
- `feat: add volatile visibility and atomicity demos`
- `fix: resolve race condition in counter`
- `docs: update README with new features`

## Process
1. First run `git status` and `git diff --staged` to see what's changed
2. Analyze the changes
3. Generate an appropriate commit message
4. Commit using `git commit -m "..."`
