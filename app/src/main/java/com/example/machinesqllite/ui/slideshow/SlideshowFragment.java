package com.example.machinesqllite.ui.slideshow;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.machinesqllite.MyItemRecyclerViewAdapter2;
import com.example.machinesqllite.databinding.FragmentSlideshowBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import classes.Machine;
import classes.Salle;
import service.MachineService;
import service.SalleService;


public class SlideshowFragment extends Fragment  implements AdapterView.OnItemSelectedListener {
    private EditText nom;
    private Button add;
    private Spinner sp;
    private  Salle s ;
    private EditText id;
    private Button rechercher;
    private TextView res;
    private SalleService ss;
    void clear(){
        nom.setText("");
        id.setText("");
    }
    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        //Insertion des étudiants
        final MachineService es = MachineService.getInstance(getContext());
      sp=binding.spinner;
        nom = binding.nom;
        id = binding.id;
        rechercher = binding.load;
        res = binding.res;
        add = binding.bn;
        ss=new SalleService(getContext());
       List<Salle> salles=ss.findAll();
        String[ ]items=new String[salles.size()];
       for(int i=0;i<items.length;i++)  items[i]=salles.get(i).getRef();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(this);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Insertion des étudiants
                es.create(new Machine(nom.getText().toString(),Double.parseDouble(String.valueOf(id.getText())),s));
                clear();

                //Parcourir la liste des étudiants

            }
        });



        rechercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Machine e = es.findById(Integer.parseInt(id.getText().toString()));
                res.setText(e.getNom()+" "+e.getPrice());
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {

        int getid = parent.getSelectedItemPosition();
        Log.d("valider", String.valueOf(getid));///get the selected element place id
        String getvalue = String.valueOf(parent.getItemAtPosition(i));   // getting the selected element value
       s=ss.findById(getid+1);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}