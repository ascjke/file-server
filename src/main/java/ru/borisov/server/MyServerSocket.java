package ru.borisov.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyServerSocket {

    private final String address;
    private final int port;
    private final ResponseHandler responseHandler;
    private final static int POOL_SIZE = Runtime.getRuntime().availableProcessors();

    public MyServerSocket(String address, int port, ResponseHandler responseHandler) {
        this.address = address;
        this.port = port;
        this.responseHandler = responseHandler;
    }

    public void start() throws IOException {

        try (ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address))) {

            ExecutorService executor = Executors.newFixedThreadPool(POOL_SIZE);
            while (!Thread.interrupted()) {
                Socket socket = acceptClientSocket(server);
                System.out.println("Server started!");
                if (socket == null) {
                    executor.shutdownNow();
                    break;
                }

                executor.submit(new ClientHandler(responseHandler, server, socket));
            }
        }
    }

    private Socket acceptClientSocket(ServerSocket server) {
        try {
            return server.accept();
        } catch (IOException e) {
            return null;
        }
    }
}
