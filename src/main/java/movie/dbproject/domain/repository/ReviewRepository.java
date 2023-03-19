package movie.dbproject.domain.repository;

import movie.dbproject.domain.vo.Cart;
import movie.dbproject.domain.vo.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository {

    Review saveR(Review review);
    Optional<Review> findById(String id);
    List<Review> findAll();
    Optional<Review> findConId(String id, String contentid);
    void updateContent(String contents,String star,String id,String contentid);
    void deleteContent(String del_flag,String id,String contentid);
}
