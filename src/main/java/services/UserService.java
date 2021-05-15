package services;

import exceptions.*;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import models.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import static services.FileSystemService.getPathToFile;

public class UserService {

    private static ObjectRepository<User> userRepository;

    private static User activeUser;

    public static void setActiveUser(String email)
    {
        for (User user : userRepository.find())
        {
            if (user.getEmail().equals(email))
            {
                activeUser = user;
            }
        }
    }

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("user_database.db").toFile())
                .openOrCreate("test", "test");

        userRepository = database.getRepository(User.class);
    }

    public static void addUser(String username, String password, String role, String email, String phoneNumber, String address, String cnp) throws EmailAlreadyExistsException {
        checkEmailDoesNotAlreadyExist(email);
        userRepository.insert(new User(username, encodePassword(email, password), role, email, phoneNumber, address, cnp));
    }

    private static void checkEmailDoesNotAlreadyExist(String email) throws EmailAlreadyExistsException {
        for (User user : userRepository.find()) {
            if (Objects.equals(email, user.getEmail()))
                throw new EmailAlreadyExistsException(email);
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

    public static void checkUserCredentials(String email,String password,String role) throws EmailDoesNotExistException, WrongPasswordException, WrongRoleException {
        int oku=0,okp=0,okr=0;
        for(User user : userRepository.find()){
            if(Objects.equals(email,user.getEmail())) {
                oku = 1;
                if(Objects.equals(role,user.getRole()))
                    okr = 1;
            }
            if(Objects.equals(encodePassword(email,password),user.getPassword()))
                okp = 1;
        }
        if( oku == 0 )
            throw new EmailDoesNotExistException(email);
        if( okr == 0 )
            throw new WrongRoleException(role);
        if ( okp == 0 )
            throw new WrongPasswordException();

    }

    public static boolean checkIfUserIsBlocked(String email)
    {
        for(User user : userRepository.find())
        {
            if (user.getEmail().equals(email) && user.getIsBlocked() == true)
            {
                return true;
            }
        }

        return false;
    }

    public static User getActiveUser()
    {
        return activeUser;
    }

    public static void blockUser(String email) throws EmailDoesNotExistException, CannotBlockAdminException {

        for(User user : userRepository.find())
        {
            if (user.getEmail().equals(email))
            {
                if (user.getRole().equals("Admin"))
                {
                    throw new CannotBlockAdminException(email);
                }
                user.setIsBlocked(true);
                userRepository.update(user);
                return;
            }
        }

        throw new EmailDoesNotExistException(email);
    }
}
