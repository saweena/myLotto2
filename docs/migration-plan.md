# MyLotto Legacy-to-Compose Migration Plan

## Document Purpose

This document analyzes the legacy Android XML resources under `specs/` and converts them into a Compose-first migration plan for the modern `myLotto2` app.

This is a documentation-only artifact. No application code is proposed here.

## Inputs Reviewed

- `AGENTS.md`
- `specs/my_lotto_design_v0_1.md`
- Legacy XML resources under `specs/res/layout`
- Legacy navigation under `specs/res/navigation/nav_graph.xml`
- Legacy menu under `specs/res/menu/bottom_nav_menu.xml`
- Legacy design tokens under `specs/res/values`
- Legacy drawable and font resources under `specs/res/drawable*` and `specs/res/font`

## Assumptions

- `fragment_won_lottery.xml` appears to be the visual reference for the legacy checking result flow because `nav_graph.xml` references `CheckingResultFragment` but there is no separate `fragment_checking_result.xml`.
- `fragment_show_lottery_result.xml` is not a standalone destination in navigation; it acts as an embedded result section inside the Home screen.

## Legacy Screen Inventory

The legacy project contains the following user-facing screens or screen-like containers.

| Legacy Resource | Legacy Role | Version 0.1 | Compose Direction |
|---|---|---:|---|
| `activity_main.xml` | App shell with `NavHostFragment` and bottom navigation | No direct 1:1 migration | Replace with a single Compose `NavHost` container and no bottom navigation |
| `fragment_home.xml` | Main entry screen with number input, QR shortcut, draw selector, embedded latest results, FAB | Yes | Compose `HomeScreen` |
| `fragment_show_lottery_result.xml` | Embedded latest result content used inside Home | Yes, as part of Home | Compose `LatestResultSection` inside `HomeScreen` |
| `fragment_won_lottery.xml` | Checking result / won-or-not result details | Yes | Compose `ResultCheckingScreen` |
| `fragment_my_lottery.xml` | Saved lottery numbers list | Yes | Compose `MyLotteryScreen` |
| `fragment_previous_result.xml` | Historical results list | No | Future `PreviousResultsScreen` |
| `fragment_trending.xml` | Trending/tip content list | No | Future `TrendingScreen` |
| `fragment_qr_code.xml` | QR scanner screen | No | Future `QrScannerScreen`; keep only visual placeholder in v0.1 |
| `fragment_multi_checking__next_draw_checking.xml` | Bulk input / next-draw checking flow | No | Future `BatchCheckScreen` or split into separate feature flows |

## Version 0.1 Screen Scope

Version 0.1 should include only these three top-level screens:

1. Home Screen
2. Result Checking Screen
3. My Lottery Screen

Supporting v0.1 content:

- Latest lottery results section on Home
- Reusable prize cards
- Reusable saved lottery cards
- Mock draw selector behavior

Explicitly out of scope for Version 0.1:

- Previous Results
- Trending
- QR Scanner
- Batch / multi-number checking
- Ads
- Firebase
- Real APIs
- Database persistence
- Bottom navigation

## Screen-by-Screen Analysis

### 1. App Shell

Legacy references:

- `activity_main.xml`
- `bottom_nav_menu.xml`
- `nav_graph.xml`

Legacy behavior:

- Hosts fragments inside a `NavHostFragment`
- Uses persistent bottom navigation with four items
- Navigation structure includes several non-MVP flows

Migration plan:

- Do not recreate the bottom navigation in v0.1
- Use one Compose `NavHost` with three destinations:
  - `home`
  - `result_checking`
  - `my_lottery`
- Treat the old bottom-navigation model as superseded by a simpler linear flow

### 2. Home Screen

Legacy references:

- `fragment_home.xml`
- `fragment_show_lottery_result.xml`
- `row_spinner.xml`
- `row_spinners_dropdown.xml`
- `itemview_cardview_detail_lottery.xml`

Legacy structure:

- Green rounded header area
- Large centered lottery number input
- Search icon button inside the search row
- QR icon shortcut
- Draw selector spinner
- Scrollable embedded result list below the header
- Floating action button repeating the search intent

Observed UI responsibilities:

- Accept 6-digit input
- Let the user select a draw
- Show latest draw results
- Provide entry points to checking flow
- Provide a visual route to QR, though QR is out of v0.1

Migration plan:

