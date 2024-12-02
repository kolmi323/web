package s03.service;

import s03.dao.AccountModel;

import java.util.List;
import java.util.regex.Pattern;

public class DataCheckerService extends Service {
    public DataCheckerService(Service service) {
        this.userDao = service.getUserDao();
    }

    public boolean checkEmailPattern(String email) {
        Pattern pattern = Pattern.compile("^([a-z0-9_.-]+)@([a-z0-9_.-]+)\\.([a-z.]{2,6})$",
                Pattern.CASE_INSENSITIVE);
        return pattern.matcher(email).matches();
    }

    public boolean checkEmailUnique(String email) {
        List<String> emails = this.userDao.getEmails();
        return emails.stream().anyMatch(e -> e.equals(email));
    }

    public boolean checkPasswordLength(String password) {
        return password.length() < 10;
    }

    public boolean checkBalanceAccountPattern(String account) {
        Pattern pattern = Pattern.compile("^[0-9]+\\.[0-9]+$", Pattern.CASE_INSENSITIVE);
        return pattern.matcher(account).matches();
    }

    public boolean checkNameAccountUnique(int userId, String name) {
        List<AccountModel> accounts = userDao.getListAccount(userId);
        return accounts.stream().anyMatch(account -> account.getName().equals(name));
    }
}
