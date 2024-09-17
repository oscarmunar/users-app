package com.app.users;

import com.app.users.dao.UserDAO;
import com.app.users.entity.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UsersAppApplicationTests {

	@Autowired
	UserDAO userDAO;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void contextLoads() {
	}

	@Test
	public void testControllerCreateUser() throws Exception {
		UserEntity newUser = new UserEntity();
		newUser.setName("Oscar");
		newUser.setEmail("oscar@correo.com");
		newUser.setPassword("123abcABC#$");

		String userJson = objectMapper.writeValueAsString(newUser);

		// Simulamos la petición POST con el JSON en el body
		mockMvc.perform(post("/create")
						.contentType(MediaType.APPLICATION_JSON)  // Tipo de contenido enviado
						.content(userJson))  // El cuerpo de la solicitud (JSON)
				.andExpect(status().isCreated())  // Validamos que el status sea 201 Created
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))  // Validamos que el contenido sea JSON
				.andExpect(jsonPath("$.name").value("Oscar"))  // Validamos el nombre
				.andExpect(jsonPath("$.email").value("oscar@correo.com"));  // Validamos el email
	}

	@Test
	public void testControllerCreateUserEmailNotValid() throws Exception {
		UserEntity newUser = new UserEntity();
		newUser.setName("Oscar");
		newUser.setEmail("oscarcorreo.com"); // no tiene el formato correcto
		newUser.setPassword("123abcABC#$");

		String userJson = objectMapper.writeValueAsString(newUser);

		// Simulamos la petición POST con el JSON en el body
		mockMvc.perform(post("/create")
						.contentType(MediaType.APPLICATION_JSON)  // Tipo de contenido enviado
						.content(userJson))  // El cuerpo de la solicitud (JSON)
				.andExpect(status().isNotFound())  // Validamos que el status sea 201 Created
				.andExpect(jsonPath("$.error").value("El correo no tiene el formato."));  // Validamos contenido
	}

	@Test
	public void testControllerCreateUserPassNotValid() throws Exception {
		UserEntity newUser = new UserEntity();
		newUser.setName("Oscar");
		newUser.setEmail("oscar@correo.com");
		newUser.setPassword("123abc"); // no tiene el formato correcto

		String userJson = objectMapper.writeValueAsString(newUser);

		// Simulamos la petición POST con el JSON en el body
		mockMvc.perform(post("/create")
						.contentType(MediaType.APPLICATION_JSON)  // Tipo de contenido enviado
						.content(userJson))  // El cuerpo de la solicitud (JSON)
				.andExpect(status().isNotFound())  // Validamos que el status sea 201 Created
				.andExpect(jsonPath("$.error").value("El password no tiene el formato."));  // Validamos contenido
	}

	@Test
	public void createUserTest() {
		UserEntity user = new UserEntity();
		user.setName("Oscar");
		user.setEmail("oscar@correo.com");
		user.setPassword("123abcABC#$");
		UserEntity newUser = userDAO.save(user);
		UUID newuuid = newUser.getId();

		assertEquals(newuuid,userDAO.findByEmail("oscar@correo.com").get().getId());
	}
}
