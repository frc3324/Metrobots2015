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
    //time_t start = time(0);
    //cout << start;
    //initModule_nonfree();
    Sight sight(0, 640, 480);
    sight.update();
    string name = "mainvindo";
    namedWindow(name, CV_WINDOW_AUTOSIZE);
    //vector< pair<float, float> > toteinfo;

    while (true) //Main loop
    {
        sight.update();
        imshow(name, sight.getFrame());
        cvWaitKey(0);
    }

    return 0;
}

