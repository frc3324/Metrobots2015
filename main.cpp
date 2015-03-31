#include <iostream>
//#include <opencv2/core/core.hpp>
//#include <opencv2/highgui/highgui.hpp>
//#include <opencv2/imgproc/imgproc.hpp>
//#include <ctime>

//#define VISUAL true
#define AUTO 15

#include "sight.h"

using namespace cv;
using namespace std;

int main()
{
    Sight sight(0, 640, 480);
    string name = "mainvindo";
    namedWindow(name, CV_WINDOW_AUTOSIZE);
    //vector< pair<float, float> > toteinfo;

    while (true) //Main loop
    {
        imshow(name, sight.getFrame());
        cvWaitKey(1);
    }

    return 0;
}

