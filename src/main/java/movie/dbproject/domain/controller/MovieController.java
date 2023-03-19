package movie.dbproject.domain.controller;


import lombok.extern.slf4j.Slf4j;
import movie.dbproject.domain.service.CartService;
import movie.dbproject.domain.vo.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.management.monitor.StringMonitor;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Controller @Slf4j
public class MovieController {
    private final CartService cartService;
    @Autowired
    public MovieController(CartService cartService) {
        this.cartService = cartService;
    }
    @RequestMapping("/detail")
    public String detail() {

        return "/movie/movie_detail";
    }
    @RequestMapping("/search")
    public String search() {

        return "/movie/movie_search";
    }
    @RequestMapping("/wishlistInsert")
    @ResponseBody
    public String wishlistInsert(HttpSession httpSession, @RequestParam String contentid,@RequestParam String type, @RequestParam String image,
                                 @RequestParam String title, Model model) {
        log.info("title={}",title);
        log.info("contentId={}",contentid);
        log.info("movConUserId={}",httpSession.getAttribute("id"));

        Cart cart = new Cart();
        cart.setContentid(contentid);
        cart.setPosterPath(image);
        cart.setTitle(title);
        cart.setType(type);
        cart.setDel_flag("0");//삭제 아닌상태 0
        cart.setWatch("true");//아직 안 본 상태 true
        cart.setId(String.valueOf(httpSession.getAttribute("id")));
        cartService.saveCart(cart);
        model.addAttribute("cart",cart);
        return "<script>alert('장바구니에 담겼습니다!');if(!confirm('장바구니로 이동하시겠습니까?')){location.href='/';}else{location.href='/wishlist'}</script>";
    }

    @RequestMapping("/wishlist")
    public String wishlist(HttpSession httpSession,Model model){
        List<Cart> cartList = cartService.findAllById(String.valueOf(httpSession.getAttribute("id")));
        model.addAttribute("cartList",cartList);

        return "/movie/cartList";


    }

    @RequestMapping("/changeWatch")
    public String changeWatch(Cart cart) {

        if(cart.getWatch().equals("true")){
            cart.setWatch("false");
            cartService.watchChange(cart);
        }else if(cart.getWatch().equals("false")){
            cart.setWatch("true");
            cartService.watchChange(cart);
        }else{
            cartService.watchChange(cart);
        }

        return "redirect:/wishlist";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String deleteCart(Cart cart, HttpServletResponse response){

        if(cart.getDel_flag().equals("0")){
            cartService.deleteCart("1",cart);
        }
        return "<script>alert('삭제 완료!');location.href='/wishlist'</script>";
    }



    @RequestMapping("/cartCheckF")
    @ResponseBody
    public String cartCheckF(@RequestParam String contentid,HttpSession httpSession) {

        Optional<Cart> result = cartService.findOneByConId(String.valueOf(httpSession.getAttribute("id")), contentid);


//        Optional<Cart> resultUser = result.get().getId()


//        log.info("user={}",result.get().getId().equals(httpSession.getAttribute("id")));

        if(result.isEmpty()){

            return"ok";
        }else if(result.get().getDel_flag().equals("1")){
            Cart cart = new Cart();
            cart.setId(String.valueOf(httpSession.getAttribute("id")));
            cart.setContentid(contentid);
            cartService.deleteCart("0",cart);
            return "ReOk";
        } else {
            return"no";
        }

    }



}
