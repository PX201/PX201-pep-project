package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {
    public Account addAccount(Account account){
        
        String query = "INSERT INTO account(username, password) VALUES(?,?)";

        try{
            Connection con = ConnectionUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            
            if(ps.executeUpdate() > 0){
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        account.setAccount_id(generatedKeys.getInt(1)); 
                        System.out.println("myAccount" + account);
                        return account;
                    }
                }
            }
            System.out.println("Out of IF Clause _____________");


        }catch(SQLException e){
            e.getStackTrace();
        }
        return null;
    }

    public Account findByAccount_id(int id){
        Account account = null;
        String query = "SELECT * FROM account WHERE account_id=?";

        try{
            Connection con = ConnectionUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                System.out.println("Account retreved successfully");
                account = new Account();
                account.setAccount_id(rs.getInt("account_id"));
                account.setUsername(rs.getString("username"));
                account.setPassword(rs.getString("password"));
            }
            

        }catch(SQLException e){
            e.getStackTrace();
        }
        System.out.println("Account: " + account);
        return account;
    }

    public Account findByUsername(String username){
        Account account = null;
        String query = "SELECT * FROM account WHERE username=?";
        try{
            Connection con = ConnectionUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                account = new Account();
                account.setAccount_id(rs.getInt("account_id"));
                account.setUsername(rs.getString("username"));
                account.setPassword(rs.getString("password"));
            }

        }catch(SQLException e){
            e.getStackTrace();
        }
        return account;
    }


    
}
