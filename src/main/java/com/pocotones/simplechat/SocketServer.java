package com.pocotones.simplechat;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    private static int PORT = 9090;

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Servidor iniciado. Esperando conexiones en el puerto " + PORT + "...");

            Socket clienteSocket = serverSocket.accept();
            System.out.println("Cliente conectado desde " + clienteSocket.getInetAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clienteSocket.getOutputStream(), true);

            BufferedReader consola = new BufferedReader(new InputStreamReader(System.in));

            String mensajeCliente;
            String mensajeServidor;

            while (true) {
                // Leer mensaje del cliente
                mensajeCliente = in.readLine();
                if (mensajeCliente.equalsIgnoreCase("exit")) {
                    break;
                }
                System.out.println("Cliente: " + mensajeCliente);

                // Enviar mensaje al cliente
                System.out.print("Servidor: ");
                mensajeServidor = consola.readLine();
                out.println(mensajeServidor);
            }

            in.close();
            out.close();
            clienteSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            System.err.println("Error de entrada/salida: " + e.getMessage());
        }
    }
}