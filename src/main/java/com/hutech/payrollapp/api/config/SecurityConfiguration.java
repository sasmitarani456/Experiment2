package com.hutech.payrollapp.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.hutech.payrollapp.api.repository.EmployeeRepository;
import com.hutech.payrollapp.api.serviceImpl.EmployeeServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter  {
		@Autowired
		private EmployeeServiceImpl employeeServiceImpl;

		@Autowired
		private EmployeeRepository employeeRepo;

		@Bean
		public BCryptPasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}

	@Bean
	public UserDetailsService userDetailsService() {
		return new EmployeeServiceImpl(employeeRepo);
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(employeeServiceImpl);
		auth.setPasswordEncoder(passwordEncoder());
		return auth;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/application**","/registration**","/v2/api-docs","/success","/loginSucess","/addEmployee","/addMultipartfile","/onboardEmployee","/verifyEmployee", "/createPassword", "/reset_password", "/createDesignation",
						"/findEmpInfo", "/paySlip", "/createBusinessUnit", "/viewBusinessUnit", "/createDepartment",
						"/createDesignation", "/createEmployeement", "/addOrganizationInfo", "/addProject",
						"/viewProject", "/createRole", "/addTimesheet", "/verify", "/forgotPasswordEmail",
						"/emp_forgot_password", "/emp_reset_password", "/addCalendarRegion", "/addHoliday",
						"/findHolidayList", "/findHolidayByRegion/{calendarRegion}", "/holidayList/{hId}", "/{hId}","/check")
				.permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.formLogin()
         		.loginPage("/login")
         		/*.defaultSuccessUrl("/loginSucess")*/
/*         		.loginPage("/adminlogin")
         		.defaultSuccessUrl("/success")*/
				.permitAll()
				.and()
				.logout()
				.invalidateHttpSession(true)
				.clearAuthentication(true)
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/adminlogin?logout")
				.permitAll().and().rememberMe().key("Abcd01234");
		http.csrf().disable();
		}	
}
