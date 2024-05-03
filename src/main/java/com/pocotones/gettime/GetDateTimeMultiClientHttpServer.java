package com.pocotones.gettime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GetDateTimeMultiClientHttpServer {

    public static void main(String[] args) {
        int portNumber = 8080; // Puerto en el que escuchará el servidor

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            System.out.println("Servidor escuchando en el puerto " + portNumber + "...");
            while (true) {
                Socket clientSocket = serverSocket.accept(); // Espera una conexión
                System.out.println("CLiente conectado...");

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                StringBuilder requestBuilder = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null && !line.isEmpty()) {
                    requestBuilder.append(line).append("\n");
                }
                System.out.println("Solicitud del cliente:\n" + requestBuilder.toString());

                // Obtener la hora actual
                LocalDateTime currentTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedTime = currentTime.format(formatter);

                // Construir la respuesta en formato HTML
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
            }
        } catch (IOException e) {
            System.err.println("Error al iniciar el servidor en el puerto " + portNumber);
            e.printStackTrace();
        }
    }
}
