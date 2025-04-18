# Developer Guide
This section covers the technical documentation of TickTock.

## Architecture
The architecture of the app follows a Model-View-Controller (MVC) pattern.
![Solution_Architecture](./developer_guide/SolutionArchitecture.svg)

The controller will be calling the application logic when it receives an input from the user.
The internal workings of the application can be summarised into 5 different components:
1. View
2. Controller
3. Model
4. Service
5. Storage

## Class Diagram
The full class diagram is shown here. The entry point `TickTockApp` is omitted here as it does not affect the
internal functionalities of the application.
![Full_Class_Diagram](./developer_guide/FullClassDiagram.svg)

## Implementation

### Entry point
The entry point of the application is via `TickTockApp`. It renders the main view for the user.

### Controllers
#### MainController
The `MainController` manages all interactions between the mainView and the logic. It handles the start and stop of a 
session. It also handles the break feature.

##### Feature: Sessions
The sequence diagram visualizes the interactions between the `MainController` and the model when the session is started.
![Session_Start](./developer_guide/SessionStartSeq.svg)

The sequence diagram visualizes the interactions between the `MainController` and the model when the 
session ends.
![Session_End](./developer_guide/SessionEndSeq.svg)

##### Feature: Breaks
The sequence diagram visualizes the interactions between the `MainController` and the model when a break is started.
![Break_View](./developer_guide/BreakSeq.svg)

#### StatsController
The `StatsController` manages all interactions between the statsView and the logic.

##### Feature: Statistics
![Stats_View](./developer_guide/StatsSeq.svg)

### Model

### Service
The Service classes provides additional functionalities to the model.

#### SessionService
`SessionService` allows `MainController` to save sessions. 

#### StorageService
`StorageService` allows modules to read and write data to storage.

### Storage
Storage for the application is stored at the application's root in `/data/sessions.json`
We have chosen to use `.json` files to store the application due to its ability to store data in a lightweight method.

For each Session, it keeps track of these information:
1. Module Information
2. Category
3. Expected Time to spend for Session
4. Actual Time spent for Session
5. List of time spent for each break

Each session potentially has a list of breaks to track, it makes sense to encapsulate all these information 
into a single object. Thus, __json__ is the appropriate format to do so.


