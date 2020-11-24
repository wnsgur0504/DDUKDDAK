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

import com.dd.dto.RecipeDetail;

public class DetailJsonParser {
	Connection con;
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "admin";
	String password = "1234";

	public DetailJsonParser() {
		StringBuffer sb;
		con();
		try {
			URL url = this.getClass().getClassLoader().getResource("res/DetailOfRecipe.json");
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
			ArrayList<RecipeDetail> list = new ArrayList<RecipeDetail>();
			for (int i = 0; i < jsonArray.size(); i++) {
//				System.out.println(i+"-------------------------");
//				System.out.println(jsonArray.get(i));
//				System.out.println(i+"-------------------------");
				RecipeDetail r = new RecipeDetail();
				JSONObject o = (JSONObject) jsonArray.get(i);
				r.setRecipe_id((long) o.get("RECIPE_ID"));
				r.setCooking_no((long) o.get("COOKING_NO"));
				r.setCooking_dc((String) o.get("COOKING_DC"));
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

	public void insert(ArrayList<RecipeDetail> list) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "insert into recipe_detail(recipe_detail, recipe_id, cooking_no, cooking_dc) values(seq_recipe_detail.nextval, ?, ?, ?)";

		for (int i = 0; i < list.size(); i++) {
			try {
				RecipeDetail r = list.get(i);
				pstmt = con.prepareStatement(sql);
				pstmt.setLong(1, r.getRecipe_id());
				pstmt.setLong(2, r.getCooking_no());
				pstmt.setString(3, r.getCooking_dc());
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
		new DetailJsonParser();
	}

}
