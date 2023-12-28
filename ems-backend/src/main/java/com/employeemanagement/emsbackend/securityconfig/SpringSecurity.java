package com.employeemanagement.emsbackend.securityconfig;


import com.employeemanagement.emsbackend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
// This means:: it tells spring to enable its web security support.
@EnableWebSecurity
public class SpringSecurity{

    private final EmployeeRepository employeeRepository;

    @Autowired
    public SpringSecurity(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }


    @Bean
    public InMemoryUserDetailsManager userDetailsService(){
        UserDetails user = User.withUsername("Jayesh").password(passwordEncoder().encode("user"))
                .roles("USER").build();

        UserDetails admin = User.withUsername("Chandan").password(passwordEncoder().encode("admin"))
                .roles("ADMIN").build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public SecurityFilterChain filerChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeHttpRequests().requestMatchers("/api/employees/**").hasRole("USER")
                .anyRequest().authenticated().and().httpBasic();

        httpSecurity.csrf().disable();
        return httpSecurity.build();
    }


    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }





//    @Override
//    protected void configure(HttpSecurity http){
//        http.authorizeHttpRequests().antMatcher("api/employee/**").authenticated().anyRequest().permitAll().and().httpBasic();
//        http.csrf().disable();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//        http.authorizeHttpRequests((authz) -> authz.requestMatchers("/api/employee/**").permitAll()
//                .anyRequest().authenticated()).build();
//
//        return http.build();
////        http.csrf().disable();
//    }
//
//    @Bean
//    protected SecurityFilterChain filterChain(AuthenticationManagerBuilder auth) throws Exception {
//        UserDetailsService userDetailsService = (String firstname) -> {
//            Employee emp = employeeRepository.findByFirstName(firstname);
//            if(emp != null){
//                return User.builder().username(emp.getFirstName())
//                        .password(emp.getLastName()).build();
//            }
//            throw new UsernameNotFoundException("SERVICE_INVALID_PASS");
//        };
//
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }
//
//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        UserDetailsService userDetailsService = (String firstname) -> {
//            Employee emp = employeeRepository.findByFirstName(firstname);
//            if(emp != null){
//                return  new InMemoryUserDetailsManager(User.builder().username(emp.getFirstName())
//                        .password(emp.getLastName()).build());
//            }
//            throw new UsernameNotFoundException("SERVICE_INVALID_PASS");
//        };
//
//        public InMemoryUserDetailsManager userDetailsService() {
//            UserDetails user = User.withUserDetails()
//                    .username("user")
//                    .password("password")
//                    .roles("USER")
//                    .build();
//            return new InMemoryUserDetailsManager(user);
//
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//
//        return new InMemoryUserDetailsManager(user);
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }

}
