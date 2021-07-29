package indi.yoloz.example.oauth2.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * @author yoloz
 */

@SpringBootApplication
@EnableAuthorizationServer
public class Oauth2SSOServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2SSOServerApplication.class,args);
    }
}
