package movie.dbproject.domain.repository;

import movie.dbproject.domain.vo.User;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

     User save(User user);
     Optional<User> findById(String id);
     Optional<User> findByName(String name);
    List<User> findAll();
}
