/*
 * Sight -- the eyes of the robot
 * interprets information from the camera
 * to find the location of the visible totes
*/

#ifndef SIGHT_H
#define SIGHT_H

#include <opencv2/highgui/highgui.hpp>
#include <opencv2/imgproc/imgproc.hpp>

using namespace cv;
using namespace std;

namespace socketry
{

char SERVER = 1;
char CLIENT = 2;

class Sight
{
public:
    Sight(int, int, int);
    ~Sight();
    vector< vector<Point> > getTotes(vector<Vec4i>&);
    Mat getFrame();

private:
    Scalar max = Scalar(37.5, 235, 266);
    Scalar min = Scalar(17.5, 135, 166);
    VideoCapture cam;
    Mat getThresholded();
};
}


#endif // SIGHT_H
