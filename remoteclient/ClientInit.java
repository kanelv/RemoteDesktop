/**
 *
 * Author le van cuong - 2017
 * Email: cuonglvrepvn@gmail.com
 */

package remoteclient;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * Lớp vào phía Client
 */
public class ClientInit {

    // Khởi tạo một Socket đế kết nối tới Server
    Socket socket = null;

    public static void main(String[] args) {
        String ip = JOptionPane.showInputDialog("Please enter Server IP");
        String port = JOptionPane.showInputDialog("Please enter Server port");        
        new ClientInit().initialize(ip, Integer.parseInt(port));
    }

    public void initialize(String ip, int port) {

        // Dùng để chụp ảnh màn hình
        Robot robot = null;
        // Đại diện kích thước màn hình
        Rectangle rectangle = null; //Used to represent screen dimensions

        try {
            System.out.println("Connecting to server ..........");
            socket = new Socket(ip, port);
            System.out.println("Connection Established.");

            // Lấy màn hình mặc định của thiết bị
            GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gDev = gEnv.getDefaultScreenDevice();

            //Lấy kích thước màn hình
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            rectangle = new Rectangle(dim);

            //Chuẩn bị một đối tượng Robot
            robot = new Robot(gDev);

            //Vẽ client GUI
            drawGUI();
            // ScreenSpyer gửi ảnh chụp màn hình của Client            
            new ScreenSpyer(socket, robot, rectangle);
            // ServerDelegate nhận lệnh từ Server và thực thi chúng            
            new ServerDelegate(socket, robot);
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (AWTException ex) {
            ex.printStackTrace();
        }
    }

    private void drawGUI() {
        JFrame frame = new JFrame("Remote Admin");
        JButton button = new JButton("Terminate");

        frame.setBounds(100, 100, 150, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(button);
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        }
        );
        frame.setVisible(true);
    }
}
