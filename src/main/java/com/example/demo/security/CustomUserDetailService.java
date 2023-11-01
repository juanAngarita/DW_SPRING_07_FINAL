package com.example.demo.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.Role;
import com.example.demo.model.Student;
import com.example.demo.model.Teacher;
import com.example.demo.model.UserEntity;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*Unico método para traer la informacion de un usuario a traves de su username */
    /*Retorna un USerDetails, que es la entidad básica en springboot que unicamente tiene
     * username, password y authorities
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Buscar al usuario si no se encuentra traer una excepcion
        //El usuario que se carga es de tipo USer Entity, el que nosotros creamos
       UserEntity userDB = userRepository.findByUsername(username).orElseThrow(
           () -> new UsernameNotFoundException("User not found")
       );
       UserDetails userDetails = new User(userDB.getUsername(),
        userDB.getPassword(),
         mapRolesToAuthorities(userDB.getRoles()));

        //El usuario que se retorna es de tipo UserDetail
        //Se mapean los datos desde el UserEntity a UserDetail
        //Es necesario pasar como tercer parametro grantedAutathorities
       return userDetails;
    }

        //PAsar de roles a GrantedAuthoritys
    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public UserEntity saveStudent(Student student){
        UserEntity user = new UserEntity();
        user.setUsername(student.getCorreo());
        user.setPassword(passwordEncoder.encode("123"));

        Role roles = roleRepository.findByName("STUDENT").get();
        user.setRoles(List.of(roles));
        return user;
    }

    public UserEntity saveTeacher(Teacher teacher){
        UserEntity user = new UserEntity();
        user.setUsername(teacher.getCorreo());
        user.setPassword(passwordEncoder.encode(teacher.getPassword()));

        Role roles = roleRepository.findByName("TEACHER").get();
        user.setRoles(List.of(roles));
        return user;
    }
        
    
}
