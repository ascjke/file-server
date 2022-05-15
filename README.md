# file-server

It's so easy to lose your precious data — unless you store it on a server. Besides, file server allow you to access your files remotely, no matter where you are. 


This program sends client files to the server. The server keeps the files and sends them back on request until you decide to delete them.
Server  recognize each file by its unique identifier. If the file is created successfully, the server output an integer identifier. 
If the creation of the file failed, no identifier is needed.

Main operations:

    get file from the server
    save file to the server
    delete file from the server


Features:

    client-server app
    multi threading server socket (ThreadPool)
    Here is applying ObjectOutputStream/ObjectInputStream (Serialization)
