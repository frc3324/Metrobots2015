/* Socketry -- a socket communications library for C++
 * LINUX ONLY (for the time being)
 */

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

char CLIENT = 1;
char SERVER = 2;

class Socketry
{
public:
    Socketry(char);
    ~Socketry();
    int connect(char*, int);
    int start(int, int, bool);
    int send(char*);
    char* receive();

private:
    int server, client;
    char type;
    socklen_t clilen;
    char buffer[256];
    struct sockaddr_in sockAddr, cliAddr;
    struct hostent *servIP;
};

}

#endif // TALKATIVE_H
