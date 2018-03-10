package bid.xiaocha.xxt.util;

import android.content.Context;
import android.util.Log;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;

/**
 * Created by 55039 on 2017/11/4.
 */

public class GetPlaceByLatlngUtil{
    public static void getPlaceByLatlng(Context context, LatLng latLng, final OnGetPlaceByLatlngResult onGetPlaceByLatlngResult){
        final GeocodeSearch geocodeSearch = new GeocodeSearch(context);
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                if (i == 1000){
                    onGetPlaceByLatlngResult.getPlaceByLatlngResult(true,regeocodeResult.getRegeocodeAddress().getFormatAddress());
                }else{
                    Log.i("getPlaceResult",i+"");
                    onGetPlaceByLatlngResult.getPlaceByLatlngResult(false,"");
                }
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

            }
        });

        final RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(latLng.latitude,latLng.longitude), 200,GeocodeSearch.AMAP);
        new Thread(new Runnable() {
            @Override
            public void run() {
                geocodeSearch.getFromLocationAsyn(query);
            }
        }).run();
    }

    public interface OnGetPlaceByLatlngResult{
        void getPlaceByLatlngResult(boolean isSuccess,String place);
    }

}
