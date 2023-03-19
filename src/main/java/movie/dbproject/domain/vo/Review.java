package movie.dbproject.domain.vo;


import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Review {

    private String id;
    private String title;
    private String posterPath;
    private String contents;
    private String watch;
    private String del_flag;
    private String star;
    private String contentid;
}
