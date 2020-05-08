# ssl-enable-spring-boot

https://www.thomasvitale.com/https-spring-boot-ssl-certificate/
-----------------------------
Configuring SSL in Spring boot.
--> It uses keytool in JDK
steps
1. Get an SSL certificate
      Generate a self-signed SSL certificate
      Use an existing SSL certificate
2. Enable HTTPS in Spring Boot
3. Redirect HTTP requests to HTTPS
4. Distribute the SSL certificate to clients.

1a. Generate a self-signed SSL certificate
JKS keystore:
  $ keytool -genkeypair -alias tomcat -keyalg RSA -keysize 2048 -keystore keystore.jks -validity 3650 -storepass password

PKCS12 keystore(Recomended in prod)
  $ keytool -genkeypair -alias tomcat -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore keystore.p12 -validity 3650 -storepass password

  genkeypair: generates a key pair;
  alias: the alias name for the item we are generating;
  keyalg: the cryptographic algorithm to generate the key pair;
  keysize: the size of the key. We have used 2048 bits, but 4096 would be a better choice for production;
  storetype: the type of keystore;
  keystore: the name of the keystore;
  validity: validity number of days;
  storepass: a password for the keystore.

 verification
  $ keytool -list -v -keystore keystore.jks --> JKS
  $ keytool -list -v -storetype pkcs12 -keystore keystore.p12 --> PKCS12

Convert a JKS keystore into PKCS12
  $ keytool -importkeystore -srckeystore keystore.jks -destkeystore keystore.p12 -deststoretype pkcs12


1b. Use an existing SSL certificate
  $ keytool -import -alias tomcat -file myCertificate.crt -keystore keystore.p12 -storepass password


2. Enable HTTPS in Spring Boot
Inside application.properties
  server.port=8443

  server.ssl.key-store-type=PKCS12
  server.ssl.key-store=classpath:keystore.p12
  server.ssl.key-store-password=password
  server.ssl.key-alias=tomcat

  security.require-ssl=true (Depricated.. So use following code)
  
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .requiresChannel()
            .anyRequest()
            .requiresSecure();
    }
}
(Check this class in src folder)

To access this in browser enable the flag in chrome : chrome://flags/#allow-insecure-localhost 









