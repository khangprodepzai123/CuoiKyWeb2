package NguyenQuocGiakhang.CuoiKyWeb2.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import NguyenQuocGiakhang.CuoiKyWeb2.domain.Role;
import NguyenQuocGiakhang.CuoiKyWeb2.domain.User;
import NguyenQuocGiakhang.CuoiKyWeb2.repository.RoleRepository;
import NguyenQuocGiakhang.CuoiKyWeb2.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

	private final RoleRepository roleRepository;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public DataInitializer(
			RoleRepository roleRepository,
			UserRepository userRepository,
			PasswordEncoder passwordEncoder) {
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(String... args) {
		Role userRole = ensureRole("USER", "Người dùng");
		ensureRole("ADMIN", "Quản trị viên");

		if (!userRepository.existsByEmail("admin@cuoikyweb.com")) {
			User admin = new User();
			admin.setEmail("admin@cuoikyweb.com");
			admin.setFullName("Admin CuoiKyWeb");
			admin.setPassword(passwordEncoder.encode("Admin@12345"));
			admin.setRole(roleRepository.findByName("ADMIN"));
			userRepository.save(admin);
		}

		if (!userRepository.existsByEmail("user@cuoikyweb.com")) {
			User demoUser = new User();
			demoUser.setEmail("user@cuoikyweb.com");
			demoUser.setFullName("Demo User");
			demoUser.setPassword(passwordEncoder.encode("User@12345"));
			demoUser.setRole(userRole);
			userRepository.save(demoUser);
		}
	}

	private Role ensureRole(String name, String description) {
		Role role = roleRepository.findByName(name);
		if (role == null) {
			role = new Role();
			role.setName(name);
			role.setDescription(description);
			role = roleRepository.save(role);
		}
		return role;
	}
}
