package com.backend.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.backend.blog.config.AppConstants;
import com.backend.blog.entities.Role;
import com.backend.blog.repositories.RoleRepo;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}

	@Bean
	ModelMapper modelmapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("dagdu@987"));
		
		try {
			Role adminRole = new Role();
			adminRole.setId(AppConstants.ADMIN_USER_ID);
			adminRole.setName(AppConstants.ADMIN_USER_NAME);
			
			Role normalRole = new Role();
			normalRole.setId(AppConstants.NORMAL_USER_ID);
			normalRole.setName(AppConstants.NORMAL_USER_NAME);
			
			List<Role> roles = List.of(adminRole,normalRole);
			
			List<Role> result = this.roleRepo.saveAll(roles);
			
			result.forEach(r ->
			{
				System.out.println(r.getName());
			});
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

}
