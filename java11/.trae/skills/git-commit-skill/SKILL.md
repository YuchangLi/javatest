---
name: "git-commit-skill"
description: "Analyzes git changes and generates commit messages starting with feat:. Invoke when user asks to commit code."
---

# Git Commit Skill

## Description
This skill analyzes the current git changes and generates appropriate commit messages starting with `feat:`.

## When to Invoke
- When the user asks to commit code
- When the user says "提交" or similar commit-related commands

## How It Works
1. Check git status and staged changes
2. Analyze the modified files and changes
3. Generate a descriptive commit message starting with `feat:`
4. Execute the git commit command

## Usage
Simply invoke this skill when the user wants to commit their code changes.
