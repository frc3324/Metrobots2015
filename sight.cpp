#include "sight.h"
#include <iostream>

using namespace cv;
using namespace std;

Sight::Sight(int camera, int width = 640, int height = 480)
{
    config = (RASPIVID_CONFIG*) malloc(sizeof(RASPIVID_CONFIG)); //Allocate memory the size of the Raspberry pi configuration,
    								 //then assign a RASPIVID_CONFIG object to that memory and get a pointer to it
    config->width = width; config->height=height; //Set the width and height of the camera object to what was passed to the function
    config->bitrate = 0; config->framerate = 0; config->monochrome = 0; //Set all the other camera configurations
    
    cam = raspiCamCvCreateCameraCapture2(0, config); //Now create the camera from the configuration data
}

Sight::~Sight() //Empty destructor to the object. Good thing that this only happens right before the application is closed, otherwise memory leakage.
{

}

Mat Sight::getThresholded()
{
    Mat hsv; cvtColor(frame, hsv, COLOR_BGR2HSV); //Convert the frame to hsv
    Mat thresholded; //A mat for the thresholded version of the object
    Size erodeVal(10, 10); //Erosion size

    inRange(hsv, min, max, thresholded); //Filter by color to the thresholded mat

    //Fill in grainy areas. Makes contouring easier.
    erode(thresholded, thresholded, getStructuringElement(MORPH_ELLIPSE, erodeVal));
    dilate(thresholded, thresholded, getStructuringElement(MORPH_ELLIPSE, erodeVal));
    dilate(thresholded, thresholded, getStructuringElement(MORPH_ELLIPSE, erodeVal));
    erode(thresholded, thresholded, getStructuringElement(MORPH_ELLIPSE, erodeVal));

    return thresholded;
}

void Sight::getTote() {
	//cout << "finding totes";
    Mat thresholded = getThresholded(); //Get the thresholded frame
    vector< vector<Point> > contours; //Create a vector (array) of contours (a vector of points)
    findContours(thresholded, contours, CV_RETR_TREE, CV_CHAIN_APPROX_SIMPLE, Point(0, 0)); //Find all the contours in the image
    											    //and assign them to the array
    if (contours.size() == 0){ //If no contours are detected, say that
		obj = false;
		cout << "no contours detected\t";
		return;
	}
	obj = true;
    Rect rect;
    int size, idx;
    //vector<Point> approx;
    for (int i = 0; i < contours.size(); i++) //Look through all contours until we find one big enouth to mark it as a tote
    {
        //approx.clear();
        //approxPolyDP(contours.at(i), approx, 0.02*arcLength(contours.at(i), true), true);
        if (contourArea(contours.at(i)) > 1000) {
            //size = contourArea(contours.at(i)); idx = i;
            cout << "tote found";
            tote = minAreaRect(contours.at(i));
        }
    }
    //if (!(idx <= 0)) tote = minAreaRect(contours.at(idx));
}

void Sight::getInfo() {
    if (obj == false){ //No totes were found, so let us say that
		angle = 0;
		cout << "no totes found\t";
		return;
	}
    float x = tote.center.x; //Get the center of the tote in the x direction
    int width = config->width; //Get the width of the frame
    int threshold = 3; //The maximum from the center you can be off in pixel to still say that the tote is in line with the robot

    //Set angle to the right value based on where it is compared to the center of the camera.
    //See sight.h for more information on the angle variable.
    if (x < width / 2 - threshold) angle = 1;
    else if (x > width / 2 + threshold) angle = 2;
    else angle = 3;
}

void Sight::update() {
    //cout << "getting new frame\t";
    frame = raspiCamCvQueryFrame(cam);
    //cout << "finding tote\t";
    getTote();
    //cout << "analyzing tote\t";
    getInfo();
}

Mat Sight::getFrame() {
    Mat out(frame);
    Scalar color = Scalar(255, 0, 0);
    ellipse(out, tote, color, 2);
    return getThresholded();
}
