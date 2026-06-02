package NguyenQuocGiakhang.CuoiKyWeb2;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DbPingController {
	private final JdbcTemplate jdbcTemplate;

	public DbPingController(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@GetMapping("/db/ping")
	public String ping() {
		Integer one = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
		return "db ok: " + one;
	}
}

