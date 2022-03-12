## MiniGame

# Prerequisites
- Maven
- A java JDK version of 11 or higher

# Setup
- Run  ```mvn install``` to create 2 jar-shaded folders

# Run SServer
- ```cd SServer/target```
- ```java -jar SServer-1.0-SNAPSHOT-shaded.jar```
- The server should start up and wait for a client to join

# Run Client
- ```cd Client/target```
- ```java -jar Client-1.0-SNAPSHOT-shaded.jar```
- The client should be running and a window should pop up
- Enter the ipAddress of the server you want to connect to. If running on the local machine the ip is **localhost**
- Port number is **5000**

# Play
- After you are connected to the server, you will need to enter a name and pick your robot type.
- Once you have done that the graphical window should close and you should have a display of your robot stats on the terminal

# Client Commands
| Command | Description |
| ------ | ------ |
| forward {steps} | Moves the robot forward a number of {steps} |
| back {steps} | Moves the robot back a number of {steps} |
| turn {left OR right} | Turns your robot left or right |
| fire | Fires a shot infront of with a certain amount of distance based on your initial shots |
| mine | Sets a mine at the robot's current position and moves you 1 space forward if available else you land on the mine |
| look | Displays all the obstacles in a given radius around you |
| state | Displays your robot's current state |
| repair | Repairs your robot's shields to it's original number |
| reload | Reloads your ammo |

# _Important notes for clients_
- There are 2 sets of permanant obstacles: walls and pits
- You cannot move into a wall or over it
- If you move over a pit you will die

- Other obstacles are other robots and mines that robots set
- You will always spawn in a random direction and location
   # Fire command
  You can shoot a certain distance away from yourself based on your original shots
  | Original Shots | Distance (steps) |
  | ----- | ----- |
  | 1 | 5 |
  | 2 | 4 |
  | 3 | 3 |
  | 4 | 2 |
  | 5 | 1 |
  # Mine command
  - The mine command has a timer and your shields will be set to 0 will you are setting it


# SServer Commands
These commands only work on the server side terminal, a client cannot enter them
| Command | Description |
| ----- | ----- |
| Robots | Lists all the robots in the world currently (Not the clients) |
| Quit | Kills all the clients currently connected to the server and then stops the server |
| Purge {client} | Kills a specific client based on their name. A client has a default name of **Client#{NUMBER IN ORDER THEY JOINED}** |
# fuzzy-octo-tribble
