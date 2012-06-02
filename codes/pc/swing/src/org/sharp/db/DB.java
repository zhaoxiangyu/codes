package org.sharp.db;

import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

@SuppressWarnings("deprecation")
public class DB {

	/*private static Connection conn = nullnewConn();
	private static QueryRunner qr = new QueryRunner();*/
	
	private static final SessionFactory sessionFactory;
    static {
        try {
            Properties properties = new Properties();
            properties.put("", "");
            
			sessionFactory = new AnnotationConfiguration()
            	.addProperties(properties)
                .configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    private Session session(){
    	return sessionFactory.getCurrentSession();
    }
    
	/*private static Connection newConn(){
		try {
			String dbpath = "db/data";
			return DriverManager.getConnection("jdbc:hsqldb:file:"+dbpath, "SA", "");
		} catch (SQLException e) {
			Console.log.error("", e);
		}
		return null;
	}

	public static int update(String sql){
		try {
			return qr.update(conn,sql);
		} catch (SQLException e) {
			Console.log.error("", e);
		}
		return -1;
	}
	
	public static <T extends Object> T load(Class<T> clazz,String sql,Object... params){
		try {
			return qr.query(conn, sql, new BeanHandler<T>(clazz), params);
		} catch (SQLException e) {
			Console.log.error("", e);
		}
		return null;
	}*/

}
