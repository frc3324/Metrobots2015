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
}

Socketry::~Socketry()
{

}

int Socketry::connect(char* ip, int port)
{
    if (type != CLIENT)
    {
        std::cerr << "Wrong instantiation type. Omit the IP for a socket server.\n";
        return -1
    }
}

int Socketry::connect(int port)
{
    if (type != SERVER)
    {
        std::cerr << "Wrong instantiation type. Include the IP for a socket client.\n";
        return -1;
    }
}
