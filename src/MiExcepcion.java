public class MiExcepcion extends Exception{
    private String mensaje;

    public MiExcepcion (String mensaje){
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
