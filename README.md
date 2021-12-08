# Ex2
This is our project for implementing graph algorithms on directed weighted graphs 
and presenting them wth GUI
in java. Our application can parse json files into graphs on which they can do 
different operations and modifications.

Our project support commonly used graph algorithms such as:tsp, isConnected 
(meaning is this graph strongly connected), center (finding the center of the graph)
Shortest Path (find the shortest path between to vertices) and Shortest Path Distance
(find the distance between two nodes)

it also supports saving and loading graphs from json and to json files

# how to use 
1. download or clone this repository and find the "Ex2.jar" file.
2. run the command line and in the directory of the jar file, then make sure that you know the
path to your json file or that the json file is at the "data" directory of this repository
   (thats the default directory in which the program searches for json files)
3. in the command line write
"java -jar Ex2.jar {path to your json file}" and the program wil run and the json file will be loaded.
if you have the json file in the "data" directory you can simply write its name with the json extension
and the json will be loaded.
