package com.glonation.biller.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadParameter {

	private String dbUrl;
	private String dbName;
	private String user;
	private String password;
	private DBConn db;
	private LoadParameter lp;

	public LoadParameter (String dbUrl, String dbName, String user, String password) {
		this.dbUrl = dbUrl;
		this.dbName = dbName;
		this.user = user;
		this.password = password;
		this.setDb(db(this.dbUrl, this.dbName, this.user, this.password));
	}

	public LoadParameter reload () {
		this.db.conClose();
		this.lp = null;
		this.lp = new LoadParameter(dbUrl, dbName, user, password);
		return lp;
	}
	
	public Map<String, Map<String, Object>> map (String query, String keyColumn, String column1, String columnLast)
			throws SQLException {
		ResultSet rs = getDb().res(query);
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Map<String, Object>> superMap = new HashMap<String, Map<String, Object>>();
		ArrayList<String> key = new ArrayList<String>(1);
		ArrayList<String> value = new ArrayList<String>(1);
		String columnIdentifier = "";

		for (int i = Integer.valueOf(column1); i < Integer.valueOf(columnLast); i++) {
			columnIdentifier = rs.getString(Integer.valueOf(keyColumn));
			key.add(rs.getString(Integer.valueOf(i)));
			value.add(rs.getString(Integer.valueOf(i+1)));
			map.put(key.get(0).toString(), value.get(0).toString());
			key.clear();
			value.clear();
		}
		superMap.put(columnIdentifier,map);
		map = null;
//		System.out.println("Ini : "+superMap.get("1"));
		
		String keyTemp = "#"+columnIdentifier+"#";
		while (rs.next()) {
			map = new HashMap<String, Object>();
			columnIdentifier = rs.getString(Integer.valueOf(keyColumn));
			for (int i = Integer.valueOf(column1); i < Integer.valueOf(columnLast); i++) {
				if (keyTemp.contains("#"+columnIdentifier+"#")) {
					key.add(rs.getString(Integer.valueOf(i)));
					value.add(rs.getString(Integer.valueOf(i+1)));
					Map<String, Object> merged = merger(superMap.get(columnIdentifier), key.get(0).toString(), value.get(0).toString());
					superMap.put(columnIdentifier, merged);
					key.clear();
					value.clear();
				}
				else {
					keyTemp = keyTemp+columnIdentifier+"#";
					key.add(rs.getString(Integer.valueOf(i)));
					value.add(rs.getString(Integer.valueOf(i+1)));
					map.put(key.get(0).toString(), value.get(0).toString());
					superMap.put(columnIdentifier, map);
					map = null;
					key.clear();
					value.clear();
				}
			}
		}
		rs.close();
		getDb().stt.close();
		getDb().con.close();
		return superMap;
	}
	
	public Map<String, Object> map2 (String query, String column1, String columnLast)
			throws SQLException {
		ResultSet rs = getDb().res(query);
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<String> key = new ArrayList<String>(1);
		ArrayList<String> value = new ArrayList<String>(1);

		for (int i = Integer.valueOf(column1); i < Integer.valueOf(columnLast); i++) {
			key.add(rs.getString(Integer.valueOf(i)));
			value.add(rs.getString(Integer.valueOf(i+1)));
			map.put(key.get(0).toString(), value.get(0).toString());
			key.clear();
			value.clear();
		}
		
		while (rs.next()) {
			for (int i = Integer.valueOf(column1); i < Integer.valueOf(columnLast); i++) {
				key.add(rs.getString(Integer.valueOf(i)));
				value.add(rs.getString(Integer.valueOf(i+1)));
				map.put(key.get(0).toString(), value.get(0).toString());
				key.clear();
				value.clear();
			}
		}
		rs.close();
		getDb().stt.close();
		getDb().con.close();
		return map;
	}
	
	private DBConn db (String dbUrl, String dbName, String user, String password) {
		DBConn db = new DBConn(dbUrl, dbName, user, password);
		db.connectDB();
		return db;
	}

	public DBConn getDb() {
		return db;
	}

	public void setDb(DBConn db) {
		this.db = db;
	}
	
	private Map<String, Object> merger (Map<String, Object> map, String key, Object value) {
		Map<String, Object> newMap = new HashMap<String, Object>();
		newMap.putAll(map);
		newMap.put(key, value);
		return newMap;
	}
	
	private Map<String, Map<String, Object>> merger2 (Map<String, Map<String, Object>> map, String key, Map<String, Object> value) {
		Map<String, Map<String, Object>> newMap = new HashMap<String, Map<String, Object>>();
		newMap.putAll(map);
		newMap.put(key, value);
		return newMap;
	}
	
	public Map<String, Map<String, Object>> mapColumnAsKey (String query, String keyColumn, String column1, String columnLast)
			throws SQLException {
		ResultSet rs = getDb().res(query);
		ResultSetMetaData metaData = rs.getMetaData();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Map<String, Object>> superMap = new HashMap<String, Map<String, Object>>();
		ArrayList<String> key = new ArrayList<String>(1);
		ArrayList<String> value = new ArrayList<String>(1);
		String columnIdentifier = "";

		for (int i = Integer.valueOf(column1); i < Integer.valueOf(columnLast)+1; i++) {
			columnIdentifier = rs.getString(Integer.valueOf(keyColumn));
			key.add(metaData.getColumnName(i));
			value.add(rs.getString(Integer.valueOf(i)));
			map.put(key.get(0).toString(), value.get(0).toString());
			key.clear();
			value.clear();
		}
		superMap.put(columnIdentifier,map);
		map = null;
//		System.out.println("Ini : "+superMap.get("1"));
		
		String keyTemp = "#"+columnIdentifier+"#";
		while (rs.next()) {
			map = new HashMap<String, Object>();
			columnIdentifier = rs.getString(Integer.valueOf(keyColumn));
			for (int i = Integer.valueOf(column1); i < Integer.valueOf(columnLast)+1; i++) {
				if (keyTemp.contains("#"+columnIdentifier+"#")) {
					key.add(metaData.getColumnName(i));
					value.add(rs.getString(Integer.valueOf(i)));
					Map<String, Object> merged = merger(superMap.get(columnIdentifier), key.get(0).toString(), value.get(0).toString());
					superMap.put(columnIdentifier, merged);
					key.clear();
					value.clear();
				}
				else {
					keyTemp = keyTemp+columnIdentifier+"#";
					key.add(metaData.getColumnName(i));
					value.add(rs.getString(Integer.valueOf(i)));
					map.put(key.get(0).toString(), value.get(0).toString());
					superMap.put(columnIdentifier, map);
					map = null;
					key.clear();
					value.clear();
				}
			}
		}
		rs.close();
		getDb().stt.close();
		getDb().con.close();
		return superMap;
	}
	
	

	public Map<String, Map<String, Map<String, Object>>> getAdapterConfiguration (String query, String identifier, String identifier2, String identifier3) throws SQLException {
		ResultSet rs = null;;
		ResultSetMetaData metaData = null;
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
		Map<String, Map<String, Object>> temp = new HashMap<String, Map<String, Object>>();
		Map<String, Map<String, Map<String, Object>>> result = new HashMap<String, Map<String, Map<String, Object>>>();
		Map<String, Map<String, Map<String, Map<String, Object>>>> resMap = new HashMap<String, Map<String, Map<String, Map<String, Object>>>>();
		
		ArrayList<String> listId = new ArrayList<String>();
		ArrayList<String> listId2 = new ArrayList<String>();
		ArrayList<String> listId3 = new ArrayList<String>();
		String[] tRes;
		String[] tRes2;
		String[] tRes3;
		try {
			rs = getDb().res(query);
			metaData = rs.getMetaData();
			int rowIndex = 1;
//			System.out.println("--------row 1--------");
			int countMapIdentifier = 0;
			int countMapIdentifier2 = 0;
			int countMapIdentifier3 = 0;
			
			String columnIdentifier = rs.getString(Integer.valueOf(identifier));
			String columnIdentifier2 = rs.getString(Integer.valueOf(identifier2));
			String columnIdentifier3 = rs.getString(Integer.valueOf(identifier3));
			
			String counterChecker = "#"+columnIdentifier+"#";
			String counterChecker2 = "#"+columnIdentifier2+"#";
			String counterChecker3 = "#"+columnIdentifier3+"#";
			
			String identifierName = metaData.getColumnName(Integer.valueOf(identifier));
			String identifierName2 = metaData.getColumnName(Integer.valueOf(identifier2));
			String identifierName3 = metaData.getColumnName(Integer.valueOf(identifier3));
			
			countMapIdentifier++;
			countMapIdentifier2++;
			countMapIdentifier3++;
			
			listId.add(columnIdentifier);
			listId2.add(columnIdentifier2);
			listId3.add(columnIdentifier3);
			
			for (int i = 1; i < metaData.getColumnCount()+1; i++) {
				map.put(metaData.getColumnName(i), rs.getString(i));
			}
			row.add(map);
			while (rs.next()) {
				rowIndex++;
				map = new HashMap<String, Object>();
//				System.out.println("--------row "+rowIndex+ "--------");
				columnIdentifier = rs.getString(Integer.valueOf(identifier));
				columnIdentifier2 = rs.getString(Integer.valueOf(identifier2));
				columnIdentifier3 = rs.getString(Integer.valueOf(identifier3));
				
				if (!counterChecker.contains("#"+columnIdentifier+"#")) {
					countMapIdentifier++;
					listId.add(columnIdentifier);
					counterChecker = counterChecker+columnIdentifier+"#";
				}
				
				if (!counterChecker2.contains("#"+columnIdentifier2+"#")) {
					countMapIdentifier2++;
					listId2.add(columnIdentifier2);
					counterChecker2 = counterChecker2+columnIdentifier2+"#";
				}
				
				if (!counterChecker3.contains("#"+columnIdentifier3+"#")) {
					countMapIdentifier3++;
					listId3.add(columnIdentifier3);
					counterChecker3 = counterChecker3+columnIdentifier3+"#";
				}
				
				for (int i = 1; i < metaData.getColumnCount()+1; i++) {
					map.put(metaData.getColumnName(i), rs.getString(i));
				}
				row.add(map);
			}
			
			tRes = listId.toArray(new String[listId.size()]);
			tRes2 = listId2.toArray(new String[listId2.size()]);
			tRes3 = listId3.toArray(new String[listId3.size()]);
			
			for (int i = 0; i < tRes.length; i++) {
				temp = new HashMap<String, Map<String, Object>>();
				for (int j2 = 0; j2 < row.size(); j2++) {
					if (row.get(j2).get(identifierName).equals(tRes[i])) {
						for (int j = 0; j < tRes2.length; j++) {
							if (row.get(j2).get(identifierName2).equals(tRes2[j])) {
								temp.put(tRes2[j], row.get(j2));
							}
						}
					}
				}
				result.put(tRes[i],temp);
			}
			
//			System.out.println("result nih : " +result);
			
//			System.out.println("CountMapIdentifier : " + countMapIdentifier);
//			for (Map.Entry<String, Map<String, Map<String, Object>>> entry : result.entrySet()) {
//				System.out.println("result key : " + entry.getKey());
//				for (Map.Entry<String, Map<String, Object>> subEntry : entry.getValue().entrySet()) {
//					System.out.println("result key : " + subEntry.getKey()+ ", value : " +subEntry.getValue());
//				}
//			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getDb().stt.close();
		getDb().con.close();
		return result;
	}
	
	public Map<String, Map<String, Map<String, Object>>> getAdapterConfiguration2 (String query, String identifier, String identifier2, String keyCol, String valCol) throws SQLException {
		ResultSet rs = null;;
		ResultSetMetaData metaData = null;
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
		Map<String, Map<String, Object>> temp = new HashMap<String, Map<String, Object>>();
		Map<String, Map<String, Map<String, Object>>> result = new HashMap<String, Map<String, Map<String, Object>>>();
		
		ArrayList<String> listId = new ArrayList<String>();
		ArrayList<String> listId2 = new ArrayList<String>();
		ArrayList<String> listId3 = new ArrayList<String>();
		String[] tRes;
		String[] tRes2;

		try {
			rs = getDb().res(query);
			metaData = rs.getMetaData();
			int rowIndex = 1;
//			System.out.println("--------row 1--------");
			int countMapIdentifier = 0;
			int countMapIdentifier2 = 0;
			int countMapIdentifier3 = 0;
			
			String columnIdentifier = rs.getString(Integer.valueOf(identifier));
			String columnIdentifier2 = rs.getString(Integer.valueOf(identifier2));
			String keyColNameIdentifier = rs.getString(Integer.valueOf(identifier));
			String valColNameIdentifier2 = rs.getString(Integer.valueOf(identifier2));
			
			String counterChecker = "#"+columnIdentifier+"#";
			String counterChecker2 = "#"+columnIdentifier2+"#";
			
			String identifierName = metaData.getColumnName(Integer.valueOf(identifier));
			String identifierName2 = metaData.getColumnName(Integer.valueOf(identifier2));
			String keyColName = metaData.getColumnName(Integer.valueOf(keyCol));
			String valColName = metaData.getColumnName(Integer.valueOf(valCol));
			
			countMapIdentifier++;
			countMapIdentifier2++;
			countMapIdentifier3++;
			
			listId.add(columnIdentifier);
			listId2.add(columnIdentifier2);
			
			for (int i = 1; i < metaData.getColumnCount()+1; i++) {
				map.put(metaData.getColumnName(i), rs.getString(i));
			}
			row.add(map);
//			System.out.println("isi row : " + map);
			while (rs.next()) {
				rowIndex++;
				map = new HashMap<String, Object>();
//				System.out.println("--------row "+rowIndex+ "--------");
				columnIdentifier = rs.getString(Integer.valueOf(identifier));
				columnIdentifier2 = rs.getString(Integer.valueOf(identifier2));
				
				if (!counterChecker.contains("#"+columnIdentifier+"#")) {
					countMapIdentifier++;
					listId.add(columnIdentifier);
					counterChecker = counterChecker+columnIdentifier+"#";
				}
				
				if (!counterChecker2.contains("#"+columnIdentifier2+"#")) {
					countMapIdentifier2++;
					listId2.add(columnIdentifier2);
					counterChecker2 = counterChecker2+columnIdentifier2+"#";
				}
				
				
				for (int i = 1; i < metaData.getColumnCount()+1; i++) {
					map.put(metaData.getColumnName(i), rs.getString(i));
				}
				row.add(map);
//				System.out.println("isi row : " + map);
			}
			
			tRes = listId.toArray(new String[listId.size()]);
			tRes2 = listId2.toArray(new String[listId2.size()]);
			
			String t1 = "";
			String t2 = "";
			int count = 0;
			
			for (int i = 0; i < row.size(); i++) {
				if (t1.equals(row.get(i).get(identifierName).toString()) && t2.equals(row.get(i).get(identifierName2).toString())) {
					map2.put(row.get(i).get(keyColName).toString(), row.get(i).get(valColName).toString());
//					System.out.println("if dalem 1 : " + row.get(i).get(identifierName).toString() +" => "+ (row.get(i).get(keyColName).toString()+ " = " + row.get(i).get(valColName).toString()));
					count++;
				}
				else if (t1.equals(row.get(i).get(identifierName).toString()) && !t2.equals(row.get(i).get(identifierName2).toString())) {
						temp.put(t2, map2);
						t2 = row.get(i).get(identifierName2).toString();
						map2 = new HashMap<String, Object>();
						map2.put(row.get(i).get(keyColName).toString(), row.get(i).get(valColName).toString());
//						System.out.println("else dalem 2 : "  + row.get(i).get(identifierName).toString() +" => "+ (row.get(i).get(keyColName).toString()+ " = " + row.get(i).get(valColName).toString()));
						count++;
				}
				else if (t1.equals("") && t2.equals(""))  {
//					if (t1.equals("") && t2.equals("")) {
						t1 = row.get(i).get(identifierName).toString();
						t2 = row.get(i).get(identifierName2).toString();
						map2.put(row.get(i).get(keyColName).toString(), row.get(i).get(valColName).toString());
						count++;
//						System.out.println("else dalem 3 : "  + row.get(i).get(identifierName).toString() +" => "+ (row.get(i).get(keyColName).toString()+ " = " + row.get(i).get(valColName).toString()));
					}
				else if (!t1.equals("") && !t2.equals("")) {
						temp.put(t2, map2);
						result.put(t1, temp);
						t1 = row.get(i).get(identifierName).toString();
						t2 = row.get(i).get(identifierName2).toString();
						temp = new HashMap<String, Map<String, Object>>();
						map2 = new HashMap<String, Object>();
						map2.put(row.get(i).get(keyColName).toString(), row.get(i).get(valColName).toString());
						count++;
//						System.out.println("else dalem 4 : "  + row.get(i).get(identifierName).toString() +" => "+ (row.get(i).get(keyColName).toString()+ " = " + row.get(i).get(valColName).toString()));
				}
			}
			temp.put(t2, map2);
			result.put(t1, temp);
			
//			System.out.println("row size : " +row.size());
//			System.out.println("count : " + count);
			
//			System.out.println("CountMapIdentifier : " + countMapIdentifier);
//			for (Map.Entry<String, Map<String, Map<String, Object>>> entry : result.entrySet()) {
//				System.out.println("result key : " + entry.getKey());
//				for (Map.Entry<String, Map<String, Object>> subEntry : entry.getValue().entrySet()) {
//					System.out.println("result key : " + subEntry.getKey()+ ", value : " +subEntry.getValue());
//				}
//			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getDb().stt.close();
		getDb().con.close();
		return result;
	}
	
	public Map<String, Map<String, Map<String, Map<String, Object>>>> getAdapterConfiguration3 (String query, String identifier, String identifier2, String identifier3) throws SQLException {
		ResultSet rs = null;;
		ResultSetMetaData metaData = null;
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
		Map<String, Map<String, Object>> temp = new HashMap<String, Map<String, Object>>();
		Map<String, Map<String, Map<String, Object>>> result = new HashMap<String, Map<String, Map<String, Object>>>();
		Map<String, Map<String, Map<String, Map<String, Object>>>> resMap = new HashMap<String, Map<String, Map<String, Map<String, Object>>>>();
		
		ArrayList<String> listId = new ArrayList<String>();
		ArrayList<String> listId2 = new ArrayList<String>();
		ArrayList<String> listId3 = new ArrayList<String>();
		String[] tRes;
		String[] tRes2;
		String[] tRes3;
		try {
			rs = getDb().res(query);
			metaData = rs.getMetaData();
			int rowIndex = 1;
//			System.out.println("--------row 1--------");
			int countMapIdentifier = 0;
			int countMapIdentifier2 = 0;
			int countMapIdentifier3 = 0;
			
			String columnIdentifier = rs.getString(Integer.valueOf(identifier));
			String columnIdentifier2 = rs.getString(Integer.valueOf(identifier2));
			String columnIdentifier3 = rs.getString(Integer.valueOf(identifier3));
			
			String counterChecker = "#"+columnIdentifier+"#";
			String counterChecker2 = "#"+columnIdentifier2+"#";
			String counterChecker3 = "#"+columnIdentifier3+"#";
			
			String identifierName = metaData.getColumnName(Integer.valueOf(identifier));
			String identifierName2 = metaData.getColumnName(Integer.valueOf(identifier2));
			String identifierName3 = metaData.getColumnName(Integer.valueOf(identifier3));
			
			countMapIdentifier++;
			countMapIdentifier2++;
			countMapIdentifier3++;
			
			listId.add(columnIdentifier);
			listId2.add(columnIdentifier2);
			listId3.add(columnIdentifier3);
			
			for (int i = 1; i < metaData.getColumnCount()+1; i++) {
				map.put(metaData.getColumnName(i), rs.getString(i));
			}
			row.add(map);
			while (rs.next()) {
				rowIndex++;
				map = new HashMap<String, Object>();
//				System.out.println("--------row "+rowIndex+ "--------");
				columnIdentifier = rs.getString(Integer.valueOf(identifier));
				columnIdentifier2 = rs.getString(Integer.valueOf(identifier2));
				columnIdentifier3 = rs.getString(Integer.valueOf(identifier3));
				
				if (!counterChecker.contains("#"+columnIdentifier+"#")) {
					countMapIdentifier++;
					listId.add(columnIdentifier);
					counterChecker = counterChecker+columnIdentifier+"#";
				}

				if (!counterChecker2.contains("#"+columnIdentifier2+"#")) {
					countMapIdentifier2++;
					listId2.add(columnIdentifier2);
					counterChecker2 = counterChecker2+columnIdentifier2+"#";
				}

				if (!counterChecker3.contains("#"+columnIdentifier3+"#")) {
					countMapIdentifier3++;
					listId3.add(columnIdentifier3);
					counterChecker3 = counterChecker3+columnIdentifier3+"#";
				}

				for (int i = 1; i < metaData.getColumnCount()+1; i++) {
					map.put(metaData.getColumnName(i), rs.getString(i));
				}
				row.add(map);
			}

			tRes = listId.toArray(new String[listId.size()]);
			tRes2 = listId2.toArray(new String[listId2.size()]);
			tRes3 = listId3.toArray(new String[listId3.size()]);

			for (int i = 0; i < tRes3.length; i++) {
				System.out.println("tRes3 : " + tRes3[i]);
			}

			/*
			for (int i = 0; i < tRes.length; i++) {
				System.out.println("tRes : " + tRes[i]);
			}
			
			for (int i = 0; i < tRes2.length; i++) {
				System.out.println("tRes2 : " + tRes2[i]);
			}
			
			for (int i = 0; i < tRes3.length; i++) {
				System.out.println("tRes3 : " + tRes3[i]);
			}
			*/
//			
//			for (int i = 0; i < tRes.length; i++) {
////				temp = new HashMap<String, Map<String, Object>>();
//				
//				for (int j2 = 0; j2 < row.size(); j2++) {
//					result = new HashMap<String, Map<String, Map<String, Object>>>();
//					temp = new HashMap<String, Map<String, Object>>();
//					if (row.get(j2).get(identifierName).equals(tRes[i])) {
//
//						for (int j = 0; j < tRes2.length; j++) {
//							if (row.get(j2).get(identifierName2).equals(tRes2[j])) {
//								for (int index = 0; index < tRes3.length; index++) {
//									if (row.get(j2).get(identifierName3).equals(tRes3[index])) {
////										temp.put(tRes2[j], row.get(j2));
//										temp.put(tRes3[index], row.get(j2));
//									}
//								}
//							}
////							result.put(tRes2[j], temp);
//						}
//						result.put(row.get(j2).get(identifierName2).toString(), temp);
//					}
//				}
////				result.put(tRes[i],temp);
//				resMap.put(tRes[i], result);
//			}
			
			for (int i = 0; i < row.size(); i++) {
				for (int j = 0; j < tRes.length; j++) {
					if (row.get(i).get(identifierName).equals(tRes[j])) {
						for (int k = 0; k < tRes2.length; k++) {
							if (row.get(i).get(identifierName2).equals(tRes2[k])) {
								for (int l = 0; l < tRes3.length; l++) {
									if (row.get(i).get(identifierName3).equals(tRes3[l])) {
										temp.put(tRes[j]+"-"+tRes2[k]+"-"+tRes3[l], row.get(i));
//										System.out.println("tRes : " + tRes[j]);
//										System.out.println("tRes2 : " + tRes2[k]);
//										System.out.println("tRes3 : " + tRes3[l]);
									}
								}
							}
						}
//						result.put(row.get(i).get(identifierName2).toString(), temp);
//						System.out.println("result : " + result);
					}
//					resMap.put(tRes[i], result);
				}
			}
			
			for (Map.Entry<String, Map<String, Object>> subEntry2 : temp.entrySet()) {
				System.out.println("temp key : " + subEntry2.getKey()+ ", value : " +subEntry2.getValue());
				String[] t1 = subEntry2.getKey().split("-");
			}
//			for (int i = 0; i < tRes.length; i++) {
//				temp = new HashMap<String, Map<String, Object>>();
//				for (int j2 = 0; j2 < row.size(); j2++) {
//					if (row.get(j2).get(identifierName).equals(tRes[i])) {
//						for (int j = 0; j < tRes2.length; j++) {
//							if (row.get(j2).get(identifierName2).equals(tRes2[j])) {
//								temp.put(tRes2[j], row.get(j2));
//							}
//						}
//					}
//				}
//				result.put(tRes[i],temp);
//			}
			
//			System.out.println("result nih : " +result);
			
			System.out.println("CountMapIdentifier : " + countMapIdentifier3);
			for (Map.Entry<String, Map<String, Map<String, Map<String, Object>>>> entry : resMap.entrySet()) {
				System.out.println("result key level 1 : " + entry.getKey());
				for (Map.Entry<String, Map<String, Map<String, Object>>> subEntry : entry.getValue().entrySet()) {
					System.out.println("result key level 2 : " + subEntry.getKey());
					for (Map.Entry<String, Map<String, Object>> subEntry2 : subEntry.getValue().entrySet()) {
						System.out.println("result key level 3 : " + subEntry2.getKey()+ ", value : " +subEntry2.getValue());
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getDb().stt.close();
		getDb().con.close();
		return resMap;
	}
}
