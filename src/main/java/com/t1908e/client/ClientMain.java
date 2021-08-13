package com.t1908e.client;

import com.t1908e.key.Hash;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.security.PublicKey;
import java.util.Map;

public class ClientMain {
    public static void main(String[] args) {
        String sentence_to_server;
        String sentence_from_server;
        try {
            System.out.print("Input from client: ");
            BufferedReader inFromUser =
                    new BufferedReader(new InputStreamReader(System.in));
            String plainMsg = inFromUser.readLine();
            Map<String, Object> keys = Hash.getRSAKeys();
            PublicKey publicKey = (PublicKey) keys.get("public");
            sentence_to_server = Hash.encryptMessage(plainMsg, publicKey);
            Socket clientSocket = new Socket("127.0.0.1", 8080);
            DataOutputStream outToServer =
                    new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer =
                    new BufferedReader(new
                            InputStreamReader(clientSocket.getInputStream()));
            outToServer.writeBytes(sentence_to_server + '\n');
            sentence_from_server = inFromServer.readLine();
            System.out.println(sentence_from_server);
            clientSocket.close();
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }
    }
}
