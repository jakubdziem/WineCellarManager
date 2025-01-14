package com.dziem.WineCellarManager.security;

import com.dziem.WineCellarManager.model.Customer;
import com.dziem.WineCellarManager.model.Role;
import com.dziem.WineCellarManager.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomeUserDetailsService implements UserDetailsService {
    private final CustomerRepository customerRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByNickname(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return new User(customer.getNickname(), customer.getPassword(), mapRolesToGrantedAuthorities(customer.getRoles()));
    }

    private Collection<GrantedAuthority> mapRolesToGrantedAuthorities(List<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
