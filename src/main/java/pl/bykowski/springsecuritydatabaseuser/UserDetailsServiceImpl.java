package pl.bykowski.springsecuritydatabaseuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {

    private AppUserRepo appUserRepo;

    @Autowired
    public UserDetailsServiceImpl(AppUserRepo appUserRepo) {
        this.appUserRepo = appUserRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> byUsername = appUserRepo.findByUsername(username);
        if (byUsername.isPresent())
            return byUsername.get();
        else {
            // do to handler
            throw new RuntimeException("This user not exist");
        }
    }
}
