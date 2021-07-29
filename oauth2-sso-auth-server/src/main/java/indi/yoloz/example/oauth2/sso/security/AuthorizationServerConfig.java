package indi.yoloz.example.oauth2.sso.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author yoloz
 */

@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {


    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    //授权端点开放
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();
        security.tokenKeyAccess("isAuthenticated()"); //开启/oauth/check_token验证端口认证权限访问
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client1")
                .secret(passwordEncoder.encode("client1-secret"))
                .authorizedGrantTypes("implicit", "authorization_code", "client_credentials")
                .scopes("read", "write", "webclient")
                .autoApprove(false)
                .accessTokenValiditySeconds(3600)
                .redirectUris("http://www.baidu.com")
                .and()
                .withClient("client2")
                .secret(passwordEncoder.encode("client2-secret"))
                .authorizedGrantTypes("implicit", "authorization_code", "client_credentials")
                .scopes("read", "write", "webclient")
                .autoApprove(false)
                .accessTokenValiditySeconds(3600)
                .redirectUris("http://www.baidu.com");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
        endpoints.accessTokenConverter(jwtAccessTokenConverter());
        endpoints.tokenStore(jwtTokenStore());
    }

    @Bean
    public JwtTokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    //JwtAccessTokenConverter是用来生成token的转换器，而token令牌默认是有签名的，且资源服务器需要验证这个签名。
    // 此处的加密及验签包括两种方式：对称加密、非对称加密（公钥密钥）
    //对称加密需要授权服务器和资源服务器存储同一key值，而非对称加密可使用密钥加密，暴露公钥给资源服务器验签
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey("yoloz");
        return jwtAccessTokenConverter;
    }
}
