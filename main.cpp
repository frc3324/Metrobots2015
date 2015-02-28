#include <iostream>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/imgproc/imgproc.hpp>

using namespace cv;
using namespace std;

#include "sight.h"

int main()
{
    Sight sight(0, 640, 480);
    namedWindow("image", CV_WINDOW_AUTOSIZE);

    while (true)
    {
        imshow("image", sight.getFrame());
        cvWaitKey(10);
    }

    return 0;
}

