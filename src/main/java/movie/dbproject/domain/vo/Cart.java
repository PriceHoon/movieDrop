package movie.dbproject.domain.vo;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Cart {
    private String id;
    private String contentid;
    private String posterPath;
    private String title;
    private String watch;
    private String del_flag;
    private String type;
}
