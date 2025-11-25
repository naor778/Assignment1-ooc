Assignment 1 – OOP (Ariel University)

This project is an implementation of Object-Oriented Programming fundamentals as part of Assignment 1 in the OOP course at Ariel University.
The assignment includes creating basic geometric classes and implementing several graphical animations using the biuoop library.
📂 Project Structure:
src/
 ├── Point.java
 ├── Line.java
 ├── Velocity.java
 ├── Ball.java
 ├── HelloWorld.java
 ├── BouncingBallAnimation.java
 ├── MultipleBouncingBallsAnimation.java
 ├── MultipleFramesBouncingBallsAnimation.java
 ├── AbstractArtDrawing.java
 ├── Tests (optional)


Implementing classes, fields, constructors, and methods

Practicing encapsulation & information hiding

Working with objects and composition

Understanding vectors, geometry, and intersections

Building animations with the biuoop library

Handling randomization and velocities

Working with command-line arguments

Point

Represents a 2D point.

Main methods:

double distance(Point other)

boolean equals(Point other)

double getX()

double getY()

Line

Represents a line segment between two points.

Main methods:

double length()

Point middle()

Double slope() (returns null for vertical lines)

boolean isIntersecting(Line other)

Point intersectionWith(Line other)

Helper: inRange(a, b, value)

Velocity

Represents movement in dx/dy.

Main methods:

applyToPoint(Point p)

getDx()

getDy()

static fromAngleAndSpeed(angle, speed)

Ball

Represents a moving ball in 2D.

Fields:

Point center

int radius

Color color

Velocity velocity

Main methods:

drawOn(DrawSurface d)

moveOneStep()

setVelocity(dx, dy)

setVelocity(Velocity v)

getVelocity()

🎬 Animations
BouncingBallAnimation

Displays a single bouncing ball inside a 200×200 window.

Usage:
java BouncingBallAnimation x y dx dy

MultipleBouncingBallsAnimation

Creates several balls with random starting positions.
Ball speed is inversely proportional to size (smaller → faster).

Usage:
java MultipleBouncingBallsAnimation 10 5 20 30 50

MultipleFramesBouncingBallsAnimation

Displays two separate animation frames on the same screen, each containing balls with different boundaries.

AbstractArtDrawing

Draws random lines and highlights their intersection points.

📦 External Library

Project uses:

biuoop-1.4.jar


Library provides:

GUI management

DrawSurface graphics

Sleeper for timing

▶ How to Compile & Run
Compile:
javac -cp biuoop-1.4.jar -d bin src/*.java

Run:
java -cp biuoop-1.4.jar;bin MultipleBouncingBallsAnimation 10 20 5 15

✔ Notes

All fields are private to enforce encapsulation.

Velocity objects are immutable (no setters).

Ball movement includes full collision detection with boundaries.

Code follows OOP guidelines required in the assignment.

✏ Author

Naor Eliyahu
Ariel University – Computer Science
