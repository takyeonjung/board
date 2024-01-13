package board.bbs.bbs.service;

import board.bbs.bbs.domain.User;
import board.bbs.bbs.dto.UserDto;
import board.bbs.bbs.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
    @Service
    public class UserService implements UserDetailsService {

        private final UserRepository userRepository;

        /**
         * Spring Security 필수 메소드 구현
         *
         * @param email 이메일
         * @return UserDetails
         * @throws UsernameNotFoundException 유저가 없을 때 예외 발생
         */
        @Override // 기본적인 반환 타입은 UserDetails, UserDetails를 상속받은 UserInfo로 반환 타입 지정 (자동으로 다운 캐스팅됨)
        public User loadUserByUsername(String email) throws UsernameNotFoundException { // 시큐리티에서 지정한 서비스이기 때문에 이 메소드를 필수로 구현
            return userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException((email)));
        }


    /**
     * 회원정보 저장
     *
     * @param infoDto 회원정보가 들어있는 DTO
     * @return 저장되는 회원의 PK
     */
    public Long save(UserDto infoDto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        infoDto.setPassword(encoder.encode(infoDto.getPassword()));

        return userRepository.save(User.builder()
                .email(infoDto.getEmail())
                .auth(infoDto.getAuth())
                .username(infoDto.getUsername())
                .password(infoDto.getPassword()).build()).getId();
    }

        /* 회원가입 시, 유효성 및 중복 검사 */
        @Transactional(readOnly = true)
        public Map<String, String> validateHandling(Errors errors) {
            Map<String, String> validatorResult = new HashMap<>();

            /* 유효성 및 중복 검사에 실패한 필드 목록을 받음 */
            for (FieldError error : errors.getFieldErrors()) {
                String validKeyName = String.format("valid_%s", error.getField());
                validatorResult.put(validKeyName, error.getDefaultMessage());
            }

            return validatorResult;
        }

}

