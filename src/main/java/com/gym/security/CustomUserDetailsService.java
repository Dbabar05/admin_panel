package com.gym.security;

import com.gym.entity.AdminUser;
import com.gym.repository.AdminUserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminUserRepository adminUserRepository;

    public CustomUserDetailsService(AdminUserRepository adminUserRepository) {
        this.adminUserRepository = adminUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Try Admin repository first
        var adminUserOpt = adminUserRepository.findByEmail(email);
        if (adminUserOpt.isPresent()) {
            AdminUser user = adminUserOpt.get();
            Set<GrantedAuthority> authorities = new HashSet<>();
            authorities.add(new SimpleGrantedAuthority("TYPE_ADMIN"));
            if (user.getRole() != null) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName()));
                if (user.getRole().getPermissions() != null) {
                    user.getRole().getPermissions().forEach(p -> authorities.add(new SimpleGrantedAuthority(p.getName())));
                }
            }
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
        }


        throw new UsernameNotFoundException("User not found with email: " + email);
    }
}
