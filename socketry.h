#ifndef TALKATIVE_H
#define TALKATIVE_H

#include <iostream>
#include <sys/types.h>   // Types used in sys/socket.h and netinet/in.h
#include <netinet/in.h>  // Internet domain address structures and functions
#include <sys/socket.h>  // Structures and functions used for socket API
#include <netdb.h>       // Used for domain/DNS hostname lookup

using namespace std;

namespace socketry
{

class Socketry
{
public:
    Socketry(char);
    ~Socketry();
    int connect(char*, int);
    int connect(int);
    int send(String);
    String receive();

private:
    int socket;
    char type;
};

}

#endif // TALKATIVE_H
