# Map Rank Overlay — Fabric 1.21.11

A **visual-only**, client-side Minecraft Fabric mod for sorting large map-art sets by eye.

## What it does

- Press the toggle key (default: **N**) to turn the overlay on/off.
- The key is fully rebindable in **Options → Controls → Key Binds → Map Rank Overlay**.
- While any inventory/container screen is open, the mod reads the filled-map IDs already visible to your client.
- It sorts those IDs from lowest to highest **only in memory for rendering**.
- It draws `1, 2, 3...` over the map icons. `1` is the lowest visible map ID.
- The lowest-ID map also gets a yellow outline.
- Duplicate copies of the same map ID receive the same rank.

## What it NEVER does

This mod contains **no inventory automation**. It does not:

- click slots
- swap or move items
- quick-move/shift-click
- place maps
- send inventory interaction packets
- call Minecraft's interaction manager

It only reads `ItemStack` map-ID components and renders GUI pixels/text.

## Minecraft / Fabric

- Minecraft: `1.21.11`
- Fabric Loader: `0.18.1+`
- Fabric API: built against `0.141.1+1.21.11`
- Java: `21`

## Build on GitHub

1. Upload the contents of this project to a GitHub repository.
2. Open the **Actions** tab.
3. Run **Build Fabric Mod**, or push a commit.
4. Download the `map-rank-overlay-jar` artifact from the completed workflow.
5. Put the regular JAR (not `-sources`) in your Minecraft `mods` folder with Fabric API.

## Local build

```bash
./gradlew build
```

The JAR will be in `build/libs/`.
