package keepInterface;

import java.util.List;

/**
 * Created by raytine on 2017/4/13.
 */

public interface RequestResult {
    void successfully(String msg, List<Object> objectList);
    void failed(String msg);
}
