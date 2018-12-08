package com.example.api.services;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.api.dao.RoleRepository;
import com.example.api.dao.UserRepository;
import com.example.api.model.Order;
import com.example.api.model.Role;
import com.example.api.model.User;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
	MongoTemplate mongoTemplate;
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

	
	public String getMaxId() {
		Query query = new Query();
		query.with(new Sort(Sort.Direction.DESC, "id"));
		query.limit(1);
		User maxObject = (User) this.mongoTemplate.findOne(query, User.class);
		if (maxObject == null) {
			return "0";
		}
		return maxObject.getId();
	}

    public boolean saveUser(User user) {
    	  try {
    	    	user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    	        user.setEnabled(true);
    	        Role userRole = roleRepository.findByRole("CLIENT");
    	        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
    	        userRepository.save(user);
    	        return true;
    	      }catch(Exception e) {
    	    	  return false;
    	      }
    }
    

    public boolean saveUserAdmin(User user,Role role) {
      try {
    	user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        Role userRole = roleRepository.findByRole(role.getRole());
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        userRepository.save(user);
        return true;
      }catch(Exception e) {
    	  return false;
      }
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);  
        if(user != null) {
            List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
            return buildUserForAuthentication(user, authorities);
        } else {
            throw new UsernameNotFoundException("username not found");
        }
    }

    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        userRoles.forEach((role) -> {
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        });

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }

    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}