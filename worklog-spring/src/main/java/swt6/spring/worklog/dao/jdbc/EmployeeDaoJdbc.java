package swt6.spring.worklog.dao.jdbc;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import swt6.spring.worklog.dao.EmployeeDao;
import swt6.spring.worklog.domain.Employee;

import java.sql.*;
import java.util.List;

public class EmployeeDaoJdbc extends JdbcDaoSupport implements EmployeeDao {


    // Helper class that maps result sets to collections
    protected static class EmployeeRowMapper implements RowMapper<Employee> {

        @Override
        public Employee mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Employee employee = new Employee();

            employee.setId(resultSet.getLong(1));
            employee.setFirstName(resultSet.getString(2));
            employee.setLastName(resultSet.getString(3));
            employee.setDateOfBirth(resultSet.getDate(4).toLocalDate());
            return employee;
        }
    }

    @Override
    public Employee findById(Long id) {
        final String sql = "select id, firstName, lastName, dateOfBirth from Employee where id =?";
        final Object[] params = {id};
        try {
            return getJdbcTemplate().queryForObject(sql, params, new EmployeeRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public Employee findById2(Long id) {
        final String sql = "select id, firstName, lastName, dateOfBirth from Employee where id =?";
        final Object[] params = {id};
        List<Employee> emplList = getJdbcTemplate().query(sql, params, new EmployeeRowMapper());
        return emplList.size() == 0 ? null : emplList.get(0);
    }

    public Employee findById3(Long id) {
        final String sql = "select id, firstName, lastName, dateOfBirth from Employee where id =?";
        final Employee employee = new Employee();
        getJdbcTemplate().query(sql, new Object[]{id}, (ResultSet resultSet) -> {
            employee.setId(resultSet.getLong(1));
            employee.setFirstName(resultSet.getString(2));
            employee.setLastName(resultSet.getString(3));
            employee.setDateOfBirth(resultSet.getDate(4).toLocalDate());
        });
        return employee.getId() == null ? null : employee;
    }

    public Employee findById4(Long id) {
        final String sql = "select id, firstName, lastName, dateOfBirth from Employee where id =?";
        final Employee employee = new Employee();
        final Object[] params = {id};
        RowCallbackHandler rowHandler = new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                employee.setId(resultSet.getLong(1));
                employee.setFirstName(resultSet.getString(2));
                employee.setLastName(resultSet.getString(3));
                employee.setDateOfBirth(resultSet.getDate(4).toLocalDate());
            }
        };
        getJdbcTemplate().query(sql, params, rowHandler);
        return employee.getId() == null ? null : employee;
    }

    @Override
    public List<Employee> findAll() {
        String sql = "select id, firstName, lastName, dateOfBirth from Employee";
        return getJdbcTemplate().query(sql, new EmployeeRowMapper());
    }

    //Version 1
    public void insert1(Employee entity) throws DataAccessException {
        final String sql = "insert into Employee(fistName, lastName, dateOfBirth) values (?,?,?)";
        try (Connection conn = getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, entity.getFirstName());
            stmt.setString(2, entity.getLastName());
            stmt.setDate(3, Date.valueOf(entity.getDateOfBirth()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    //Version 2
    public void insert2(Employee entity) throws DataAccessException {
        final String sql = "insert into Employee(fistName, lastName, dateOfBirth) values (?,?,?)";

        getJdbcTemplate().update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement stmt) throws SQLException {
                stmt.setString(1, entity.getFirstName());
                stmt.setString(2, entity.getLastName());
                stmt.setDate(3, Date.valueOf(entity.getDateOfBirth()));
            }
        });
    }

    //Version 2b
    public void insert2b(Employee entity) throws DataAccessException {
        final String sql = "insert into Employee(fistName, lastName, dateOfBirth) values (?,?,?)";
        getJdbcTemplate().update(sql, stmt -> {
            stmt.setString(1, entity.getFirstName());
            stmt.setString(2, entity.getLastName());
            stmt.setDate(3, Date.valueOf(entity.getDateOfBirth()));
        });
    }

    //Version 3
    public void insert3(Employee entity) throws DataAccessException {
        final String sql = "insert into Employee(firstName, lastName, dateOfBirth) values (?,?,?)";
        getJdbcTemplate().update(sql, entity.getFirstName(), entity.getLastName(), entity.getDateOfBirth());
    }

    @Override //Version 4 if autogenerated key is needed
    public void insert(Employee entity) throws DataAccessException {
        final String sql = "insert into Employee(firstName, lastName, dateOfBirth) values (?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update(conn -> {
            PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"id"});
            stmt.setString(1, entity.getFirstName());
            stmt.setString(2, entity.getLastName());
            stmt.setDate(3, Date.valueOf(entity.getDateOfBirth()));
            return stmt;
        }, keyHolder);
        entity.setId(keyHolder.getKey().longValue());
    }

    private void update(Employee employee) throws DataAccessException {
        final String sql = "update Employee set firstName=?,lastName=?, dateOfBirth=? where id = ?";
        getJdbcTemplate().update(sql,
                employee.getFirstName(),
                employee.getLastName(),
                Date.valueOf(employee.getDateOfBirth()),
                employee.getId());
    }

    @Override
    public Employee merge(Employee entity) {
        if (entity.getId() == null) {
            insert(entity);
        } else {
            update(entity);

        }
        return entity;
    }
}