package movie.dbproject.domain.repository;

import movie.dbproject.domain.vo.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcMemberRepository implements MemberRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcMemberRepository(DataSource dataSource) {

        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public User save(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("user");

        Map<String, Object> parameter = new HashMap<>();
        parameter.put("id",user.getId());
        parameter.put("name",user.getName());
        parameter.put("pwd",user.getPwd());
        parameter.put("p_num",user.getP_num());
        parameter.put("e_mail",user.getE_mail());
        jdbcInsert.execute(parameter);

        return user;


    }

    @Override
    public Optional<User> findById(String id) {
        List<User> result = jdbcTemplate.query("select * from user where id= ?",userRowMapper(),id);
        return result.stream().findAny();
    }

    @Override
    public Optional<User> findByName(String name) {
        List<User> result = jdbcTemplate.query("select * from user where name = ?", userRowMapper(), name);
        return result.stream().findAny();
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("select * from user", userRowMapper());
    }

    private RowMapper<User> userRowMapper() {
        //람다식 (매개변수1,매개변수2) -> {실행문} 클레스 선언 x로 메서드 실행!
        return (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPwd(rs.getString("pwd"));
            user.setP_num(rs.getString("p_num"));
            user.setE_mail(rs.getString("e_mail"));

            return user;
        };
    }
}
