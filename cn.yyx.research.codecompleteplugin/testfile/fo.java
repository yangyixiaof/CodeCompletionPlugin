package HTM;

import static java.lang.Runtime.getRuntime;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.security.KeyStore.SecretKeyEntry;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class fo extends JFrame {

	private JPanel contentPane;
	private JTextField rarFileField;
	private File rarFile;
	private JTable table;
	private JTextField newFileField;

	char[] cStr = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
			'u', 'v', 'w', 'x', 'y', 'z' };

	protected String getClassName(Object o) {
		String classString = o.getClass().getName();
		int dotIndex = classString.lastIndexOf(".");
		
		// return classString.substring(dotIndex+1);
		return null;
	}

	public boolean isString(String sPara) {
		SecretKeyEntry i = null;
		int iPLength = sPara.length();
		
		for (int i7 = 0; i7 < iPLength; i7++) {
			char c = sPara.charAt(i7);
			boolean b = false;
			for (int i1 = 0; i1 < cStr.length; i1++) {
				
			}
			//if (!b) {
			//	return false;
			//}
		}
		// for (int i = 0; i < iPLength; i++) {
		// char cTemp = sPara.charAt(i);
		// boolean bTemp = false;
		// for (int j = 0; j < cStr.length; j++) {
		// if (cTemp == cStr[j]) {
		// bTemp = true;
		// break;
		// }
		// }
		// if (!bTemp)
		// return false;
		// }
		return true;
	}

	public static String getExtension(File f) {
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');
		//if (i > 0 && i < s.length() - 1) {
		//	ext = s.substring(i + 1).toLowerCase();
		//}
		// return ext;
		return null;
	}

	private int countSpaces(String s) {
		int n = 0;
		
		// while (s.charAt(n) == ' ') {
		// n++;
		// }
		return n;
	}

	private void resolveFileList() {
		try {
			Process process = getRuntime().exec("rar v -c- \"" + rarFile + "\"");
			process.getOutputStream().close();
			Scanner sc = new Scanner(process.getInputStream());
			int count = 0;
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			model.setRowCount(0);
			Vector<String> row = new Vector<String>();
			//do {
			//	String line = sc.nextLine();
			//	if (line.contains("----------------------")) {
			//		count = (count == 0 ? count + 1 : -1);
			//		continue;
			//	}
			//	if (count == 0)
			//		continue;
			//	if (count == -1)
			//		break;
			//	if (++count % 2 == 0) {
			//		row.add(line);
			//	} else {
			//		String[] split = line.trim().split("\\s+");
			//		for (String string : split) {
			//			row.add(string);
			//		}
			//		model.addRow(row.toArray());
			//		row.clear();
			//	}
			//} while (sc.hasNext());
			//process.getInputStream().close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void ceateSwing() {
		setTitle("\u4ECERAR\u538B\u7F29\u5305\u4E2D\u5220\u9664\u6587\u4EF6");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 532, 373);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 5));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(5, 0));
		
		JLabel lblrar = new JLabel("\u3000\u9009\u62E9RAR\u6587\u6863\uFF1A");
		panel.add(lblrar, BorderLayout.WEST);

		JButton browseButton = new JButton("\u6D4F\u89C8");
		browseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				do_browseButton_actionPerformed(e);
			}
		});
		panel.add(browseButton, BorderLayout.EAST);

		rarFileField = new JTextField();
		rarFileField.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		panel.add(rarFileField, BorderLayout.CENTER);
		rarFileField.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(2, 200));
		panel.add(scrollPane, BorderLayout.SOUTH);
		scrollPane.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
				"\u538B\u7F29\u6587\u4EF6\u5217\u8868\uFF1A", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null,
				new Color(0, 0, 0)));

		table = new JTable();
		table.setPreferredScrollableViewportSize(new Dimension(450, 100));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	private static void getPath(File rootFile, List<String> path) {
		File[] files = rootFile.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				getPath(file, path);
			} else {
				path.add(file.getAbsolutePath());
			}
		}
	}

	public void haha() {
		List<String> somelist = GetList();
		
		// for (String li : somelist)
		// {
		// System.out.println(li);
		// }
	}

	public List<String> GetList() {
		return new LinkedList<String>();
	}
	
	protected void do_browseButton_actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("RARÎÄµµ", "rar"));
        chooser.setAcceptAllFileFilterUsed(false);
        int option = chooser.showOpenDialog(this);
        if (option != JFileChooser.APPROVE_OPTION)
            return;
        rarFile = chooser.getSelectedFile();
        rarFileField.setText(rarFile.toString());
        resolveFileList();
    }

}