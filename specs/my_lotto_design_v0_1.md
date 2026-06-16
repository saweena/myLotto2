# MyLotto App — AI Migration Requirements

## Purpose

A native Android lottery result app rebuilt as an AI-first modernization case study.

The goal is to migrate a legacy Android XML-based Thai lottery app into a modern Jetpack Compose application using prompt-driven AI development. The app itself is intentionally small, but the engineering story is important: legacy XML screens, custom views, fragments, and RecyclerView layouts are used as references, then rebuilt into clean, modern Compose UI.

This project should demonstrate:

- Legacy Android XML to Jetpack Compose migration
- AI-assisted development workflow
- Modern Android UI structure
- Stateless composables and reusable components
- Mock-first local implementation
- Clean Git history showing prompt-driven development steps

## Version 0.1 Scope

Version 0.1 must implement **only 3 screens**.

The goal of Version 0.1 is to create a presentable portfolio milestone, not a complete production lottery app.

### Included Screens

1. Home Screen
2. Result Checking Screen
3. My Lottery Screen

### Data Scope

- Local mock data only
- No real API integration
- No database
- No Firebase
- No camera / QR integration
- No authentication
- Compose UI only

### Engineering Scope

- Kotlin
- Jetpack Compose
- Material3
- Single-module app
- In-memory mock repository
- Compose Navigation
- Reusable UI components
- Preview support

## Out of Scope for Version 0.1

The following screens and features must **not** be implemented in Version 0.1:

- Previous Results Screen
- Trending Screen
- QR Code Scanner Screen
- Real lottery API integration
- Room database
- Firebase
- Camera permission
- Push notifications
- Ads
- Authentication
- Complex animation
- Multi-module architecture

These can be added in later versions after the AI migration case study is presentable.

## Source Reference

The original project is a personal legacy Android project named `mylotto`.

The AI should treat the legacy XML files as **visual and structural references only**. Do not copy the XML implementation directly. Recreate the experience using idiomatic Jetpack Compose.

Legacy resources are stored under:

```text
specs/res/
```

Important legacy references:

| Legacy File | Usage |
|---|---|
| `fragment_home.xml` | Main screen reference |
| `fragment_show_lottery_result.xml` | Lottery result display reference |
| `fragment_my_lottery.xml` | Saved lottery numbers reference |
| `item_user_lottery.xml` | Saved lottery number item reference |
| `itemview_cardview_detail_lottery.xml` | Prize card reference |
| `item_recyclerview_digit.xml` | Historical result item reference |
| `item_trending_recyclerview.xml` | Future trending item reference |
| `res/values` | Colors, strings, styles reference |
| `res/font` | Legacy Thai font reference |

For Version 0.1, use only the references needed for:

- Home Screen
- Result Checking Screen
- My Lottery Screen
- Prize cards
- Saved lottery cards

## Product Concept

MyLotto helps users:

- Check Thai government lottery results
- Search a specific 6-digit lottery number
- View the latest draw result
- Save personal lottery numbers locally
- Recheck saved lottery numbers later

The modernized version should feel like a polished Thai lottery utility app, not a gambling app. The tone should be simple, trustworthy, clean, and mobile-first.

## What Is a Lottery Number

A lottery number is a 6-digit numeric string.

Rules:

- Must contain digits only
- Must be exactly 6 digits for full lottery checking
- Leading zeroes are allowed
- Saved lottery numbers are stored locally in memory for Version 0.1
- Duplicate saved numbers for the same draw date should be treated as already saved
- For Version 0.1, all data is mocked

Examples:

```text
123456
045789
999999
```

## What Is a Lottery Draw

A lottery draw represents one official Thai lottery result date.

Each draw contains:

- Draw date
- First prize
- Last 2 digits
- First 3 digits
- Last 3 digits
- Nearby first prize numbers
- Second prize numbers
- Third prize numbers
- Fourth prize numbers
- Fifth prize numbers

For Version 0.1, prize data must be mocked and deterministic.

## Screen 1 — Home Screen

The app's main screen. This screen combines lottery number search, draw-date display, latest result preview, and entry point to saved numbers.

### Purpose

The Home Screen allows the user to:

- Enter a 6-digit lottery number
- Select or view the current draw date
- View the latest lottery result
- Navigate to Result Checking Screen
- Navigate to My Lottery Screen

### Layout

- Full-screen background using app background color
- Top header section using primary green
- App title: `MyLotto`
- Subtitle or selected draw date
- Saved numbers icon button or text button leading to My Lottery Screen
- Lottery search section
- Latest lottery result section
- Scrollable prize card list
- Optional floating search button if it matches the legacy UI reference

