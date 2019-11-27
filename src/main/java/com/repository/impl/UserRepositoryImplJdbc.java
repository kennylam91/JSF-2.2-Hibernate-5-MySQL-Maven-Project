package com.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.hibernate.SessionFactory;

import com.beans.User;
import com.constant.AUTHORITIES;
import com.constant.Constant;
import com.repository.UserRepository;
import com.util.JdbcConnection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ManagedBean(name = "userRepositoryJdbc")
@SessionScoped
public class UserRepositoryImplJdbc implements UserRepository {

	private static final long serialVersionUID = -8381815397768976670L;

	@Override
	public Long save(User user) {
		String sql = ""
				+ "INSERT into users( username, password, email, authority ) "
				+ "VALUES(?,?,?,?)";
		Long status = null;
		try {
			Connection con = JdbcConnection.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getAuthority().toString());
			status = (long) ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
		return status;
	}

	@Override
	public User validateUser(User user) {
		String sql = ""
				+ "SELECT username, email, authority "
				+ "FROM users "
				+ "WHERE email = ? and password = ?";
		try {
			Connection con = JdbcConnection.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, user.getEmail());
			ps.setString(2, user.getPassword());
			ResultSet rs = ps.executeQuery();
			System.out.println(ps.toString());
			if(rs.next()) {
				String username = rs.getString("username");
				String email = rs.getString("email");
				AUTHORITIES authority = AUTHORITIES.valueOf(rs.getString("authority"));
				con.close();
				return User.builder()
						.username(username)
						.email(email)
						.authority(authority)
						.build();
			}
			return null;
			
			
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}		
	}

	public List<User> getAllUsers(){
		String sql = ""
				+ "SELECT 	user_id, username, email, authority "
				+ "FROM 	users";
		try {
			Connection con = JdbcConnection.getConnection();
			Statement sttm = con.createStatement();
			ResultSet rs = sttm.executeQuery(sql);
			List<User> users = new LinkedList<>();
			if(rs.next()) {
				User user = User.builder()
						.username(rs.getString("username"))
						.id(Long.parseLong(rs.getString("user_id")))
						.email(rs.getString("email"))
						.authority(AUTHORITIES.valueOf(rs.getString("authority")))
						.build();
				users.add(user);
			}
			return users;
			
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}
	
	public User findUserByEmail(String email) {
		String sql = ""
				+ "SELECT 	user_id, username, email, authority "
				+ "FROM 	users "
				+ "WHERE 	email = ?";
		User user = null;
		try {
			Connection con = JdbcConnection.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				user = User.builder()
						.username(rs.getString("username"))
						.id(Long.parseLong(rs.getString("user_id")))
						.email(rs.getString("email"))
						.authority(AUTHORITIES.valueOf(rs.getString("authority")))
						.build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

}
