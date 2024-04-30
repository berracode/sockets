/*
 * @(#)SocketClient.java
 *
 * Copyright (c) BANCO DE CHILE (Chile). All rights reserved.
 *
 * All rights to this product are owned by BANCO DE CHILE and may only
 * be used under the terms of its associated license document. You may NOT
 * copy, modify, sublicense, or distribute this source file or portions of
 * it unless previously authorized in writing by BANCO DE CHILE.
 * In any event, this notice and the above copyright must always be included
 * verbatim with this file.
 */
package com.pocotones.simplechat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * SocketClient.
 *
 * @author Juan Hernandez.
 * @version 1.0.0, 29-04-2024
 */
public class SocketClient {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("192.168.1.161", 9090);

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