### Lottery Search Section

Components:

- Rounded input field for a 6-digit lottery number
- Hint text: `กรอกเลข 6 หลัก`
- Search button
- Optional QR button placeholder is allowed visually, but must not navigate to a real QR screen in Version 0.1

Behavior:

- Accepts only numeric input
- Maximum length is 6
- Input text should be large and readable
- Search button is enabled only when the input is valid
- On search, navigate to Result Checking Screen

Validation messages:

- Empty input: `กรุณากรอกเลขสลาก`
- Less than 6 digits: `กรุณากรอกเลขให้ครบ 6 หลัก`
- Invalid characters: `กรุณากรอกเฉพาะตัวเลข`

### Latest Lottery Result Section

Display selected draw result using reusable prize cards.

Content:

- Section title: `ผลสลากกินแบ่งรัฐบาล`
- Draw date
- First prize card
- Last 2 digits card
- First 3 digits card
- Last 3 digits card
- Other prize groups

Visual priority:

1. First prize should be the most visually dominant
2. Last 2 digits should be prominent
3. First 3 and last 3 digits can be displayed side-by-side
4. Other prize groups can use standard cards

### Interactions

- Tap search button: validate input and navigate to Result Checking Screen
- Tap saved numbers button: navigate to My Lottery Screen
- Tap draw date selector: for Version 0.1, show mock date options or keep it static
- Tap latest prize cards: no action required for Version 0.1

### Empty State

If no latest result exists, show:

```text
ยังไม่มีผลสลาก
กรุณาเลือกงวดอื่นหรือลองใหม่ภายหลัง
```

The search field should remain visible.

## Screen 2 — Result Checking Screen

Shows whether the user's lottery number won any prize for the selected draw date.

### Purpose

The Result Checking Screen allows the user to:

- See the checked lottery number
- See the selected draw date
- Know whether the number won or did not win
- View matched prize details
- Save the checked number
- Return to Home Screen

### Layout

- Top app bar:
  - Back arrow
  - Title: `ตรวจผลสลาก`
- Result summary card:
  - User lottery number in large text
  - Draw date below
  - Result status badge
- Matched prize section
- Action buttons

### Result States

#### Won State

Show:

- Status badge: `ถูกรางวัล`
- Matched prize name
- Prize amount text
- Success color
- Celebration or check icon

Example message:

```text
ยินดีด้วย คุณถูกรางวัล
```

#### Not Won State

Show:

- Status badge: `ไม่ถูกรางวัล`
- Neutral message:

```text
เสียใจด้วย งวดนี้ยังไม่ถูกรางวัล
```

Secondary text:

```text
ลองตรวจเลขอื่น หรือบันทึกเลขไว้ตรวจครั้งถัดไป
```

#### Multiple Prize Match State

A lottery number may match more than one prize. If multiple matches exist, show all matched prizes in priority order.

### Action Buttons

Primary action:

```text
บันทึกเลขนี้
```

Secondary action:

```text
ตรวจเลขอื่น
```

### Interactions

- Tap back: return to Home Screen
- Tap save: save the number locally
- If already saved for the selected draw date, show `บันทึกไว้แล้ว`
- Tap check another: return to Home Screen and clear or focus input

### Edge Cases

- Invalid number should not reach this screen
- If draw data is unavailable, show generic error state
- If number is already saved, disable save button or update button text to `บันทึกไว้แล้ว`

## Screen 3 — My Lottery Screen

Shows saved lottery numbers.

### Purpose

The My Lottery Screen allows the user to:

- View saved lottery numbers
- See draw date and checking status
- Delete saved numbers
- Open Result Checking Screen for a saved number

### Layout

- Top app bar:
  - Back arrow
  - Title: `เลขของฉัน`
- Vertical saved number list
- Empty state

### Saved Lottery Card

Each saved lottery card contains:

- 6-digit lottery number
- Draw date
- Latest checking status
- Optional prize result
- Delete icon button

Status chip values:

- Pending: `รอตรวจผล`
- Won: `ถูกรางวัล`
- Not won: `ไม่ถูกรางวัล`

### Interactions

- Tap a card: navigate to Result Checking Screen for that saved number and draw date
- Tap delete: show delete confirmation dialog
- Tap back: return to Home Screen

### Delete Confirmation Dialog

Title:

```text
ลบเลขนี้?
```

Message:

