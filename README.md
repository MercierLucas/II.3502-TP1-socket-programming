# II.3502-TP1-socket-programming

This project is the 1st project of the II.3502 - Distributed architecture and programming
It is composed of 3 tasks

## 1 - Simple FTP server
This part was an introduction to socket programming. 
The aim was to replicate a simple FTP client to server interaction.
The process is simple and quite similar to the first part ( the calculation server).
1 - The client retrieve the content of the file placed in "Resources/file.txt"
2 - It then tries to connect to the server
3 - If the connection is successfull then it retrieve the file content and create a new file server side in which the content is appended.
4 - It finally acknowledge the client if the transfer was a success or not.

-- Note: It's not a real FTP server as we don't send a file but the content of the file and recreate it in the server, whereas real ftp send the file directly.


## 2 - Threaded server
In first part, the server was only able to accept one client at the time. In this part we want to improve the server code to accept any client to login at the same time.

The process is quite similar so we won't repeat past steps but only explain what changed.

Instead of accepting directly the first request from a client the server while create a new Thread for each client that connect to this server. Each thread manage inputs / calculations and output to his own client.

A big advantage from this is also that each thread is independant. If one crash or raise any errors, it's handled by the corresponding thread and doesn't impact other threads.


## 3 - Chat server
The last part of this project was to create chat that will allow multiple user to communicate with each other through a GUI.
Once again we reused the previous part to achieve this and mainly the threaded server code.

The only difference was the ability for each thread to broadcast messages to all clients when a message waw received. This was simply achieve by sharing a List of all sockets (clients) that logged in and to send each of them the desired message.
