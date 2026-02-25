//package me.shinsunyoung.springbootdeveloper.config;
//
//import lombok.RequiredArgsConstructor;
//import me.shinsunyoung.springbootdeveloper.service.UserDetailService;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.ProviderManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class WebSecurityConfig {
//
//
//    private final UserDetailService userService;
//
//    // 1. 스피링 시큐리티 기능 비활성화: 스프링 시큐리티의 모든 기능을 사용하지 않게 설정 (인가, 인증을 모든 곳에 적용X)
//    @Bean
//    public WebSecurityCustomizer configure() {
//        return (web) -> web.ignoring()
//                .requestMatchers(toH2Console())
//                .requestMatchers(new AntPathRequestMatcher("/static/**"));
//    }
//
//
//    // 2. 특정 HTTP 요청에 대한 웹 기반 보안 구성: 인증, 인가 및 로그인, 로그아웃 관련 설정
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http
//                .authorizeRequests(auth -> auth // 3. 인증, 인가 설정: 특정 경로에 대한 액세스 설정
//                        .requestMatchers( // 특정 URL에 대한 엑세스 설정
//                                new AntPathRequestMatcher("/login"),
//                                new AntPathRequestMatcher("/signup"),
//                                new AntPathRequestMatcher("/user")
//                        ).permitAll() // "/login", "/signup", "/user"로 요청 -> 인증/인가 없이 접근 가능
//                        .anyRequest().authenticated()) // 그 이외의 url 요청에 대한 설정 // authenticated: 별도의 인가는 필요 없지만 인증이 성공된 상태여야 함
//                .formLogin(formLogin -> formLogin // 4. 폼 기반 로그인 설정
//                        .loginPage("/login") // 로그인 페이지 경로 설정
//                        .defaultSuccessUrl("/articles") // 로그인이 완료되었을 때 이동할 경로 설정
//                )
//                .logout(logout -> logout // 5. 로그아웃 설정
//                        .logoutSuccessUrl("/login") // 로그아웃이 완료되었을 때 이동할 경로 설정
//                        .invalidateHttpSession(true) // 로그아웃 이후에 세션을 전체 삭제할지 여부 설정
//                )
//                .csrf(AbstractHttpConfigurer::disable) // 6. csrf 비활성화
//                .build();
//    }
//
//    // 7. 인증 관리자 관련 설정
//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailService userDetailService) throws Exception {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userService); // 8. 사용자 정보 서비스 설정; userDetailService -> 사용자 정보를 가져올 서비스 설정
//        authProvider.setPasswordEncoder(bCryptPasswordEncoder); // 비밀번호를 암호화하기 위한 인코더 설정
//        return new ProviderManager(authProvider);
//    }
//
//    // 9. 패스워드 인코더로 사용할 빈 등록
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}