package texteditor;

import javax.swing.SwingUtilities;

public class Test {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(TextEditor::new);
	}

}
