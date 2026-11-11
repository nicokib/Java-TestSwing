package utils;

import model.Person;

public class CurrentUser {

    private static Person user;

    public static Person getUser() {
        return user;
    }

    public static void setUser(Person newUser) {
        user = newUser;
    }

    /*public boolean isStudent() {
        return user instanceof Student;
    }

    public boolean isInstructor() {
        return user instanceof Instructor;
    }*/
}
