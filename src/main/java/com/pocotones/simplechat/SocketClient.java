package com.pocotones.simplechat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
public class SocketClient {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("192.168.1.102", 9090);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader consola = new BufferedReader(new InputStreamReader(System.in));

            String mensajeCliente;
            String mensajeServidor;

            while (true) {
                // Enviar mensaje al servidor
                System.out.print("Cliente: ");
                mensajeCliente = consola.readLine();
                out.println(mensajeCliente);
                if (mensajeCliente.equalsIgnoreCase("exit")) {
                    break;
                }

                // Leer mensaje del servidor
                mensajeServidor = in.readLine();
                System.out.println("Servidor: " + mensajeServidor);
            }

            in.close();
            out.close();
            socket.close();
            consola.close();
        } catch (IOException e) {
            System.err.println("Error de entrada/salida: " + e.getMessage());
        }
    }
}