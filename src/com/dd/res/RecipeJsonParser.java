package com.dd.res;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.dd.dto.RecipeBasic;

public class RecipeJsonParser {
	Connection con;
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "admin";
	String password = "1234";

	public RecipeJsonParser() {
		StringBuffer sb;
		con();
		try {
			URL url = this.getClass().getClassLoader().getResource("res/BasicOfRecipe.json");
			URI uri = url.toURI(); // url을 uri로 변경!
			FileReader reader = new FileReader(new File(uri));
			BufferedReader buffr = new BufferedReader(reader);
			sb = new StringBuffer();
			String data = null;
			while (true) {
				data = buffr.readLine();
				if (data == null)
					break;
				sb.append(data);
			}
			JSONParser jsonParser = new JSONParser();
			JSONObject obj = (JSONObject) jsonParser.parse(sb.toString());
			JSONArray jsonArray = (JSONArray) obj.get("data");
			ArrayList<RecipeBasic> list = new ArrayList<RecipeBasic>();
			for (int i = 0; i < jsonArray.size(); i++) {
//				System.out.println(i+"-------------------------");
//				System.out.println(jsonArray.get(i));
//				System.out.println(i+"-------------------------");
				RecipeBasic r = new RecipeBasic();
				JSONObject o = (JSONObject) jsonArray.get(i);
				r.setRecipe_id((Long) o.get("RECIPE_ID"));
				r.setRecipe_nm_ko((String) o.get("RECIPE_NM_KO"));
				r.setSumry((String) o.get("SUMRY"));
				r.setQnt((String) o.get("QNT"));
				r.setLevel_nm((String) o.get("LEVEL_NM"));
				r.setCooking_time((String) o.get("COOKING_TIME"));
				r.setCalorie((String) o.get("CALORIE"));
				r.setImg_url((String) o.get("IMG_URL"));
				list.add(r);
			}
			insert(list);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void con() {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insert(ArrayList<RecipeBasic> list) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "insert into recipe_basic(recipe_id, recipe_nm_ko, sumry, cooking_time, calorie, qnt, level_nm, img_url) values(?, ?, ?, ? ,? ,? ,? ,?)";

		for (int i = 0; i < list.size(); i++) {
			try {
				RecipeBasic r = list.get(i);
				pstmt = con.prepareStatement(sql);
				pstmt.setLong(1, r.getRecipe_id());
				pstmt.setString(2, r.getRecipe_nm_ko());
				pstmt.setString(3, r.getSumry());
				pstmt.setString(4, r.getCooking_time());
				pstmt.setString(5, r.getCalorie());
				pstmt.setString(6, r.getQnt());
				pstmt.setString(7, r.getLevel_nm());
				pstmt.setString(8, r.getImg_url());
				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}

		System.out.println(result);
	}

	public static void main(String[] args) {
		new RecipeJsonParser();
	}

}
