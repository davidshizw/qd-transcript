package jumpstart;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class GraphicalUI {
	private static JFrame frame = new JFrame();
	private static String PATH = "";
	private static String FONT = "";
	private static String SRC = "";
	private static String DEST = "";
	
    public static void main(String[] args) {
    	new GraphicalUI();
    }
	
	public GraphicalUI() {
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Project");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmStartNew = new JMenuItem("Start New");
		mntmStartNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newProject();
			}
		});
		mnNewMenu.add(mntmStartNew);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnNewMenu.add(mntmExit);
		
		JMenu mnAbout = new JMenu("About");
		menuBar.add(mnAbout);
		
		JMenuItem mntmCopyright = new JMenuItem("Copyright");
		mntmCopyright.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame,
					    "<html>Version: final (Unlikely to change unless bugs found)<hr>Developed by Zhongwei Shi @ davidshizw@gmail.com.<br> Shanghai Qibao Dwight High School owns the right to use this software for free.</html>");
			}
		});
		mnAbout.add(mntmCopyright);
		frame.getContentPane().setLayout(null);
		
		JButton btnStartNewProject = new JButton("\u65B0\u5EFA\u9879\u76EE Start New Project");
		btnStartNewProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newProject();
			}
		});
		btnStartNewProject.setBounds(236, 203, 195, 47);
		frame.getContentPane().add(btnStartNewProject);
		
		JLabel lblWelcomeTo = new JLabel("<html>\r\n<p style=\"margin-bottom: 5px;\">\u6B22\u8FCE\u4F7F\u7528\u6210\u7EE9\u5355\u81EA\u52A8\u751F\u6210\u7CFB\u7EDF\uFF0C\u8BF7\u4E25\u683C\u6309\u7167\u4EE5\u4E0B\u6559\u7A0B\u8FDB\u884C\u64CD\u4F5C\u3002<br></p>\r\n1. \u70B9\u51FB\u4E0B\u8F7D\u6309\u94AE\u4E0B\u8F7D\u6210\u7EE9\u5BFC\u5165\u6A21\u677F\uFF1B<br>\r\n2. \u8F93\u5165\u5B8C\u6210\u540E\uFF0C\u70B9\u51FB\u4E0B\u65B9\u201C\u65B0\u5EFA\u9879\u76EE\u201D\u6309\u94AE\u3002<br><br>\r\n\r\n<p style=\"margin-bottom: 5px;\">Welcome to Automatic Transcript Generator. Please strictly follow the instructions below. <br></p>\r\n1. Click the download button to download the template for importing grades, <br>\r\n2. Once done, click the \"Start New Project\" button below.\r\n\r\n</html>");
		lblWelcomeTo.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcomeTo.setBounds(22, 11, 409, 194);
		frame.getContentPane().add(lblWelcomeTo);
		
		JButton btnNewButton = new JButton("\u4E0B\u8F7D\u6A21\u677F Download Template");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			   try {         
			     java.awt.Desktop.getDesktop().browse(java.net.URI.create("https://github.com/davidshizw/qd-transcript"));
			   }
			   catch (java.io.IOException err) {
			       System.out.println(err.getMessage());
			   }
			}
		});
		btnNewButton.setBounds(22, 203, 195, 47);
		frame.getContentPane().add(btnNewButton);
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
//		frame.setResizable(false);
		frame.setLocationRelativeTo(null);  // *** this will center your app ***
		frame.setTitle("成绩单自动生成系统");
		frame.setSize(469,332);
		frame.setVisible(true);
	}
	
	public void newProject(){
		
	    int reply = JOptionPane.showConfirmDialog(null, "<html>导出成绩单前您需要进行四步操作：<br>1. 选取成绩单使用的字体<br>2. 选取空成绩单模板<br>3. 选取成绩单工作簿<br>4. 选择成绩单导出文件夹<br><hr>点击确定以继续操作。</html>", "提示 | Reminder", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {        	
			JFileChooser font = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory().getPath());
			font.setDialogTitle("请选择成绩单字体 | Please select the transcript font");
			FileNameExtensionFilter ttf = new FileNameExtensionFilter("ttf file","ttf");
			font.setFileFilter(ttf);
			if(font.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				FONT = font.getSelectedFile().getPath();
			
				JFileChooser template = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory().getPath());
				template.setDialogTitle("请选择成绩单模板 | Please select the transcript template");
				FileNameExtensionFilter pdf = new FileNameExtensionFilter("PDF file","pdf");
				template.setFileFilter(pdf);
				if(template.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					PATH = template.getSelectedFile().getPath();
							
					JFileChooser fchooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory().getPath());
					fchooser.setDialogTitle("请选择成绩工作簿 | Please select the workbook that stores grades");
					FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel file","xlsx");
					fchooser.setFileFilter(filter);
					if(fchooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
						SRC = fchooser.getSelectedFile().getPath();
						
						JFileChooser fchooser2 = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory().getPath());
						fchooser2.setDialogTitle("请选择成绩单导出文件夹 | Please select the directory for exporting transcripts");
						fchooser2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						fchooser2.setAcceptAllFileFilterUsed(false);
						if(fchooser2.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
							DEST = fchooser2.getSelectedFile().getPath();
							
							Transcript a = new Transcript(SRC, DEST, PATH, FONT);
							if(a.importExcel()) {
								for(Student s: a.getStudentList()) {
									if(s.check()) {
										if(!a.generateTranscript(s)){
											JOptionPane.showMessageDialog(frame,
													"<html>Student: " + s.getID() + " - " + s.getName() + "<hr>导出该学生成绩单时发生未知错误<br>Exception occurred while exporting the transcript for this student.</html>",
												    "Exception",
												    JOptionPane.ERROR_MESSAGE);
										}
									} else {
										JOptionPane.showMessageDialog(frame,
												"<html>Student: " + s.getID() + " - " + s.getName() + "<hr>该学生成绩格式错误，请检查成绩工作簿。<br>This student's grades failed to pass quality check. Please check workbook. </html>",
											    "Exception",
											    JOptionPane.ERROR_MESSAGE);
									}
								}
								JOptionPane.showMessageDialog(frame,
									    "<html>成绩单导出成功<br>All Transcripts is exported successfully.</html>");
							} else {
								JOptionPane.showMessageDialog(frame,
										"<html>读取成绩工作簿时出现错误<br>Exception occurred while importing Excel file.</html>",
									    "Exception",
									    JOptionPane.ERROR_MESSAGE);
							}
							
						}
						
					}
				}
			}
        }
	}
}
