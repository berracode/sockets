package com.pocotones.gettime;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GetDateTimeHttpServer {

    public static void main(String[] args) {
        int portNumber = 9090; // Puerto en el que escuchará el servidor

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            System.out.println("Servidor escuchando en el puerto " + portNumber + "...");

            // Esperar una conexión
            Socket clientSocket = serverSocket.accept(); //navegqdor

            // Obtener la hora actual
            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedTime = currentTime.format(formatter);

            // Construir la respuesta en formato HTML -- SSR
            String response = "<html><body><h1>Hora actual:</h1><p>" + formattedTime + "</p></body></html>";

            // Escribir la respuesta en el socket del cliente
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: text/html");
            out.println("Content-Length: " + response.length());
            out.println(); // Línea en blanco que indica el fin de los headers
            out.println(response);

            // Cerrar el socket del cliente
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Error al iniciar el servidor en el puerto " + portNumber);
            e.printStackTrace();
        }
    }
}
