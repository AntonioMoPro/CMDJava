import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Consola c = new Consola();
        Scanner sc = new Scanner(System.in);
        String [] comandoArray;

        do{
            System.out.print(c.getRuta() + "> ");
            String comando = sc.nextLine();

            comando = comando.replace(" ", "ª");

            if (comando.startsWith("cd") || comando.startsWith("mkdir") || comando.startsWith("info") || comando.startsWith("cat") || comando.startsWith("delete") || comando.startsWith("start")){
                comando = ajusteComando2(comando);
            }else if (comando.startsWith("top") || comando.startsWith("readpoint")){
                comando = ajusteComando3Leer(comando);
            }else if (comando.startsWith("mkfile") || comando.startsWith("write")){
                comando = ajusteComando3Escribir(comando);
            }else {
                comando = comando;
            }

            comandoArray = comando.split("ª");

            menu(comandoArray, c);

        }while(!comandoArray[0].equalsIgnoreCase("exit"));
    }

    public static String ajusteComando3Escribir(String comando){

        if (comando.contains("\"")){
            if (comando.contains("ª")){
                String ncomando = "";
                for (int i = 0; i < comando.indexOf("ª") + 1; i++){
                    ncomando = ncomando + comando.charAt(i);
                }

                String nruta = "";
                for (int i = comando.indexOf("ª") + 1; i < comando.indexOf("\"") - 1; i++){
                    nruta = nruta + comando.charAt(i);
                }

                nruta = nruta.replace("ª", " ");

                String text = "";
                for (int i = comando.indexOf('"')-1 ; i < comando.length(); i++){
                    text = text + comando.charAt(i);
                }
                return ncomando + nruta + text;
            }

        }else{
            //Lanzar excepcion
        }
        return comando;
    }

    public static String ajusteComando2 (String comando){
        if (comando.contains(":")){
            return siComandoRutaAbosluta(comando);
        }

        if (comando.contains("ª") && !comando.contains(":")){

            String nruta = "";

            for (int i = comando.indexOf("ª") + 1; i < comando.length(); i++){
                nruta = nruta + comando.charAt(i);
            }

            String ncomando = "";
            for (int i = 0 ; i < comando.indexOf("ª") + 1; i++){
                ncomando = ncomando + comando.charAt(i);
            }

            nruta = nruta.replace("ª", " ");

            return ncomando + nruta;
        }
        return comando;
    }

    public static String ajusteComando3Leer (String comando){
        if (comando.contains(":")){
            return siComandoRutaAbosluta(comando);
        }

        if (comando.contains("ª") && !comando.contains(":")){

            String comandoReducido = "";

            for (int i = comando.indexOf("ª") + 1; i < comando.length(); i++){
                comandoReducido = comandoReducido + comando.charAt(i);
            }

            String posAlreves = "";
            for (int i = comandoReducido.indexOf("ª"); i > -1; i--){
                posAlreves = posAlreves + comandoReducido.charAt(i);
            }

            String pos2 = "";
            for (int i = posAlreves.length() - 1; i > -1; i--){
                pos2 = pos2 + posAlreves.charAt(i);
            }

            String nruta = "";
            for (int i = comandoReducido.indexOf("ª") + 1; i < comandoReducido.length(); i++){
                nruta = nruta + comandoReducido.charAt(i);
            }

            String ncomando = "";
            for (int i = 0 ; i < comando.indexOf("ª") + 1; i++){
                ncomando = ncomando + comando.charAt(i);
            }

            nruta = nruta.replace("ª", " ");

            return ncomando + pos2 + nruta;
        }
        return comando;
    }

    public static String siComandoRutaAbosluta (String comando){
        String nruta = "";

        for (int i = comando.indexOf(":") - 1; i < comando.length(); i++){
            nruta = nruta + comando.charAt(i);
        }

        nruta = nruta.replace("ª", " ");

        String ncomando = "";
        for (int i = 0 ; i < comando.indexOf(":") - 1; i++){
            ncomando = ncomando + comando.charAt(i);
        }
        return ncomando + nruta;
    }

    public static void menu (String [] comandoArray, Consola c){
        try {
            switch (comandoArray[0]) {
                case "":
                    break;

                case "help":
                    c.help();
                    break;

                case "cd":
                    c.cd(comandoArray);
                    break;

                case "mkdir":
                    c.mkdir(comandoArray);
                    break;

                case "info":
                    c.info(comandoArray);
                    break;

                case "cat":
                    c.cat(comandoArray);
                    break;

                case "top":
                    c.top(comandoArray);
                    break;

                case "mkfile":
                    c.mkfile(comandoArray);
                    break;

                case "write":
                    c.write(comandoArray);
                    break;

                case "dir":
                    c.dir(comandoArray);
                    break;

                case "tree":
                    c.tree(comandoArray);
                    break;

                case "readpoint":
                    c.readpoint(comandoArray);
                    break;

                case "delete":
                    c.delete(comandoArray);
                    break;

                case "start":
                    c.start(comandoArray);
                    break;

                case "close":
                    c.close(comandoArray);
                    break;

                case "clear":
                    c.clear(comandoArray);
                    break;

                case "exit":
                    System.out.println("Adios");
                    break;

                default:
                    comandoArray[0] = comandoArray[0].replace("ª", "");
                    System.out.println(comandoArray[0] + " no se reconoce como un comando interno o externo programa o archivo por lotes ejecutable.");
                    System.out.println("Utilice el comando help para obtener ayuda de los comandos");
            }
        }catch (MiExcepcion e){
            System.err.println(e.getMensaje());
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}