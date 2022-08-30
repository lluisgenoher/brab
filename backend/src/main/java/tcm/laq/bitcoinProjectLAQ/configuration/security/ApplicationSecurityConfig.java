package tcm.laq.bitcoinProjectLAQ.configuration.security;


import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import tcm.laq.bitcoinProjectLAQ.configuration.security.jwt.JwtConfig;
import tcm.laq.bitcoinProjectLAQ.configuration.security.jwt.JwtTokenVerifierFilter;
import tcm.laq.bitcoinProjectLAQ.configuration.security.jwt.JwtUsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    private static final String USERS_QUERY = "select email, password, enabled from absuser where email = ?";
    private static final String AUTHORITIES_QUERY = "select email, u_role from authorities where email = ?";

    private DataSource dataSource;

    private final JwtConfig jwtConfig;

    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, DataSource dataSource, JwtConfig jwtConfig) {
        this.passwordEncoder = passwordEncoder;
        this.dataSource = dataSource;
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernamePasswordAuthenticationFilter(authenticationManager(), jwtConfig))
                .addFilterAfter(new JwtTokenVerifierFilter(jwtConfig),JwtUsernamePasswordAuthenticationFilter.class)

                .authorizeRequests()
                .antMatchers( "/*.html").permitAll()
                .antMatchers("/brokers/**").hasRole("BROKER")
                .antMatchers("/bidders/**").hasRole("BIDDER")
                .antMatchers("/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated();
    }

    //Configure authentication manager
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(USERS_QUERY)
                .authoritiesByUsernameQuery(AUTHORITIES_QUERY)
                .passwordEncoder(passwordEncoder);
    }
}
