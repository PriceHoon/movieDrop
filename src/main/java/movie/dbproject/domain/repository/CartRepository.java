package movie.dbproject.domain.repository;

import movie.dbproject.domain.vo.Cart;

import java.util.List;
import java.util.Optional;

public interface CartRepository {

    Cart saveM(Cart cart);
    Optional<Cart> findById(String id);
    List<Cart> findByIdAll(String id);
    Optional<Cart> findConId(String id,String contentid);
    List<Cart> findAll();
    void delete(String del_flag,Cart cart);
    void watchCheck(Cart cart);


}
