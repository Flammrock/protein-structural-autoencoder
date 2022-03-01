package system;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import bio.Protein;

public class ResourceManager {
	
	private static final String Root = System.getProperty("user.home");
	private static final Path RootPath = Paths.get(Root.toString(),".protein_structural_autoencoder");
	private static final Path ProteinPath = Paths.get(RootPath.toString(), "Protein");
	
	public static List<File> getResourceFiles(String basePath) {
		List<File> files = new ArrayList<>();
		
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
	    URL url = loader.getResource(basePath);
	    String path = url.getPath();
	    
	    File[] fs = new File(path).listFiles();
	    for (File f : fs) {
	    	files.add(f);
	    }
		
		return files;
	}
	
	public static Resource getResourceAsStream(String path) throws FileNotFoundException {
		File file = Paths.get(RootPath.toString(), path).toFile();
		return new Resource(file.getName(), new FileInputStream(file));
	}
	
	public static void setup() {
		File directory = new File(ProteinPath.toString());
	    if (!directory.exists()){
	    	directory.mkdirs();
	    }
	    List<File> files = getResourceFiles("protein");
	    for (File file : files) {
	    	Path pathDest = Paths.get(ProteinPath.toString(),file.getName());
	    	File dest = new File(pathDest.toString());
	    	if (dest.exists()) continue;
	    	try {
				Files.copy(new FileInputStream(file),pathDest);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	}
	
}
