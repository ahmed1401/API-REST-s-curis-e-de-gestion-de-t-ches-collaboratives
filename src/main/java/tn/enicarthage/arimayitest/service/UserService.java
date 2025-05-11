package tn.enicarthage.arimayitest.service;

import tn.enicarthage.arimayitest.dto.UserRegistrationDto;
import tn.enicarthage.arimayitest.dto.UserCreationDto;
import tn.enicarthage.arimayitest.model.User;
import tn.enicarthage.arimayitest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(UserRegistrationDto registrationDto) {
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        User user = new User();
        user.setName(registrationDto.getName());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setRole(User.Role.USER);

        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createAdmin(UserCreationDto userCreationDto) {
        if (userRepository.existsByEmail(userCreationDto.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        User admin = new User();
        admin.setName(userCreationDto.getName());
        admin.setEmail(userCreationDto.getEmail());
        admin.setPassword(passwordEncoder.encode(userCreationDto.getPassword()));
        admin.setRole(User.Role.ADMIN);

        return userRepository.save(admin);
    }

    public User updateUserRole(Long userId, User.Role newRole) {
        User user = findById(userId);
        user.setRole(newRole);
        return userRepository.save(user);
    }
}