- Merge `fragment_home.xml` and `fragment_show_lottery_result.xml` into one Compose screen
- Remove the floating action button unless it adds clear value beyond the inline search action
- Keep the QR affordance as a disabled or no-op visual placeholder only if needed for visual continuity
- Replace spinner UI with a Compose dropdown menu, exposed menu, or simple segmented mock selector

Recommended Compose sections:

- `HomeHeader`
- `LotterySearchBar`
- `DrawDateSelector`
- `LatestResultSection`
- `PrizeCardGrid` or stacked `PrizeCard` list

### 3. Result Checking Screen

Legacy references:

- `fragment_won_lottery.xml`
- `itemview_cardview_detail_lottery.xml`

Legacy structure:

- Large draw date label
- Dominant lottery number card
- Result text
- Optional quantity input for prize total calculation
- Delete icon
- Ad banner at the bottom

Observed UI responsibilities:

- Show checked number
- Show date
- Show whether the number won
- Show matched prize detail
- Possibly calculate amount by ticket count
- Support delete in a saved-number context

Migration plan:

- Use this layout as the legacy visual reference for `ResultCheckingScreen`
- Remove ad banner
- Replace delete icon with explicit actions aligned to v0.1 behavior
- Treat quantity input as optional and likely defer unless the user wants payout calculation in v0.1
- Use a state-based result card:
  - Won
  - Not won
  - Pending or unavailable

Recommended Compose sections:

- `LottoTopBar`
- `CheckResultCard`
- `MatchedPrizeList`
- `PrimaryActionRow`

### 4. My Lottery Screen

Legacy references:

- `fragment_my_lottery.xml`
- `item_user_lottery.xml`

Legacy structure:

- Header text
- RecyclerView list of saved numbers
- Ad banner

Observed UI responsibilities:

- Show saved lottery numbers
- Tap item to re-open result details
- Show at least the number and ticket amount

Migration plan:

- Remove ad banner
- Upgrade each saved item to carry more state than the legacy card
- Align with the product spec by showing:
  - Number
  - Draw date
  - Status chip
  - Optional prize summary
  - Delete action

Recommended Compose sections:

- `LottoTopBar`
- `SavedLotteryList`
- `SavedLotteryCard`
- `EmptySavedState`

### 5. Previous Results Screen

Legacy references:

- `fragment_previous_result.xml`
- `item_recyclerview_digit.xml`

Legacy structure:

- Header
- Historical result cards in a list
- Ad banner

Migration status:

- Excluded from Version 0.1
- Keep as a future feature source

Compose direction:

- Future `LazyColumn` of `HistoricalResultCard`

### 6. Trending Screen

Legacy references:

- `fragment_trending.xml`
- `item_trending_recyclerview.xml`

Legacy structure:

- Banner ad
- Large image-based list using `PhotoView`

Migration status:

- Excluded from Version 0.1

Compose direction:

- Future content feed with zoomable image support only if the feature remains valuable

### 7. QR Scanner Screen

Legacy references:

- `fragment_qr_code.xml`

Legacy structure:

- Full-screen scanner surface with camera framing UI

Migration status:

- Excluded from Version 0.1

Compose direction:

- Future dedicated screen backed by CameraX / ML Kit or equivalent scanner support

### 8. Multi-Checking / Next Draw Screen

Legacy references:

- `fragment_multi_checking__next_draw_checking.xml`

Legacy structure:

- Draw spinner
- Five repeated number inputs
- Per-row won / not-won messages
- Save, restore, clear, and check actions

Migration status:

- Excluded from Version 0.1

Compose direction:

- Future split into:
  - `BatchCheckScreen`
  - Optional `SavedBatchNumbers` workflow

## Reusable UI Components Found in Legacy Resources

These are the most reusable building blocks to carry forward conceptually into Compose.

| Legacy Resource | Reusable Concept | Compose Equivalent |
|---|---|---|
| `itemview_cardview_detail_lottery.xml` | Prize presentation card with title, subtitle, and result number area | `PrizeCard` |
| `item_user_lottery.xml` | Saved lottery chip/card | `SavedLotteryCard` |
| `item_recyclerview_digit.xml` | Historical draw summary card | `HistoricalResultCard` |
| `row_spinner.xml` | Selected draw presentation | `SelectedDrawField` |
| `row_spinners_dropdown.xml` | Dropdown row item | `DrawDateMenuItem` |
| `header_background.xml` | Rounded green header block | `HeaderSurface` |
| `white_rectangle_background.xml` | Rounded white input/surface background | `RoundedSurface` or `OutlinedInputSurface` |
| `search_icon_background.xml` | Green rounded icon container | `IconActionContainer` |
| `cardview_show_detail.xml` | Green rounded prize surface | `PrizeCardContainerStyle` |

