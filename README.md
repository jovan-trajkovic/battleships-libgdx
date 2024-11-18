# Game Design Draft for Battleships Game

## Game Overview

This project represents a multiplayer Battleships game developed using LibGDX. The game allows players to connect via a local network and take turns shooting at each other. This game is supposed to mimic the board game most of us have played as children.

### Game Dynamics

The goal of the game is to eliminate the other player's ships. The players take turns at shooting at the opposing player's ships on the board. The game continues until one player successfully hits all of the opponent’s ships.

### Game Mechanics

- **Local Network Multiplayer**: Players connect via a network to exchange shots.

- **Turn-Based Gameplay**: Each player takes one shot per turn.

- **Feedback**: The shooting player can see the result of their shots.

### Game Elements

- **Player Grid**: Represents the layout of ships and tracks shot attempts.

- **Multiple Ships**: The player has ships of varying sizes on their grid.

- **Multiplayer**: Network communication between devices allows exchanging shots.

- **Hit Tracking System**: Keeps a record of hits and misses for both players to determine the winner.
