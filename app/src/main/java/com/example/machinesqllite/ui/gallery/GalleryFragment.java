package com.example.machinesqllite.ui.gallery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.machinesqllite.databinding.FragmentGalleryBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.android.material.slider.LabelFormatter;

import java.util.ArrayList;
import java.util.List;

import classes.Machine;
import classes.Salle;
import service.MachineService;
import service.SalleService;

public class GalleryFragment extends Fragment  {

    private FragmentGalleryBinding binding;
    private MachineService ms;
    private SalleService ss;
    String[] labels;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        BarChart chart = binding.chart;
        ms=MachineService.getInstance(getContext());
         List<Machine>machines= ms.findBySalle(getContext());
        ArrayList<BarEntry> entries = new ArrayList<>();
          for(int i=0;i<machines.size();i++){
              entries.add(new BarEntry(i,machines.get(i).getCount()));
          }
        Log.d("entries", String.valueOf(entries.size()));
        BarDataSet dataset = new BarDataSet(entries, "nombre machine par salle");
         labels = new String[machines.size()];
        for(int i=0;i<machines.size();i++){
           labels[i]=machines.get(i).getS().getRef();
        }

        BarData data = new BarData( dataset);
        chart.getXAxis().setValueFormatter(new LabelFormatter(labels));
        chart.setData(data);
        return root;
    }
    public class LabelFormatter implements IAxisValueFormatter {
        private final String[] mLabels;

        public LabelFormatter(String[] labels) {
            mLabels = labels;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            if (mLabels.length > (int) value)  return mLabels[(int) value];
            else return  "";
        }
    }


}