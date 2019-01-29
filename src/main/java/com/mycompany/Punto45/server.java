/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Punto45;

import java.net.*;
import java.io.*;
import java.nio.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 2112076
 */
public class server {
    private static Socket clientSocket;
    public static void main(String[] args) throws IOException {
        
        ServerSocket serverSocket = null;
        Integer port;
        try {
            port = new Integer(System.getenv("PORT"));
        } catch (Exception e) {
            port = 35000;
        }

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        Socket clientSocket = null;
        while (true) {
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            server.clientSocket = clientSocket;
            cargador();
        }

    }

    public static void cargador() {
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader( clientSocket.getInputStream()));
            String inputLine, outputLine;
            String inputu = null;
            String format = null;
            String data = null;
            byte[] bytes = null;
            while ((inputLine = in.readLine()) != null) {
                try {
                    if (inputLine.startsWith("GET")) {
                        inputu = inputLine;
                    }
                } catch (java.lang.StringIndexOutOfBoundsException e) {
                }
                //System.out.println(inputLine);
                if (!in.ready()) {
                    break;
                }
            }
            if (inputu != null) {
                String[] temp;
                temp = inputu.split(" ");
                String flag = "";
                try {
                    if (temp[1].endsWith(".html")) {
                        bytes = Files.readAllBytes(new File("./" + temp[1].substring(1)).toPath());
                        data = "" + bytes.length;
                        format = "text/html";
                    } else /*if (temp[1].endsWith("png"))*/ {
                        bytes = Files.readAllBytes(new File("./" + temp[1].substring(1)).toPath());
                        data = "" + bytes.length;
                        format = "image/png";
                    }/** else {
                        bytes = Files.readAllBytes(new File("./error.html").toPath());
                        data = "" + bytes.length;
                        format = "text/html";

                    }**/
                    } catch (NoSuchFileException e) {
                    bytes = Files.readAllBytes(new File("./error.html").toPath());
                    data = "" + bytes.length;
                    format = "text/html";
                    }
                inputu = flag;
            }
            outputLine = "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: "
                    + format
                    + "\r\n"
                    + "Content-Length: "
                    + data
                    + "\r\n\r\n";
            byte[] bite = outputLine.getBytes();
            try {
                byte[] salida = new byte[bytes.length + bite.length];
                for (int i = 0; i < bite.length; i++) {
                    salida[i] = bite[i];
                }
                for (int i = bite.length; i < bite.length + bytes.length; i++) {
                    salida[i] = bytes[i - bite.length];
                }
                clientSocket.getOutputStream().write(salida);
            } catch (java.lang.NullPointerException e) {

            }
            clientSocket.close();

        } catch (IOException e) {
            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, e);
        }

    }
}
