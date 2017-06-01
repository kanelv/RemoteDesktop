/**
 *
 * Author le van cuong - 2017
 * Email: cuonglvrepvn@gmail.com
 */

package remoteserver;

/**
 * 
 * Dùng để biểu diễn lệnh có thể gửi bởi Server
 */
public enum EnumCommands {
    PRESS_MOUSE(-1),
    RELEASE_MOUSE(-2),
    PRESS_KEY(-3),
    RELEASE_KEY(-4),
    MOVE_MOUSE(-5);

    private int abbrev;

    EnumCommands(int abbrev){
        this.abbrev = abbrev;
    }

    public int getAbbrev(){
        return abbrev;
    }
}
