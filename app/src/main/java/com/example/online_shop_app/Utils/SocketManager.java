package com.example.online_shop_app.Utils;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class SocketManager {
    private static Socket socket;
    private static final String SERVER_URL = "http://192.168.43.159:5000";

    public static Socket getSocket() {
        if (socket == null) {
            try {
                socket = IO.socket(SERVER_URL);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return socket;
    }
}
