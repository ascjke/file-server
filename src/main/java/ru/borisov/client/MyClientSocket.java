package ru.borisov.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import static ru.borisov.utils.Configurations.SUCCESS;

public class MyClientSocket {

    private final String ADRESS;
    private final int PORT;

    public MyClientSocket(String address, int port) {
        this.ADRESS = address;
        this.PORT = port;
    }

    public String sendPutRequest(String request, byte[] fileBytes) throws IOException {
        try (
                Socket socket = new Socket(InetAddress.getByName(ADRESS), PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {
            output.writeUTF(request);
            output.writeInt(fileBytes.length);
            output.write(fileBytes);

            return input.readUTF();
        }
    }

    public byte[] sendGetRequest(String request) throws IOException {
        try (
                Socket socket = new Socket(InetAddress.getByName(ADRESS), PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {
            output.writeUTF(request);

            String responseCode = input.readUTF();
            if (SUCCESS.equals(responseCode)) {
                int length = input.readInt();
                byte[] fileBytes = new byte[length];
                input.readFully(fileBytes, 0, fileBytes.length);
                return fileBytes;
            }

            return null;
        }
    }

    public String sendDeleteRequest(String request) throws IOException {
        try (
                Socket socket = new Socket(InetAddress.getByName(ADRESS), PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {
            output.writeUTF(request);

            return input.readUTF();
        }
    }

    public void sendExitRequest(String request) throws IOException {
        try (
                Socket socket = new Socket(InetAddress.getByName(ADRESS), PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {
            output.writeUTF(request);
        }
    }
}
