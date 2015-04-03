#include <iostream>
#include <wiringPi.h>

#define GUI
#define G1 8
#define G2 9
#define AUTO 15

#include "sight.h"

using namespace cv;
using namespace std;

int main()
{
    cout << "program started\n";
    wiringPiSetup();
    pinMode(8, OUTPUT); pinMode(9, OUTPUT);
    cout << "pins set\n";
    //Sight sight(0, 240, 135);
    Sight sight(0, 160, 120);
    cout << "camera class instantiated\n";
    #ifdef GUI
        string name = "mainvindo";
        namedWindow(name, CV_WINDOW_AUTOSIZE);
    #endif
    
    cout << "main loop starting\n";
    while (true) //Main loop
    {
        //cout << "updating info ";
        sight.update();
        cout << sight.angle << "\n";
        if (sight.angle == "u") {
            digitalWrite(G1, LOW);
            digitalWrite(G2, LOW);
        }
        else if (sight.angle == "l") { 
            digitalWrite(G1, HIGH);
            digitalWrite(G2, LOW);
        }
        else if (sight.angle == "r") {
            digitalWrite(G1, LOW);
            digitalWrite(G2, HIGH);
        }
        else {
            digitalWrite(G1, HIGH);
            digitalWrite(G2, HIGH);
        }
        
        #ifdef GUI
            cout << "\tdrawing gui\n";
            imshow(name, sight.getFrame());
            cvWaitKey(1);
        #endif
    }
    /*while (true) {
	socket << "test";
	cout << "sent test string\n";
	}*/

    return 0;
}