```text
คุณต้องการลบเลขสลากนี้ออกจากรายการของคุณหรือไม่
```

Actions:

```text
ลบ
ยกเลิก
```

### Empty State

If there are no saved numbers, show:

```text
ยังไม่มีเลขที่บันทึก
บันทึกเลขสลากของคุณเพื่อตรวจผลภายหลัง
```

## Navigation

Version 0.1 destinations:

```text
home
result_checking
my_lottery
```

Navigation rules:

- Home -> Result Checking: tap search with valid 6-digit number
- Home -> My Lottery: tap saved numbers action
- Result Checking -> Home: tap back or tap `ตรวจเลขอื่น`
- Result Checking -> My Lottery: not required
- My Lottery -> Result Checking: tap saved lottery card
- My Lottery -> Home: tap back

Bottom navigation:

- Do not use bottom navigation in Version 0.1
- There are only 3 destinations, but the user flow is simple and does not need persistent bottom navigation

Transitions:

- Use standard Compose Navigation transitions
- No custom animations required

## Data Model

### LotteryDraw

```kotlin
data class LotteryDraw(
    val id: String,
    val drawDate: LocalDate,
    val firstPrize: String,
    val lastTwoDigits: String,
    val firstThreeDigits: List<String>,
    val lastThreeDigits: List<String>,
    val nearbyFirstPrize: List<String>,
    val secondPrize: List<String>,
    val thirdPrize: List<String>,
    val fourthPrize: List<String>,
    val fifthPrize: List<String>
)
```

### SavedLotteryNumber

```kotlin
data class SavedLotteryNumber(
    val id: String,
    val number: String,
    val drawDate: LocalDate,
    val createdAt: LocalDateTime,
    val status: LotteryCheckStatus
)
```

### LotteryCheckStatus

```kotlin
sealed interface LotteryCheckStatus {
    data object Pending : LotteryCheckStatus
    data object NotWon : LotteryCheckStatus
    data class Won(
        val prizes: List<MatchedPrize>
    ) : LotteryCheckStatus
}
```

### MatchedPrize

```kotlin
data class MatchedPrize(
    val prizeName: String,
    val prizeAmountText: String,
    val matchedNumber: String
)
```

## Prize Checking Logic

For Version 0.1, implement local checking against mock `LotteryDraw`.

Rules:

- If input number equals `firstPrize`, result includes first prize
- If last 2 digits match `lastTwoDigits`, result includes last 2 digits prize
- If first 3 digits match any value in `firstThreeDigits`, result includes first 3 digits prize
- If last 3 digits match any value in `lastThreeDigits`, result includes last 3 digits prize
- If input number is in `nearbyFirstPrize`, result includes nearby first prize
- If input number is in second/third/fourth/fifth prize lists, result includes corresponding prize
- A number may match more than one prize
- If no match, result = not won

Prize priority for display:

1. First prize
2. Nearby first prize
3. Second prize
4. Third prize
5. Fourth prize
6. Fifth prize
7. First 3 digits
8. Last 3 digits
9. Last 2 digits

## Design Direction

The app should be modern, clean, and more polished than the legacy XML version while preserving the original app's personality:

- Thai lottery utility app
- Green as the primary brand color
- White cards for readability
- Rounded surfaces
- Strong emphasis on lottery numbers
- High contrast for result checking

The UI should avoid looking like a betting, casino, or gambling app.

## Design System

### Colors

#### Backgrounds

| Token | Hex | Usage |
|---|---|---|
| Background | #F6F8F5 | Screen background |
| Surface | #FFFFFF | Cards, input fields, result containers |
| Surface Green | #E8F5E9 | Highlight sections |
| Surface Yellow | #FFF8E1 | Prize highlight sections |
| Overlay | #000000 | Dialog scrim |

#### Brand

| Token | Hex | Usage |
|---|---|---|
| Primary | #2E7D32 | Header, primary buttons, selected states |
| Primary Light | #60AD5E | Gradients, accents |
| Primary Dark | #005005 | Pressed states, strong emphasis |
| Secondary | #F9A825 | Prize highlight, winning state accent |

#### Semantic

| Token | Hex | Usage |
|---|---|---|
| Success | #2E7D32 | Won result, success chip |
| Warning | #F9A825 | Pending state, prize accent |
| Error | #D32F2F | Validation error, delete action |
| Info | #1976D2 | Informational chips |

#### Text

