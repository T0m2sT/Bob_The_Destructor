# LDTS_T04_G04 - BOB, THE DESTRUCTOR

## com.ldtsfeup2526.bobTheDestructor.controller.Game Description

  In this project, our main focus is to develop a 2D mining game inspired by games like Minecraft and Terraria. The main persona is a miner named Bob, as the game’s name suggests. This persona starts his adventure in the upperground area, where the core progression system (upgrades and shop) are located. 
The main objective of this game is to go lower and lower in the underground to discover the terrain blocks with the most valuable resources, collect them and return to the upperground area to invest them in rewards. The game’s dynamics is strictly on exploration, resource collection and tool upgrades. As the distance from the upperground area increases, the stiffness of the subterranean layers also increases.

This project was developed by Aléxis Ramos, Pedro Tomás Teixeira, Rafael Pinho e Silva for LDTS 2024/25.

### Total features (to be split in the two below)

- **Collision System**
- **Physics System**
- **Player Movement**
- **Sprite Loader**
- **Terrain Generator**
- **Destruction System**
- **Main Menu**
- Screen Resizer
- Animation Manager
- Collectible System
- Gadget System
- Upgrade System### Design

#### THE SCREENS AND GAME LOOP SHOULD BE SWITCHABLE WITHOUT UPDATING IF/SWITCH LOGIC

**Problem in Context**

We need to support different application screens such as gameplay and menus and switch between them while the application is running.
This means we can't mix rendering coordination with screen logic, because it would violate the Single Responsibility Principle and made transitions more complex.

Manual calls from the loop in the `Game` class to different drawing would have led to conditionals deciding which screen to update and draw.

**The Pattern**

We applied the State pattern. Each screen is a distinct state encapsulating its own model, viewer and controller.
`Game` holds a reference to the current `State<?>` and then it only needs to delegate to it. This way, switching screens is now replacing the state instance.

This fits because screens show states behavior with uniform operations (`update(gui)`), and transitions are well expressed as state replacements without conditionals.

**Implementation**

