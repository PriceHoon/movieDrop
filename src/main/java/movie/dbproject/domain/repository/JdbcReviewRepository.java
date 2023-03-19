package movie.dbproject.domain.repository;

import movie.dbproject.domain.vo.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcReviewRepository implements ReviewRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcReviewRepository(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Review saveR(Review review) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("review");

        Map<String, Object> parameter = new HashMap<>();
        parameter.put("id",review.getId());
        parameter.put("title",review.getTitle());
        parameter.put("posterPath",review.getPosterPath());
        parameter.put("contents",review.getContents());
        parameter.put("watch",review.getWatch());
        parameter.put("del_flag",review.getDel_flag());
        parameter.put("star",review.getStar());
        parameter.put("contentid",review.getContentid());

        simpleJdbcInsert.execute(parameter);
        return review;
    }

    @Override
    public Optional<Review> findById(String id) {
        List<Review> result = jdbcTemplate.query("select * from review where id =?",reviewRowMapper(),id);
        return result.stream().findAny();
    }

    @Override
    public List<Review> findAll() {
        List<Review> result = jdbcTemplate.query("select * from review",reviewRowMapper());
        return result;
    }

    @Override
    public Optional<Review> findConId(String id, String contentid) {
        List<Review> result = jdbcTemplate.query("select * from review where id=? AND contentid = ?",reviewRowMapper(),id,contentid);
        return result.stream().findAny();
    }

    @Override
    public void updateContent(String contents,String star,String id,String contentid) {
        jdbcTemplate.update("update review set contents =? , star =? where id =? AND contentid =?",contents,star,id,contentid);
    }

    @Override
    public void deleteContent(String del_flag,String id, String contentid) {
        jdbcTemplate.update("update review set del_flag =? where id=? AND contentid=?",del_flag,id,contentid);
    }

    private RowMapper<Review> reviewRowMapper() {
        return (rs,rowNum) ->{
            Review review = new Review();
            review.setId(rs.getString("id"));
            review.setTitle(rs.getString("title"));
            review.setPosterPath(rs.getString("posterPath"));
            review.setContents(rs.getString("contents"));
            review.setWatch(rs.getString("watch"));
            review.setDel_flag(rs.getString("del_flag"));
            review.setStar(rs.getString("star"));
            review.setContentid(rs.getString("contentid"));

            return review;
        };
    }
}
