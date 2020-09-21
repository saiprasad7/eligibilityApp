package com.eligibility.benefit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import com.eligibility.benefit.Service.UserService;


@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
 @Autowired
	private UserService userDetailsService;
 
 @Value("${token.expired}")
	private int expiredTime;
 @Value("${token.scope}")
 private String scope;
 @Value("${token.grandtype}")
 private String grandType;
 
 @Value("${token.clientaut}")
 private String clientauth;
 @Value("${token.key}")
 private String key;
/* token.scope=all
		 token.grandtype=password
		 token.clientaut=eligibilitydb
		 token.key=secret*/

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.allowFormAuthenticationForClients();
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
				.withClient(clientauth)
				.secret(passwordEncoder.encode(key))
				.authorizedGrantTypes(grandType,"client_credentials")
				.scopes(scope)
				.accessTokenValiditySeconds(expiredTime);
	
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore())
			.authenticationManager(authenticationManager).userDetailsService(userDetailsService);
			
	}

	

	@Bean
	public TokenStore tokenStore() {
		return new InMemoryTokenStore();
	}

}