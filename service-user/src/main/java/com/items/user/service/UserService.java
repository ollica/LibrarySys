package com.items.user.service;

import com.items.user.model.User;
import com.items.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // 哈希密码（BCrypt）

    public User registerUser(String username, String password) {
        // 检查用户是否存在
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("User already exists");
        }
        // 保存前对密码进行哈希处理
        String hashedPassword = passwordEncoder.encode(password);
        User newUser = new User(username, hashedPassword);
        return userRepository.save(newUser);
    }

    public String getUserRole(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        // 检查用户是否存在
        if (!user.isPresent()) {
            throw new RuntimeException("User doesn't exist");
        }
        return user.get().getRole();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), new ArrayList<>()); // No roles for now
    }
}
