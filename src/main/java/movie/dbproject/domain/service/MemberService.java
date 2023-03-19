package movie.dbproject.domain.service;

import movie.dbproject.domain.repository.MemberRepository;
import movie.dbproject.domain.vo.User;

import java.util.List;
import java.util.Optional;


public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository=memberRepository;
    }

    public String join(User user){
        memberRepository.save(user);
        return user.getId();
    }

    public List<User> findUsers() {
        return memberRepository.findAll();
    }

    public Optional<User> findOneById(String userId){

        return memberRepository.findById(userId);
    }

    public Optional<User> findOneByName(String userName){

        return memberRepository.findById(userName);
    }


}
