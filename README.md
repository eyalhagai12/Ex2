# Ex2

This is our project on graph algorithms (specificly directed weighted graphs) and GUI in java. it can be used to run
different algorithms on graphs using a simple graphical user interface

## Description

This is our project for implementing graph algorithms on directed weighted graphs and presenting them wth GUI in java.
Our application can parse json files into graphs on which they can do different operations and modifications to the
graph.

Our project support commonly used graph algorithms such as:tsp (a simplified variation of tsp), isConnected
(meaning is this graph strongly connected), center (finding the center of the graph), Shortest Path (find the shortest
path between two vertices) and Shortest Path Distance
(find the distance between two nodes)

it also supports saving and loading graphs from and to json files
(Note: The json file structure should look like those in the data folder when trying to load them)

### UML

It was too big, so we put it in the end of this file. [click here to go to uml](#uml-image)


# Getting started

1. Download or clone this repository and find the "`Ex2.jar`" file.
2. Run the command line and in the directory of the jar file, then make sure that you know the path to your json file or
   that the json file is at the "`data`" or "`saved_graphs`" directory of this repository
   (these are the default directories in which the program searches for json files)
3. In the command line write "`java -jar Ex2.jar {path to your json file}`" and the program will run and the json file
   will be loaded. if you have the json file in the default directories you can simply write its name and the json will
   be loaded. (in both ways please also write the json file extension at the end i.e. "`path/to/file.json`"
   or "`file.json`")

# UML image

![](hugeUML.png)






