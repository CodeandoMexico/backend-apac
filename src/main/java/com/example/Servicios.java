/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Gad
 */
@RestController()
public class Servicios {

    @RequestMapping(value = "/pruebaRespuesta", method = RequestMethod.GET)
    public String pruebaRespuesta() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping(value = "/consulta/usuario", method = RequestMethod.POST)
    public String pruebaConsulta(@RequestParam("u") String user, @RequestParam("p") String password) {
        
        return "Usuario es: " + user + "-" + password;
    }

}
