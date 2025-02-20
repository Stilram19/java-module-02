package ex00;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Signatures {
    private int maxSignatureLength;
    private final String filePath;
    private final Map<String, String> signatures;

    Signatures(String filePath) {
        this.maxSignatureLength = 0;
        this.filePath = filePath;
        this.signatures = new HashMap<>();
        this.buildSignaturesMap();
    }

    public int getMaxSignatureLength() {
        return (this.maxSignatureLength);
    }

    public String getFileFormat(String signature) {
        return (this.signatures.get(signature));
    }

    private void buildSignaturesMap() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toString()))) {
            String line;
            String error = "Invalid Input from Signatures.txt";

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length != 2) {
                    throw new RuntimeException(error);
                }

                String fileFormat = parts[0].trim();
                String fileSignature = parts[1].trim();

                try {
                    this.signatures.put(fileSignature, fileFormat);
                    this.maxSignatureLength = Math.max(this.maxSignatureLength, fileSignature.length());
                } catch (RuntimeException e) {
                    throw new RuntimeException("Error filling the hashmap!");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Input Error: " + e.getMessage());
        }
    }
}