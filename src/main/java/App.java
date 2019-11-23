import com.beans.User;
import com.constant.AUTHORITIES;
import com.repository.impl.UserRepositoryImplJdbc;

public class App {

	public static void main(String[] args) {
		UserRepositoryImplJdbc userRepoJdbc = new UserRepositoryImplJdbc();
		User user = User.builder()
				.username("hoangquynh")
				.password("hoangquynh")
				.email("hoang.quynh7721@gmail.com")
				.authority(AUTHORITIES.STUDENT_ROLE)
				.build();
		userRepoJdbc.save(user);
	}
}
