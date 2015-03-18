Vision2015
=============
Here is Metro's 2015 Source Code for robot vision processing. It is written in C++ for speed.

The inner workings
==================

##Main loop
The autonomous section of the game lasts for 15 seconds, so the program contains a 15 second 'game loop' where the action takes place. In pseudocode,
```
create an empty JSON dictionary as j
while currentTime < initialTime + 15:
  find contours on image that have more than 10 sides and a pixel area of greater than 100 as totes
  for every tote in totes:
    calculate distance to tote as d //Explained in distance maths section
    calculate x-value of tote compared to camera center as x //Explained in lateral distance maths section
    j[d] = x
    send to the roboRIO j
  clear j
```

##Maths
Vision processing is complicated, but I will try to simplify the explanation as much as I can.

####Distance Maths
I started my adventure of learning how to calculate the distance to a given object through [this](http://www.pyimagesearch.com/2015/01/19/find-distance-camera-objectmarker-using-python-opencv/) article from pyimagesearch. The gist of the story is to find out how the camera interpolates the distance to an object to pixels and undo it, if that makes any sense. So, on to the algorithms.

From that same link, I got that the algorithm for calculating distance is *distance = (object width &#42; focal width in pixels) / pixel width*. I had a Pi camera, so I went and googled 'pi camera focal width,' and lo and behold I got the focal width from [this article](http://www.raspberrypi.org/forums/viewtopic.php?f=43&t=45191)... in millimeters, which sadly isn't useful. I needed the  So, another google search for 'opencv convert focal width mm to pixels' yielded [this](http://answers.opencv.org/question/17076/conversion-focal-distance-from-mm-to-pixels/) article on doing exactly what I needed. So, my final algorithm became **distance = (actual width &#42; 683) / pixel width**.

####Lateral movement Maths
The robot also has to know how much to move laterally to align itself with the tote. This was much simpler, as all I needed to do was a simple ratio calculation: **distance from center in pixels &#42; (box width in pixels / 429)**.

*The values 683 and 429 are the dimensions of the tote from the [official specifications](http://www.orbiscorporation.com/Products/Hand-Held-Containers/Stack-N-Nest/FP243?u=m#.VQeZR-Hp43h)

##Data transmission
As Java (the language we're using to program on the roboRIO) does not have an builtin way of interacting with USB, I am going to pop open a TCP socket to the roboRIO. As soon as the program calculates the forward and lateral distance to all totes, it will send a JSON dictionary containing the forward and lateral distance of all the totes, in that order.


Testing
=======
Compile it on the RPi, plug the Picam into it, plop a tote in front of the Picam, and see if it is detected with all the accurate measurements.
