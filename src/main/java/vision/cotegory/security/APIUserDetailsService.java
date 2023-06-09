package vision.cotegory.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vision.cotegory.entity.Member;
import vision.cotegory.repository.MemberRepository;

@Service
@Log4j2
@RequiredArgsConstructor
public class APIUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByLoginIdAndActivatedIsTrue(username)
                .orElseThrow(() -> new UsernameNotFoundException("Cannot find loginId"));

        APIUserDTO dto = new APIUserDTO(member);
        return dto;
    }
}