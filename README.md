# Machine Learning Algorithms
Several Machine Learning Algorithms
## How To Install
### Netbeans
File -> New Project
Select The Maven Folder
Select Java Application
Next, Name your project, Finish
### Other
Create A New Maven Project (look it up if you have to)
### Add Dependancy
Paste this inside the ```<project>``` tag of the pom.xml file:
```xml
<dependencies>
    <dependency>
        <groupId>com.googlecode.matrix-toolkits-java</groupId>
        <artifactId>mtj</artifactId>
        <version>1.0.2</version>
    </dependency>
</dependencies>
```
### Add Files
Initialize a new git repository in the root directory of the maven project and pull all the files from this repository

—or—

Download all the files and manually paste them into the src/main/java directory of your project
## Using Fmincg
Look at the [`MTJTests.java`](src/main/java/com/mycompany/maventest/MTJTests.java) file for a demo.
Basically, you define a cost and a gradient function in a new CostGradient Object and run the routine of the Fmincg object. Call .getX() from the output object to get the optimal values.