package s03.view.EnterUserData;

import s03.service.DTO.ServiceDTO;
import s03.view.UserDataControl.UserDataChecker;

import java.util.function.Predicate;

public class UserDataReceiver {
    private final InputRequest inputRequest;
    private final UserDataChecker userDataChecker;

    public UserDataReceiver(ServiceDTO service) {
        this.inputRequest = new InputRequest();
        this.userDataChecker = new UserDataChecker(service);
    }

    public String enterName() {
        String message = "Enter your Name or enter \"exit\" to exit: ";
        return this.inputRequest.requestNotBlancString(message);
    }

    public String enterEmail(boolean isEmailForRegistration) {
        String message = "Enter your Email or enter \"exit\" to exit: ";
        Predicate<String> predicate;
        if (isEmailForRegistration) {
            predicate = str -> userDataChecker.isEmailCorrect(str) && userDataChecker.isEmailUnique(str);
            return this.inputRequest.requestNotBlancString(message, predicate);
        } else {
            predicate = userDataChecker::isEmailCorrect;
            return this.inputRequest.requestNotBlancString(message, predicate);
        }
    }

    public String enterPassword() {
        String message = "Enter your Password or enter \"exit\" to exit: ";
        Predicate<String> predicate = this.userDataChecker::isPasswordCorrect;
        return this.inputRequest.requestNotBlancString(message, predicate);
    }

    public String enterNameAccount(int userId, boolean isNameForCreateAccount) {
        String message = "Enter your Name Account or enter \"exit\" to exit: ";
        if (isNameForCreateAccount) {
            Predicate<String> predicate = str -> userDataChecker.isNameAccountUnique(userId, str);
            return this.inputRequest.requestNotBlancString(message, predicate);
        } else {
            return this.inputRequest.requestNotBlancString(message);
        }
    }

    public String enterBalanceAccount() {
        String message = "Enter your Balance for your account or enter \"exit\" to exit: ";
        Predicate<String> predicate= this.userDataChecker::isBalanceAccountCorrect;
        return this.inputRequest.requestNotBlancString(message, predicate);
    }

    public String enterNameType(int userId, FlagEnterNameType flag) {
        String message = "Enter your Name Type or enter \"exit\" to exit: ";
        if (flag == FlagEnterNameType.CREATE) {
            Predicate<String> predicate = str -> userDataChecker.isNameTypeUnique(userId, str);
            return this.inputRequest.requestNotBlancString(message, predicate);
        } else if (flag == FlagEnterNameType.UPDATE) {
            Predicate<String> predicate = str -> !userDataChecker.isNameTypeUnique(userId, str);
            return this.inputRequest.requestNotBlancString("Enter your Old Name Type or enter \"exit\" to exit: ",
                    predicate);
        } else {
            return this.inputRequest.requestNotBlancString(message);
        }
    }
}
