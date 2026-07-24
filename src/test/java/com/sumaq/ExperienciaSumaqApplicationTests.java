package com.sumaq;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.bind.annotation.GetMapping;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;

@SpringBootTest(properties = "debug=false")
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(ExperienciaSumaqApplicationTests.ControladorErrorPrueba.class)
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
	void loginReiniciaElErrorYMuestraLogoutFueraDelFormulario() throws Exception {
		String loginConError = mockMvc.perform(get("/login").param("error", "true"))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		assertThat(loginConError)
				.contains("data-login-form", "data-login-error", "/js/sumaq.js");

		String loginConLogout = mockMvc.perform(get("/login").param("logout", "true"))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		int cierreFormulario = loginConLogout.indexOf("</form>");
		int mensajeLogout = loginConLogout.indexOf("La sesión se cerró correctamente.");
		assertThat(cierreFormulario).isGreaterThan(0);
		assertThat(mensajeLogout).isGreaterThan(cierreFormulario);
		assertThat(loginConLogout.substring(0, cierreFormulario))
				.doesNotContain("La sesión se cerró correctamente.");
		assertThat(loginConLogout).contains("class=\"toast\"", "role=\"status\"");
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
				.andExpect(status().isForbidden())
				.andExpect(forwardedUrl("/error/403"));
		mockMvc.perform(get("/error/403"))
				.andExpect(status().isForbidden())
				.andExpect(content().string(containsString("Acceso restringido")));
	}

	@Test
	@WithMockUser(username = "administrador", roles = "ADMINISTRADOR")
	void rutaInexistenteMuestraPagina404() throws Exception {
		mockMvc.perform(get("/ruta-que-no-existe"))
				.andExpect(status().isNotFound())
				.andExpect(content().string(containsString("No encontramos esta página")));
	}

	@Test
	@WithMockUser(username = "administrador", roles = "ADMINISTRADOR")
	void errorInesperadoMuestraPagina500SinDetalleTecnico() throws Exception {
		mockMvc.perform(get("/__test/error"))
				.andExpect(status().isInternalServerError())
				.andExpect(content().string(containsString("Ocurrió un problema inesperado")))
				.andExpect(content().string(containsString("Referencia:")))
				.andExpect(content().string(org.hamcrest.Matchers.not(
						containsString("Fallo técnico de prueba"))));
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
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.application.name").value("Experiencia Sumaq"))
				.andExpect(jsonPath("$.application.description")
						.value("Sistema web de pedidos y gestión operativa"))
				.andExpect(jsonPath("$.application.java-target").value(21));
		mockMvc.perform(get("/actuator/metrics"))
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "cocinero", roles = "COCINA")
	void rolCocinaNoAccedeAAdministracion() throws Exception {
		mockMvc.perform(get("/admin"))
				.andExpect(status().isForbidden());
	}

	@Controller
	static class ControladorErrorPrueba {

		@GetMapping("/__test/error")
		String error() {
			throw new IllegalStateException("Fallo técnico de prueba");
		}
	}

}
