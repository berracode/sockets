# Generación de certificado autofirmado

## Usando openSSL + keytool si es una aplicación Java

1. Generar certificado
```sh
openssl req -x509 -newkey rsa:4096 -keyout key.pem -out cert.pem -days 365
```
genera una clave privada (key.pem) y una llave publica (cert.pem). Adecuado usar openSSL para interoperabilidad, pero como estamos 
trabajando en Java podemos usar directamente keytool

2. Convertir PEM PKCS12

Si estamos trabajando una aplicación Java, el código en la clase GetDateTimeTLSServer no soporta 
el formato PEM (Privacy Enhanced Mail). Por tanto debemos convertirlo a PKCS12 (Public Key Cryptography Standards #12) para luego agregarlo a un keystore jks.

```sh
openssl pkcs12 -export -in cert.pem -inkey key.pem -out certificate.p12 -name "certificate"
```

3. Generar java keystore (jks)

```sh
keytool -importkeystore -srckeystore certificate.p12 -srcstoretype pkcs12 -destkeystore cert.jks
```

finalmente, tenemos nuestro archivo que representa nuestro java keystore

# Usando keytool (aplicaciones Java regularmente)

Generamos nuestras claves 

```sh
keytool -genkey -keystore serverks.jks -keyalg RSA -keysize 4096 -alias mykey
```



