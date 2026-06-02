package NguyenQuocGiakhang.CuoiKyWeb2.service.validator;

import org.springframework.stereotype.Service;

import NguyenQuocGiakhang.CuoiKyWeb2.domain.dto.RegisterDTO;
import NguyenQuocGiakhang.CuoiKyWeb2.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Service
public class RegisterValidator implements ConstraintValidator<RegisterChecked, RegisterDTO> {

	private final UserService userService;

	public RegisterValidator(UserService userService) {
		this.userService = userService;
	}

	@Override
	public boolean isValid(RegisterDTO user, ConstraintValidatorContext context) {
		boolean valid = true;

		if (user.getPassword() == null || user.getConfirmPassword() == null
				|| !user.getPassword().equals(user.getConfirmPassword())) {
			context.buildConstraintViolationWithTemplate("Passwords nhập không chính xác")
					.addPropertyNode("confirmPassword")
					.addConstraintViolation()
					.disableDefaultConstraintViolation();
			valid = false;
		}

		if (user.getEmail() != null && this.userService.checkEmailExist(user.getEmail())) {
			context.buildConstraintViolationWithTemplate("Email đã tồn tại")
					.addPropertyNode("email")
					.addConstraintViolation()
					.disableDefaultConstraintViolation();
			valid = false;
		}

		return valid;
	}
}
