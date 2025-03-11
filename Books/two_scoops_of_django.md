# Two Scoops of Django 3.x

## ACID Principles for Databases

Not all non-relational databases are ACID compliant. ACID principles ensure data integrity:

- **Atomicity:** Transactions either fully complete or fully fail — no partial completion. This prevents data corruption.
- **Consistency:** Transactions maintain data in a valid state throughout. This prevents data corruption.
- **Isolation:** Concurrent transactions operate independently, avoiding conflicts or data leaks. This prevents data corruption.
- **Durability:** Once committed, a transaction is permanent, even after a database crash.

---

## Git Tags

- **Git Tags:** Create a snapshot of your code at the time of release to PyPI for better tracking and versioning.

---

## Best Practices for Testing

### Unit Tests
- **Focus Narrowly:** Each unit test should assert the behavior of a **single** view, model, form, or method.
- **Use Mocks:** Mocks prevent unit tests from interacting with external dependencies like APIs, emails, or webhooks. (Ref: 24.3.8)

### Importance of Documented Tests
- Undocumented test code can make projects **impossible to maintain and test**.

### Integration Tests
- Combine and test individual modules as a group **after unit tests are complete**.
- Integration tests are **slower** than unit tests but essential for validating system-wide functionality.

---

## Why Tests Matter

Skipping tests may seem faster, but consider the impact when upgrading:

- With tests: Upgrade the environment → Run tests → Fix bugs (if any) → Done.
- Without tests: You'll need to manually validate every possible scenario — a time-consuming and error-prone process.

**Having tests** gives you the confidence to make changes and upgrades without risking a buggy experience for users.
