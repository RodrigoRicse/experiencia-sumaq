package com.sumaq;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@SpringBootTest(properties = "debug=false")
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ExperienciaSumaqApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	void inicioYMenuSonPublicos() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(status().isOk());
		mockMvc.perform(get("/menu"))
				.andExpect(status().isOk());
	}

	@Test
	void loginEsPublicoYCocinaRequiereAutenticacion() throws Exception {
		mockMvc.perform(get("/login"))
				.andExpect(status().isOk());
		mockMvc.perform(get("/cocina"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/login"));
	}

	@Test
	@WithMockUser(username = "cocinero", roles = "COCINA")
	void rolCocinaAccedeSoloASuPanel() throws Exception {
		mockMvc.perform(get("/cocina"))
				.andExpect(status().isOk());
		mockMvc.perform(get("/caja"))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "cajero", roles = "CAJA")
	void rolCajaAccedeSoloASuPanel() throws Exception {
		mockMvc.perform(get("/caja"))
				.andExpect(status().isOk());
		mockMvc.perform(get("/cocina"))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "administrador", roles = "ADMINISTRADOR")
	void rolAdministradorAccedeAlPanelYPortalLoRedirige() throws Exception {
		mockMvc.perform(get("/admin"))
				.andExpect(status().isOk());
		mockMvc.perform(get("/admin/productos/nuevo"))
				.andExpect(status().isOk());
		mockMvc.perform(get("/personal"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/admin"));
	}

	@Test
	@WithMockUser(username = "cocinero", roles = "COCINA")
	void rolCocinaNoAccedeAAdministracion() throws Exception {
		mockMvc.perform(get("/admin"))
				.andExpect(status().isForbidden());
	}

}
