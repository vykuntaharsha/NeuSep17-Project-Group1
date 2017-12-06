package com.neuSep17.service;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.MouseInputListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.neuSep17.dto.Vehicle;

public class InventoryListService {

	public static ArrayList<Vehicle> readAndGetVehicles(File file) {
		ArrayList<Vehicle> res = new ArrayList<>();
		BufferedReader buf = null;
		try {
			buf = new BufferedReader(new FileReader(file));
			String curLine = buf.readLine();
			curLine = buf.readLine();
			while (curLine != null) {
				String[] arr = curLine.split("~");
				Vehicle v = new Vehicle(arr);
				res.add(v);
				curLine = buf.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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
			dic.add(item.getID().toUpperCase());
			dic.add(item.getWebID().toUpperCase());
			dic.add(item.getCategory().toString().toUpperCase());
			dic.add(item.getMake().toUpperCase());
			dic.add(item.getModel().toUpperCase());
			dic.add(item.getTrim().toUpperCase());
			dic.add(item.getBodyType().toUpperCase());
			dic.add(String.valueOf(item.getYear()).toUpperCase());
			dic.add(item.getBattery().toUpperCase());
			dic.add(item.getEngine().toUpperCase());
			dic.add(item.getExteriorColor().toUpperCase());
			dic.add(item.getInteriorColor().toUpperCase());
			dic.add(item.getFuelType().toUpperCase());
			dic.add(item.getOptionalFeatures().toUpperCase());
			dic.add(item.getTransmission().toUpperCase());
			dic.add(item.getVin().toUpperCase());
			dic.add(item.getEntertainment().toUpperCase());
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
			String pattern = item.getID() + item.getWebID() + item.getCategory().toString() + item.getYear() + item.getMake() + item.getModel()
					+ item.getTrim() + item.getBodyType() + item.getPrice() + item.getVin() + item.getEntertainment() + item.getInteriorColor()
					+ item.getExteriorColor() + item.getFuelType() + item.getEngine() + item.getTransmission() + item.getBattery() + item.getOptionalFeatures();
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
			String[] arr = new String[19];
			arr[0] = vehicle.getID();
			arr[1] = vehicle.getWebID();
			arr[2] = vehicle.getCategory().toString();
			arr[3] = String.valueOf(vehicle.getYear());
			arr[4] = vehicle.getMake();
			arr[5] = vehicle.getModel();
			arr[6] = vehicle.getTrim();
			arr[7] = vehicle.getBodyType();
			arr[8] = String.valueOf(vehicle.getPrice());
			arr[9] = vehicle.getPhotoURL().toString();
			arr[10] = vehicle.getVin();
			arr[11] = vehicle.getEntertainment();
			arr[12] = vehicle.getInteriorColor();
			arr[13] = vehicle.getExteriorColor();
			arr[14] = vehicle.getFuelType();
			arr[15] = vehicle.getEngine();
			arr[16] = vehicle.getTransmission();
			arr[17] = vehicle.getBattery();
			arr[18] = vehicle.getOptionalFeatures();

			tableModel.addRow(arr);
		}

		table.invalidate();
	}

	public static void sortById(ArrayList<Vehicle> list, boolean isAscend) {
		Collections.sort(list, new Comparator<Vehicle>() {
			@Override
			public int compare(Vehicle o1, Vehicle o2) {
				if (isAscend) {
					return o1.getID().compareTo(o2.getID());
				} else {
					return o2.getID().compareTo(o1.getID());
				}
			}
		});
	}

	public static void sortByWebId(ArrayList<Vehicle> list, boolean isAscend) {
		Collections.sort(list, new Comparator<Vehicle>() {
			@Override
			public int compare(Vehicle o1, Vehicle o2) {
				if (isAscend) {
					return o1.getWebID().compareTo(o2.getWebID());
				} else {
					return o2.getWebID().compareTo(o1.getWebID());
				}
			}
		});
	}

	public static void sortByCategory(ArrayList<Vehicle> list, boolean isAscend) {
		Collections.sort(list, new Comparator<Vehicle>() {
			@Override
			public int compare(Vehicle o1, Vehicle o2) {
				if (isAscend) {
					return o1.getCategory().compareTo(o2.getCategory());
				} else {
					return o2.getCategory().compareTo(o1.getCategory());
				}
			}
		});
	}

	public static void sortByYear(ArrayList<Vehicle> list, boolean isAscend) {
		Collections.sort(list, new Comparator<Vehicle>() {
			@Override
			public int compare(Vehicle o1, Vehicle o2) {
				if (isAscend) {
					return o1.getYear() - o2.getYear();
				} else {
					return o2.getYear() - o1.getYear();
				}
			}
		});
	}

	public static void sortByPrice(ArrayList<Vehicle> list, boolean isAscend) {
		Collections.sort(list, new Comparator<Vehicle>() {
			@Override
			public int compare(Vehicle o1, Vehicle o2) {
				if (isAscend) {
					return (int) (o1.getPrice() - o2.getPrice());
				} else {
					return (int) (o2.getPrice() - o1.getPrice());
				}
			}
		});
	}
	
	public static void sortByModel(ArrayList<Vehicle> list, boolean isAscend) {
		Collections.sort(list, new Comparator<Vehicle>() {
			@Override
			public int compare(Vehicle o1, Vehicle o2) {
				if (isAscend) {
					return o1.getModel().compareTo(o2.getModel());
				} else {
					return o2.getModel().compareTo(o1.getModel());
				}
			}
		});
	}
	
	public static void sortByExteriorColor(ArrayList<Vehicle> list, boolean isAscend) {
        Collections.sort(list, new Comparator<Vehicle>() {
            @Override
            public int compare(Vehicle o1, Vehicle o2) {
                if (isAscend) {
                    return o1.getExteriorColor().compareTo(o2.getExteriorColor());
                } else {
                    return o2.getExteriorColor().compareTo(o1.getExteriorColor());
                }
            }
        });
    }
	
	public static void sortByInteriorColor(ArrayList<Vehicle> list, boolean isAscend) {
        Collections.sort(list, new Comparator<Vehicle>() {
            @Override
            public int compare(Vehicle o1, Vehicle o2) {
                if (isAscend) {
                    return o1.getInteriorColor().compareTo(o2.getInteriorColor());
                } else {
                    return o2.getInteriorColor().compareTo(o1.getInteriorColor());
                }
            }
        });
    }
	
	public static void sortByTransmission(ArrayList<Vehicle> list, boolean isAscend) {
        Collections.sort(list, new Comparator<Vehicle>() {
            @Override
            public int compare(Vehicle o1, Vehicle o2) {
                if (isAscend) {
                    return o1.getTransmission().compareTo(o2.getTransmission());
                } else {
                    return o2.getTransmission().compareTo(o1.getTransmission());
                }
            }
        });
    }
	
	public static void sortByEngine(ArrayList<Vehicle> list, boolean isAscend) {
        Collections.sort(list, new Comparator<Vehicle>() {
            @Override
            public int compare(Vehicle o1, Vehicle o2) {
                if (isAscend) {
                    return o1.getEngine().compareTo(o2.getEngine());
                } else {
                    return o2.getEngine().compareTo(o1.getEngine());
                }
            }
        });
    }

	public static void sortByType(ArrayList<Vehicle> list, boolean isAscend) {
		Collections.sort(list, new Comparator<Vehicle>() {
			@Override
			public int compare(Vehicle o1, Vehicle o2) {
				if (isAscend) {
					return o1.getBodyType().compareTo(o2.getBodyType());
				} else {
					return o2.getBodyType().compareTo(o1.getBodyType());
				}
			}
		});
	}

	public static void sortByMake(ArrayList<Vehicle> list, boolean isAscend) {
		Collections.sort(list, new Comparator<Vehicle>() {
			@Override
			public int compare(Vehicle o1, Vehicle o2) {
				if (isAscend) {
					return o1.getMake().compareTo(o2.getMake());
				} else {
					return o2.getMake().compareTo(o1.getMake());
				}
			}
		});
	}
	
	public class LinkCellRenderer extends DefaultTableCellRenderer implements MouseInputListener {

	    //鼠标事件所在的行
	    private int row = -1;
	    //鼠标事件所在的列
	    private int col = -1;
	    //当前监听的Table
	    private JTable table = null;

	    @Override
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	        //恢复默认状态
	        this.table = table;
	        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	        this.setForeground(Color.black);
	        table.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	        this.setText(value.toString());
	        //如果当前需要渲染器的单元格就是鼠标事件所在的单元格
	        if (row == this.row && column == this.col) {
	            //如果是第二列(第二列是显示超链接的列)
	            if (column == 1) {
	                //改变前景色(文字颜色)
	                this.setForeground(Color.blue);
	                //改变鼠标形状
	                table.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	                //显示超链接样式
	                this.setText("<html><u>" + value.toString() + "</u></html>");
	            }
	            setBackground(table.getSelectionBackground());
	        } else if (isSelected) {
	            //如果单元格被选中,则改变前景色和背景色
	            setForeground(table.getSelectionForeground());
	            setBackground(table.getSelectionBackground());
	        } else {
	            //其他情况下恢复默认背景色
	            setBackground(Color.white);
	        }
	        return this;
	    }

	    /**
	     * 鼠标移出事件
	     * @param e 
	     */
	    public void mouseExited(MouseEvent e) {
	        if (table != null) {
	            int oldRow = row;
	            int oldCol = col;
	            //鼠标移出目标表格后,恢复行列数据到默认值
	            row = -1;
	            col = -1;
	            //当之前的行列数据有效时重画相关区域
	            if (oldRow != -1 && oldCol != -1) {
	                Rectangle rect = table.getCellRect(oldRow, oldCol, false);
	                table.repaint(rect);
	            }
	        }
	    }

	    /**
	     * 鼠标拖动事件
	     * @param e 
	     */
	    public void mouseDragged(MouseEvent e) {
	        mouseMoved(e);
	    }
	    /**
	     * 鼠标移动事件
	     * @param e 
	     */
	    public void mouseMoved(MouseEvent e) {
	        if (table != null) {
	            Point p = e.getPoint();
	            int oldRow = row;
	            int oldCol = col;
	            row = table.rowAtPoint(p);
	            col = table.columnAtPoint(p);
	            //重画原来的区域
	            if (oldRow != -1 && oldCol != -1) {
	                Rectangle rect = table.getCellRect(oldRow, oldCol, false);
	                table.repaint(rect);
	            }
	            //重画新的区域
	            if (row != -1 && col != -1) {
	                Rectangle rect = table.getCellRect(row, col, false);
	                table.repaint(rect);
	            }
	        }
	    }

	    /**
	     * 鼠标单击事件
	     * @param e 
	     */
	    public void mouseClicked(MouseEvent e) {
	        //获取事件所在的行列坐标信息
	        Point p = e.getPoint();
	        int c = table.columnAtPoint(p);
	        if(c != 1){
	            return;
	        }
	        int r = table.rowAtPoint(p);
	        try {
	            //取得目标单元格的值,即链接信息
	            URL url = new URL(table.getValueAt(r, c).toString());
	            //在系统默认浏览器中打开链接
	            Desktop.getDesktop().browse(url.toURI());
	        } catch (Exception ex) {
	            Logger.getLogger(LinkCellRenderer.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }
	    /**
	     * 鼠标按下事件
	     * @param e 
	     */
	    public void mousePressed(MouseEvent e) {
	    }

	    /**
	     * 鼠标释放事件
	     * @param e 
	     */
	    public void mouseReleased(MouseEvent e) {
	    }

	    /**
	     * 鼠标进入事件
	     * @param e 
	     */
	    public void mouseEntered(MouseEvent e) {
	    }

	    
//	    /**
//	     * 测试方法
//	     * @param args 
//	     */
//	    public static void main(String[] args) {
//	        //将在表格中呈现的数据
//	        Object[] header = new String[]{"标题", "链接"};
//	        Object[][] data = new String[10][2];
//	        for (int i = 0; i < 10; i++) {
//	            data[i][0] = "网页标题";
//	            data[i][1] = "http://www.yshjava.cn/post/529.html";
//	        }
//	        //构建表格数据模型
//	        TableModel model = new DefaultTableModel(data, header);
//	        //创建表格对象
//	        JTable table = new JTable(model);
//	        //创建单元格渲染器暨鼠标事件监听器
//	        LinkCellRenderer renderer = new LinkCellRenderer();
//	        //注入渲染器
//	        table.setDefaultRenderer(Object.class, renderer);
//	        //注入监听器
//	        table.addMouseListener(renderer);
//	        table.addMouseMotionListener(renderer);
//
//	        //为表格增加爱滚动窗格
//	        JScrollPane sp = new JScrollPane(table);
//	        //创建窗口程序
//	        JFrame f = new JFrame("JTable 单元格超链接测试");
//	        f.getContentPane().add(sp, BorderLayout.CENTER);
//	        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	        f.setSize(800, 600);
//	        f.setLocationRelativeTo(null);
//	        //显示窗口
//	        f.setVisible(true);
//	    }
	}

}
