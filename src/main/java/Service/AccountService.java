package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {

    AccountDAO accountDAO;

    public AccountService(){
        this.accountDAO = new AccountDAO();
    }

    public Account login(Account account){
        Account retreivedAccount = accountDAO.findByUsername(account.getUsername());


        if(retreivedAccount != null && retreivedAccount.getPassword().equals(account.getPassword()))
            return retreivedAccount;
        return null;
    }


    public Account addAccount(Account account){
        if(account.getUsername().isBlank() || account.getPassword().length() < 4)
            return null;

        return accountDAO.addAccount(account);
    }
    
}
