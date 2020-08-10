package br.com.farmacia10.farmacia.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.farmacia10.farmacia.model.Usuario;
import br.com.farmacia10.farmacia.repository.UsuarioRepository;


public class UserDetailsServiceImpl implements UserDetailsService {
	
	 @Autowired
	    private UsuarioRepository usuarioRepository;
	    @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    	
	        Optional<Usuario> user = usuarioRepository.findByEmail(username);
	        user.orElseThrow(() -> new UsernameNotFoundException(username + " not found" ));
	       
	        return user.map(UserDetailsImpl::new).get();
	 }
}
