# The Yellow Submarine

A mobile arcade game prototype developed as a study project with **LibGDX** and **Java** for Android. The project focuses on **touch-based gameplay**, **screen management**, **UI flow**, and simple **game state handling** in a compact, polished experience.

## About the project

The game puts the player in control of a submarine moving through an underwater environment. The goal is to survive as long as possible while managing oxygen, avoiding obstacles, and reacting to enemy spawns from both the sea and the surface.

It includes:
- a main menu with background music
- a settings screen to toggle sound and change difficulty
- an in-game HUD with oxygen and score
- collision-based game over states
- animated feedback and themed UI skins

## How it works

The game is structured around LibGDX `Screen` classes:

- **MenuScreen**: main entry point with start/settings buttons and best score display
- **SettingsScreen**: lets the player switch music on/off and cycle between `EASY`, `NORMAL`, and `HARDCORE`
- **GameScreen**: main gameplay loop, where movement, oxygen, enemies, and collisions are handled

The game uses `Scene2D` UI actors for the interface and game objects for the submarine, sharks, boats, ships, and seaweed.

## Gameplay

- The submarine moves with **touch input**
- Holding the **upper half** of the screen moves the submarine up
- Holding the **lower half** moves it down
- The submarine naturally sinks due to gravity
- Oxygen decreases over time
- Oxygen is recovered when the submarine reaches the surface
- Collisions with sharks, boats, or ships end the run
- The score increases while the submarine stays alive

## Difficulty

The settings screen lets the player cycle through:

- **Easy**
- **Normal**
- **Hardcore**

Difficulty changes the spawn timing for surface enemies.

## Tech stack

- **Java**
- **LibGDX**
- **Android SDK**
- **Scene2D UI**
- **Gradle**

## Project structure

- `core/` → gameplay logic, screens, actors, timers
- `android/` → Android launcher and platform-specific setup
- `assets/` → textures, sounds, fonts, skins, and animations

## Notes

- Best score is shown in the menu and updated during the app session.
- Sound/music can be toggled from the settings screen.
- This is a single-player arcade prototype, not an online or persistent game.