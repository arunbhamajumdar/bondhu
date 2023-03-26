

import java.io.File;
import java.util.Arrays;
import java.util.List;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.EncryptionMethod;

public class EncryptedZip {

	public static void main(String [] args) throws ZipException {
		if("-ZSD".equals(args[0])) {
			if(args.length!=5) {
				System.out.println("Usage:\n java -classpath zip4j-2.11.4.jar EncryptedZip -ZSD inputFolder zipFileName password sizeInMB");
				System.exit(1);
			}
			String inputFolder = args[1];
			String zipFileName = args[2];
			String pwd = args[3];
			int mb = Integer.valueOf(args[4]);
			zipSplitFolder(zipFileName, pwd, inputFolder, mb);
		}
		else if("-U".equals(args[0])) {
			if(args.length!=4) {
				System.out.println("Usage:\n java -classpath zip4j-2.11.4.jar EncryptedZip -U zipFileName destinationFolder password");
				System.exit(1);
			}
			String zipFileName = args[1];
			String destFolder = args[2];
			String pwd = args[3];
			unzip(zipFileName, pwd, destFolder);
		}
	}
	public static void zipFile(String zipFileName, String pwd, String fileName) throws ZipException {
		ZipParameters zipParameters = new ZipParameters();
		zipParameters.setEncryptFiles(true);
		zipParameters.setCompressionLevel(CompressionLevel.HIGHER);
		zipParameters.setEncryptionMethod(EncryptionMethod.AES);

		ZipFile zipFile = new ZipFile(zipFileName, pwd.toCharArray());
		zipFile.addFile(new File(fileName), zipParameters);		
	}
	
	public static void zipMultipleFiles(String zipFileName, String pwd, List<File> filesToAdd ) throws ZipException {
		ZipParameters zipParameters = new ZipParameters();
		zipParameters.setEncryptFiles(true);
		zipParameters.setEncryptionMethod(EncryptionMethod.AES);


		ZipFile zipFile = new ZipFile(zipFileName, pwd.toCharArray());
		zipFile.addFiles(filesToAdd, zipParameters);		
	}
	
	public static void zipFolder(String zipFileName, String pwd) throws ZipException {
		ZipParameters zipParameters = new ZipParameters();
		zipParameters.setEncryptFiles(true);
		zipParameters.setEncryptionMethod(EncryptionMethod.AES);
		ZipFile zipFile = new ZipFile(zipFileName, pwd.toCharArray());
		zipFile.addFolder(new File("/users/folder_to_add"), zipParameters);		
	}
	
	public static void zipSplitFile(String zipFileName, String pwd) throws ZipException {
		ZipParameters zipParameters = new ZipParameters();
		zipParameters.setEncryptFiles(true);
		zipParameters.setEncryptionMethod(EncryptionMethod.AES);
		ZipFile zipFile = new ZipFile(zipFileName, pwd.toCharArray());
		int splitLength = 1024 * 1024 * 10; //10MB
		zipFile.createSplitZipFile(Arrays.asList(new File("aFile.txt")), zipParameters, true, splitLength);
			
	}
	public static void zipSplitFolder(String zipFileName, String pwd, String folderToAdd, int mb) throws ZipException {
		ZipParameters zipParameters = new ZipParameters();
		zipParameters.setEncryptFiles(true);
		zipParameters.setEncryptionMethod(EncryptionMethod.AES);
		ZipFile zipFile = new ZipFile(zipFileName, pwd.toCharArray());
		int splitLength = 1024 * 1024 * mb; 
		zipFile.createSplitZipFileFromFolder(new File(folderToAdd), zipParameters, true, splitLength);
			
	}	
	
	public static void unzip(String zipFileName, String pwd, String destFolder) throws ZipException {
		ZipFile zipFile = new ZipFile(zipFileName, pwd.toCharArray());
		zipFile.extractAll(destFolder);		
	}
	
	public static void unzipFile(String zipFileName, String pwd, FileHeader fileToExtract, String destFolder) throws ZipException {
		ZipFile zipFile = new ZipFile(zipFileName, pwd.toCharArray());
		zipFile.extractFile(fileToExtract, destFolder);		
	}
}
