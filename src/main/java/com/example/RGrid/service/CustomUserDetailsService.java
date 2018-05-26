package com.example.RGrid.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.RGrid.model.*;
import com.example.RGrid.repository.UsersRepository;
import com.example.RGrid.repository.VerificationTokenRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UsersRepository uRepository;
	
	@Autowired
    private VerificationTokenRepository tokenRepository;
	
	public List<Users> getAllUsers(){
		return (List<Users>) uRepository.findAll();
	}
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		Optional<Users> optUser = uRepository.findByUserName(username);
		
		optUser.orElseThrow(() -> new UsernameNotFoundException("Username not found"));
				
		return optUser.map(CustomUserDetails::new).get();
	}
	
	public boolean addNewUser(Users user){
		
		if (!this.getAllEmails().contains(user.getEmail()) & !this.getAllLogins().contains(user.getLogin())){
			Users newUser = new Users(user);
			newUser.setActive(true);
			newUser.setUserType(0);
			newUser.setRegistrationDate(new Date());
			newUser.setPassword(this.getPasswordEncoder().encode(user.getPassword()));
			uRepository.save(newUser);
			return true;
		}
		else
			return false;
		
	}
	
	public List<String> getAllEmails(){
		List<Users> users = this.getAllUsers();
		List<String> list = new ArrayList<>();
		for (Users u :users){
			list.add(u.getEmail());
		}
		return list;
	}
	
	public List<String> getAllLogins(){
		List<Users> users = this.getAllUsers();
		List<String> list = new ArrayList<>();
		for (Users u : users){
			list.add(u.getLogin());
		}
		return list;
	}
	
	private PasswordEncoder getPasswordEncoder(){
		return new BCryptPasswordEncoder(); 
	}
	
	public Users getUser(String verificationToken) {
        Users user = tokenRepository.findByToken(verificationToken).getUser();
        return user;
    }
     
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }
     
    public void saveRegisteredUser(Users user) {
        uRepository.save(user);
    }
     
    public void createVerificationToken(Users user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }
    
    

}
