import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
public class Consola {

    private String ruta;
    private Path p;
    private ArrayList<Process> listaProcesos;

    public Consola (){
        ruta = System.getProperty("user.dir");
        p = Path.of(ruta);
        listaProcesos = new ArrayList<>();
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public Path getP() {
        return p;
    }

    public void setP(Path p) {
        this.p = p;
    }

    public ArrayList<Process> getListaProcesos() {
        return listaProcesos;
    }

    public void setListaProcesos(ArrayList<Process> listaProcesos) {
        this.listaProcesos = listaProcesos;
    }

    public void help(){
        System.out.println("\uF0D8 help -> lista todos comandos\".\n" +
                "\uF0D8 cd -> muestra la ruta del directorio actual\n" +
                "o cd .. \uF0E0 Se mueve al directorio padre.\n" +
                "o cd + nombre \uF0E0 lista archivos de ese directorio.\n" +
                "\uF0D8 mkdir -> crea un directorio de la ruta actual\"\n" +
                "\uF0D8 info <nombre> -> Muestra la información del elemento Indicando:\n" +
                "\uF0B7 Nº of elements\n" +
                "\uF0B7 FreeSpace,\n" +
                "\uF0B7 TotalSpace\n" +
                "\uF0B7 UsableSpace\n" +
                "\uF0D8 cat <nombreFichero> -> Muestra el contenido de un fichero.\n" +
                "\uF0D8 top <numeroLineas><NombreFichero> -> Muestra las líneas especificadas de un\n" +
                "fichero.\"\n" +
                "\uF0D8 mkfile <nombreFichero> <texto> -> Crea un fichero con ese nombre y el contenido de\n" +
                "texto.\n" +
                "\uF0D8 write <nombreFichero> <texto>-> Añade 'texto' al final del fichero especificado.\n" +
                "\uF0D8 dir -> Lista los archivos o directorios de la ruta actual.\n" +
                "\uF0D8 tree-> Lista recursiva.\n" +
                "\uF0D8 readpoint <posición> <nombreFichero1> \uF0E0 Lee un archivo desde una determinada\n" +
                "posición del puntero.\n" +
                "\uF0D8 delete <nombre>-> Borra el fichero, si es un directorio borra todo su contenido y a si\n" +
                "mismo.\n" +
                "\uF0D8 Start <programa> ejecuta este proceso.\n" +
                "\uF0D8 close-> Cierra el programa.\n" +
                "\uF0D8 Clear -> Vacía la lista.");
    }

    public void cd (String [] comandoArray) throws MiExcepcion{

        if (comandoArray.length > 2){
            throw new MiExcepcion("Error de sintaxis. Utilice el comando help.");
        }else if (comandoArray.length == 1){

            System.out.println(ruta);

        }else if (comandoArray[1].equalsIgnoreCase("..")){

            p = p.getParent();
            ruta = p.toString();

        }else {
            Path newRuta = Path.of(rutaAbsoluta(comandoArray[1]).getAbsolutePath());

            if (Files.exists(newRuta)){

                ruta = newRuta.toAbsolutePath().toString();
                p = Path.of(ruta);

            }else {
                throw new MiExcepcion("La ruta no existe.");
            }

        }
    }

    public void mkdir (String [] comandoArray) throws MiExcepcion{

        if (comandoArray.length != 2){
            throw new MiExcepcion("Error de sintaxis. Utilice el comando help.");

        }else {
            File f = rutaAbsoluta(comandoArray[1]);

            if (f.mkdirs()){
                System.out.println("Se creo la carpeta " + comandoArray[1] + " en " + ruta);
            }else {
                throw new MiExcepcion("No se pudo crear la carpeta.");
            }

        }
    }

    public File rutaAbsoluta(String rutaAbsoluta){
        if (!rutaAbsoluta.contains("\\")){
            return new File(ruta +"\\" + rutaAbsoluta);
        }else {
            return new File(rutaAbsoluta);
        }
    }

    public void info (String [] comandoArray) throws MiExcepcion{
        if (comandoArray.length != 2){
            throw new MiExcepcion("Error de sintaxis. Utilice el comando help.");
        }else {

            File f = rutaAbsoluta(comandoArray[1]);

            if (f.exists()) {

                try {
                    File [] roots = f.listFiles();
                    int nElementos = 0;

                    for (int i = 0; i < roots.length; i++){
                        nElementos++;
                    }

                    System.out.println("Numero de elementos: " + nElementos);
                    System.out.println("Ruta: " + f.getAbsolutePath());
                    System.out.println("Espacio libre: " + f.getFreeSpace() + " bytes");
                    System.out.println("Espacio total: " + f.getTotalSpace() + " bytes");
                    System.out.println("Espacio usable: " + f.getUsableSpace() + " bytes");
                }catch (NullPointerException e){
                    System.err.println("No se pudo listar el archivo seleccionado.");
                }

            }else {
                throw new MiExcepcion("El archivo o la ruta no existe.");
            }
        }
    }

    public void cat (String [] comandoArray) throws MiExcepcion{
        if (comandoArray.length != 2){
            throw new MiExcepcion("Error de sintaxis. Utilice el comando help.");
        }else {
            File f = rutaAbsoluta(comandoArray[1]);

            if (f.exists()) {
                try {

                    File[] roots = f.listFiles();

                    if (roots.length != 0){
                        for (File root : roots) {
                            System.out.println(root.getName());
                        }
                    }else {
                        throw new MiExcepcion("La carpeta esta vacia");
                    }

                }catch (NullPointerException e){
                    System.err.println("No se pudo listar el archivo seleccionado.");
                }
            }else {
                throw new MiExcepcion("El archivo o la ruta no existe.");
            }
        }
    }

    public String extension (String archivo, String extension){
        if (archivo.endsWith(extension)){
            return archivo;
        }else {
            return archivo + extension;
        }
    }

    public File fileExtensionRutaAbs (String path, String extension){
        path = extension(path, extension);
        return rutaAbsoluta(path);
    }

    public int numeroLineas (File f) throws IOException {

        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        int numeroLinea = 0;

        while ((br.readLine()) != null) {
            numeroLinea++;
        }
        return numeroLinea;

    }

    public void top (String [] comandoArray) throws MiExcepcion{
        if (comandoArray.length != 3){
            throw new MiExcepcion("Error de sintaxis. Utilice el comando help.");
        }else {

            try{

                File f = fileExtensionRutaAbs(comandoArray[1], ".txt");
                int lineasDeseadas = Integer.parseInt(comandoArray[2]);

                if (f.exists()){

                    if (lineasDeseadas > numeroLineas(f)){
                        throw new MiExcepcion("No se pudo leer " + lineasDeseadas + " lineas, porque el archivo tiene " + numeroLineas(f) + " lineas.");
                    }

                    BufferedReader br = new BufferedReader(new FileReader(f));

                    int numeroLinea = 1;

                    while (numeroLinea <= lineasDeseadas) {
                        System.out.println(br.readLine());
                        numeroLinea++;
                    }

                    br.close();

                }else {
                    throw new MiExcepcion("El archivo o la ruta no existe.");
                }

            }catch (NumberFormatException e){
                System.err.println("El tercer argumento debe ser un numero.");
            }catch (IOException e) {
                e.printStackTrace();
                System.err.println("No se pudo leer el archivo.");
            }
        }
    }

    public void mkfile (String [] comandoArray) throws MiExcepcion{

        if (comandoArray.length < 3){
            throw new MiExcepcion("Error de sintaxis. Utilice el comando help.");
        }else {
            File f = fileExtensionRutaAbs(comandoArray[1], ".txt");

            try {

                if(f.createNewFile()){
                    System.out.println("Se creó el archivo " + f.getName());
                    write(comandoArray);

                }else{
                    throw new MiExcepcion("No se pudo crear el archivo " + f.getName() + ".");
                }

            } catch (IOException e) {
                System.err.println("No se pudo crear el archivo " + f.getName() + ".");
            }
        }
    }

    public void write(String [] comandoArray) throws MiExcepcion{
        if (comandoArray.length < 3){
            throw new MiExcepcion("Error de sintaxis. Utilice el comando help.");
        }else{
            File f = fileExtensionRutaAbs(comandoArray[1], ".txt");

            if (f.exists()){
                try {

                    FileWriter fw = new FileWriter(f, true);
                    BufferedWriter bw = new BufferedWriter(fw);

                    String mensaje = comandoArray[2];
                    for (int i = 3; i < comandoArray.length; i++) {
                        mensaje = mensaje + " " + comandoArray[i];
                    }

                    bw.write(mensaje);
                    bw.newLine();
                    System.out.println("Se escribió el texto en el archivo " + f.getName());

                    bw.close();
                }catch (IOException e){
                    System.err.println("No se pudo escribir en el archivo.");
                }
            }else{
                throw new MiExcepcion("El archivo o la ruta no existe.");
            }
        }
    }

    public void dir (String [] comandoArray) throws MiExcepcion{
        if (comandoArray.length != 1){
            throw new MiExcepcion("Error de sintaxis. Utilice el comando help.");
        }else {
            File f = new File(ruta);

            try{
                File[] roots = f.listFiles();

                for (File root : roots) {
                    System.out.println(root.getName());
                }
            }catch (NullPointerException e){
                System.err.println("No se pudo listar el archivo seleccionado.");
            }
        }
    }

    public void readpoint (String [] comandoArray) throws MiExcepcion{
        if (comandoArray.length != 3){
            throw new MiExcepcion("Error de sintaxis. Utilice el comando help.");
        }else {
            File f = fileExtensionRutaAbs(comandoArray[1], ".txt");

            if (f.exists()){
                RandomAccessFile randomAccessFile = null;
                try{
                    int posicion = Integer.parseInt(comandoArray[2]);
                    randomAccessFile = new RandomAccessFile(f, "r");

                    if (posicion > randomAccessFile.length()){
                        throw new MiExcepcion("No se pudo acceder a la posición " + posicion + ".");
                    }else{

                        randomAccessFile.seek(posicion);
                        String linea;
                        while ((linea = randomAccessFile.readLine()) != null){
                            System.out.println(linea);
                        }

                    }
                }catch (NumberFormatException e){
                    System.err.println("El tercer argumento debe ser un numero.");
                }catch (FileNotFoundException e){
                    System.err.println("Archivo no encontrado.");
                }catch (IOException e){
                    System.err.println("No se pudo acceder a la posicion.");
                }

                try {
                    randomAccessFile.close();
                } catch (IOException e) {
                    System.err.println("No se pudo cerrar el randomAccessFile.");
                } catch (NullPointerException e){
                    System.err.println("No se pudo cerrar el randomAccessFile.");
                }

            }else {
                throw new MiExcepcion("El archivo o la ruta no existe.");
            }
        }
    }

    public void delete (String [] comandoArray) throws MiExcepcion{
        if (comandoArray.length !=2){
            throw new MiExcepcion("Error de sintaxis. Utilice el comando help.");
        }else{
            File f = rutaAbsoluta(comandoArray[1]);

            if (f.exists()){
                borrar(f);
            }else{
                throw new MiExcepcion("El archivo o la ruta no existe.");
            }
        }
    }

    public void borrar (File f) throws MiExcepcion{
        if (f.isDirectory()){
            File[] archivos = f.listFiles();
            if (archivos != null) {
                for (File archivo : archivos) {
                    if (archivo.isDirectory()) {
                        borrar(archivo);
                    } else {
                        archivo.delete();
                    }
                }
            }
            if(f.delete()){
                System.out.println("El archivo " + f.getName() + " se elimino correctamente");
            }else{
                throw new MiExcepcion("No se pudo eliminar el archivo" + f.getName() + ".");
            }
        }else{
            if(f.delete()){
                System.out.println("El archivo " + f.getName() + " se elimino correctamente");
            }else{
                throw new MiExcepcion("No se pudo eliminar el archivo" + f.getName() + ".");

            }
        }
    }

    public void start(String [] comandoArray) throws MiExcepcion{
        if (comandoArray.length != 2){
            throw new MiExcepcion("Error de sintaxis. Utilice el comando help.");
        }else {
            String proceso = comandoArray[1];

            try {
                ProcessBuilder pb = new ProcessBuilder(proceso);
                Process ps = pb.start();
                listaProcesos.add(ps);

            } catch (IOException e) {
                System.err.println("No se pudo abrir " + proceso + ".");
            }
        }
    }

    public void close (String [] comandoArray) throws MiExcepcion{
        if (comandoArray.length != 1){
            throw new MiExcepcion("Error de sintaxis. Utilice el comando help.");
        }else {

                String nombreProceso;
                for (Process ps : listaProcesos){
                    ps.destroy();
                }

                listaProcesos.clear();


        }
    }

    public void clear (String [] comandoArray) throws MiExcepcion{
        if (comandoArray.length != 1){
            throw new MiExcepcion("Error de sintaxis. Utilice el comando help.");
        }else {
            for (int i = 0; i < 10; i++){
                System.out.println("");
            }
        }
    }

    public void tree (String [] comandoArray) throws MiExcepcion{
        if (comandoArray.length != 1){
            throw new MiExcepcion("Error de sintaxis. Utilice el comando help.");
        }else {
            listarRecursivo(ruta, 0);
        }
    }

    public void listarRecursivo (String rout, int nivel){
        File f = new File(rout);
        File [] arrayF = f.listFiles();
        if (arrayF != null){
            for (File archivo : arrayF){
                String espacios = "  ".repeat(nivel);
                System.out.println(espacios + archivo.getName() + (archivo.isDirectory() ? "/" : ""));

                if (archivo.isDirectory()){
                    listarRecursivo(archivo.getAbsolutePath(), nivel + 1);
                }
            }
        }
    }
}
