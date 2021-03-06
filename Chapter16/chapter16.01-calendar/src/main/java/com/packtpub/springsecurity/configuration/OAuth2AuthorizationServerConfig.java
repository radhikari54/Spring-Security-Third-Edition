package com.packtpub.springsecurity.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.*;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
//@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class OAuth2AuthorizationServerConfig
//        extends ResourceServerConfigurerAdapter
//    extends AuthorizationServerConfigurerAdapter
// see http://blog.monkey.codes/how-to-use-jwt-and-oauth-with-spring-boot/
{

    @Value("${security.oauth2.resource.jwt.keyPair.keystore}")
    private String keystore;

    @Value("${security.oauth2.resource.jwt.keyPair.alias}")
    private String keyPairAlias;

    @Value("${security.oauth2.resource.jwt.keyPair.storePassword}")
    private String keyStorePass;

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        KeyPair keyPair = new KeyStoreKeyFactory(
                new ClassPathResource(keystore),
                keyStorePass.toCharArray()
        ).getKeyPair(keyPairAlias);

        converter.setKeyPair(keyPair);
        return converter;
    }

} // The End...
