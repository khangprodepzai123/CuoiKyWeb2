package NguyenQuocGiakhang.CuoiKyWeb2.controller.admin;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import NguyenQuocGiakhang.CuoiKyWeb2.domain.User;
import NguyenQuocGiakhang.CuoiKyWeb2.service.UploadService;
import NguyenQuocGiakhang.CuoiKyWeb2.service.UserService;
import jakarta.validation.Valid;

@Controller
public class UserController {

	private final UserService userService;
	private final UploadService uploadService;
	private final PasswordEncoder passwordEncoder;

	public UserController(
			UserService userService,
			UploadService uploadService,
			PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.uploadService = uploadService;
		this.passwordEncoder = passwordEncoder;
	}

	@RequestMapping("/admin/user")
	public String getUserPage(Model model) {
		List<User> users = this.userService.getAllUsers();
		model.addAttribute("users1", users);
		return "admin/user/show";
	}

	@RequestMapping("/admin/user/{id}")
	public String getUserDetailPage(Model model, @PathVariable long id) {
		User user = this.userService.getUserById(id);
		if (user == null) {
			return "redirect:/admin/user";
		}
		model.addAttribute("user", user);
		model.addAttribute("id", id);
		return "admin/user/detail";
	}

	@GetMapping("/admin/user/create")
	public String getCreateUserPage(Model model) {
		model.addAttribute("newUser", new User());
		return "admin/user/create";
	}

	@PostMapping("/admin/user/create")
	public String createUser(
			@ModelAttribute("newUser") @Valid User user,
			BindingResult bindingResult,
			@RequestParam("hoidanitFile") MultipartFile file) {
		if (bindingResult.hasErrors()) {
			return "admin/user/create";
		}

		if (this.userService.checkEmailExist(user.getEmail())) {
			bindingResult.rejectValue("email", "error.newUser", "Email đã tồn tại");
			return "admin/user/create";
		}

		String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");
		String hashPassword = this.passwordEncoder.encode(user.getPassword());

		user.setAvatar(avatar);
		user.setPassword(hashPassword);
		String roleName = (user.getRole() != null && user.getRole().getName() != null)
				? user.getRole().getName()
				: "USER";
		user.setRole(this.userService.getRoleByName(roleName));
		this.userService.handleSaveUser(user);
		return "redirect:/admin/user";
	}

	@RequestMapping("/admin/user/update/{id}")
	public String getUpdateUserPage(Model model, @PathVariable long id) {
		User currentUser = this.userService.getUserById(id);
		if (currentUser == null) {
			return "redirect:/admin/user";
		}
		model.addAttribute("newUser", currentUser);
		return "admin/user/update";
	}

	@PostMapping("/admin/user/update")
	public String postUpdateUser(@ModelAttribute("newUser") User user) {
		User currentUser = this.userService.getUserById(user.getId());
		if (currentUser != null) {
			currentUser.setAddress(user.getAddress());
			currentUser.setFullName(user.getFullName());
			currentUser.setPhone(user.getPhone());
			this.userService.handleSaveUser(currentUser);
		}
		return "redirect:/admin/user";
	}

	@GetMapping("/admin/user/delete/{id}")
	public String getDeleteUserPage(Model model, @PathVariable long id) {
		model.addAttribute("id", id);
		model.addAttribute("newUser", new User());
		return "admin/user/delete";
	}

	@PostMapping("/admin/user/delete")
	public String postDeleteUser(@ModelAttribute("newUser") User user) {
		this.userService.deleteAUser(user.getId());
		return "redirect:/admin/user";
	}
}
