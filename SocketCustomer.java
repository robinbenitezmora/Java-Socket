import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarning("serial")
public class SocketCustomer extends JFrame implements ActionListener, Runnable {
  JTextArea textArea = new JTextArea();
  JScrollPane jp = JScrollPane();
  JTextField inputText = new JTextField();
  JMenuBar menuBar = new JMenuBar();

  Socket sk;
  BufferedReader br;
  PrintWriter pw;

 public SocketCustomer() {
  super("Socket客户端");
  setFont(new Font("Arial Black", Font.PLAIN, 12));
  setForeground(new Color(0, 0, 60));
  setBackground(new Color(60, 0, 0));
  textArea.setToolTipText("Tell us your History");
  textArea.setForeground(new Color(60, 0, 0));
  textArea.setFont(new Font("Arial Black", Font.PLAIN, 12));
  textArea.setEditable(false);
  
  getContentPane().add(jp, "Center");
  inputText.setText("Enter your message: ");
  inputText.setToolTipText("Enter your Message");
  inputText.setForeground(new Color(0, 0, 0));
  inputText.setFont(new Font("Tahoma", Font.BOLD, 12));
  inputText.setBackground(new Color(250, 250, 250));

  
  getContentPane().add(inputText, "South");
  setSize(350, 450)
  setVisible(true);
  
  inputText.requestFocus();
  setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

 }

  private JScrollPane JScrollPane() {
    return null;
  }

  public void serverConnection() {
    try {
      String IP = JOptionPane.showInputDialog(this, "Please enter a server IP", JOptionPane.INFORMATION_MESSAGE);
      while (getName().length() > 7) {
        setName(
            JOptionPane.showInputDialog(this, "Please enter a name less than 7 characters",
                JOptionPane.INFORMATION_MESSAGE));
      }

      br = new BufferedReader(new InputStreamReader(sk.getInputStream()));
      pw = new PrintWriter(sk.getOutputStream(), true);
      pw.println(getName());

      new Thread(this).start();
    } catch (Exception e) {
      System.out.println(e + " Socket Connection error");
    }
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == inputText) {
      pw.println(inputText.getText());
      inputText.setText("");
    }
  }

  public void run() {
    try {
      while (true) {
        String message = br.readLine();
        textArea.append(message + "\n");
      }
    } catch (Exception e) {
      System.out.println(e + " Socket run error");
    }
  }

  public static void main(String[] args) {
    new SocketCustomer();
  }
}
