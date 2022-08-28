package modelo;

import controlador.ControladorProducto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import vista.Vista;

@SpringBootApplication
public class Reto5Application {

    @Autowired
    RepositorioProducto rep;

    public static void main(String[] args) {
        //SpringApplication.run(Reto5Application.class, args);
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Reto5Application.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);
    }

    @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            final Log logger = LogFactory.getLog(getClass());//permite tener un registro de las operaciones dentro de Bean
            Vista vista = new Vista();
            vista.setVisible(true);
            ControladorProducto controlador = new ControladorProducto(rep, vista);
            vista.setControlador(controlador);
            controlador.setListaProducto(controlador.obtenerProductos());
            controlador.inicializaTabla();
        };
    }

}
