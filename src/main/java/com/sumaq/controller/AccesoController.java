package com.sumaq.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccesoController {

    @GetMapping("/login")
    public String login(Authentication authentication) {
        if (authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/personal";
        }
        return "acceso/login";
    }

    @GetMapping("/personal")
    public String portal(Authentication authentication) {
        boolean administrador = tieneRol(authentication, "ROLE_ADMINISTRADOR");
        boolean cocina = tieneRol(authentication, "ROLE_COCINA");
        boolean caja = tieneRol(authentication, "ROLE_CAJA");
        if (administrador) {
            return "redirect:/admin";
        }
        if (cocina) {
            return "redirect:/cocina";
        }
        if (caja) {
            return "redirect:/caja";
        }
        return "redirect:/";
    }

    private boolean tieneRol(Authentication authentication, String rol) {
        return authentication != null && authentication.getAuthorities().stream()
                .anyMatch(authority -> rol.equals(authority.getAuthority()));
    }
}