## Compose Equivalents for Legacy XML Implementations

### Layout and Navigation

| Legacy XML Pattern | Compose Equivalent |
|---|---|
| `Fragment` + `NavHostFragment` | Navigation Compose `NavHost` |
| Nested fragment inside Home | Plain composable section inside the screen |
| `RecyclerView` | `LazyColumn` or `LazyVerticalGrid` |
| `ScrollView` + vertical content | `LazyColumn` when list-like, `Column` with `verticalScroll()` when small |
| `Spinner` | `ExposedDropdownMenuBox`, `DropdownMenu`, or modal selection sheet |
| `BottomNavigationView` | Omit for v0.1 |
| `FloatingActionButton` for duplicate search action | Inline CTA button or omit |
| `CardView` | `Card`, `ElevatedCard`, or `Surface` |
| `ConstraintLayout` screen composition | `Column`, `Row`, `Box`, and occasional Compose `ConstraintLayout` only if truly needed |

### Input and Feedback

| Legacy XML Pattern | Compose Equivalent |
|---|---|
| `EditText` numeric input | `OutlinedTextField` or custom `TextField` with digit filtering |
| `ImageView` acting as button | `IconButton` |
| Hidden/visible `TextView` result states | State-driven conditional composables |
| Inline delete icon | `IconButton` with confirmation dialog |
| Hardcoded status text | `AssistChip`, `Badge`, or labeled text row driven by state |

### Visual Components

| Legacy XML Pattern | Compose Equivalent |
|---|---|
| Custom `LotteryCardView` | Reusable Compose `PrizeCard` |
| Rounded shape drawables | `RoundedCornerShape` with `Surface` |
| Background layer-list splash | Compose splash theme or Android splash API using the same assets |
| Fixed width/height cards | Adaptive layouts with min sizes and content-driven height |

## Color Palette Analysis

There are two color sources:

1. Legacy XML values in `specs/res/values/colors.xml`
2. Newer design-system tokens defined in `specs/my_lotto_design_v0_1.md`

### Legacy XML Palette

| Token | Hex | Notes |
|---|---|---|
| `green_200` | `#66BB6A` | Main legacy green used for header, cards, and emphasis |
| `green_100` | `#FFC8E6C9` | Light green used for accent surfaces and icon backgrounds |
| `accent` | `#EDECEC` | Legacy page background |
| `background` | `#A5D6A7` | Alternate green-toned background |
| `white` | `#FFFFFFFF` | Surfaces and text on green |
| `black` | `#FF000000` | Text and controls |
| `red` | `#F80000` | Negative result / warning |
| `pink_100` | `#F8BBD0` | Not central to current migration |

### Version 0.1 Design-System Palette

The spec introduces a more polished palette and should be treated as the target direction:

| Token | Hex | Usage |
|---|---|---|
| Background | `#F6F8F5` | Screen background |
| Surface | `#FFFFFF` | Cards and fields |
| Surface Green | `#E8F5E9` | Soft highlighted sections |
| Surface Yellow | `#FFF8E1` | Prize highlight |
| Primary | `#2E7D32` | Primary brand green |
| Primary Light | `#60AD5E` | Supporting accents |
| Primary Dark | `#005005` | Strong emphasis |
| Secondary | `#F9A825` | Winning accent |
| Success | `#2E7D32` | Won state |
| Warning | `#F9A825` | Pending state |
| Error | `#D32F2F` | Validation and destructive actions |
| Info | `#1976D2` | Informational chips |
| Text Primary | `#1B1B1B` | Main text |
| Text Secondary | `#4F5B52` | Supporting text |
| Text Tertiary | `#7A857D` | Captions |
| Border | `#DDE5DD` | Outlines |

### Palette Recommendation

- Use the spec palette as the Compose target system
- Preserve the legacy green identity, but modernize it from `#66BB6A` toward the stronger `#2E7D32`
- Keep white and soft green surfaces to retain the utility-app feel
- Use yellow only as a prize accent, not as a dominant brand color

## Typography Analysis

### Legacy Typography

Legacy XML styles and layouts suggest:

