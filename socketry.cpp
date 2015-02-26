#include "socketry.h"

using namespace socketry;

Socketry::Socketry(char netType)
{
    type = netType;
    socket = socket(AF_INET, SOCK_STREAM, IPPROTO_IP);
    if(socket < 0)
    {
        cerr << "Could not connect. Exiting.";
        exit(-1);
    }
    //Happy ending.
}

Socketry::~Socketry()
{
    //Where I delete stuff. Todo.
}

int Socketry::connect(char* ip, int port)
{
    if (type != CLIENT) //Check to make sure that the right initialization method is being used
    {
        std::cerr << "Wrong instantiation type. Omit the IP for a socket server.\n";
        return -1
    }
    server = gethostbyname(ip);
    if (server == NULL) return -1;
    
}

int Socketry::start(int port, int queue = 5, bool broadcast = false)
{
    if (type != SERVER) //Check to make sure that the right initialization method is being used
    {
        std::cerr << "Wrong instantiation type. Include the IP for a socket client.\n";
        return -1;
    }
    sockAddr.sin_family = AF_INET; //Basic socket setup
    sockAddr.sin_addr.s_addr = INADDR_ANY;
    if (broadcast) sockAddr.sin_addr.s_addr = INADDR_BROADCAST;
    sockAddr.sin_port = htons(port);
    if (bind(socket, (const sockaddr *) &sockAddr, sizeof(sockAddr)) < 0) return -1; //Error checking
    listen(socket, queue);
    cliSocket = accept(socket, cliAddr, sizeof(cliAddr)); //Receive connection into cliocket
    if (cliSocket < 0) return -1; //Error checking
    return 0; //Happy ending.
}