| Token | Hex | Usage |
|---|---|---|
| Text Primary | #1B1B1B | Main headings, lottery numbers |
| Text Secondary | #4F5B52 | Body text |
| Text Tertiary | #7A857D | Captions, secondary labels |
| Text On Primary | #FFFFFF | Text on green surfaces |
| Border | #DDE5DD | Card borders, input borders |

### Typography

Use Android system fonts by default for MVP. Do not require custom fonts.

If using the legacy Mitr font from `specs/res/font`, apply it consistently and ensure Thai text is not clipped.

| Style | Weight | Size | Usage |
|---|---:|---:|---|
| Display | Bold | 32sp | Main lottery number |
| Heading | Bold | 24sp | Screen title |
| Title | SemiBold | 18sp | Card title |
| Body | Medium | 14sp | General content |
| Caption | Regular | 12sp | Labels, draw dates |
| Button | Bold | 16sp | CTA text |

Thai text must be readable and not clipped. Avoid fixed heights for Thai labels.

### Shape

| Token | Value | Usage |
|---|---:|---|
| Small | 8dp | Chips, small badges |
| Medium | 14dp | Cards |
| Large | 20dp | Header cards, primary result cards |
| Full | 999dp | Pills, chips |

### Spacing

| Token | Value |
|---|---:|
| xxs | 4dp |
| xs | 8dp |
| sm | 12dp |
| md | 16dp |
| lg | 24dp |
| xl | 32dp |

## Reusable Components

### LottoTopBar

Used on Result Checking and My Lottery screens.

Props:

```kotlin
title: String
showBack: Boolean
onBackClick: () -> Unit
```

### LotterySearchBar

Used on Home Screen.

Props:

```kotlin
value: String
onValueChange: (String) -> Unit
onSearchClick: () -> Unit
errorText: String?
```

Behavior:

- Only allow numeric text
- Limit to 6 digits
- Center-align input number
- Search button enabled only when valid

### PrizeCard

Reusable card for each prize group.

Props:

```kotlin
title: String
prizeAmountText: String?
numbers: List<String>
emphasized: Boolean
```

Rules:

- First prize uses emphasized style
- Last 2 digits uses compact emphasized style
- Multiple numbers should wrap nicely
- Large numbers should be highly readable

### CheckResultCard

Used on Result Checking Screen.

Props:

```kotlin
number: String
drawDate: LocalDate
status: LotteryCheckStatus
```

Rules:

- Won state uses success and secondary colors
- Not won state uses neutral colors
- Pending state uses warning color
- Do not rely only on color; also show text status

### SavedLotteryCard

Used on My Lottery Screen.

Props:

```kotlin
savedNumber: SavedLotteryNumber
onClick: () -> Unit
onDeleteClick: () -> Unit
```

Rules:

- Entire card is clickable
- Delete button has separate click action
- Touch targets must be at least 48dp

## Mock Data

Use deterministic mock data so previews are stable.

### Example Draw

```kotlin
LotteryDraw(
    id = "2026-06-16",
    drawDate = LocalDate.of(2026, 6, 16),
    firstPrize = "123456",
    lastTwoDigits = "56",
    firstThreeDigits = listOf("123", "789"),
    lastThreeDigits = listOf("456", "999"),
    nearbyFirstPrize = listOf("123455", "123457"),
    secondPrize = listOf("234567", "345678", "456789"),
    thirdPrize = listOf("111111", "222222", "333333"),
    fourthPrize = listOf("444444", "555555", "666666"),
    fifthPrize = listOf("777777", "888888", "999999")
)
```

### Saved Numbers

```text
123456 — Won
045789 — Pending
888888 — Not won
```

## Empty, Loading, and Error States

### Loading

Use simple centered loading indicator with text:

```text
กำลังโหลดข้อมูล...
```

### Generic Error

Show:

```text
เกิดข้อผิดพลาด
กรุณาลองใหม่อีกครั้ง
```

CTA:

```text
ลองใหม่
```

### No Result Data

Show:

```text
ยังไม่มีผลสลาก
กรุณาเลือกงวดอื่นหรือลองใหม่ภายหลัง
```

### No Saved Numbers

Show:

```text
ยังไม่มีเลขที่บันทึก
บันทึกเลขสลากของคุณเพื่อตรวจผลภายหลัง
```

## Validation

### Lottery Number Input

Rules:

- Required
- Digits only
- Exactly 6 digits

Error messages:

- Empty: `กรุณากรอกเลขสลาก`
- Too short: `กรุณากรอกเลขให้ครบ 6 หลัก`
- Invalid characters: `กรุณากรอกเฉพาะตัวเลข`

### Saved Number

Rules:

