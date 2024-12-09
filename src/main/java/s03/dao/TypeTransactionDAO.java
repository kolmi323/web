package s03.dao;

import s03.CustomException.DAOException;
import s03.dao.AbstractClass.DAO;
import s03.dao.Model.TypeTransactionModel;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TypeTransactionDAO extends DAO {
    public TypeTransactionDAO(DataSource ds) {
        super(ds);
    }

    public boolean updateType(TypeTransactionModel typeTransactionModel, String newName) {
        try (PreparedStatement psst = getDataSource().getConnection().prepareStatement
                ("UPDATE type SET name = ? WHERE name = ? AND user_id = ?")) {
            psst.setString(1, newName);
            psst.setString(2, typeTransactionModel.getName());
            psst.setInt(3, typeTransactionModel.getUserId());
            if (psst.executeUpdate() == 1) {
                return true;
            } else {
                throw new DAOException();
            }
        } catch (DAOException daoe) {
            System.out.println(daoe.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean insertType(TypeTransactionModel typeTransactionModel) {
        try (PreparedStatement psst = getDataSource().getConnection().prepareStatement
                ("INSERT INTO type (user_id, name) VALUES (?, ?)"))
        {
            psst.setInt(1, typeTransactionModel.getUserId());
            psst.setString(2, typeTransactionModel.getName());
            if (psst.executeUpdate() == 1) {
                return true;
            } else {
                throw new DAOException();
            }
        } catch (DAOException daoe) {
            System.out.println(daoe.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean deleteType(TypeTransactionModel typeTransactionModel) {
        try (PreparedStatement psst = getDataSource().getConnection().prepareStatement
                ("DELETE FROM type WHERE user_id = ? and name = ?"))
        {
            psst.setInt(1, typeTransactionModel.getUserId());
            psst.setString(2, typeTransactionModel.getName());
            if (psst.executeUpdate() == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
