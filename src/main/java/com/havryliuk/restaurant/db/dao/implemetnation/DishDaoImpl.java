package com.havryliuk.restaurant.db.dao.implemetnation;

import com.havryliuk.restaurant.db.connection.ConnectionPool;
import com.havryliuk.restaurant.db.connection.RestaurantConnectionPool;
import com.havryliuk.restaurant.exceptions.DBException;
import com.havryliuk.restaurant.exceptions.DaoException;
import com.havryliuk.restaurant.db.dao.DishDao;
import com.havryliuk.restaurant.db.entity.Category;
import com.havryliuk.restaurant.db.entity.Dish;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DishDaoImpl implements DishDao {
    static Logger log = Logger.getLogger(DishDaoImpl.class.getName());
    private static ConnectionPool connectionPool;

    public DishDaoImpl () throws DBException {
        connectionPool = RestaurantConnectionPool.getInstance();
    }

    @Override
    public Dish findByName(String name) throws DBException {
        Dish dish = null;
//        Connection con = connectionPool.getConnection();
//        try (PreparedStatement stmt = con.prepareStatement(DishSql.FIND_DISH_BY_NAME.QUERY)) {
//            stmt.setString(1, name);
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    dish = mapTeam(rs);
//                }
//            }
//            log.info("The  " + team.getName() + " team received from DB.");
//        } catch (SQLException e) {
//            log.error("Error in getting team " + name, e);
//            throw new DBException(e);
//        }
        return dish;
    }

    @Override
    public List<Dish> findByCategory(Category category) throws DBException {
        return null;
    }

    @Override
    public List<Dish> getSortedByName() throws DBException {
        return null;
    }

    @Override
    public List<Dish> getSortedByPrice() throws DBException {
        return null;
    }

    @Override
    public List<Dish> getSortedByCategory() throws DBException {
        return null;
    }

    @Override
    public boolean create(Dish entity) {
        return false;
    }

    @Override
    public Dish findById(Long id) throws DBException {
        return null;
    }

    @Override
    public List<Dish> findAll() throws DBException {
        return null;
    }

    @Override
    public Dish update(Dish entity) throws DBException {
        return null;
    }

    @Override
    public boolean delete(Dish entity) throws DBException {
        return false;
    }

    @Override
    public boolean delete(Long id) throws DBException {
        return false;
    }

    private Dish mapDish(ResultSet rs) throws SQLException {

//        String name = rs.getString(Fields.TEAM_NAME);
//        return Dish.getInstance(name, );


return null;
    }

    enum DishSql {
        ADD_DISH("INSERT INTO dish (name) values (?)"),//todo
        FIND_DISH_BY_NAME("SELECT * FROM dish d WHERE d.name=?"),
        UPDATE_DISH("UPDATE teams SET name=? WHERE id=?"),//todo
        //        DELETE_TEAM("DELETE t, ut FROM teams t JOIN users_teams ut WHERE t.id=? AND ut.team_id=?;"),
        DELETE_DISH("DELETE FROM teams WHERE id=?"),//todo
        DELETE_USERS_IN_TEAM("DELETE FROM users_teams WHERE team_id=?"),//todo
        GET_ALL_DISHES("SELECT * FROM teams t ORDER BY t.name"),//todo
        GET_TEAMS_BY_USER("SELECT id, name FROM teams t JOIN users_teams ut ON t.id=ut.team_id WHERE ut.user_id=?");//todo

        String QUERY;

        DishSql(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}
