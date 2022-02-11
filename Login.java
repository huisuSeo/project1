package aproject1;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Login extends JFrame implements ActionListener, MouseListener {
	JButton jbtnLogin;
	JTextField jtfID;
	JPasswordField jtfPasssword;

	public JTextField getJtfID() {
		return jtfID;
	}//getJtfID

	public Login() {
		super("�α��� â");
		JLabel jlbTitle = new JLabel("�α���");
		JLabel jlbInform = new JLabel("�����ϱ� ���� �����۾��� �ʿ��մϴ�.");

		jtfID = new JTextField("ID");
		jtfPasssword = new JPasswordField("PASSWORD");

		jbtnLogin = new JButton("�α���");
		setLayout(null);

		add(jlbTitle);
		jlbTitle.setFont(new Font("���� ���", Font.BOLD, 35));
		jlbTitle.setBounds(30, 25, 150, 50);

		add(jlbInform);
		jlbInform.setFont(new Font("���� ���", Font.PLAIN, 12));
		jlbInform.setBounds(30, 80, 250, 30);
		jtfPasssword.setEchoChar('*');
		add(jtfID);
		add(jtfPasssword);
		jtfID.setBounds(30, 130, 275, 50);
		jtfPasssword.setBounds(30, 190, 275, 50);

		add(jbtnLogin);
		jbtnLogin.setBounds(30, 270, 275, 50);

		jtfID.addMouseListener(this);
		jtfPasssword.addMouseListener(this);
		jbtnLogin.addActionListener(this);

		setBounds(500, 300, 350, 400);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	} // Login

	public static void main(String[] args) {
		new Login();
	} // main

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent ae) {

		Map<String, String> map = new HashMap<String, String>();

		map.put("admin", "1234");
		map.put("root", "1111");
		map.put("administrator", "12345");

		String ID = jtfID.getText();
		String Password = jtfPasssword.getText();

		if (map.containsKey(ID)) {

			if (map.get(ID).equals(Password)) {
				new ProCase(map.get(ID));

				dispose();

			} else {
				JOptionPane.showMessageDialog(this, "�α��� ������ Ʋ�Ƚ��ϴ�.", "�α��� ����", JOptionPane.WARNING_MESSAGE);
			} // end else

		} else {
			JOptionPane.showMessageDialog(this, "�α��� ������ Ʋ�Ƚ��ϴ�.", "�α��� ����", JOptionPane.WARNING_MESSAGE);
		} // end else

	} // actionPerformed

	@Override
	public void mouseClicked(MouseEvent me) {

		if (me.getSource() == jtfID) {
			jtfID.setText("");
		} // end if

		if (me.getSource() == jtfPasssword) {
			jtfPasssword.setText("");
		} // end if

	} // mouseClicked

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	

} // class