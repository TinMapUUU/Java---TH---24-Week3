package texteditor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextEditor extends JFrame implements ActionListener {
	private JTextArea textArea;
	private JFileChooser fileChoose;

	public TextEditor() {
		setTitle("Text Editor");
		setSize(600, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);

		textArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(textArea);
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");

		JMenuItem openItem = new JMenuItem("Open");
		JMenuItem deleteItem = new JMenuItem("Delete");
		JMenuItem copyItem = new JMenuItem("Copy");

		openItem.addActionListener(this);
		deleteItem.addActionListener(this);
		copyItem.addActionListener(this);

		fileMenu.add(openItem);
		fileMenu.add(deleteItem);
		fileMenu.add(copyItem);

		menuBar.add(fileMenu);
		setJMenuBar(menuBar);
		fileChoose = new JFileChooser();       
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		switch (command) {
		case "Open":
			openFile();
			break;
		case "Delete":
			deleteFile();
			break;
		case "Copy":
			copyFile();
			break;
		}

	}

	private void copyFile() {
		int returnVal = fileChoose.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChoose.getSelectedFile();
			try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
				StringBuilder text = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null) {
					text.append(line).append("\n");
				}
				textArea.setText(text.toString());
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

	private void deleteFile() {
		int returnVal = fileChoose.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChoose.getSelectedFile();
			if (file.delete()) {
				JOptionPane.showMessageDialog(this, "File deleted successfully.");
				textArea.setText("");
			} else {
				JOptionPane.showMessageDialog(this, "Failed to delete file.");
			}
		}

	}

	private void openFile() {
		int returnVal = fileChoose.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File sourceFile = fileChoose.getSelectedFile();
			fileChoose.setDialogTitle("Choose destination directory");
			fileChoose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int returnVal2 = fileChoose.showOpenDialog(this);

			if (returnVal2 == JFileChooser.APPROVE_OPTION) {
				File destinationDir = fileChoose.getSelectedFile();
				try {
					File destinationFile = new File(destinationDir.getAbsolutePath() + "/" + sourceFile.getName());
					Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
					JOptionPane.showMessageDialog(this, "File copied successfully.");
				} catch (IOException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(this, "Failed to copy file.");
				}
			}
		}

	}


}
