package com.ahmedosman.tripplanner;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.AlarmManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class AddTrip extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private Calendar calendar;
    private int year, month, day;
    private int CalendarHour, CalendarMinute;
    private String timeFormat;
    private TimePickerDialog timepickerdialog;
    private TextInputLayout usernameWrapper;
    private EditText title;
    private Button timeBtn;
    private Button dateBtn;
    private Button noteBtn;
    private Button picBtn;
    private Trip trip;
    private int reminderValue;
    private int PICK_IMAGE_REQUEST = 1;
    private ImageView selectedImg;
    private ArrayList<String> notes;
    private ListView noteList;
    private static final String LOG_TAG = "MainActivity";
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private AutoCompleteTextView startPoint;
    private AutoCompleteTextView endPoint;
    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        noteBtn = (Button) findViewById(R.id.noteBtn);
        timeBtn = (Button) findViewById(R.id.timeBtn);
        dateBtn = (Button) findViewById(R.id.dateBtn);
        picBtn = (Button) findViewById(R.id.picBtn);
        selectedImg = (ImageView) findViewById(R.id.selectedImg);
        spinner = (Spinner) findViewById(R.id.spinner);
        startPoint = (AutoCompleteTextView) findViewById(R.id.startPoint);
        endPoint = (AutoCompleteTextView) findViewById(R.id.endPoint);
        usernameWrapper = (TextInputLayout) findViewById(R.id.titleWrapper);
        usernameWrapper.setHint("Username");
        title = (EditText) findViewById(R.id.title);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.reminder_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);
        mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(Places.GEO_DATA_API).enableAutoManage(this, GOOGLE_API_CLIENT_ID, this).addConnectionCallbacks(this).build();
        startPoint.setThreshold(3);
        endPoint.setThreshold(3);
        reminderValue = 0;
        startPoint.setOnItemClickListener(mAutocompleteClickListener);
        endPoint.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1, BOUNDS_MOUNTAIN_VIEW, null);
        startPoint.setAdapter(mPlaceArrayAdapter);
        endPoint.setAdapter(mPlaceArrayAdapter);

        if(getIntent().getSerializableExtra("") != null){
            trip = (Trip)getIntent().getSerializableExtra("");
            title.setText(trip.getTripName());
            startPoint.setText(trip.getStartPoint());
            endPoint.setText(trip.getEndPoint());
            calendar.set(trip.getYear(),trip.getMonth(),trip.getDay(),trip.getMinutes(),trip.getHours());
            //trip.getTimeFormate(timeFormat);
            spinner.setId(trip.getReminder());
            String[] notes = {"dddd","ddddd"};
            //trip.getNotes(notes);
            //trip.getRoundTrip(false);
            //trip.getTripImage();
        }else{
            trip = new Trip();
        }

        trip.setTripName(title.getText().toString());
        trip.setStartPoint(startPoint.getText().toString());
        trip.setEndPoint(endPoint.getText().toString());
        trip.setStatus("upcoming");
        trip.setDay(day);
        trip.setMonth(month);
        trip.setYear(year);
        trip.setHours(CalendarHour);
        trip.setMinutes(CalendarMinute);
        trip.setTimeFormate(timeFormat);
        trip.setReminder(reminderValue);
        String[] notes = {"dddd","ddddd"};
        trip.setNotes(notes);
        trip.setRoundTrip(false);
        //trip.setTripImage();

        //name with date and hour and minute
        /*Intent i = new Intent("in.wptrafficanalyzer.servicealarmdemo.demoactivity");
        PendingIntent operation = PendingIntent.getActivity(getBaseContext(), 0, i, Intent.FLAG_ACTIVITY_NEW_TASK);
        AlarmManagerCompat alarmManager = (AlarmManagerCompat) getBaseContext().getSystemService(ALARM_SERVICE);
        DatePicker dpDate = (DatePicker) findViewById(R.id.dp_date);
        TimePicker tpTime = (TimePicker) findViewById(R.id.tp_time);
        int year = dpDate.getYear();
        int month = dpDate.getMonth();
        int day = dpDate.getDayOfMonth();
        int hour = tpTime.getCurrentHour();
        int minute = tpTime.getCurrentMinute();
        GregorianCalendar calendar = new GregorianCalendar(year, month, day, hour, minute);
        long alarm_time = calendar.getTimeInMillis();
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarm_time, operation);
        Toast.makeText(getBaseContext(), "Alarm is set successfully", Toast.LENGTH_SHORT).show();*/

        //cancel button
        //save button
        //round trip



        timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);
                timepickerdialog = new TimePickerDialog(AddTrip.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                if (hourOfDay == 0) {
                                    hourOfDay += 12;
                                    timeFormat = "AM";
                                } else if (hourOfDay == 12) {
                                    timeFormat = "PM";
                                } else if (hourOfDay > 12) {
                                    hourOfDay -= 12;
                                    timeFormat = "PM";
                                } else {
                                    timeFormat = "AM";
                                }
                                Toast.makeText(getApplicationContext(), hourOfDay + ":" + minute + timeFormat, Toast.LENGTH_SHORT).show();
                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();
            }
        });

        picBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(999);
                Toast.makeText(getApplicationContext(), "ca", Toast.LENGTH_SHORT)
                        .show();
            }
        });

    }

    @Override
    public void onConnected(Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i(LOG_TAG, "Google Places API connected.");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(LOG_TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
        Log.e(LOG_TAG, "Google Places API connection suspended.");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                selectedImg.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i(LOG_TAG, "Selected: " + item.description);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            Log.i(LOG_TAG, "Fetching details for ID: " + item.placeId);
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e(LOG_TAG, "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }
            
            final Place place = places.get(0);
            CharSequence attributions = places.getAttributions();

            Log.i(LOG_TAG, "Place Dataaaaaaaaaaaaaaaa");
            Log.i(LOG_TAG, "placeName: " + Html.fromHtml(place.getName() + ""));
            Log.i(LOG_TAG, "address: " + Html.fromHtml(place.getAddress() + ""));
            Log.i(LOG_TAG, "id: " + Html.fromHtml(place.getId() + ""));
            Log.i(LOG_TAG, "phonenumber: " + Html.fromHtml(place.getPhoneNumber() + ""));
            Log.i(LOG_TAG, "website: " + place.getWebsiteUri() + "");
            if (attributions != null) {
                Log.i(LOG_TAG, "attributions: " + Html.fromHtml(attributions.toString()));
            }
        }
    };

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        Toast.makeText(getApplicationContext(), String.valueOf(spinner.getSelectedItem()),
                Toast.LENGTH_SHORT)
                .show();
        reminderValue = pos*5 ;
    }

    private void showDate(int year, int month, int day) {
        Toast.makeText(getApplicationContext(), new StringBuilder().append(day).append("/")
                        .append(month).append("/").append(year),
                Toast.LENGTH_SHORT)
                .show();
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    showDate(arg1, arg2 + 1, arg3);
                }
            };

    public void setPic(View view) {
        ImageView imgageView = (ImageView) view;
        Drawable drawable = imgageView.getDrawable();
        if (drawable != null) {
            selectedImg.setImageDrawable(drawable);
        }
    }
}





















