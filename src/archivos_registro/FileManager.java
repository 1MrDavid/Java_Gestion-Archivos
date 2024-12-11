package archivos_registro;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private File directory;

    // Establecer el directorio
    public void setDirectory(File directory) {
        this.directory = directory;
    }

    public File getDirectory() {
        return this.directory;
    }

    // Método para obtener nombres de archivos en la carpeta
    public List<String> getFileNames() {
        List<String> fileNames = new ArrayList<>();
        if (directory != null && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) { // Solo archivos, no carpetas
                        fileNames.add(file.getName());
                    }
                }
            }
        } else {
            System.out.println("El directorio no es válido o no está establecido.");
        }
        return fileNames;
    }
    
    public int eliminar_1() {
    	int count = 0;
    	if (directory != null && directory.isDirectory()) {
    		File[] files = directory.listFiles();
    		if (files != null) {
    			for (File file : files) {
    				if (file.isFile() && file.getName().contains("_1")) {
    					String archivo = file.getName();
    					
    					String nuevoNombre = archivo.replace("_1", "");
    						
    					File antiguoArchivo = new File(directory, archivo);
    					File nuevoArchivo = new File(directory, nuevoNombre);
    						
    					if (antiguoArchivo.renameTo(nuevoArchivo)) {
    						count += 1;
    					}
    					
    				}
    			}
    		}
    	}
    	return count;
    }
}