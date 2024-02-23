Descripción:

Este proyecto es una aplicación de consola en Java que permite al usuario ejecutar diversos comandos para interactuar con el sistema de archivos, ejecutar programas y obtener información del sistema.

Funcionalidades:

Navegar por el sistema de archivos:
-cd: Cambiar el directorio actual.
-dir: Listar los archivos y directorios del directorio actual.
-tree: Listar los archivos y directorios del directorio actual de forma recursiva.

Información del sistema:
-help: Mostrar una lista de todos los comandos disponibles.
-info: Mostrar información sobre un archivo o directorio.

Manipulación de archivos:
-mkdir: Crear un nuevo directorio.
-mkfile: Crear un nuevo archivo.
-cat: Mostrar el contenido de un archivo.
-top: Mostrar las primeras líneas de un archivo.
-write: Agregar texto al final de un archivo.
-readpoint: Leer un archivo desde una posición específica.
-delete: Eliminar un archivo o directorio.

Ejecución de programas:
-start: Iniciar un programa.
-close: Cerrar todos los programas abiertos.

Ejemplo de uso:

cd /home/usuario/Documentos,
dir,
mkdir nuevo_directorio,
mkfile nuevo_archivo.txt,
cat nuevo_archivo.txt,
write nuevo_archivo.txt "Este es un nuevo texto.",
readpoint 10 nuevo_archivo.txt,
delete nuevo_directorio