- Core abstraction: [`states/State.java`](../src/main/java/com/ldtsfeup2526/bobTheDestructor/states/State.java#L11-L33). It holds the screen model, a `Controller<T>`, and a `ScreenViewer<T>`, created via factory methods.
- Game loop delegation: [`Game.java`](../src/main/java/com/ldtsfeup2526/bobTheDestructor/Game.java#L43-L61) calls `state.update(gui)` every frame and exposes `setState(...)` for transitions ([`Game.java#setState`](../src/main/java/com/ldtsfeup2526/bobTheDestructor/Game.java#L65-L67)).
- Concrete states: a `GameState` links the `Scene` model with its screen viewer and controller.

**Consequences**

Benefits:
- Clean separation of concerns between the main loop and screen logic.
- Adding a new screen is simpler: implement a new `State<T>` subclass and its viewer and controller.
- Transitions are explicit and testable via `Game.setState(...)`.

Liabilities:
- More types and indirection.

#### RENDERING BEHAVIOR FOR DIFFERENT ELEMENTS SHOULD BE EXTENSIBLE WITHOUT MODIFYING A CENTRAL RENDERER

**Problem in Context**

As we introduced multiple elements to render like player, tiles and decor, a single monolithic renderer would accumulate `if/switch` chains to handle each type,
making it harder to add new visuals and violating the Open/Closed Principle.

**The Pattern**

We applied the Strategy pattern for the drawing method. Each drawable element has its own viewer strategy implementing a common interface.
Screens then will compose these strategies to render the model.

**Implementation**

- Strategy interface: [`view/elements/ElementViewer.java`](../src/main/java/com/ldtsfeup2526/bobTheDestructor/view/elements/ElementViewer.java#L6-L8).
- Strategy composition/provider: [`view/ViewerProvider.java`](../src/main/java/com/ldtsfeup2526/bobTheDestructor/view/ViewerProvider.java#L7-L16) instantiates and exposes concrete viewers like `PlayerViewer`.
- Example concrete strategy: [`view/elements/PlayerViewer.java`](../src/main/java/com/ldtsfeup2526/bobTheDestructor/view/elements/PlayerViewer.java).
- Screen-level composition: `ScreenViewer<T>` aggregates the strategies to draw a complete screen.

**Consequences**

Benefits:
- New renderable types can be added by creating a new `ElementViewer` implementation while existing code stays closed to modification.
- Testability improves by isolating drawing logic per element.

Liabilities:
- Slight increase in the number of classes and indirection through the provider.

#### GUI IMPLEMENTATION SHOULD BE SEPARATED FROM LANTERNA LOGIC AND SCREEN CREATION

**Problem in Context**

Directly using Lanterna APIs in game code makes so that we can only render to a specific terminal library and also make configuration details (resolution, font, title) more confuse across the code.
This would mess up portability and made switching or configuring the backend harder.

**The Pattern**

We combined Adapter and Factory patterns:
- Adapter: define a minimal `GUI` interface for drawing operations, with `GUILanterna` adapting Lanterna’s `Screen` to that interface.
- Factory: centralize creation and also configuration of the `Screen` via a `ScreenCreator`.

**Implementation**

- Adapter (`GUILanterna`): wraps Lanterna and implements drawing methods; see [`gui/GUILanterna.java`](../src/main/java/com/ldtsfeup2526/bobTheDestructor/gui/GUILanterna.java#L19-L81). Notable operations include `drawPixel`, `clear`, `refresh`, and `close`.
- Factory (`ScreenCreator`): interface to build a configured Lanterna `Screen`; see [`gui/ScreenCreator.java`](../src/main/java/com/ldtsfeup2526/bobTheDestructor/gui/ScreenCreator.java#L11-L13). `GUILanterna` delegates actual creation to this factory in `createScreen(...)`.
- Integration in boot: `Game` builds `GUILanterna` with resolution, pixel size, and title; see [`Game.java` constructor](../src/main/java/com/ldtsfeup2526/bobTheDestructor/Game.java#L26-L31).

**Consequences**

Benefits:
- The view classes uses a stable `GUI` API and is decoupled from Lanterna specific methods.
- Screen creation details, the resolution, font size, window title and KeyListener are centralized, easing configuration and testing.
- Future backends can be introduced by implementing `GUI` and a matching `ScreenCreator`.

Liabilities:
- Additional abstraction layers making it more complex and efficiency might be compromised.

#### INPUT HANDLING SHOULD MAP KEY EVENTS TO HIGH-LEVEL ACTIONS AND SUPPORT SINGLE-SHOT SEMANTICS

**Problem in Context**

Raw key events (press/release) are noisy and platform-dependent. Game logic should operate on semantic actions (e.g., `UP`, `JUMP`, `SELECT`) and avoid repeated triggers for single-shot actions while a key is held.
Naive solutions would scatter key-code checks and introduce confusion across controllers.

**The Pattern**

We used the Observer style provided by Java AWT (`KeyListener`) to receive events, and then applied a parsing layer that behaves like a Command/Interpreter for inputs: `ActionParser` translates key codes into domain actions and coordinates one-shot behavior using an `InputReader` buffer.

This combination separates event capture from action semantics.

**Implementation**

- Event capture buffer: [`controller/input/InputReader.java`](../src/main/java/com/ldtsfeup2526/bobTheDestructor/controller/input/InputReader.java#L9-L56) implements `KeyListener`, maintaining `inputPressed` and `inputFinished` lists; `updateInputPressed()` updates inputs each frame.
- Parsing to actions: [`controller/input/ActionParser.java`](../src/main/java/com/ldtsfeup2526/bobTheDestructor/controller/input/ActionParser.java#L8-L59) maps key codes to `Action` values and marks single-shot actions (`SPACE`, `ENTER`, `ESCAPE`) as finished.
- Wiring in boot: `Game` creates the parser and passes its `InputReader` into the GUI, so the same listener receives events; see [`Game.java` boot](../src/main/java/com/ldtsfeup2526/bobTheDestructor/Game.java#L26-L31).

**Consequences**

Benefits:
- Clear separation between event capture and semantic actions.
- One-shot actions are handled uniformly; held keys don’t cause repeated triggers unless intended.
- Controllers can depend on `Action` lists without caring about AWT details.

Liabilities:
- Requires careful usage of `inputFinished` to avoid starving inputs; controllers must mark single-shot actions appropriately.

- Shop System
- Particle System
- Ore System
- GUI
- Top score
- Tile Visibility System

### Implemented features

### Planned features

2
#### Singletons...


### Known code smells

### Testing

### Self-Evaluation