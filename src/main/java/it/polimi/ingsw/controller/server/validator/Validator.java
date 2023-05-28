package it.polimi.ingsw.controller.server.validator;

public class Validator {

    public static boolean isValidUsername(String username) {
        return username != null && username.length() <= 20 && !username.isBlank() && !username.contains("@") && !username.contains(" ");
    }

}