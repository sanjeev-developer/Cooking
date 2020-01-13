package com.saavor.user.Classes;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;

public class GPSTracker extends Service implements LocationListener {

    // Get Class Name
    public static String TAG = GPSTracker.class.getName();

    public final Context mContext;

    // flag for GPS Status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS Tracking is enabled
    boolean isGPSTrackingEnabled = false;

    public Location location;
    public double latitude;
    public double longitude;

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    TextView tvLongitude , tvLatitude;
    Activity activity;

    // How many Geocoder should return our GPSTracker
    public int geocoderMaxResults = 1;

    // The minimum distance to change updates in meters
    public static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    public static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;

    // Store LocationManager.GPS_PROVIDER or LocationManager.NETWORK_PROVIDER information
    public String provider_info;

    public GPSTracker(Context context, Activity activity, double latitude, double longitude, Location location)
    {
        this.mContext = context;
        this.activity=activity;
        this.latitude=latitude;
        this.longitude=longitude;
        this.location=location;

//        // check availability of play services
//        if (checkPlayServices()) {
//            // Building the GoogleApi client
//            buildGoogleApiClient();
//        }
//        if (mGoogleApiClient != null) {
//            mGoogleApiClient.connect();
//        }
    }



    /**
     * Get list of address by latitude and longitude
     * @return null or List<Address>
     */
    public List<Address> getGeocoderAddress(Context context) {
        if (location != null) {

            Geocoder geocoder = new Geocoder(context, Locale.ENGLISH);

            try {
                /**
                 * Geocoder.getFromLocation - Returns an array of Addresses
                 * that are known to describe the area immediately surrounding the given latitude and longitude.
                 */
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, this.geocoderMaxResults);

                return addresses;
            } catch (IOException e) {
                //e.printStackTrace();
                Log.e(TAG, "Impossible to connect to Geocoder", e);
            }
        }

        return null;
    }

    /**
     * Try to get AddressLine
     * @return null or addressLine
     */
    public String getAddressLine(Context context) {
        List<Address> addresses = getGeocoderAddress(context);

        if (addresses != null && addresses.size() > 0) {
            Address address = addresses.get(0);
            String addressLine = address.getAddressLine(0);

            return addressLine;
        } else {
            return null;
        }
    }

    /**
     * Try to get Locality
     * @return null or locality
     */
    public String getLocality(Context context) {
        List<Address> addresses = getGeocoderAddress(context);

        if (addresses != null && addresses.size() > 0) {
            Address address = addresses.get(0);
            String locality = address.getLocality();

            return locality;
        }
        else {
            return null;
        }
    }

    /**
     * Try to get Postal Code
     * @return null or postalCode
     */
    public String getPostalCode(Context context) {
        List<Address> addresses = getGeocoderAddress(context);

        if (addresses != null && addresses.size() > 0) {
            Address address = addresses.get(0);
            String postalCode = address.getPostalCode();

            return postalCode;
        } else {
            return null;
        }
    }

    /**
     * Try to get State
     * @return null or State
     */
    public String getState(Context context) {
        List<Address> addresses = getGeocoderAddress(context);

        if (addresses != null && addresses.size() > 0) {
            Address address = addresses.get(0);
            String state = address.getAdminArea();

            return state;
        } else {
            return null;
        }
    }

    /**
     * Try to get CountryName
     * @return null or postalCode
     */
    public String getCountryName(Context context) {
        List<Address> addresses = getGeocoderAddress(context);
        if (addresses != null && addresses.size() > 0) {
            Address address = addresses.get(0);
            String countryName = address.getCountryName();
            return countryName;

        } else {
            return null;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int resultCode = googleAPI.isGooglePlayServicesAvailable(activity);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(resultCode)) {
                googleAPI.getErrorDialog(activity, resultCode,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(this.getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                activity.finish();
            }
            return false;
        }
        return true;
    }

    /**
     * Creating google api client object
     */
//    protected synchronized void buildGoogleApiClient() {
//        mGoogleApiClient = new GoogleApiClient.Builder(activity)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API).build();
//    }

//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//        if (mLastLocation != null) {
//
//            // Setting Current Longitude
//            longitude=mLastLocation.getLongitude();
//            System.out.println(">>>>"+longitude);
//          //  tvLongitude.setText("Longitude:" + mLastLocation.getLongitude());
//
//            // Setting Current Latitude
//            latitude=mLastLocation.getLatitude();
//            System.out.println(">>>>"+latitude);
//
//            //tvLatitude.setText("Latitude:" + mLastLocation.getLatitude() );
//        }
//
//        else
//        {
//            // Setting Current Longitude
//            //tvLongitude.setText("nhi mila");
//
//            // Setting Current Latitude
//           // tvLatitude.setText("nhi mila");
//        }
//    }
//
//
//
//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }
}