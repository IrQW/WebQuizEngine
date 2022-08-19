package engine.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<MyUser, Long> {

    public default MyUser findUserByUsername(String username) {
        for (MyUser user:
                this.findAll()) {
            if (user.getEmail().equals(username)){
                return user;
            }
        }
        return null;
    }
}
