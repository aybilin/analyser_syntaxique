package com.analyser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

class ClassStructureVisitor extends ASTVisitor {

	 // Visite des déclarations de classes
    @Override
    public boolean visit(TypeDeclaration node) {
        System.out.println("Classe trouvée: " + node.getName());
        return super.visit(node);
    }

    // Visite des méthodes
    @Override
    public boolean visit(MethodDeclaration node) {
        System.out.println("\tMéthode trouvée: " + node.getName());
        return super.visit(node);
    }

    // Visite des déclarations de variables
    @Override
    public boolean visit(VariableDeclarationFragment node) {
        System.out.println("\t\tVariable trouvée: " + node.getName());
        return super.visit(node);
    }
}
