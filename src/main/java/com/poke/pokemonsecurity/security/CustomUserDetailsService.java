package com.poke.pokemonsecurity.security;

import com.poke.pokemonsecurity.models.Role;
import com.poke.pokemonsecurity.models.UserEntity;
import com.poke.pokemonsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("username not found"));

        return new User(user.getUsername(),user.getPassword(), mapRolesToAuthorities(user.getRoles()));

    }

    private List<SimpleGrantedAuthority> mapRolesToAuthorities(List<Role> roles) {

        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList();

    }

}
