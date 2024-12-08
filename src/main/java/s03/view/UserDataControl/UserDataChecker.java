package s03.view.UserDataControl;

import s03.service.DataCheckerService;
import s03.service.DTO.ServiceDTO;

public class UserDataChecker {
    private final DataCheckerService service;

    public UserDataChecker(ServiceDTO service) {
        this.service = new DataCheckerService(service);
    }

    public boolean isEmailCorrect(String email) {
        if (service.checkEmailPattern(email)) {
            return true;
        }
        System.out.println("Email is not valid" +
                "\nEmail example: vasnecov@yandex.ru");
        return false;
    }

    public boolean isEmailUnique(String email) {
        if (service.checkEmailUnique(email)) {
            System.out.println("Email already exists.");
            return false;
        }
        return true;
    }

    public boolean isPasswordCorrect(String password) {
        if (service.checkPasswordLength(password)) {
            return true;
        }
        System.out.println("Password length is too long");
        return false;
    }

    public boolean isBalanceAccountCorrect(String balance) {
        if (service.checkBalanceAccountPattern(balance)) {
            return true;
        }
        System.out.println("Balance in not valid" +
                "\nBalance example: 12345.12");
        return false;
    }

    public boolean isNameAccountUnique(int userId, String name) {
        if (service.checkNameAccountUnique(userId, name)) {
            System.out.println("Name already exists.");
            return false;
        }
        return true;
    }
}
