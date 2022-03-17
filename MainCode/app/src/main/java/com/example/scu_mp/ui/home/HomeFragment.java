package com.example.scu_mp.ui.home;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;


import com.example.scu_mp.MapsActivity;
import com.example.scu_mp.R;
import com.example.scu_mp.databinding.FragmentHomeBinding;
import com.example.scu_mp.listing;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//import com.google.android.libraries.places.compat.Places;
//import com.google.android.gms.location.places.Places;


public class HomeFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener{

    private FragmentHomeBinding binding;
    CardView cvFurniture;
    CardView cvTextbook;
    CardView cvTransportation;
    CardView cvComputer;
    CardView cvSchoolSupplies;
    CardView cvMiscellaneous;
    ImageView icPlace;
    EditText enteredItem;
    EditText enteredLocation;
    ToggleButton button1;
    ToggleButton button2;
    ToggleButton button3;
    ToggleButton button4;
    ToggleButton button5;
    ToggleButton button6;
    ToggleButton button7;
    ArrayList<ToggleButton> listButton;
    //AutoCompleteTextView searchPlace;
    //private GoogleApiClient mGoogleApiClient;
    private static int AUTOCOMPLETE_REQUEST_CODE = 1;

    private static final String TAG = "HOME_FRAGMENT";

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = inflater.inflate(R.layout.fragment_home,container, false);

        cvFurniture = (CardView)  view.findViewById(R.id.card_view1);
        cvTextbook = (CardView) view.findViewById(R.id.card_view2);
        cvTransportation = (CardView) view.findViewById(R.id.card_view3);
        cvComputer = (CardView) view.findViewById(R.id.card_view5);
        cvSchoolSupplies = (CardView) view.findViewById(R.id.card_view4);
        cvMiscellaneous = (CardView) view.findViewById(R.id.card_view6);
        icPlace = (ImageView) view.findViewById(R.id.icPlace);
        enteredItem = (EditText) view.findViewById(R.id.search_text);
        enteredLocation = (EditText) view.findViewById(R.id.search_place);
        button1 = (ToggleButton) view.findViewById(R.id.button1);
        button2 = (ToggleButton) view.findViewById(R.id.button2);
        button3 = (ToggleButton) view.findViewById(R.id.button3);
        button4 = (ToggleButton) view.findViewById(R.id.button4);
        button5 = (ToggleButton) view.findViewById(R.id.button5);
        button6 = (ToggleButton) view.findViewById(R.id.button6);
        button7 = (ToggleButton) view.findViewById(R.id.button7);
        listButton = new ArrayList<>();
        listButton.add(button7);
        listButton.add(button6);
        listButton.add(button5);
        listButton.add(button4);
        listButton.add(button3);
        listButton.add(button2);
        listButton.add(button1);

        //searchPlace = (AutoCompleteTextView) view.findViewById(R.id.search_place);
/*
        Places.initialize(view.getContext(), "AIzaSyAYu5iVzIR2vJg3JVnMBsq_t8BGZTpbgq8");
        // Create a new PlacesClient instance
        PlacesClient placesClient = Places.createClient(view.getContext());
        // Set the fields to specify which types of place data to
        // return after the user has made a selection.
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);

        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .build(getActivity());
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);

 */

        cvFurniture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), listing.class);
                intent.putExtra("Category", "Furniture");
                startActivity(intent);
            }
        });

        cvTextbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), listing.class);
                intent.putExtra("Category", "Textbooks");
                startActivity(intent);
            }
        });
        cvTransportation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), listing.class);
                intent.putExtra("Category", "Transportation");
                startActivity(intent);
            }
        });
        cvComputer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), listing.class);
                intent.putExtra("Category", "Computers");
                startActivity(intent);
            }
        });
        cvSchoolSupplies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), listing.class);
                intent.putExtra("Category", "School Supplies");
                startActivity(intent);
            }
        });
        cvMiscellaneous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), listing.class);
                intent.putExtra("Category", "Miscellaneous");
                startActivity(intent);
            }
        });

        icPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (enteredItem.getText().toString().equals(""))
                {
                    Toast.makeText(getContext(), "Item Cannot Be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Log.i(TAG, "enteredItem: " + enteredItem.getText().toString());
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                intent.putExtra("Item", enteredItem.getText().toString());
                intent.putExtra("Location", enteredLocation.getText().toString());
                String radButton = anySelectedButtons();
                intent.putExtra("Radius", radButton);
                startActivity(intent);
            }
        });

        enteredItem.setOnEditorActionListener(editorListener);
        enteredLocation.setOnEditorActionListener(editorListener);


        /*button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (button1.isEnabled()) {
                    button1.setEnabled(false);
                    button1.setText("Ok Button is disabled.");
                }
                else
                {
                    button1.setEnabled(true);
                }
            }
        });
*/
        return view;
    }

    private String anySelectedButtons()
    {
        for (ToggleButton x : listButton)
        {
            if (x.isChecked())
            {
                return x.getText().toString();
            }
        }
        return "";
    }

    private TextView.OnEditorActionListener editorListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            switch (actionId) {
                case EditorInfo.IME_ACTION_NEXT:
                    Toast.makeText(getContext(), "Next", Toast.LENGTH_SHORT).show();
                    break;
                case EditorInfo.IME_ACTION_GO:
                    if (!enteredItem.getText().toString().equals("")) {

                        if (enteredLocation.getText().toString().equals("")) {
                            Intent intent = new Intent(getActivity(), listing.class);
                            intent.putExtra("Item", enteredItem.getText().toString());
                            startActivity(intent);
                        }
                        else
                        {
                            Intent intent = new Intent(getActivity(), MapsActivity.class);
                            intent.putExtra("Location", enteredLocation.getText().toString());
                            intent.putExtra("Item", enteredItem.getText().toString());
                            String radButton = anySelectedButtons();
                            intent.putExtra("Radius", radButton);
                            startActivity(intent);
                        }
                    }
                    else
                    {
                        Toast.makeText(getContext(), "Item cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            return false;
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
                Log.i(TAG, "Cancelled");
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}