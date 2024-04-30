package com.pocotones.securesocket;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.*;
import java.security.cert.CertificateException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GetDateTimeTLSServer {

    public static void main(String[] args) {

        int portNumber = 8443;

        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());

            char[] password = "changeit".toCharArray();
            keyStore.load(new FileInputStream("serverks.jks"), password);

            keyManagerFactory.init(keyStore, password);
            sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

            SSLServerSocketFactory socketFactory = sslContext.getServerSocketFactory();
            SSLServerSocket serverSocket = (SSLServerSocket) socketFactory.createServerSocket(portNumber);

            System.out.println("Servidor escuchando en el puerto " + portNumber + "...");

            while (true) {
                SSLSocket clientSocket = (SSLSocket) serverSocket.accept();
                System.out.println("CLiente conectado de manera segura desde el puerto: "+ clientSocket.getPort());

                LocalDateTime currentTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedTime = currentTime.format(formatter);

                String response = "<html><body><h1>Hora actual:</h1><p>" + formattedTime + "</p></body></html>";

                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println("HTTP/1.1 200 OK");
                out.println("Content-Type: text/html");
                out.println("Content-Length: " + response.length());
                out.println();
                out.println(response);

                clientSocket.close();
            }
        } catch (IOException | NoSuchAlgorithmException | KeyManagementException | KeyStoreException |
                 CertificateException | UnrecoverableKeyException e) {
            e.printStackTrace();
        }

    }
}
