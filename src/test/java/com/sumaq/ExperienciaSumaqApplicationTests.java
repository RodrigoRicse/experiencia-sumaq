package com.sumaq;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

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
	void actualizacionDinamicaDelCarritoEsPublicaYRequiereCsrf() throws Exception {
		mockMvc.perform(post("/carrito/items/7/cantidad")
					.header("X-Requested-With", "XMLHttpRequest")
					.param("cantidad", "2"))
				.andExpect(status().isForbidden());

		mockMvc.perform(post("/carrito/items/7/cantidad")
					.with(csrf())
					.header("X-Requested-With", "XMLHttpRequest")
					.param("cantidad", "2"))
				.andExpect(handler().methodName("actualizarDesdeCheckout"))
				.andExpect(status().isNotFound());
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
	void healthEsPublicoPeroInfoYMetricasRequierenAutenticacion() throws Exception {
		mockMvc.perform(get("/actuator/health"))
				.andExpect(status().isOk());
		mockMvc.perform(get("/actuator/info"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/login"));
		mockMvc.perform(get("/actuator/metrics"))
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
	@WithMockUser(username = "administrador", roles = "ADMINISTRADOR")
	void rolAdministradorConsultaInfoYMetricas() throws Exception {
		mockMvc.perform(get("/actuator/info"))
				.andExpect(status().isOk());
		mockMvc.perform(get("/actuator/metrics"))
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "cocinero", roles = "COCINA")
	void rolCocinaNoAccedeAAdministracion() throws Exception {
		mockMvc.perform(get("/admin"))
				.andExpect(status().isForbidden());
	}

}
