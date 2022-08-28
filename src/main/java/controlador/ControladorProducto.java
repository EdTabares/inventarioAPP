package controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.swing.table.DefaultTableModel;
import modelo.Producto;
import modelo.RepositorioProducto;
import org.springframework.beans.factory.annotation.Autowired;
import vista.Actualizar;
import vista.InformeV;
import vista.MuestraError;
import vista.Vista;

public class ControladorProducto { //Define las funcionalidades a la BD

    @Autowired // Con esto spring es el encargado de crear las instancias de los objetos
    ArrayList<Producto> listaProducto;
    RepositorioProducto repositorio;
    Vista vista;

    public ControladorProducto() {

    }

    public ControladorProducto(RepositorioProducto repositorio, Vista vista) {
        this.repositorio = repositorio;
        this.vista = vista;
    }

    public ArrayList<Producto> getListaProducto() {
        return listaProducto;
    }

    public void setListaProducto(ArrayList<Producto> listaProducto) {
        this.listaProducto = listaProducto;
    }

    public RepositorioProducto getRepositorio() {
        return repositorio;
    }

    public void setRepositorio(RepositorioProducto repositorio) {
        this.repositorio = repositorio;
    }

    public Producto agregar(Producto p) {
        return repositorio.save(p);

    }

    public boolean borrar(Long id) {
        try {
            repositorio.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Producto actualizar(Producto p) {
        return repositorio.save(p);
    }

    public void informes() {

    }

    public double inventarioTotal() {
        double suma = 0;
        for (Producto p : listaProducto) {
            suma += p.getPrecio() * p.getInventario();
        }
        return suma;
    }

    public String precioMenor() {
        String nombre = listaProducto.get(0).getNombre();
        double precio = listaProducto.get(0).getPrecio();
        for (Producto p : listaProducto) {
            if (p.getPrecio() < precio) {
                nombre = p.getNombre();
                precio = p.getPrecio();
            }
        }
        return nombre;
    }

    public String precioMayor() {
        String nombre = listaProducto.get(0).getNombre();
        double precio = listaProducto.get(0).getPrecio();
        for (Producto p : listaProducto) {
            if (p.getPrecio() > precio) {
                nombre = p.getNombre();
                precio = p.getPrecio();
            }
        }
        return nombre;
    }

    public double promedio() {
        double suma = 0;
        for (Producto p : listaProducto) {
            suma += p.getPrecio();
        }
        return suma / listaProducto.size();
    }

//    
//    public List<Producto> listar(){
//        List<Producto> productos = ( List<Producto>) repositorio.findAll();
//        for (Producto producto : productos) {
//            System.out.println("Id:" + producto.getCodigo() +"Nombre: " + producto.getNombre()); // atento aquí
//        }
//        return productos;
//    }
    public ArrayList<Producto> obtenerProductos() {
        return (ArrayList<Producto>) repositorio.findAll();
    }

    public Optional<Producto> obtenerProducto(Long id) {
        return repositorio.findById(id);
    }

    public void eventoAgregar() {
        String nombre = vista.getCampoNombre();
        String precio = vista.getCampoPrecio();
        String inventario = vista.getCampoInventario();
        if (!nombre.equals("") && !precio.equals("") && !inventario.equals("")) {
            Producto nuevo = new Producto(nombre, Double.parseDouble(precio), Integer.parseInt(inventario));
            listaProducto.add(nuevo);
            DefaultTableModel modelo = (DefaultTableModel) vista.getTabla().getModel();
            modelo.insertRow(listaProducto.size() - 1, new Object[]{nuevo.getNombre(), nuevo.getPrecio(), nuevo.getInventario()});
        } else{
            MuestraError me = new MuestraError();
            me.setVisible(true);
        }
    }

    public void eventoEliminar() {
        int filaEliminar = vista.getTabla().getSelectedRow(); // Para saber cuál se está presionando
        listaProducto.remove(filaEliminar);
        DefaultTableModel modelo = (DefaultTableModel) vista.getTabla().getModel();
        modelo.removeRow(filaEliminar);
    }

    public void ventanaActualizacion() { //abrirVentanaAct()
        Actualizar a = new Actualizar();
        a.setControlador(this);
        a.setVisible(true);
    }

    public void eventoActualizar(Actualizar v) {
        
        String nombre = v.getCampoNombreA();
        String precio = v.getCampoPrecioA();
        String inventario = v.getCampoInventarioA();
        if (!nombre.equals("") && !precio.equals("") && !inventario.equals("")) {
            int filaActualizar = vista.getTabla().getSelectedRow();
            DefaultTableModel modelo = (DefaultTableModel) vista.getTabla().getModel();
            listaProducto.get(filaActualizar).setNombre(nombre);
            listaProducto.get(filaActualizar).setPrecio(Double.parseDouble(precio));
            listaProducto.get(filaActualizar).setInventario(Integer.parseInt(inventario));
            actualizar(listaProducto.get(filaActualizar));
            modelo.setValueAt(nombre, filaActualizar, 0);
            modelo.setValueAt(Double.parseDouble(precio), filaActualizar, 1);
            modelo.setValueAt(Integer.parseInt(inventario), filaActualizar, 2);
        }else{
            MuestraError me = new MuestraError();
            me.setVisible(true);
        }        
    }
    
    public void eventoInforme(){
        //String mostrar = "Producto precio Mayor: "+precioMayor()+ "\n "+ " Producto precio menor: "+precioMenor()+ "\n " + " Promedio de precios: "+promedio() + "\n " +" Valor inventario: "+inventarioTotal();
        InformeV i = new InformeV();
        i.setVisible(true);
        i.setInventario(i.getInventario()+inventarioTotal());
        i.setMayor(i.getMayor()+precioMayor());
        i.setMenor(i.getMenor()+precioMenor());
        i.setPromedio(i.getPromedio()+promedio());
    }

    public void inicializaTabla() {
        DefaultTableModel modelo = (DefaultTableModel) vista.getTabla().getModel();
        int cont = 0;
        for (Producto producto : listaProducto) {
            modelo.insertRow(cont, new Object[]{producto.getNombre(), producto.getPrecio(), producto.getInventario()});
            cont += 1;
        }
    }

}
