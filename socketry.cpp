 #include "socketry.h"

using namespace socketry;

Socketry::Socketry(char netType)
{
    type = netType;
    server = socket(AF_INET, SOCK_STREAM, IPPROTO_IP);
    if(server < 0) error("Could not connect.");
    servAddr.sin_family = AF_INET;
    //Happy ending.
}

Socketry::~Socketry()
{
    //Where I delete stuff. Todo.
}

int Socketry::link(char* ip, int port)
{
    if (type != CLIENT) //Check to make sure that the right initialization method is being used
    {
        std::cerr << "Wrong instantiation type. Omit the IP for a socket server.\n";
        return -1;
    }

    servIP = gethostbyname(ip); //Get IP from char*, and setup servAddr object
    if (servIP == NULL) return -1;
    memset((char *) &servAddr, 0, sizeof(servAddr)); //Make sure the servAddr object is empty
    memcpy((char *)servIP->h_addr, (char *)&servAddr.sin_addr.s_addr, servIP->h_length); //Copy stuff from servIP to servAddr
    servAddr.sin_port = htons(port);

    if (connect(server, (struct sockaddr *) &servAddr, sizeof(servAddr)) < 0) return -1; //Connect
    return 0; //Happy ending.
}

int Socketry::serve(int port, int queue = 5, bool broadcast = false)
{
    if (type != SERVER) //Check to make sure that the right initialization method is being used
    {
        std::cerr << "Wrong instantiation type. Include the IP for a socket client.\n";
        return -1;
    }

    servAddr.sin_addr.s_addr = INADDR_ANY; //Basic socket setup
    if (broadcast) servAddr.sin_addr.s_addr = INADDR_BROADCAST;
    servAddr.sin_port = htons(port);
    if (bind(server, (const sockaddr *) &servAddr, sizeof(servAddr)) < 0) return -1; //Error checking
    listen(server, queue);
    clilen = sizeof(client);
    client = accept(server, (struct sockaddr *) &client, &clilen); //Receive connection into cliocket
    if (client < 0) return -1; //Error checking
    return 0; //Happy ending.
}

int Socketry::send(char* data)
{
    if (write(server, data, strlen(data)) < 0) return -1;
    return 0; //Happy ending.
}

char* Socketry::receive()
{
    memset(buffer, 0, 10);
    if (read(server, buffer, 10) < 0) return "\0";
    return buffer; //Happy ending.
}

void Socketry::end()
{
    close(server); close(client); //Happy endings for everyone.
}

void error(char* msg)
{
    std::cerr << msg;
    std::exit(-1);
}
