/*
 * Sight -- the eyes of the robot
 * interprets information from the camera
 * to find the location of the visible totes
*/

#ifndef SIGHT_H
#define SIGHT_H

#include <opencv2/highgui/highgui.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <RaspiCamCV.h>

using namespace cv;
using namespace std;

class Sight
{
public:
    Sight(int, int, int); //Constructor for the sight class
    ~Sight(); //Destructor for the sight class
    void getTote(); //Find the tote
    void update(); //Update all of the vision data
    void getInfo(); //Get information about what the camera is seeing
    Mat getFrame(); //Get the current frame of the camera
    RotatedRect tote; //The bound rectangle of the tote in the frame
    char angle = 0; //Stores information about the angle of the tote in relation to the RoboRIO
    		    //0 = unknown data
    		    //1 = tote is to the left of the camera
    		    //2 = tote is to the right of the camera
    		    //3 = tote is aligned with the camera

private:
	bool obj = false; //Says whether there is a tote in the camera
    Scalar max = Scalar(38, 255, 255); //Maximum color for tote detection
    Scalar min = Scalar(16, 216, 100); //Minimum color for tote detection
    RASPIVID_CONFIG* config; //Configuration data of the Raspberry Pi camera
    RaspiCamCvCapture* cam; //Raspberry pi camera object
    Mat frame; //The current frame
    Mat getThresholded(); //Retrieve the thresholded version of the frame for getting the contours of the tote
};


#endif // SIGHT_H
