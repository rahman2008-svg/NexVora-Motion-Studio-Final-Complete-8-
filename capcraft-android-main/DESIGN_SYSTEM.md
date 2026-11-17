# NexVora Motion design system

Defined in `ui/Theme.kt` (tokens) and `ui/common/NexVora.kt` (components); XML tokens in `res/values/colors.xml`.

## Colour (approved reference palette)
| Token | Value | Use |
|---|---|---|
| `Palette.Black` | #03040B | App background |
| `Palette.Chrome` / `Panel` | #070A16 / #0B1024 | Editor chrome, sheets |
| `Palette.Card` | #10152C | Cards and rows |
| `Palette.Elevated` | #151B38 | Dialogs, sheets |
| `Palette.Divider` | #252D49 | Borders |
| `Palette.PurpleDeep` / `Purple` | #7B2CFF / #C026FF | Gradients, switches |
| `Palette.Blue` | #245BFF | Gradient end, hero button |
| `Palette.Cyan` | #00D9FF | Highlights: active nav, tool icons |
| `Palette.Accent` | #A020F0 | Neon highlight: selected text, rings, ticks |
| `Palette.Text` / `TextSecondary` | #FFFFFF / #8993B8 | Text |
| `Success` #25D695, `Warning` #FFB547, `Danger` #FF5572 | | States |
Gradients: `PrimaryBrush` (purple to blue, buttons, white ink), `PurpleBrush` (New Project, Create), `HeroBrush` (hero banner).

## Shape and space
`NexShape`: Chip 8dp, Button 12dp, Card 16dp, PanelTop/PanelBottom 20dp (editing panels, export settings), Dialog 24dp, Sheet 24dp top corners. `Space`: 4, 8, 12, 16, 24dp. Touch targets: 44dp (`NexVoraIconButton`, buttons have a 44dp minimum).

## Type
`Type.Display` 32 bold, `Headline` 22 bold, `Title` 17, `Subtitle` 15, `BodyStrong`, `Body` 14, `Small`, `Label` 12, `Caption` 11, `DockLabel`, `Numeric` (14 semibold, tabular digits, for slider/ruler values; defined, not yet applied to the sliders).

## Components
`NexVoraPrimaryButton` (cyan, `destructive` red), `NexVoraSecondaryButton`, `NexVoraIconButton`, `NexVoraCard` (optionally clickable), `NexVoraSectionHeader`, `NexVoraEmptyState`, `NexVoraLoadingState`, `NexVoraErrorState`, `NexVoraGrabber`, `NexVoraChip`, `NexVoraSegmentedTabs`, `NexVoraSearchField`. Panel tabs, Projects filters, the media picker tabs and the Projects search use them. The shared `ConfirmDialog`, `ActionSheet`, `Toast`, `PanelFrame` and `CapSlider` use them or the same tokens.

## Rules
Cyan means selected or primary; purple only for effect clips and the logo; one gradient (primary button); borders 1dp Divider; no blur or glow; animations are the originals (no new ones, reduced-motion not reviewed).

## Gaps
Many panels still use inline buttons and hard-coded `Color.White`; there is no slider component beyond `CapSlider` and the export sheet's own stepped slider.
