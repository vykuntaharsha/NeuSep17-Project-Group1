package com.neuSep17.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.neuSep17.dto.*;

public class Service {
	
	public static ArrayList<Vehicle> readAndGetVehicles(File file){
		ArrayList<Vehicle> res = new ArrayList<>();
		BufferedReader buf = null;
		try {
			buf = new BufferedReader(new FileReader(file));
			String curLine = buf.readLine(
					);
			curLine = buf.readLine();
			while(curLine != null) {
				String[] arr = curLine.split("~");
				Vehicle v = new Vehicle(arr);
				res.add(v);
				curLine = buf.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				buf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return res;
	}
	
	public static ArrayList<Vehicle> search(ArrayList<Vehicle> filter, ArrayList<Vehicle> list, JTextField txtSearch) {
		filter = new ArrayList<>();
		String str = txtSearch.getText();
		str = str.toUpperCase();

		for (Vehicle item : list) {
			HashSet<String> dic = new HashSet<>();
			dic.add(item.id.toUpperCase());
			dic.add(item.webId.toUpperCase());
			dic.add(item.category.toString().toUpperCase());
			dic.add(item.make.toUpperCase());
			dic.add(item.model.toUpperCase());
			dic.add(item.trim.toUpperCase());
			dic.add(item.type.toUpperCase());
			dic.add(item.year.toUpperCase());
			boolean[] dp = new boolean[str.length() + 1];
			dp[0] = true;
			for (int i = 1; i <= str.length(); i++) {
				for (int j = 0; j < i; j++) {
					if (dp[j] && dic.contains(str.substring(j, i))) {
						dp[i] = true;
					}
				}
			}
			if (dp[str.length()]) {
				filter.add(item);
			}
		}
		return filter;
	}
	
	public static ArrayList<Vehicle> filter(ArrayList<Vehicle> filter, ArrayList<Vehicle> list, JTextField txtFilter) {
		filter = new ArrayList<>();
		String str = txtFilter.getText();
		str = str.toUpperCase();
		for (Vehicle item : list) {
			String pattern = item.id + item.webId + item.category.toString() + item.year + item.make
					+ item.model + item.trim + item.type + item.price;
			if (pattern.toUpperCase().contains(str)) {
				filter.add(item);
			}
		}
		return filter;
	}
	
	public static void fillTable(ArrayList<Vehicle> list, JTable table) {
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.setRowCount(0);

		for (Vehicle vehicle : list) {
			String[] arr = new String[10];
			arr[0] = vehicle.id;
			arr[1] = vehicle.webId;
			arr[2] = vehicle.category.toString();
			arr[3] = vehicle.year;
			arr[4] = vehicle.make;
			arr[5] = vehicle.model;
			arr[6] = vehicle.trim;
			arr[7] = vehicle.type;
			arr[8] = String.valueOf(vehicle.price);
			arr[9] = vehicle.photo.toString();

			tableModel.addRow(arr);
		}

		table.invalidate();
	}
	
	public static void sortById(ArrayList<Vehicle> list, boolean isAscend) {
		Collections.sort(list, new Comparator<Vehicle>() {
			@Override
			public int compare(Vehicle o1, Vehicle o2) {
				if(isAscend) {
					return o1.id.compareTo(o2.id);
				}else {
					return o2.id.compareTo(o1.id);
				}
			}
		});
	}
	
	public static void sortByWebId(ArrayList<Vehicle> list, boolean isAscend) {
		Collections.sort(list, new Comparator<Vehicle>() {
			@Override
			public int compare(Vehicle o1, Vehicle o2) {
				if(isAscend) {
					return o1.webId.compareTo(o2.webId);
				}else {
					return o2.webId.compareTo(o1.webId);
				}
			}
		});
	}
	
	public static void sortByCategory(ArrayList<Vehicle> list, boolean isAscend) {
		Collections.sort(list, new Comparator<Vehicle>() {
			@Override
			public int compare(Vehicle o1, Vehicle o2) {
				if(isAscend) {
					return o1.category.compareTo(o2.category);
				}else {
					return o2.category.compareTo(o1.category);
				}
			}
		});
	}
	
	public static void sortByYear(ArrayList<Vehicle> list, boolean isAscend) {
		Collections.sort(list, new Comparator<Vehicle>() {
			@Override
			public int compare(Vehicle o1, Vehicle o2) {
				if(isAscend) {
					return o1.year.compareTo(o2.year);
				}else {
					return o2.year.compareTo(o1.year);
				}
			}
		});
	}
	
	public static void sortByPrice(ArrayList<Vehicle> list, boolean isAscend) {
		Collections.sort(list, new Comparator<Vehicle>() {
			@Override
			public int compare(Vehicle o1, Vehicle o2) {
				if(isAscend) {
					return (int)(o1.price - o2.price);
				}else {
					return (int)(o2.price - o1.price);
				}
			}
		});
	}
	
	public static void sortByModel(ArrayList<Vehicle> list, boolean isAscend) {
		Collections.sort(list, new Comparator<Vehicle>() {
			@Override
			public int compare(Vehicle o1, Vehicle o2) {
				if(isAscend) {
					return o1.model.compareTo(o2.model);
				}else {
					return o2.model.compareTo(o1.model);
				}
			}
		});
	}
	
	public static void sortByType(ArrayList<Vehicle> list, boolean isAscend) {
		Collections.sort(list, new Comparator<Vehicle>() {
			@Override
			public int compare(Vehicle o1, Vehicle o2) {
				if(isAscend) {
					return o1.type.compareTo(o2.type);
				}else {
					return o2.type.compareTo(o1.type);
				}
			}
		});
	}
	
	public static void sortByMake(ArrayList<Vehicle> list, boolean isAscend) {
		Collections.sort(list, new Comparator<Vehicle>() {
			@Override
			public int compare(Vehicle o1, Vehicle o2) {
				if(isAscend) {
					return o1.make.compareTo(o2.make);
				}else {
					return o2.make.compareTo(o1.make);
				}
			}
		});
	}
	
}
