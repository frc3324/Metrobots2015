#include <iostream>
//#include <opencv2/core/core.hpp>
//#include <opencv2/highgui/highgui.hpp>
//#include <opencv2/imgproc/imgproc.hpp>
//#include <ctime>

#define GUI
#define AUTO 15

#include "sight.h"
#include "socketry.h"

using namespace cv;
using namespace std;
using namespace socketry;

int main()
{
    cout << "program started";
    Socketry socket(CLIENT);
    socket.link("<broadcast>", 8080);
    Sight sight(0, 640, 480);
    #ifdef GUI
        string name = "mainvindo";
        namedWindow(name, CV_WINDOW_AUTOSIZE);
    #endif

    while (true) //Main loop
    {
        sight.update();
        cout << sight.angle;
        socket.transmit(sight.angle);
        #ifdef GUI
            cout << "\tdrawing gui\n";
            imshow(name, sight.getFrame());
            cvWaitKey(1);
        #endif
    }

    return 0;
}

