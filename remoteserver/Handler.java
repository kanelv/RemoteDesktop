/**
 *
 * Author le van cuong - 2017
 * Email: cuonglvrepvn@gmail.com
 */
package remoteserver;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

class Handler extends Thread {
    // JDesktopPane chưa chính chứa tất cả thành phần của Client được kết nối
    private JDesktopPane desktop = null;
    // Socket của Client được kết nối
    private Socket cSocket = null;
    private JInternalFrame interFrame = new JInternalFrame("Client Screen",
            true, true, true);
    private JPanel cPanel = new JPanel();

    public Handler(Socket cSocket, JDesktopPane desktop) {
        this.cSocket = cSocket;
        this.desktop = desktop;
        start();
    }

    /*
     * Vẽ GUI mỗi Client kết nối đến
     */
    public void drawGUI() {
        interFrame.setLayout(new BorderLayout());
        interFrame.getContentPane().add(cPanel, BorderLayout.CENTER);
        interFrame.setSize(100, 100);
        desktop.add(interFrame);
        try {
            // Hiển thị InternalFrame tối đa
            interFrame.setMaximum(true);
        } catch (PropertyVetoException ex) {
            ex.printStackTrace();
        }      
        // Cho phép xử lý sự kiện KeyListener
        cPanel.setFocusable(true);
        interFrame.setVisible(true);
    }

    @Override
    public void run() {

        // Đại diện cho kích thước màn hình Client
        Rectangle clientScreenDim = null;
        // Dùng để đọc ảnh màn hình và kích thước màn hình của Client
        ObjectInputStream ois = null;
        // Bắt đầu vẽ GUI
        drawGUI();

        try {
            //Đọc kích thước màn hình Client
            ois = new ObjectInputStream(cSocket.getInputStream());
            clientScreenDim = (Rectangle) ois.readObject();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        // Bắt đầu nhận ảnh chụp màn hình
        new ScreenReciever(ois, cPanel);
        // Bắt đầu gửi sự kiện đến Client
        new CommandsSender(cSocket, cPanel, clientScreenDim);
    }

}
