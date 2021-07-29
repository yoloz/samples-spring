package indi.yoloz.example.oauth2ssoclientusermsg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

@SpringBootApplication
@EnableOAuth2Sso
public class Oauth2SsoClientUsermsgApplication {

	public static void main(String[] args) {
		SpringApplication.run(Oauth2SsoClientUsermsgApplication.class, args);
	}

}
