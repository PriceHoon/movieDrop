package movie.dbproject.domain.service;

import movie.dbproject.domain.repository.CartRepository;
import movie.dbproject.domain.vo.Cart;

import java.util.List;
import java.util.Optional;

public class CartService {

    private final CartRepository cartRepository;


    public CartService(CartRepository cartRepository) {
        this.cartRepository=cartRepository;
    }

    public String saveCart(Cart cart) {

        cartRepository.saveM(cart);
        return cart.getId();
    }

    public Optional<Cart> fineOneById(String id) {
        return cartRepository.findById(id);
    }
    public List<Cart> findAllById(String id){
        return cartRepository.findByIdAll(id);
    }

    public Optional<Cart> findOneByConId(String id,String contentid){
        return cartRepository.findConId(id,contentid);
    }
    public List<Cart> findCarts() {
        return cartRepository.findAll();
    }

    public void deleteCart(String del_flag,Cart cart) {
        cartRepository.delete(del_flag,cart);
    }

    public void watchChange(Cart cart) {
        cartRepository.watchCheck(cart);
    }

}