- Header sizes around `30sp`
- Prize emphasis around `25sp`
- Search input around `25sp`
- Result number around `40sp`
- Supporting text around `18sp` to `23sp`
- Legacy theme font family set to `@font/mitr_regular`

Available font assets:

- `specs/res/font/mitr_regular.ttf`
- `specs/res/font/mitr_medium.ttf`
- `specs/res/font/mitr_semibold.ttf`

### Version 0.1 Typography Target

The product spec prefers:

- System fonts by default for MVP
- Mitr only if applied consistently and Thai text remains unclipped

Recommended decision:

- Start with system font for implementation speed and predictable Thai rendering
- Keep Mitr as an optional enhancement pass after the first Compose milestone is stable

Recommended type scale from spec:

| Style | Weight | Size | Primary Usage |
|---|---:|---:|---|
| Display | Bold | `32sp` | Main lottery number |
| Heading | Bold | `24sp` | Screen title |
| Title | SemiBold | `18sp` | Card titles |
| Body | Medium | `14sp` | General content |
| Caption | Regular | `12sp` | Labels and dates |
| Button | Bold | `16sp` | CTA text |

## Assets and Visual References Worth Reusing

Useful assets or motifs from legacy resources:

- `icon_small_v1.png`: small brand mark used near draw selection
- `logo_v1.png`: branding reference
- `splashv1.png` and `splash_image.xml`: splash reference
- `ic_search.xml`, `ic_mylotto.xml`, `ic_delete.xml`, `ic_check.xml`, `ic_not_won.xml`: reusable icon concepts
- `header_background.xml`: defines the rounded-bottom green header signature

Assets to avoid carrying into v0.1:

- AdView placements
- QR scanner behavior
- Trending content imagery
- Legacy bottom navigation icons as a navigation pattern

## Legacy-to-Compose Migration Strategy

### Phase 1. Establish Visual Foundations

- Create the Compose theme from the v0.1 spec palette
- Decide whether MVP typography uses system font only or a later Mitr pass
- Rebuild legacy rounded surface styles as Compose shapes and color tokens

### Phase 2. Build Reusable Components

- `LottoTopBar`
- `LotterySearchBar`
- `PrizeCard`
- `CheckResultCard`
- `SavedLotteryCard`
- `StatusChip`
- `DrawDateSelector`

This phase should replace the legacy XML custom-view mentality with a small Compose component system.

### Phase 3. Implement v0.1 Screens

1. Home
   - Search input
   - Draw selector
   - Latest result section
   - Navigation to result checking and saved numbers
2. Result Checking
   - Number summary
   - Draw date
   - Match evaluation states
   - Save action
3. My Lottery
   - Saved list
   - Empty state
   - Delete confirmation
   - Re-open result details

### Phase 4. Validation and UX Hardening

- Input validation for 6-digit numbers
- Duplicate save prevention by number + draw date
- Stable mock data for previews and testing
- Accessibility pass for Thai text, icon descriptions, and touch targets

### Phase 5. Future Backlog

- Previous results
- Trending
- QR scanner
- Batch checking
- Persistence
- Real API integration

## Recommended Screen Mapping for Implementation

| Compose Screen | Legacy Inputs | Notes |
|---|---|---|
| `HomeScreen` | `fragment_home.xml`, `fragment_show_lottery_result.xml`, spinner rows, prize card layout | Most important migration surface |
| `ResultCheckingScreen` | `fragment_won_lottery.xml`, prize card layout | Use spec behavior over legacy ad/delete patterns |
| `MyLotteryScreen` | `fragment_my_lottery.xml`, `item_user_lottery.xml` | Enrich card contents to meet v0.1 spec |

## Risks and Gaps

- The legacy result-checking reference is indirect; `fragment_won_lottery.xml` is likely correct, but naming is inconsistent.
- The legacy project contains more flows than v0.1 needs, so strict scope control is important.
- The legacy theme uses Mitr while the new spec permits system fonts; the team should choose one early to avoid visual churn.
- The legacy UI leans on fixed sizes; Compose migration should avoid hardcoded heights where Thai text could clip.

## Final Recommendation

Treat the legacy project as a visual reference library, not an architectural template.

For Version 0.1:

- Migrate only Home, Result Checking, and My Lottery
- Fold the embedded legacy result fragment into the Home screen
- Replace all XML view patterns with a small set of reusable Compose components
- Use the new spec palette and spacing system as the target design language
- Defer legacy-only flows and ad-driven layouts until after the first Compose milestone is complete
