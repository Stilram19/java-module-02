package ex00;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

class Program {
    public static void main(String[] args) {
        Path workDir = Paths.get(System.getProperty("user.dir"));
        Path signaturesFilePath = workDir.resolve("ex00/signatures.txt");
        Path resultFilePath = workDir.resolve("ex00/result.txt");
        File resultFile = resultFilePath.toFile();
        Signatures signatures = new Signatures(signaturesFilePath.toString());
        Scanner scanner = new Scanner(System.in);
        int maxSignatureLength = signatures.getMaxSignatureLength();

        // clear the result file at the beginning
        try (FileWriter clearWriter = new FileWriter(resultFile, false)) {
        } catch (IOException e) {
            System.err.println("error clearing result.txt: " + e.getMessage());
        }

        try (FileWriter resultWriter = new FileWriter(resultFile, true)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                try (BufferedInputStream reader = new BufferedInputStream(new FileInputStream(line))) {
                    String signature = "";
                    for (int i = 0; i < maxSignatureLength; i++) {
                        int readByte = reader.read();
                        if (readByte == -1) {
                            break ;
                        }
                        signature += String.format("%02X", readByte);
                        String fileFormat = signatures.getFileFormat(signature);

                        if (fileFormat != null) {
                            System.out.println("PROCESSED");
                            resultWriter.write(fileFormat + "\n");
                            break ;
                        }

                        if (i == maxSignatureLength - 1) {
                            System.out.println("SIGNATURE: " + signature);
                            System.err.println("UNDEFINED");
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Error reading or processing the file: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error opening the result.txt file: " + e.getMessage());
        }
    }
}
