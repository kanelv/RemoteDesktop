/**
 *
 * Author le van cuong - 2017
 * Email: cuonglvrepvn@gmail.com
 */

package remoteserver;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *  
 * Lớp vào phía Server
 */
public class ServerInit {

    // Frame chính của Server
    private JFrame frame = new JFrame();
    // JDesktopPane chưa chính chứa tất cả màn hình của các Client được kết nối
    private JDesktopPane desktop = new JDesktopPane();

    public static void main(String args[]) {
        String port = JOptionPane.showInputDialog("Please enter listening port");
        new ServerInit().initialize(Integer.parseInt(port));
    }

    public void initialize(int port) {

        try {
            ServerSocket sc = new ServerSocket(port);
            // Hiển thị Server GUI
            drawGUI();
            // Lắng nghe và chấp nhận kết nối
            while (true) {
                // Đợi cho đến khi có kết nối
                Socket client = sc.accept();
                System.out.println("New client Connected to the server");
                // Mỗi Client tạo một ClientHandler
                new Handler(client, desktop);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Vẽ ra server GUI chính
     */
    public void drawGUI() {
        frame.add(desktop, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Hiển thị Frame ở trạng thái tối da
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
}
