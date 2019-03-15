package toristest.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class jdbcTest {
	private Connection con = null; // Database objects
	// 連接object
	private Statement stat = null;
	// 執行,傳入之sql為完整字串
	private ResultSet rs = null;
	// 結果集
	private PreparedStatement pst = null;
	// 執行,傳入之sql為預儲之字申,需要傳入變數之位置
	// 先利用?來做標示

	private String dropdbSQL = "DROP TABLE Toris ";

	private String createdbSQL = "CREATE TABLE Toris (" + "    id     INTEGER " + "  , name    VARCHAR(20) "
			+ "  , passwd  VARCHAR(20))";

	private String insertdbSQL = "insert into Toris(id,name,passwd) " + "select ifNULL(max(id),0)+1,?,? FROM Toris";

	private String selectSQL = "select * from Toris ";

	@Before
	public void beforeClass() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/jdbc?characterEncoding=utf-8&serverTimezone=UTC", "root", "1234");
		} catch (ClassNotFoundException e) {
			System.out.println("找不到驅動程式類別");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("con問題");
			e.printStackTrace();
		}
	};

	@After
	public void after() {
		
	}
	
	@Test
	public void main() {
		// 測看看是否正常
	    dropTable(); 
	    createTable(); 
	    insertTable("num", "0"); 
	    insertTable("content", "test"); 
	    SelectTable(); 

	}
	@Test
	public void update() {
		// 測看看是否正常
		try {
			String sql = "UPDATE Toris " +
	                "SET name = ? WHERE id = 1";
			pst = con.prepareStatement(sql);
			pst.setString(1, "mandsan");
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Close();
		}

 

	}

	public void jdbcmysql() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 註冊driver
			con = DriverManager.getConnection("jdbc:mysql://localhost/jdbc?characterEncoding=utf-8&serverTimezone=UTC",
					"root", "12345");
			// 取得connection

		} catch (ClassNotFoundException e) {
			System.out.println("DriverClassNotFound :" + e.toString());
		} // 有可能會產生sqlexception
		catch (SQLException x) {
			System.out.println("Exception :" + x.toString());
		}

	}

	// 建立table的方式
	// 可以看看Statement的使用方式
	public void createTable() {
		try {
			stat = con.createStatement();
			stat.executeUpdate(createdbSQL);
		} catch (SQLException e) {
			System.out.println("CreateDB Exception :" + e.toString());
		} finally {
			Close();
		}
	}

	// 新增資料
	// 可以看看PrepareStatement的使用方式
	public void insertTable(String name, String passwd) {
		try {
			pst = con.prepareStatement(insertdbSQL);

			pst.setString(1, name);
			pst.setString(2, passwd);
			pst.executeUpdate();
		} catch (SQLException e) {
			System.out.println("InsertDB Exception :" + e.toString());
		} finally {
			Close();
		}
	}

	// 刪除Table,
	// 跟建立table很像
	public void dropTable() {
		try {
			stat = con.createStatement();
			stat.executeUpdate(dropdbSQL);
		} catch (SQLException e) {
			System.out.println("DropDB Exception :" + e.toString());
		} finally {
			Close();
		}
	}

	// 查詢資料
	// 可以看看回傳結果集及取得資料方式
	public void SelectTable() {
		try {
			stat = con.createStatement();
			rs = stat.executeQuery(selectSQL);
			System.out.println("ID\t\tName\t\tPASSWORD");
			while (rs.next()) {
				System.out.println(rs.getInt("id") + "\t\t" + rs.getString("name") + "\t\t" + rs.getString("passwd"));
			}
		} catch (SQLException e) {
			System.out.println("DropDB Exception :" + e.toString());
		} finally {
			Close();
		}
	}

	// 完整使用完資料庫後,記得要關閉所有Object
	// 否則在等待Timeout時,可能會有Connection poor的狀況
	private void Close() {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (stat != null) {
				stat.close();
				stat = null;
			}
			if (pst != null) {
				pst.close();
				pst = null;
			}
		} catch (SQLException e) {
			System.out.println("Close Exception :" + e.toString());
		}
	}

}
