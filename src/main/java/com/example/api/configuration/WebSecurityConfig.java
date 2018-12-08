package com.example.api.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.example.api.services.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	CustomizeAuthenticationSuccessHandler customizeAuthenticationSuccessHandler;

	@Bean
	public UserDetailsService mongoUserDetails() {
		return new CustomUserDetailsService();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
	        CorsConfiguration configuration = new CorsConfiguration();
	        configuration.setAllowedOrigins(Arrays.asList("*"));
	        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "DELETE", "PUT", "PATCH"));
	        configuration.setAllowedHeaders(Arrays.asList("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"));
	        configuration.setAllowCredentials(true);
	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", configuration);
	        return source;
	    }
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		UserDetailsService userDetailsService = mongoUserDetails();
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
				.authorizeRequests()
//				.antMatchers("/").permitAll()
				 .antMatchers(HttpMethod.OPTIONS.GET, "/**").permitAll()
				.antMatchers("/login").permitAll()
				.antMatchers("/logout").permitAll()
				.antMatchers("/image/**").permitAll()
				//.antMatchers(HttpMethod.POST,"/order").hasRole("CLIENT")
				.antMatchers(HttpMethod.POST,"/order").permitAll()
				.antMatchers("/auth").permitAll()
				.antMatchers("/login/**").permitAll()
				.antMatchers("/signup").permitAll().antMatchers("/admin/**")
				.hasAuthority("ADMIN").anyRequest().authenticated().and().csrf().disable().formLogin()
				.successHandler(customizeAuthenticationSuccessHandler).loginPage("/login").failureUrl("/login/false")
				.usernameParameter("email").passwordParameter("password").and().logout().logoutUrl("/logout")
				.deleteCookies("remember-me").logoutSuccessUrl("/logout-success").permitAll().and().rememberMe()
				// .logoutRequestMatcher(new AntPathRequestMatcher("/logout")))
				.and().exceptionHandling();

		// .antMatchers("/", "/public/**").permitAll()
		// .antMatchers("/users/**").hasAuthority("ADMIN")
		// .anyRequest().fullyAuthenticated()
		// .and()
		// .formLogin()
		// .loginPage("/login")
		// .failureUrl("/login?error")
		// .usernameParameter("email")
		// .permitAll()
		// .and()
		// .logout()
		// .logoutUrl("/logout")
		// .deleteCookies("remember-me")
		// .logoutSuccessUrl("/")
		// .permitAll()
		// .and()
		// .rememberMe();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	}
}