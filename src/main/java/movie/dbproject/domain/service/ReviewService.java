package movie.dbproject.domain.service;

import movie.dbproject.domain.repository.ReviewRepository;
import movie.dbproject.domain.vo.Review;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class ReviewService {

    private final ReviewRepository reviewRepository ;

    @Autowired
    public ReviewService (ReviewRepository reviewRepository) {
        this.reviewRepository=reviewRepository;
    }

    public String saveReview(Review review){
        reviewRepository.saveR(review);
        return review.getId();
    }

    public List<Review> findAllReview() {
        return reviewRepository.findAll();
    }

    public Optional<Review> findConidReview(String id,String contentid) {
        return reviewRepository.findConId(id,contentid);

    }

    public void update_review(String contents,String star,String id,String contentid) {
        reviewRepository.updateContent(contents,star,id,contentid);
    }

    public void delete_review(String del_flag,String id,String contentid){
        reviewRepository.deleteContent(del_flag,id,contentid);
    }

    // 미사용 예정 메서드
    public Optional<Review> findByIdReview(String id) {
        return reviewRepository.findById(id);
    }
}
