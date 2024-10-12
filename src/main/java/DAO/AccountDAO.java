package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {
    Account addAccount(Account account){
        Connection con = ConnectionUtil.getConnection();
        try{
            String query = "INSERT INTO account(username, password) VALUES(?,?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            if(!ps.execute())
                return account;
            

        }catch(SQLException e){
            e.getStackTrace();
        }
        return null;
    }

    Account findByAccount_id(int id){
        Connection con = ConnectionUtil.getConnection();
        Account account = null;
        try{
            String query = "SELECT * FROM account WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                account = new Account();
                account.setAccount_id(rs.getInt("Account_id"));
                account.setUsername(rs.getString("username"));
                account.setPassword(rs.getString("password"));
            }
            

        }catch(SQLException e){
            e.getStackTrace();
        }
        return account;
    }

    Account findByUsername(String username){
        Connection con = ConnectionUtil.getConnection();
        Account account = null;
        try{
            String query = "SELECT * FROM account WHERE username=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                account = new Account();
                account.setAccount_id(rs.getInt("Account_id"));
                account.setUsername(rs.getString("username"));
                account.setPassword(rs.getString("password"));
            }
            

        }catch(SQLException e){
            e.getStackTrace();
        }
        return account;
    }


    
}
