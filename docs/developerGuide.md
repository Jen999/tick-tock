# Developer Guide
This section covers the technical documentation of TickTock.

# Table of contents
1. [Architecture](#architecture)
2. [Class Diagram](#class-diagram)
3. [Implementation](#implementation)
    1. [Entry Point](#entry-point)
    2. [Controllers](#controllers)
       1. [MainController](#maincontroller)
          1. [Feature: Sessions](#feature-sessions)
          2. [Feature: Breaks](#feature-breaks)
       2. [StatsController](#statscontroller)
          1. [Feature: Statistics](#feature-statistics)
   3. [Model](#model)
   4. [Service](#service)
      1. [Session Service](#sessionservice)
      2. [Storage Service](#storageservice)
   5. [Storage](#storage)
4. [Testing](#testing)

## Prerequisites
You can fork the repository [here](https://github.com/Jen999/tick-tock).

Dependencies for the project can be found in `build.gradle`

Java JDK version used: __21__

Build tool: __gradle__

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

Precondition: No session is currently running

![Session_Start](./developer_guide/SessionStartSeq.svg)

The sequence diagram visualizes the interactions between the `MainController` and the model when the 
session ends.

Precondition: A session/break is currently running

![Session_End](./developer_guide/SessionEndSeq.svg)

##### Feature: Breaks
The sequence diagram visualizes the interactions between the `MainController` and the model when a break is started.

Precondition: A session is currently running

![Break_View](./developer_guide/BreakSeq.svg)

#### StatsController
The `StatsController` manages all interactions between the statsView and the logic.

##### Feature: Statistics
The sequence diagram visualizes the interactions between the `StatsController` and the services to generate the 
statistics to update the statsView

Precondition: No precondition

![Stats_View](./developer_guide/StatsSeq.svg)

### Model
The classes in the model houses all the logic required for the creation of sessions and breaks.

### Service
The Service classes provides additional functionalities to facilitate the controllers.

#### SessionService
`SessionService` allows `MainController` to save sessions. This is done through the API defined for StorageService.
Internally, SessionService depends on StorageService via
```
// how MainController will initiate the SessionSerivce
SessionService.createAndSaveSession(args)

// how sessionService will initiate StorageService
StorageService.loadSessions()
StorageService.saveSessions()
```
This is done to ensure that all the current sessions are loaded and the new information is appended to the storage.

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

Example structure of the json object:
```
Structure of /data/session.json:
[
  {
    "module": "cs2100",
    "category": "test",
    "goalMinutes": 15,
    "actualTime": "00:00:03",
    "totalBreakTime": "00:00:00",
    "breakSessions": [
      "00:00:10",
      "02:10:05",
    ]
  }
]
```

## Testing
To learn more about the testing framework and how to conduct your tests, refer to this [guide](./testingGuide.md) 
for more information!

You have reached the end of the developer guide!
To head back, click [here](./README.md)
