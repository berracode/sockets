package com.pocotones.securesocket;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class GetDateTimeTLSServer {

    public static void main(String[] args) {

        int portNumber = 8443;

        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());

            char[] password = "changeit".toCharArray();
            keyStore.load(new FileInputStream("/Users/juanhernandez/Documents/PProjects/JavaProjects/sockets/cert-test-udea.jks"), password);

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
