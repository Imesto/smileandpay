package com.smileandpay.exception;

/**
 * Erreur si une entity n'est pas trouvé dans la base de donnée
 */
public class EntityNotFound extends RuntimeException {

    public EntityNotFound(String message) {
        super(message);
    }
}
