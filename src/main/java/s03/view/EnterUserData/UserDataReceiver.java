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
        return this.inputRequest.requestNotBlancString("Enter your Name or enter \"exit\" to exit: ");
    }

    public String enterEmail(boolean isEmailForRegistration) {
        Predicate<String> predicate;
        if (isEmailForRegistration) {
            predicate = str -> userDataChecker.isEmailCorrect(str) && userDataChecker.isEmailUnique(str);
            return this.inputRequest.requestNotBlancString("Enter your Email or enter \"exit\" to exit: ",
                    predicate);
        } else {
            predicate = userDataChecker::isEmailCorrect;
            return this.inputRequest.requestNotBlancString("Enter your Email or enter \"exit\" to exit: ",
                    predicate);
        }
    }

    public String enterPassword() {
        Predicate<String> predicate = this.userDataChecker::isPasswordCorrect;
        return this.inputRequest.requestNotBlancString("Enter your Password or enter \"exit\" to exit: ", predicate);
    }

    public String enterNameAccount(int userId, boolean isNameForCreateAccount) {
        if (isNameForCreateAccount) {
            Predicate<String> predicate = str -> userDataChecker.isNameAccountUnique(userId, str);
            return this.inputRequest.requestNotBlancString("Enter your Name Account or enter \"exit\" to exit: ",
                    predicate);
        } else {
            return this.inputRequest.requestNotBlancString("Enter your Name Account or enter \"exit\" to exit: ");
        }
    }

    public String enterBalanceAccount() {
        Predicate<String> predicate= this.userDataChecker::isBalanceAccountCorrect;
        return this.inputRequest.requestNotBlancString("Enter balance for your account " +
                " or enter \"exit\" to exit: ", predicate);
    }
}
