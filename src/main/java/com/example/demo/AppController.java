package com.example.demo;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.JWTVerifier;

@RestController
@CrossOrigin({"http://localhost:4200/","*"})
public class AppController {
	
	@Autowired
	UserRepository userRepo;
	
	
	@PostMapping("authenticate")
	public ResponseEntity<Response> doAuthenticate(@RequestBody User user){
		User u = userRepo.findById(user.getUsername()).get();
		
		if(u.getPassword().equals(user.getPassword())) {
			
			String token=JWT.create().withSubject(u.getUsername())
					.withIssuedAt(new Date(System.currentTimeMillis()))
					.withExpiresAt(new Date(System.currentTimeMillis()+(1000*60*10)))
					.sign(Algorithm.HMAC256("helloworld"));
			
			HttpHeaders headers=new  HttpHeaders();
			headers.set("Authorization","Bearer "+ token);
			
			
			return new ResponseEntity<Response>(new Response("Bearer "+ token,true),headers,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Response>(new Response("outu",false),HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/")
	public String home() {
		return "hello";
	}
	
	
	@PostMapping("validate")
	public ResponseEntity<String> doValidate(HttpServletRequest request){
		try {
		String authToken=request.getHeader("Authorization");
		String token=authToken.substring("Bearer ".length());
		Algorithm al = Algorithm.HMAC256("helloworld".getBytes());
		
		JWTVerifier verifier = JWT.require(al).build();
		
		verifier.verify(token);
		
		return new ResponseEntity<String>("true",HttpStatus.OK);
		}catch(Exception e) {
			
			return new ResponseEntity<String>("false",HttpStatus.OK);

		
		}
		}
	
	
	
	
	
	
	
}
