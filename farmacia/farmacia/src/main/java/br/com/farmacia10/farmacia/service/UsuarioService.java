package br.com.farmacia10.farmacia.service;


import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.farmacia10.farmacia.model.UserLogin;
import br.com.farmacia10.farmacia.model.Usuario;
import br.com.farmacia10.farmacia.repository.UsuarioRepository;


@Service
public class UsuarioService {
	
	 @Autowired
	    private UsuarioRepository usuarioRepository;

	    public Usuario CadastrarUsuario(Usuario usuario){
	        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	        String senhaEncoder =encoder.encode(usuario.getSenha());
	        usuario.setSenha(senhaEncoder);
	        return usuarioRepository.save(usuario);
	    }
	    public Optional<UserLogin> Logar(Optional<UserLogin> userLogin){
	        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	        Optional<Usuario> usuario = usuarioRepository.findByEmail(userLogin.get().getEmail());
	        if(usuario.isPresent()){
	            if(encoder.matches(userLogin.get().getSenha(),usuario.get().getSenha())){
	                String auth = userLogin.get().getEmail()+":"+userLogin.get().getSenha();
	                byte[] encodeAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
	                String authHeader = "Basic "+ new String(encodeAuth);

	                userLogin.get().setToken(authHeader);
	                userLogin.get().setNome(usuario.get().getNome());
	                return userLogin;
	            }
	        }
	        return null;
	    }

}
