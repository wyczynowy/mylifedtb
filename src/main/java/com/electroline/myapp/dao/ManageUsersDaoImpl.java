package com.electroline.myapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.electroline.myapp.domain.User;

@Repository(value = "manageUsersDao")
public class ManageUsersDaoImpl implements ManageUsersDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	@Transactional
	public int createNewUser(User user) throws Exception {
		final String sql = "insert into users(username, password, enabled)"
				+ "values(?, ?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql, new String [] {"id"});
				ps.setString(1, user.getUsername());
				ps.setString(2, user.getPassword());
				ps.setInt(3, user.getEnabled());
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public User getUser(int usernameId) {
		final String sql = "select * from users where id = :usernameId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("usernameId", usernameId);
		return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new RowMapper<User>() {

			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setUserId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setEnabled(rs.getInt("enabled"));
				return user;
			}
		});
	}

	@Override
	public List<User> getAllUsers() {
		final String sql = "select * from users";
		return jdbcTemplate.query(sql, new RowMapper<User>() {

			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setUserId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setEnabled(rs.getInt("enabled"));
				return user;
			}
		});
	}

	@Override
	public void deleteUser(int usernameId) {
		final String sql = "delete from users where id =:usernameId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("usernameId", usernameId);
		namedParameterJdbcTemplate.update(sql, namedParameters);
		
	}

	@Override
	public void updateUser(User user) {
		jdbcTemplate.update("update users set username = ? where id = ?", user.getUsername(), user.getUserId());
		jdbcTemplate.update("update users set password = ? where id = ?", user.getPassword(), user.getUserId());
		jdbcTemplate.update("update users set enabled = ? where id = ?", user.getEnabled(), user.getUserId());
	}

}
