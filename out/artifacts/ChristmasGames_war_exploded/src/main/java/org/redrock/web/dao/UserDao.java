package org.redrock.web.dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.redrock.web.Utils.DBCPUtil;
import org.redrock.web.Utils.FormatUtil;
import org.redrock.web.clazz.User;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class UserDao {
    static DataSource dataSource=null;
    static final int reFreshCount=39;

    static final String user_table_sql="(SELECT id,openid,nickname,imgurl,score,share,count,last_login_time,rank FROM" +
            "(SELECT *,@rank := @rank + 1 AS rank FROM " +
            "(SELECT * FROM gameuser ORDER BY score DESC) as a,(SELECT @rank := 0 as zero) as b) as result)" +
            "as user_table";

    static {
        dataSource=DBCPUtil.getDataSource();
    }

    public  User findUserBy(String target, String info) throws SQLException {
        QueryRunner queryRunner=new QueryRunner(dataSource);
        String sql = "select * from "+user_table_sql+" where "+target+"=?";
        List<User> list=queryRunner.query(sql,new BeanListHandler<User>(User.class),info);
        if (list.size()>0) {
            return list.get(0);
        }else {
            return null;
        }
    }
    public  List<User> getRank(int num) throws SQLException {
        QueryRunner queryRunner=new QueryRunner(dataSource);
        String sql="SELECT * FROM "+user_table_sql+" LIMIT 0,"+num;
        List<User> list=queryRunner.query(sql,new BeanListHandler<User>(User.class));
        return list;
    }

    /**
     * 这个原来不准备写死的，直接让更新score和每日更新放一起，然而觉得那样写太累，就懒得写了
     * @param user
     * @throws SQLException
     */
    public void reFresh(User user) throws SQLException {
        Date date=new Date();
        QueryRunner queryRunner=new QueryRunner(dataSource);
        String sql="update gameuser set count = ? , last_login_time = ? where id = ?";
        queryRunner.update(sql,reFreshCount,FormatUtil.getDate(date),user.getId());
    }

    public void postPoint(User user,String score) throws SQLException {
        String sql="update gameuser set score = ?,count = "+(user.getCount()-1)+" where id = ? ";
        QueryRunner queryRunner=new QueryRunner(dataSource);
        queryRunner.update(sql,score,user.getId());
    }
}
