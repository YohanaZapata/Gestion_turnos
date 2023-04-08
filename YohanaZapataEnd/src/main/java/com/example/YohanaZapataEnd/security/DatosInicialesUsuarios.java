package com.example.YohanaZapataEnd.security;


import com.example.YohanaZapataEnd.domain.Usuario;
import com.example.YohanaZapataEnd.domain.UsuarioRole;
import com.example.YohanaZapataEnd.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatosInicialesUsuarios implements ApplicationRunner {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //crear un usuario como si fuera real
        //guardarlo en la base
        BCryptPasswordEncoder cifrador= new BCryptPasswordEncoder();
        String passSinCifrar="digital";
        String passwordCifrada1=cifrador.encode(passSinCifrar);
        Usuario agragarUsuario=new Usuario("Yohana", "Zapata",
                "yohaskj22@gmail.com",passwordCifrada1, UsuarioRole.ROLE_ADMIN);
        usuarioRepository.save(agragarUsuario);

        //Crear un usuario tipo Admin
        String passwordCifrada2 = cifrador.encode("digitalHouse");
        agragarUsuario = new Usuario("Emanuel", "Manu", "emaheza23@gmail.com",
                passwordCifrada2, UsuarioRole.ROLE_USER);
        usuarioRepository.save(agragarUsuario);
    }


}

