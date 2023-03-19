package movie.dbproject.domain.controller;

import lombok.extern.slf4j.Slf4j;
import movie.dbproject.domain.service.ReviewService;
import movie.dbproject.domain.vo.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @RequestMapping("/addReview")
    @ResponseBody
    public String addReview(Review review) {

        Optional<Review> result =reviewService.findConidReview(review.getId(),review.getContentid());

        if(result.isEmpty()){
            reviewService.saveReview(review);
            return "<script>alert('리뷰 등록이 완료되었습니다!!');location.href='/wishlist'</script>";
        }else if(result.get().getDel_flag().equals("1")) {
            reviewService.delete_review("0", review.getId(), review.getContentid());
            reviewService.update_review(review.getContents(),review.getStar(),review.getId(),review.getContentid());
            return "<script>alert('리뷰 등록이 완료되었습니다!!');location.href='/wishlist'</script>";
        } else{
            return "<script>alert('이미 등록된 리뷰입니다!!');location.href='/wishlist'</script>";
        }


    }

    @RequestMapping("/seeReview")
    public String seeReview(Model model) {


        List<Review> allReview = reviewService.findAllReview();
        model.addAttribute("review",allReview);

        return"/review/rt_review";

    }

    @RequestMapping("/update_reviewForm")
    public String update_review_form(Model model,Review review){
        model.addAttribute("review",review);
        return "/review/rt_review_update";
    }

    @RequestMapping("/update_review")
    @ResponseBody
    public String update_review(@RequestParam String contents,@RequestParam String id,
                                @RequestParam String contentid,@RequestParam String star){

        reviewService.update_review(contents,star,id,contentid);

        return "<script>alert('수정 완료!');opener.window.location='/seeReview';close();</script>";
    }

    @RequestMapping("/deleteReview")
    @ResponseBody
    public String delete_review(@RequestParam String id ,@RequestParam String contentid){
        reviewService.delete_review("1",id,contentid);
        return "<script>alert('삭제가 완료되었습니다!');location.href='/seeReview'</script>";
    }


}
