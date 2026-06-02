package NguyenQuocGiakhang.CuoiKyWeb2.service;

import org.springframework.stereotype.Service;

import NguyenQuocGiakhang.CuoiKyWeb2.domain.User;
import NguyenQuocGiakhang.CuoiKyWeb2.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User getUserByEmail(String email) {
		return this.userRepository.findByEmail(email);
	}

	public boolean checkEmailExist(String email) {
		return this.userRepository.existsByEmail(email);
	}
}
