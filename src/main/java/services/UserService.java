package services;

import exceptions.UsernameDoesNotExistException;
import exceptions.WrongPasswordException;
import exceptions.WrongRoleException;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import exceptions.UsernameAlreadyExistsException;
import models.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import static services.FileSystemService.getPathToFile;

public class UserService {

    private static ObjectRepository<User> userRepository;

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("user_database.db").toFile())
                .openOrCreate("test", "test");

        userRepository = database.getRepository(User.class);
    }

    public static void addUser(String username, String password, String role) throws UsernameAlreadyExistsException {
        checkUserDoesNotAlreadyExist(username);
        userRepository.insert(new User(username, encodePassword(username, password), role));
    }

    private static void checkUserDoesNotAlreadyExist(String username) throws UsernameAlreadyExistsException {
        for (User user : userRepository.find()) {
            if (Objects.equals(username, user.getUsername()))
                throw new UsernameAlreadyExistsException(username);
        }
    }

    private static String encodePassword(String salt, String password) {
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        // This is the way a password should be encoded when checking the credentials
        return new String(hashedPassword, StandardCharsets.UTF_8)
                .replace("\"", ""); //to be able to save in JSON format
    }

    private static MessageDigest getMessageDigest() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 does not exist!");
        }
        return md;
    }

    public static void checkUserCredentials(String username,String password,String role) throws UsernameDoesNotExistException, WrongPasswordException, WrongRoleException {
        int oku=0,okp=0,okr=0;
        for(User user : userRepository.find()){
            if(Objects.equals(username,user.getUsername())) {
                oku = 1;
                if(Objects.equals(role,user.getRole()))
                    okr = 1;
            }
            if(Objects.equals(encodePassword(username,password),user.getPassword()))
                okp = 1;
        }
        if( oku == 0 )
            throw new UsernameDoesNotExistException(username);
        if( okr == 0 )
            throw new WrongRoleException(role);
        if ( okp == 0 )
            throw new WrongPasswordException();

    }

    public static String getLoggedUser(String username){
        for (User user : userRepository.find()) {
            if (Objects.equals(username, user.getUsername()))
                return username;
        }
        return "";
    }

    public static String getUserRole(String username){
        for (User user : userRepository.find()) {
            if (Objects.equals(username, user.getUsername()))
                if(Objects.equals(user.getRole(),"Client"))
                    return "Client";
                else
                    return "Admin";
        }
        return "";
    }

}
