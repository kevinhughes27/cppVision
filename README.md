cppVision
=========

Framework for creating a FRC Vision System using C++/OpenCV 2.xx

A SmartDashboard widget that calls a c++ executable to perform a Computer Vision Algorithm on the Axis Camera image using OpenCV 2.xx. Before calling the c++ executable the widget saves the image to be proccessed to hard disk, the c++ executable then reads this file, runs the vision algorithm and writes the results to a text file ("target.txt"). The widget also draws large crosshairs over the whole video feed and small crosshairs over the target found by the c++ executable.

To install the Smartdashboard widget simply copy the cppVision.jar file to your Smartdashboard/extensions folder then add it to your Smartdashboard using the drop down menu the same way you add the plain camera widget. You also need to copy your c++ executable to the Smartdashboard folder.

To build your own Vision program you will need to install OpenCV (http://opencv.org/) and minGW (http://www.mingw.org/) and add them both to the system path. A makefile has been provided in the cppMain folder, more details on building a OpenCV project using command line and minGW can be found here: http://kevinhughes.ca/tutorials/opencv-mingw-makefile/

Future Considerations:
* The upcoming version of OpenCV features official desktop Java bindings essentially making this framework obsolete
* It would be cool to code a stand-alone (aka no Smartdashboard) java program that does the same thing
* It would also be neat to do the entire framework in c++, I originally looked into compiling the FRC / WPIlib NetworkTables for desktop c++ I made some progress but eventually ran out of time/steam
* Another possible avenue for separate FRC vision systems would be to use the python wrappers for OpenCV and the robotpy (https://github.com/robotpy) project which can be compiled for desktop.
