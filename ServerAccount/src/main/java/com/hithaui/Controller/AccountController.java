package com.hithaui.Controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.hithaui.DTO.AccountDTO;
import com.hithaui.Exception.DuplicateException;
import com.hithaui.Model.Account;
import com.hithaui.Repositories.AccountRepository;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private Cloudinary cloudinary;
	
	@GetMapping
	public ResponseEntity<?> getAllAccount(){
		List<Account> list= accountRepository.findAll();
		if (list==null) {
			ResponseEntity.status(404).build();
		}
		return ResponseEntity.status(200).body(list);
	}
	
	@PostMapping
	public ResponseEntity<?> createAccount (@RequestParam("username") String username, 
			@RequestParam("password") String password, @RequestParam("avt") MultipartFile avtFile) throws IOException{
		Account account= accountRepository.findByUsername(username);
		if (account !=null) {
			throw new DuplicateException("Account has aready exists");
		}
		Map<?, ?> map= cloudinary.uploader().upload(avtFile.getBytes(), ObjectUtils.emptyMap());
		String avtLink = (String) map.get("secure_url");
		
		Account account2= accountRepository.save(new Account(username, password, avtLink));
		
		return ResponseEntity.status(201).body(account2);
	}
	
	@PostMapping("/body")
	public ResponseEntity<?> creatAccoutbyBody(@RequestBody AccountDTO accountDTO){
		Account account= accountRepository.findByUsername(accountDTO.getUsername());
		if (account!=null) {
			throw new DuplicateException("Tài khoản đã tồn tại");
		}
		Account account1= new Account(accountDTO.getUsername(), accountDTO.getPassword(), accountDTO.getAvt());
		Account account2 = accountRepository.save(account1);
		return ResponseEntity.status(201).body(account2);
		
	}
	
	
}
