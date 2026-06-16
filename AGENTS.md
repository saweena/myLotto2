# MyLotto Compose AI Migration

Single-module Android application built with Kotlin and Jetpack Compose.

This repository is an AI-assisted modernization project.

The goal is to migrate a legacy Android XML application into a modern Compose application while maintaining visual consistency with the original application.

---

# Source of Truth

Before implementing any feature, always read:

```text
specs/my_lotto_design_v0_1.md
```

This file defines:

* Product requirements
* Screen behavior
* Navigation
* Design system
* MVP scope
* Acceptance criteria

If there is any conflict between files, follow:

```text
specs/my_lotto_design_v0_1.md
```

---

# Legacy Resources

Legacy Android resources are located in:

```text
specs/res/
```

Important folders:

```text
specs/res/layout
specs/res/drawable
specs/res/font
specs/res/values
```

These resources are references only.

Use them to understand:

* Layout structure
* Visual hierarchy
* Typography
* Colors
* Reusable components

Do NOT:

* Copy XML implementation directly
* Recreate Fragment architecture
* Recreate ViewBinding
* Recreate DataBinding
* Recreate legacy patterns

The target architecture is modern Compose.

---

# MVP Scope

Version 0.1 includes ONLY:

1. Home Screen
2. Result Checking Screen
3. My Lottery Screen

Do not implement:

* Trending Screen
* Previous Results Screen
* QR Scanner
* Real API Integration
* Room Database
* Firebase
* Authentication
* Ads
* Widgets
* Notifications

Unless explicitly requested.

---

# Architecture Style

Use:

```text
MVVM + Lightweight Clean Architecture
```

The goal is simplicity and readability.

Do not introduce enterprise-level complexity.

---

# Presentation Layer Rules

Use:

* Jetpack Compose
* Material3
* ViewModel
* StateFlow
* Immutable UiState

Screen composables should be stateless where possible.

Recommended pattern:

```kotlin
HomeScreen(
    uiState = state,
    onAction = viewModel::onAction
)
```

ViewModels:

* Own screen state
* Expose StateFlow
* Handle user actions
* Do not contain UI code

Avoid:

* Full MVI reducer architecture
* Orbit
* Mavericks
* Redux
* Event buses

Unless explicitly requested.

---

# Domain Layer Rules

Business logic must not live inside composables.

Move business logic into:

```text
domain/
util/
```

Examples:

```text
LotteryChecker
LotteryPrizeMatcher
```

Domain logic should be pure and testable.

---

# Data Layer Rules

Version 0.1 uses:

```text
Mock Repository
In-memory Data Source
```

Do NOT create:

* Retrofit
* API Services
* Room
* Firebase
* SQL Database

Unless explicitly requested.

---

# Package Structure

Use:

```text
com.saweena.mylotto

├── data
├── domain
├── model
├── navigation
├── ui
│   ├── component
│   ├── home
│   ├── result
│   ├── saved
│   └── theme
└── util
```

Keep package structure simple.

Do not create feature modules.

This project is intentionally single-module.

---

# UI Rules

When implementing UI:

1. Read only the relevant XML files needed for the current task.
2. Read only the relevant resources needed for the current task.
3. Rebuild using Compose idioms.
4. Preserve the overall visual identity of the original app.
5. Prefer simplicity over pixel-perfect XML replication.

Visual similarity is important.

Architectural similarity is not.

---

# Compose Rules

Use:

* Material3
* Navigation Compose
* LazyColumn
* rememberSaveable
* @Preview

Prefer:

* State hoisting
* Reusable composables
* Small composables
* Immutable state

Avoid:

* Massive composables
* Business logic inside composables
* Hardcoded strings
* Hardcoded colors

---

# Font Rules

Fonts are located in:

```text
specs/res/font
```

Current font family:

```text
mitr_regular.ttf
mitr_medium.ttf
mitr_semibold.ttf
```

Use Mitr as the primary font if custom typography is implemented.

If custom typography is not yet implemented, use Material3 defaults.

---

# Navigation Rules

Use:

```text
Navigation Compose
```

Destinations:

```text
home
result_checking
my_lottery
```

Do not add additional destinations unless explicitly requested.

---

# Development Strategy

Work incrementally.

Do not generate the entire application in one task.

Preferred order:

1. Theme
2. Navigation
3. Reusable Components
4. Home Screen
5. Result Checking Screen
6. My Lottery Screen
7. Mock Repository
8. Refinement

Every task should be independently reviewable.

---

# Git Rules

Prefer small commits.

Good examples:

```text
docs: add migration plan

feat: create compose theme

feat: create lottery search bar

feat: create prize card

feat: implement home screen

feat: implement result checking screen

feat: implement my lottery screen
```

Avoid:

```text
feat: build complete application
```

---

# Scope Control

When scope is ambiguous:

* Implement the simplest solution
* Do not assume extra requirements
* Do not add future features
* Do not expand beyond Version 0.1

If unsure:

Choose the smaller implementation.

---

# Performance Expectations

This project is a portfolio and modernization case study.

Priorities:

1. Readability
2. Architecture clarity
3. Compose best practices
4. Reusable components
5. Maintainability

Not priorities:

* Extreme optimization
* Enterprise complexity
* Premature abstraction

---

# Primary Goal

The goal of this repository is NOT to build the most complete lottery application.

The goal is to demonstrate:

* Legacy Android modernization
* XML → Compose migration
* AI-assisted development workflow
* Clean architecture decisions
* Incremental Git history
* Modern Android development practices