- Cannot save empty number
- Cannot save invalid number
- Duplicate same number and same draw date should show existing saved state

## Accessibility

- All icon buttons must have content descriptions
- Search button: `ค้นหาเลขสลาก`
- Back button: `ย้อนกลับ`
- Delete button: `ลบเลขสลาก`
- Saved numbers button: `เลขของฉัน`
- Prize cards should expose title and numbers as readable text
- Do not rely on color alone to indicate won / not won status
- Touch targets should be at least 48dp
- Thai text must be readable with system font scaling

## Compose Implementation Rules

AI must follow these rules:

- Use Kotlin and Jetpack Compose
- Use Material3
- UI should be stateless where possible
- Screen-level composables receive state and event lambdas
- No XML
- No DataBinding
- No ViewBinding
- No Fragment
- No real API for Version 0.1
- No Firebase for Version 0.1
- No camera permission for Version 0.1
- Use mock repository or in-memory data
- Create `@Preview` for all major screens and reusable components
- Use `LazyColumn` for vertical lists
- Use `FlowRow` or wrapping layout for prize numbers if needed
- Use `rememberSaveable` for local input state if no ViewModel exists yet
- Avoid hardcoded magic values in screen files; move colors and dimensions to theme where practical

## Suggested Package Structure

```text
com.saweena.mylotto
├── MainActivity.kt
├── navigation
│   ├── MyLottoNavHost.kt
│   └── MyLottoDestination.kt
├── data
│   ├── LotteryMockData.kt
│   └── LotteryRepository.kt
├── model
│   ├── LotteryDraw.kt
│   ├── SavedLotteryNumber.kt
│   ├── LotteryCheckStatus.kt
│   └── MatchedPrize.kt
├── ui
│   ├── theme
│   │   ├── Color.kt
│   │   ├── Theme.kt
│   │   └── Type.kt
│   ├── component
│   │   ├── LottoTopBar.kt
│   │   ├── LotterySearchBar.kt
│   │   ├── PrizeCard.kt
│   │   ├── CheckResultCard.kt
│   │   └── SavedLotteryCard.kt
│   ├── home
│   │   └── HomeScreen.kt
│   ├── result
│   │   └── ResultCheckingScreen.kt
│   └── saved
│       └── MyLotteryScreen.kt
└── util
    └── LotteryChecker.kt
```

## MVP Acceptance Criteria

Version 0.1 is complete when:

- Home Screen is implemented in Compose
- Result Checking Screen is implemented in Compose
- My Lottery Screen is implemented in Compose
- Navigation between all 3 screens works
- User can enter a 6-digit number on Home Screen
- User can search and navigate to Result Checking Screen
- Result Checking Screen shows won / not won using mock data
- User can save a checked number
- My Lottery Screen displays saved mock or in-memory numbers
- User can delete a saved number with confirmation dialog
- Latest lottery result is displayed using reusable `PrizeCard`
- `LotterySearchBar` component exists
- `SavedLotteryCard` component exists
- All major components have Compose previews
- Design system is centralized
- README explains this as an AI-assisted legacy modernization project
- Prompt history is stored in `/prompts`

## AI Workflow Requirements

This repository should document AI-assisted development.

Recommended folders:

```text
/docs
  my_lotto_design.md
  migration-plan.md
  ai-lessons-learned.md

/prompts
  001_analyze_legacy_xml.md
  002_generate_compose_project.md
  003_generate_design_system.md
  004_generate_home_screen.md
  005_generate_prize_card.md
  006_generate_result_checking_screen.md
  007_generate_my_lottery_screen.md
```

Each prompt file should include:

- Goal
- Context
- Input files or screenshots
- Rules
- Expected output
- Commit message used after applying the output

Recommended commit sequence:

```text
chore: initialize AI-first migration workflow
docs: add MyLotto product and design requirements
docs: analyze legacy XML screens
feat: scaffold Compose project via AI
feat: generate Compose design system from legacy resources
feat: generate Home screen from XML reference
feat: create reusable lottery prize card
feat: generate result checking screen
feat: add mock lottery data and checker
feat: generate saved lottery screen
docs: document AI migration findings
```

## Future Improvements

Potential next phases after Version 0.1:

- Previous Results Screen
- Trending Screen
- QR code placeholder screen
- Real QR code scanning
- Room database for saved lottery numbers
- Real lottery result API
- Widgets
- Notification when new draw result is available
- Dark theme
- UI tests
- GitHub Actions CI
- AI PR review workflow
- Before / after XML vs Compose documentation
