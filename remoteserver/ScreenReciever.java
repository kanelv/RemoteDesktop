/**
 *
 * Author le van cuong - 2017
 * Email: cuonglvrepvn@gmail.com
 */
package remoteserver;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * ScreenReciever chịu trách nhiệm nhận ảnh chụp màn hình Client và hiển thị nó
 * trong Server. Mỗi Client kết nối có một đối tượng riêng lớp này
 */
class ScreenReciever extends Thread {

    private ObjectInputStream cObjectInputStream = null;
    private JPanel cPanel = null;
    private boolean continueLoop = true;

    public ScreenReciever(ObjectInputStream ois, JPanel p) {
        cObjectInputStream = ois;
        cPanel = p;
        // Bắt đầu Thread và gọi phương thức run()
        start();
    }

    @Override
    public void run() {

        try {

            // Đọc ảnh chụp màn hình của Client sau đó vẽ chúng
            while (continueLoop) {
                //Recieve client screenshot and resize it to the current panel size
                // Nhận ảnh chụp màn hình của Client và thay đổi kích thước nó với kích thước panel hiện tại
                ImageIcon imageIcon = (ImageIcon) cObjectInputStream.readObject();
                System.out.println("New image recieved");
                Image image = imageIcon.getImage();
                // Tạo lại một trường hợp thu nhỏ image với Width và Height của cPanel
                image = image.getScaledInstance(cPanel.getWidth(), cPanel.getHeight(), Image.SCALE_FAST);
                // Vẽ ảnh chụp màn hình nhận được
                Graphics graphics = cPanel.getGraphics();
                graphics.drawImage(image, 0, 0, cPanel.getWidth(), cPanel.getHeight(), cPanel);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
