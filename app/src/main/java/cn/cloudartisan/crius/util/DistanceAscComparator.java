
package cn.cloudartisan.crius.util;

import cn.cloudartisan.crius.bean.Friend;
import cn.cloudartisan.crius.bean.User;

import java.util.Comparator;

public class DistanceAscComparator implements Comparator<Friend> {
    User _self;
    
    public int compare(Friend paramFriend1, Friend paramFriend2) {
        double d1 = 0.0D;
        double d2 = 0.0D;
        try
        {
            d1 = AppTools.getDistance(Double.parseDouble(this._self.getLongitude()), Double.parseDouble(this._self.getLatitude()),
                    Double.parseDouble(paramFriend1.longitude), Double.parseDouble(paramFriend1.latitude));
            d2 = AppTools.getDistance(Double.parseDouble(this._self.getLongitude()),
                    Double.parseDouble(this._self.getLatitude()), Double.parseDouble(paramFriend2.longitude), Double.parseDouble(paramFriend2.latitude));

        }
        catch (Exception ex)
        {

                ex.printStackTrace();

        }

        return (int)((d1 - d2) * 100.0D);
    }
    
    public DistanceAscComparator(User self) {
        _self = self;
    }
}
