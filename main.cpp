#include <iostream>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <ctime>

#define VISUAL true
#define AUTO 15

using namespace cv;
using namespace std;

#include "sight.h"

int main()
{
    time_t start = time(0);
    cout << start;
    Sight sight(0, 640, 480);
    namedWindow("image", CV_WINDOW_AUTOSIZE);
    vector<float> angles;

    while (difftime(start, time(0)) < 15) //Main loop
    {
        cout << ((float)clock())/CLOCKS_PER_SEC;
        if (VISUAL)
        {
            imshow("image", sight.getFrame());
            cvWaitKey(10);
        }
        if (!VISUAL)
        {
            angles = sight.getAngles(sight.getTotes(), 683);
        }
    }

    return 0;
}

