# FoxesAndRabbitsJAVA
The foxes and rabbits’ project consist of a simulation of different actions between the different characters included in the system.

## What I've Learnt?
1. Graphical User Interface which have been used in the new class named Controller View where a JFrame has been created in order to control the simulation process:
![Controller](https://user-images.githubusercontent.com/25366487/55292680-bb823680-53e5-11e9-886d-23fd730a997e.png)
2. Inheritance has been used for the creation of a new class which represents the Bear, by extending the Animal abstract class. The Bear can eat rabbits and foxes, by consequence a few changes have been implemented in order to balance the breeding probabilities and the creation probabilities of the characters along with the food values of the hunted animals.
3. A new Interface has been used for introducing the Hunter and Police characters, called Actor interface which requires 4 methods: 
..* Act(), setLocation() used in the Animals abstract class as well.
..* By using inheritance, the Hunter class has been created, which hunts the rabbits, foxes and bears. After that, the Police class has been added, since I found that hunting is illegal in Scotland according to the “Protection Of Wild Mammals Act”, so that the policeman character can arrest the hunters.
..* The Actor interface has been implemented in the Animal abstract class in order to centralize all the method used by both classes, and this led to change all the subclasses and the Simulator Class as well by using Actor as interface for the simulation with the use of Casting which led to assigning the subtypes to the supertypes.
4. Exceptions and File I/O have been used in the Reader and SimulatorView classes for reading/storing all the data from the view. ArrayLists have been used for storing each character’s population with the steps also by using the Scanner for reading and splitting the data in the related ArrayLists. After reading the data from the text file the function used in the class gathers everything in a big List which will be passed to the PopulationBarChart class for building the bar chart.
5. Use of JAVAFX has been implemented for building the bar chart below, which uses the data of the stored steps on the text file.
6. Icons have been added for styling those old-looking buttons in order to make the GUI more user-friendly, by using icons downloaded from https://icons8.com.
