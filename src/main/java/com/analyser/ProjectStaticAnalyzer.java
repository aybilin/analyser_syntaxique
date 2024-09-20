package com.analyser;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class ProjectStaticAnalyzer {

    public static void main(String[] args) throws IOException {
        // Chemin vers le projet à analyser
        String projectPath = "C:\\Users\\Ayoub\\Downloads\\visitorDesignPattern\\visitorDesignPattern";  // Remplace par le chemin de ton projet

        // Parcours du répertoire du projet pour trouver tous les fichiers .java
        File projectDir = new File(projectPath);
        if (projectDir.exists() && projectDir.isDirectory()) {
            analyzeDirectory(projectDir);
        } else {
            System.out.println("Le chemin spécifié n'est pas un répertoire valide.");
        }
    }

    // Fonction pour parcourir tous les fichiers dans un répertoire
    public static void analyzeDirectory(File dir) throws IOException {
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                // Appel récursif si c'est un sous-répertoire
                analyzeDirectory(file);
            } else if (file.getName().endsWith(".java")) {
                // Analyse du fichier si c'est un fichier Java
                analyzeJavaFile(file.getPath());
            }
        }
    }

    // Fonction pour analyser un fichier Java avec l'AST
    public static void analyzeJavaFile(String filePath) throws IOException {
        // Lecture du contenu du fichier
        String sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));

        // Création et configuration du parser AST
        ASTParser parser = ASTParser.newParser(AST.JLS4);  // Spécifier Java 8 (JLS8)
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setSource(sourceCode.toCharArray());

        // Options de compilation pour Java 8
        Map<String, String> options = JavaCore.getOptions();
        options.put(JavaCore.COMPILER_SOURCE, "1.8");  // Utiliser Java 8
        options.put(JavaCore.COMPILER_COMPLIANCE, "1.8");
        options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, "1.8");
        parser.setCompilerOptions(options);

        // Création de l'AST
        CompilationUnit cu = (CompilationUnit) parser.createAST(null);

        // Impression du fichier en cours d'analyse
        System.out.println("Analyzing: " + filePath);

        // Utilisation d'un visiteur pour extraire les informations
        cu.accept(new ClassStructureVisitor());
    }
}