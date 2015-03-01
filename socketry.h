/* Socketry -- a socket communications library for C++
 * LINUX ONLY (for the time being)
 */

#ifndef TALKATIVE_H
#define TALKATIVE_H

#include <cstdlib>
#include <unistd.h>
#include <iostream>
#include <sys/types.h>   // Types used in sys/socket.h and netinet/in.h
#include <netinet/in.h>  // Internet domain address structures and functions
#include <sys/types.h>
#include <sys/socket.h>  // Structures and functions used for socket API
#include <netdb.h>       // Used for domain/DNS hostname lookup
#include <string.h>

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
    int link(char*, int);
    int serve(int, int, bool);
    int send(char*);
    char* receive();
    void end();

private:
    int server, client;
    char type;
    socklen_t clilen;
    char buffer[10];
    struct sockaddr_in servAddr, cliAddr;
    struct hostent *servIP;
};

}

void error(char*);

#endif // TALKATIVE_H
