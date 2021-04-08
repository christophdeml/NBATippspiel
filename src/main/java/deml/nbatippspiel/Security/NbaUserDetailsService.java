package deml.nbatippspiel.Security;

import deml.nbatippspiel.Repository.UserPasswordRepository;
import deml.nbatippspiel.Repository.UserRepository;
import deml.nbatippspiel.Service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class NbaUserDetailsService implements UserDetailsService {

    private final UserService userService;
    private final UserPasswordRepository userPasswordRepository;

    public NbaUserDetailsService(final UserService userService, final UserPasswordRepository userPasswordRepository) {
        this.userService = userService;
        this.userPasswordRepository = userPasswordRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final deml.nbatippspiel.Model.User user = userService.getUserByUsername(username);
        final List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        final String password = userPasswordRepository.findById(user.getId()).get().getPassword();
        return new User(username, password, authorities);
    }
}
