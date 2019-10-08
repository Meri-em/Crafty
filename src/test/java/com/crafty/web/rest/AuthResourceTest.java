package com.crafty.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.crafty.dto.LoginDTO;
import com.crafty.dto.RegistrationDTO;
import com.crafty.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AuthResource.class, secure = false)
public class AuthResourceTest {

	@Autowired
	private MockMvc mvc;

	private ObjectMapper objectMapper = new ObjectMapper();

	@MockBean
	private AuthService authService;

	@Test
	public void registerTest() throws Exception {
		RegistrationDTO registrationDTO = new RegistrationDTO();
		mvc.perform(post("/api/v1/register")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(registrationDTO)))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	public void loginTest() throws Exception {
		RegistrationDTO registrationDTO = new RegistrationDTO();
		mvc.perform(post("/api/v1/login")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(new LoginDTO())))
			.andDo(print())
			.andExpect(status().isOk());
	}
}
