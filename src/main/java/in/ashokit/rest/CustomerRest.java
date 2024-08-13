package in.ashokit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.ashokit.entity.Customer;
import in.ashokit.repo.CustomerRepo;

@RestController
public class CustomerRest {

	@Autowired
	private CustomerRepo crepo;
	
	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private PasswordEncoder encoder;
	
	@PostMapping("/register")
	public String saveCustomer(@RequestBody Customer c) {
		
		String encode = encoder.encode(c.getPwd());
		c.setPwd(encode);
		Customer save = crepo.save(c);
	
		return "customer save";
	}
	
	@PostMapping("/login")
	public String loginCheck(@RequestBody Customer c) {
		
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(c.getName(), c.getPwd());
		
		Authentication authenticate = authManager.authenticate(token);

		try {
		
		if(authenticate.isAuthenticated()) {
			return "User Sucessfully logged in";
		}
		
		}catch(Exception e) {
			
		}
		return "Credentials are incorrect";
	}
}
