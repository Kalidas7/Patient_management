# Git Commit Message Conventions

Commit messages are a crucial part of project history. Writing clear and consistent commits makes collaboration, debugging, and automation easier.

---

## 📌 General Guidelines
1. **Use present tense** — "add feature" not "added feature".
2. **Keep subject line short** — ideally **≤ 50 characters**.
3. **Capitalize only the first word of the subject**.
4. **No period (.)** at the end of the subject line.
5. **Use the body** (optional) to explain *why* and *how*, not just what.
6. Separate **subject** from **body** with a blank line.

---

## 🔥 Conventional Commits Format

<type>[optional scope]: <short description>

[optional body]

[optional footer(s)]

markdown
Copy code

---

### ✅ Types

- **feat:** A new feature
  `feat(auth): add login API`

- **fix:** A bug fix
  `fix(ui): resolve navbar flicker on scroll`

- **docs:** Documentation only changes
  `docs(readme): update setup instructions`

- **style:** Code style (formatting, whitespace, missing semicolons, etc.), no logic changes
  `style: apply prettier formatting`

- **refactor:** Code changes that neither fix a bug nor add a feature
  `refactor(db): simplify query builder`

- **perf:** Performance improvement
  `perf(api): reduce response time for search`

- **test:** Adding or updating tests
  `test(user): add validation unit tests`

- **chore:** Changes to build process, dependencies, or tooling
  `chore(deps): update express to v5`

---

## 📝 Examples

### Simple one-liner
fix: handle null user input in login form

shell
Copy code

### With scope
feat(cart): add ability to apply discount codes

shell
Copy code

### With body
refactor(auth): simplify token validation

Replaced nested conditionals with guard clauses for readability.
This does not affect functionality but improves maintainability.

shell
Copy code

### With footer (breaking change + issue reference)
feat(api): add support for bulk user upload

BREAKING CHANGE: The user upload endpoint now requires a CSV file
instead of JSON.

Closes #123

yaml
Copy code

---

## 🌱 Best Practices
- One commit = one logical change.
- Don’t commit unrelated changes together.
- Use **draft commits (WIP)** locally, but squash or clean before merging.
- Agree on a convention with your team (Conventional Commits is widely used).
- Use commit linting tools like [commitlint](https://commitlint.js.org/) for consistency.

---