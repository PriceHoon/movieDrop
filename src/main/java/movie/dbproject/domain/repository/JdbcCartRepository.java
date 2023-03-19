package movie.dbproject.domain.repository;

import lombok.extern.slf4j.Slf4j;
import movie.dbproject.domain.vo.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Slf4j
public class JdbcCartRepository implements CartRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcCartRepository(DataSource dataSource) {
        jdbcTemplate= new JdbcTemplate(dataSource);
    }
    @Override
    public Cart saveM(Cart cart) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("cart");

        Map<String, Object> parameter = new HashMap<>();
        parameter.put("id",cart.getId());
        parameter.put("contentid",cart.getContentid());
        parameter.put("posterPath",cart.getPosterPath());
        parameter.put("title",cart.getTitle());
        parameter.put("watch",cart.getWatch());
        parameter.put("del_flag",cart.getDel_flag());
        parameter.put("type",cart.getType());
        jdbcInsert.execute(parameter);

        return cart;

    }

    @Override
    public Optional<Cart> findById(String id) {
        List<Cart> result = jdbcTemplate.query("select * from cart where id = ?",cartRowMapper(),id);
        return result.stream().findAny();
    }

    @Override
    public List<Cart> findByIdAll(String id) {
        List<Cart> result =jdbcTemplate.query("select * from cart where id = ?",cartRowMapper(),id);
        return result;
    }

    @Override
    public List<Cart> findAll() {
        List<Cart> result = jdbcTemplate.query("select * from cart",cartRowMapper());
        return result;
    }

    @Override
    public Optional<Cart> findConId(String id,String contentid) {
        List<Cart> result = jdbcTemplate.query("select * from cart where id=? AND contentid = ?",cartRowMapper(),id,contentid);

        return result.stream().findAny();
    }

    @Override
    public void delete(String del_flag,Cart cart) {

        jdbcTemplate.update("update cart set del_flag = ? where id=? AND contentid=?",del_flag,cart.getId(),cart.getContentid());

    }

    @Override
    public void watchCheck(Cart cart)  {

        jdbcTemplate.update("update cart set watch = ? where id=? AND contentid = ?",cart.getWatch(),cart.getId(),cart.getContentid());
    }

    private RowMapper<Cart> cartRowMapper() {
        return (rs,rowNum) ->{
            Cart cart = new Cart();
            cart.setWatch(rs.getString("watch"));
            cart.setId(rs.getString("id"));
            cart.setContentid(rs.getString("contentid"));
            cart.setPosterPath(rs.getString("posterPath"));
            cart.setTitle(rs.getString("title"));
            cart.setDel_flag(rs.getString("del_flag"));
            cart.setType(rs.getString("type"));
            return cart;
        };
    }
}
