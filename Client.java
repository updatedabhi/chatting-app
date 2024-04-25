import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

// import javax.swing.border.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

public class Client implements ActionListener {
    JTextField text;
    static JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static DataOutputStream dout;

    static JFrame f = new JFrame();

    Client() {

        f.setLayout(null);

        // Fist yellow panel.
        JPanel p1 = new JPanel();
        p1.setBackground(Color.YELLOW);
        p1.setBounds(0, 0, 450, 70);
        p1.setLayout(null);
        f.add(p1);

        // Arrow icon
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/arrow.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5, 20, 25, 25);
        p1.add(back);

        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });

        // FirstProfile icon
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icon/woman.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40, 10, 50, 50);
        p1.add(profile);

        // code icon
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icon/pro.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel code = new JLabel(i9);
        code.setBounds(300, 20, 30, 30);
        p1.add(code);

        code.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                try {

                    URI uri = new URI("https://www.jdoodle.com/online-java-compiler/");

                    java.awt.Desktop.getDesktop().browse(uri);

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });

        // call icon
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icon/news.png"));
        Image i11 = i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel call = new JLabel(i12);
        call.setBounds(360, 20, 35, 30);
        p1.add(call);

        call.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                try {

                    URI uri = new URI("https://dev.to/t/news");

                    java.awt.Desktop.getDesktop().browse(uri);

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });

        // more icon
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icon/dots.png"));
        Image i14 = i13.getImage().getScaledInstance(20, 25, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel more = new JLabel(i15);
        more.setBounds(420, 20, 20, 25);
        p1.add(more);

        // Naming on jLabel
        JLabel name = new JLabel("Sonam");
        name.setBounds(110, 15, 100, 10);
        name.setForeground(Color.BLACK);
        // name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        p1.add(name);

        // status naming
        JLabel status = new JLabel("online");
        status.setBounds(110, 35, 100, 18);
        status.setForeground(Color.BLACK);
        p1.add(status);

        // text-area panel
        a1 = new JPanel();
        a1.setBounds(5, 75, 440, 570);
        f.add(a1);

        // text-field
        text = new JTextField();
        text.setBounds(5, 655, 310, 40);
        text.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f.add(text);

        // Button
        JButton send = new JButton("send");
        send.setBounds(320, 655, 123, 40);
        send.setBackground(Color.YELLOW);
        send.addActionListener(this);
        f.add(send);

        f.setSize(450, 700);
        f.setLocation(800, 50);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.WHITE);
        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            String out = text.getText();

            JPanel p2 = formatLabel(out);

            a1.setLayout(new BorderLayout());
            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);

            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));
            a1.add(vertical, BorderLayout.PAGE_START);

            dout.writeUTF(out);
            text.setText("");

            f.repaint();
            f.invalidate();
            f.validate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static JPanel formatLabel(String out) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel output = new JLabel("<html><p style = \"width: 150px\">" + out + "</p></html");
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(255, 255, 225));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50));
        panel.add(output);

        // for time
        Calendar ca1 = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:MM");
        JLabel time = new JLabel();
        time.setText(sdf.format(ca1.getTime()));
        panel.add(time);
        return panel;
    }

    public static void main(String[] args) {
        new Client();

        try {
            Socket s = new Socket("127.0.0.1", 6001);
            DataInputStream din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());

            while (true) {
                a1.setLayout(new BorderLayout());

                String msg = din.readUTF();
                JPanel panel = formatLabel(msg);
                JPanel left = new JPanel(new BorderLayout());
                left.add(panel, BorderLayout.LINE_START);
                vertical.add(left);
                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical, BorderLayout.PAGE_START);
                f.validate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
