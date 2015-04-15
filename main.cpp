#include <iostream>
#include <wiringPi.h>

#define GUI
#define G1 4
#define G2 5
#define AUTO 15

#include "sight.h"

using namespace cv;
using namespace std;

int main()
{
    cout << "program started\n";
    wiringPiSetup();
    pinMode(G1, OUTPUT); pinMode(G2, OUTPUT);
    cout << "pins set\n";
    //Sight sight(0, 240, 135);
    Sight sight(0, 160, 120);
    cout << "camera class instantiated\n";
    /*#ifdef GUI
        string name = "mainvindo";
        namedWindow(name, CV_WINDOW_AUTOSIZE);
    #endif*/
    
    cout << "main loop starting\n";
    while (true) //Main loop
    {
        //cout << "updating info ";
        sight.update();
        //cout << sight.angle << "\t";
        if (sight.angle == 0) {
			cout << "setting off";
            digitalWrite(G1, LOW);
            digitalWrite(G2, LOW);
        }
        else if (sight.angle == 1) { 
			cout << "setting left";
            digitalWrite(G1, HIGH);
            digitalWrite(G2, LOW);
        }
        else if (sight.angle == 2) {
			cout << "setting right";
            digitalWrite(G1, LOW);
            digitalWrite(G2, HIGH);
        }
        else if (sight.angle == 3) {
			cout << "setting good";
            digitalWrite(G1, HIGH);
            digitalWrite(G2, HIGH);
        }
        cout << endl;
        /*#ifdef GUI
            cout << "\tdrawing gui\n";
            imshow(name, sight.getFrame());
            cvWaitKey(1);
        #endif*/
    }
    /*while (true) {
	socket << "test";
	cout << "sent test string\n";
	}*/

    return 0;
}

