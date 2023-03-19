package movie.dbproject;

import movie.dbproject.domain.repository.*;
import movie.dbproject.domain.service.CartService;
import movie.dbproject.domain.service.MemberService;
import movie.dbproject.domain.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


@Configuration
public class SpringConfig {

    private final DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }

    @Bean
    public CartService cartService(){
        return new CartService(cartRepository());
    }

    @Bean
    public CartRepository cartRepository() {
        return new JdbcCartRepository(dataSource);
    }

    @Bean
    public MemberRepository memberRepository() {

        return new JdbcMemberRepository(dataSource);
    }

    @Bean
    public ReviewService reviewService() {
        return new ReviewService(reviewRepository());
    }

    @Bean
    public ReviewRepository reviewRepository(){
        return new JdbcReviewRepository(dataSource);
    }



}
